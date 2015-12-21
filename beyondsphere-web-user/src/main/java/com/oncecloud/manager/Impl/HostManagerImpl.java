package com.oncecloud.manager.Impl;

import java.util.Date;

import javax.annotation.Resource;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.oncecloud.core.HostCore;
import com.oncecloud.entity.OCHost;
import com.oncecloud.entity.OCLog;
import com.oncecloud.entity.OCPool;
import com.oncecloud.log.LogAction;
import com.oncecloud.log.LogObject;
import com.oncecloud.log.LogRecord;
import com.oncecloud.log.LogRole;
import com.oncecloud.manager.HostManager;
import com.oncecloud.message.MessageUtil;
import com.oncecloud.service.HostService;
import com.oncecloud.service.PoolService;
import com.oncecloud.tools.SSH;
import com.oncecloud.util.TimeUtils;

@Component("HostManager")
public class HostManagerImpl implements HostManager {

	@Resource
	private HostCore hostCore;
	@Resource
	private HostService hostService;
	@Resource
	private PoolService poolService;
	@Resource
	private LogRecord logRecord;
	@Resource
	private MessageUtil message;
	
	@Override
	public JSONArray getHostList(int page, int limit, String search) {
		return hostService.getHostList(page, limit, search);
	}
	
	@Override
	public JSONArray createHost(String hostName, String hostPwd,
			String hostDesc, String hostIp, String hostType, int userid) {
		JSONArray ja = new JSONArray();
		String hostUuid = ""; 
		String[] dockerInfos = hostCore.connect(hostIp, hostPwd, hostType).split("\n");
	    int hostCpu  = 0, hostMem = 0;
	    for(int i=0;i < dockerInfos.length;i++){
	    	String info = dockerInfos[i];
	    	int index = info.indexOf(":");
    		if(info.contains("CPUs")){
    			hostCpu = Integer.parseInt(info.substring(index+2));
    		}else if(info.contains("Memory")){
    			hostMem = (int) Double.parseDouble(info.substring(index+2, info.length()-5))*1024; 
    		}else if(info.contains("ID")){
    			hostUuid = info.substring(index+2);
    		}
	    }
		ja = hostService.createDockerHost(hostUuid, hostName, hostPwd, hostDesc, hostIp, hostType, hostCpu, hostMem, userid);
		return ja;
	}

	@Override
	public JSONArray add2Pool(String uuidJsonStr, String hasMaster,
			String poolUuid, int userid) {
		Date startTime = new Date();
		OCPool poolInfo = poolService.getPoolById(poolUuid);
		String pname = poolInfo.getPoolName();
		String poolType = poolInfo.getPoolType();
		JSONArray qaArray = new JSONArray();
		String[] dockerHosts = uuidJsonStr.split(",");
		if (hasMaster.equals("1")) {
			for (int i = 0; i < dockerHosts.length; i++) {
				String hostUuid = dockerHosts[i];
				boolean result = addHostToPool(userid, dockerHosts[i], poolUuid, poolType);
				Date endTime = new Date();
				JSONArray infoArray = new JSONArray();
				infoArray.put(TimeUtils.createLogInfo(
						LogRole.HOST, "host-"
								+ hostUuid.substring(0, 8)));
				infoArray.put(TimeUtils.createLogInfo(
						LogRole.POOL, pname));
				if (result) {
					JSONObject tObj = new JSONObject();
					tObj.put("huid", hostUuid);
					tObj.put("puid", poolUuid);
					tObj.put("pname", TimeUtils.encodeText(pname));
					qaArray.put(tObj);
					OCLog log = logRecord.addSuccessLog(userid, LogObject.HOST, 
							LogAction.JOIN, infoArray.toString(), startTime, endTime); 
					message.pushSuccess(userid, log.toString());
				} else {
					OCLog log = logRecord.addFailedLog(userid, LogObject.HOST, 
							LogAction.JOIN, infoArray.toString(), startTime, endTime); 
					message.pushError(userid, log.toString());
				}
			}
		} else {
			String masterUuid = dockerHosts[0];
			boolean result = createDockerPool(masterUuid, poolUuid);
			Date endTime = new Date();
			JSONArray infoArray = new JSONArray();
			infoArray.put(TimeUtils.createLogInfo(
					LogRole.HOST,
					"host-" + masterUuid.substring(0, 8)));
			infoArray.put(TimeUtils.createLogInfo(
					LogRole.POOL, pname));
			if (result) {
				OCLog log = logRecord.addSuccessLog(userid, LogObject.MASTERNODE, 
						LogAction.JOIN, infoArray.toString(), startTime, endTime);
				message.pushSuccess(userid, log.toString());
				JSONObject tObj1 = new JSONObject();
				tObj1.put("huid", dockerHosts[0]);
				tObj1.put("puid", poolUuid);
				tObj1.put("pname", TimeUtils.encodeText(pname));
				qaArray.put(tObj1);
				for (int i = 1; i < dockerHosts.length; i++) {
					Date sTime = new Date();
					boolean addResult = addHostToPool(userid, dockerHosts[i], poolUuid, poolType);
					Date eTime = new Date();
					JSONArray infoArray1 = new JSONArray();
					infoArray1.put(TimeUtils.createLogInfo(
							LogRole.HOST, "host-"
									+ masterUuid.substring(0, 8)));
					infoArray1.put(TimeUtils.createLogInfo(
							LogRole.POOL, pname));
					if (addResult) {
						JSONObject tObj = new JSONObject();
						tObj.put("huid", dockerHosts[i]);
						tObj.put("puid", poolUuid);
						tObj.put("pname", TimeUtils.encodeText(pname));
						qaArray.put(tObj);
						OCLog log1 = logRecord.addSuccessLog(userid, LogObject.HOST, 
								LogAction.JOIN, infoArray.toString(), sTime, eTime); 
						message.pushSuccess(userid, log1.toString());
					} else {
						OCLog log1 = logRecord.addFailedLog(userid, LogObject.HOST, 
								LogAction.JOIN, infoArray.toString(), startTime, endTime);
						message.pushError(userid, log1.toString());
					}
				}
			}
		}
		return qaArray;
	}

	@Override
	public JSONArray queryAddress(String address) {
		return hostService.queryAddress(address);
	}
	
	@Override
	public JSONArray getTablePool(String uuidJsonStr) {
		return hostService.getTablePool(uuidJsonStr);
	}
	
	private boolean addHostToPool(int userId, String hostUuid, String poolUuid, String poolType) {
		boolean result = false;
		if (hostService.isSameType(hostUuid, poolUuid)) {
			OCHost host = hostService.getHostById(hostUuid);
			OCHost masterHost = hostService.getMasterOfPool(poolUuid);
			/*SSH ssh = hostCore.getSshConnection(host.getHostIP(), host.getHostPwd(), host.getHostType());
			if (ssh.connect()) {
				try {
					StringBuffer buffer = new StringBuffer();
					buffer.append("systemctl stop docker.service; \n");
					buffer.append("docker -H tcp://0.0.0.0:2375 --insecure-registry "+masterHost.getHostIP()+":5000 -d");
					//System.out.println(ssh.executeWithResult(buffer.toString()));
					//ssh.Command(buffer.toString());
					ssh.executeWithResult(buffer.toString(), 500);
				} catch (Exception e) {
					e.printStackTrace();
				}finally{
					ssh.close();
				}
			}*/
			SSH masterSsh = hostCore.getSshConnection(masterHost.getHostIP(), masterHost.getHostPwd(), poolType);
			if (masterSsh.connect()) {
				try {
					result = masterSsh.execute("echo "+ host.getHostIP()+":2375 >> /opt/cluster \n");
				} catch (Exception e) {
					e.printStackTrace();
				}finally{
					masterSsh.close();
				}
			}
			if (result) {
				hostService.setPool(hostUuid, poolUuid);
			}
		}else{
			message.pushError(userId, "资源池和主机类型不同，不能将主机添加到资源池！");
		}
		return result;
	}
	
	private boolean createDockerPool(String hostUuid, String poolUuid){
		boolean result = false;
		OCHost targetHost = hostService.getHostById(hostUuid);
		OCPool pool = poolService.getPoolById(poolUuid);
		SSH ssh = hostCore.getSshConnection(targetHost.getHostIP(), targetHost.getHostPwd(), targetHost.getHostType());
		if (ssh.connect()) {
			try {
				StringBuffer buffer = new StringBuffer();
				buffer.append("touch /opt/cluster; \n");
				buffer.append("swarm manage -H tcp://"+targetHost.getHostIP()+":2375 file:///opt/cluster & \n");
				result = ssh.execute(buffer.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				ssh.close();
			}
		}
		if (targetHost.getPoolUuid() == null && pool != null) {
			targetHost.setPoolUuid(pool.getPoolUuid());
			pool.setPoolMaster(targetHost.getHostUuid());
			result = hostService.updateHost(targetHost);
			result = poolService.updatePool(pool);
		}
		return result;
	}

}
