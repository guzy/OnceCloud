/**
 * @author hty
 * @time 上午11:05:48
 * @date 2014年12月9日
 */
package com.beyondsphere.manager.Impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.Xen.API.Host;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.beyondsphere.manager.HostManager;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.oncecloud.constants.SystemConstant;
import com.oncecloud.core.HostCore;
import com.oncecloud.core.PoolCore;
import com.oncecloud.entity.OCHost;
import com.oncecloud.entity.OCLog;
import com.oncecloud.entity.OCPool;
import com.oncecloud.entity.Storage;
import com.oncecloud.log.LogAction;
import com.oncecloud.log.LogObject;
import com.oncecloud.log.LogRecord;
import com.oncecloud.log.LogRole;
import com.oncecloud.message.MessageUtil;
import com.oncecloud.service.HostService;
import com.oncecloud.service.ImageService;
import com.oncecloud.service.PoolService;
import com.oncecloud.service.SRService;
import com.oncecloud.service.VMService;
import com.oncecloud.tools.SSH;
import com.oncecloud.util.TimeUtils;

@Component("HostManager")
public class HostManagerImpl implements HostManager {
	
	private final static long MB = 1024 * 1024;

	@Resource
	private ImageService imageService ;
	@Resource
	private PoolService poolService;
	@Resource
	private HostService hostService;
	@Resource
	private SRService srService;
	@Resource
	private PoolCore poolCore;
	@Resource
	private HostCore hostCore;
	@Resource
	private VMService vmService;
	@Resource
	private LogRecord logRecord;
	@Resource
	private MessageUtil message;

	public JSONArray createHost(String hostName, String hostPwd,
			String hostDesc, String hostIp, String hostType, int userid) {
//		if(validHost(hostIp)) {
//			String hostUuid = hostCore.connectToHost(hostIp, hostPwd, hostType);
//			return hostService.createHost(Utilities.toNormalUUID(hostUuid), hostName, hostPwd, hostDesc, hostIp, hostType, userid);
//		}
//		
//		return null;
//		//李宁提交的，目前有冲突暂时注释
		JSONArray ja = new JSONArray();
		
		String hostUuid = ""; 
		if(SystemConstant.BEUONDCLOUD.equals(hostType)){
			hostUuid = hostCore.connect(hostIp, hostPwd, hostType).trim();
			ja = hostService.createHost(hostUuid, hostName, hostPwd, hostDesc, hostIp, hostType, userid);
		}else if(SystemConstant.VSPHERE.equals(hostType)){
			ja = hostService.createVSphereHost(hostUuid, hostName, hostPwd, hostDesc, hostIp, hostType, userid);
		}else if(SystemConstant.DOCKER.equals(hostType)){
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
		}
		return ja;
	}

	public JSONArray getHostList(int page, int limit, String search) {
		return hostService.getHostList(page, limit, search);
	}

	public JSONArray getHostLoadList(int page, int limit, String searchStr,
			String srUuid) {
		return hostService.getHostLoadList(page, limit, searchStr, srUuid);
	}

	public JSONArray getSrOfHost(String hostUuid) {
		return hostService.getSrOfHost(hostUuid);
	}

	public JSONObject unbindSr(String hostUuid, String srUuid, int userid) {
		boolean result = false;
		JSONObject jo = new JSONObject();
		String poolUuid = hostService.getHostById(hostUuid).getPoolUuid();
		//OCHost master = hostService.getMasterOfPool(poolUuid); 
		if (poolUuid != null) {
			message.pushWarning(userid, "请先做离池操作，再卸载存储！");
		}else {
			OCHost host = hostService.getHostById(hostUuid);
			Storage storage = srService.getStorage(srUuid);
			if (hostCore.unBindStorage(host, storage)) {
				hostService.unbindSr(hostUuid, srUuid, userid);
				result = true;
			}else {
				message.pushError(userid, "卸载存储失败");
			}
		}
		jo.put("result", result);
		return jo;
	}

	public JSONArray isSameSr(String uuidJsonStr) {
		return hostService.isSameSr(uuidJsonStr);
	}

	public JSONArray getTablePool(String uuidJsonStr) {
		return hostService.getTablePool(uuidJsonStr);
	}

	public JSONArray deleteHost(String hostId, String hostName, int userid) {
		return hostService.deleteHost(hostId, hostName, userid);
	}

	public JSONArray queryAddress(String address) {
		return hostService.queryAddress(address);
	}

	public JSONArray add2Pool(String uuidJsonStr, String hasMaster,
			String poolUuid, int userid) {
		Date startTime = new Date();
		OCPool poolInfo = poolService.getPoolById(poolUuid);
		String pname = poolInfo.getPoolName();
		String poolType = poolInfo.getPoolType();
		JSONArray qaArray = new JSONArray();
		if (poolType.equals(SystemConstant.BEUONDCLOUD)) {
			JsonParser jp = new JsonParser();
			JsonElement je = jp.parse(uuidJsonStr);
			JsonArray ja = je.getAsJsonArray();
			if (hasMaster.equals("1")) {
				for (int i = 0; i < ja.size(); i++) {
					String hostUuid = ja.get(i).getAsString();
					boolean result = addHostToPool(userid, hostUuid, poolUuid, poolType);
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
				String masterUuid = ja.get(0).getAsString();
				boolean result = createPool(masterUuid, poolUuid);
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
					tObj1.put("huid", ja.get(0).getAsString());
					tObj1.put("puid", poolUuid);
					tObj1.put("pname", TimeUtils.encodeText(pname));
					qaArray.put(tObj1);
					for (int i = 1; i < ja.size(); i++) {
						Date sTime = new Date();
						boolean addResult = addHostToPool(userid, ja.get(i).getAsString(),
								poolUuid, poolType);
						Date eTime = new Date();
						JSONArray infoArray1 = new JSONArray();
						infoArray1.put(TimeUtils.createLogInfo(
								LogRole.HOST, "host-"
										+ masterUuid.substring(0, 8)));
						infoArray1.put(TimeUtils.createLogInfo(
								LogRole.POOL, pname));
						if (addResult) {
							JSONObject tObj = new JSONObject();
							tObj.put("huid", ja.get(i).getAsString());
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
				} else {
					OCLog log = logRecord.addFailedLog(userid, LogObject.MASTERNODE, 
							LogAction.JOIN, infoArray.toString(), startTime, endTime);
					message.pushError(userid, log.toString());
				}
			}
		}else if (poolType.equals(SystemConstant.DOCKER)) {
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
				} else {
					OCLog log = logRecord.addFailedLog(userid, LogObject.MASTERNODE, 
							LogAction.JOIN, infoArray.toString(), startTime, endTime);
					message.pushError(userid, log.toString());
				}
			}
		}
		
		return qaArray;
	}

	public JSONArray r4Pool(String hostUuid, int userid) {
		JSONArray qaArray = new JSONArray();
		Date startTime = new Date();
		if (vmService.countVMNumberOfHost(hostUuid) > 0) {
			JSONObject tObj = new JSONObject();
			tObj.put("isSuccess", false);
			qaArray.put(tObj);
			message.pushWarning(userid, "服务器中存在未销毁的虚拟机，不能离池。");
		}else if(imageService.countImageOfHost(hostUuid)>0){
			JSONObject tObj = new JSONObject();
			tObj.put("isSuccess", false);
			qaArray.put(tObj);
			message.pushWarning(userid, "服务器中存在未删除的映像，不能离池。");
		}else {
			String poolUuid = hostService.getHostById(hostUuid).getPoolUuid();
			if (poolUuid != null) {
				boolean result = ejectHostFromPool(userid, hostUuid);
				// write log and push message
				Date endTime = new Date();
				JSONArray infoArray = new JSONArray();
				infoArray.put(TimeUtils.createLogInfo(
						LogRole.HOST,
						"host-" + hostUuid.substring(0, 8)));
				infoArray.put(TimeUtils.createLogInfo(
						LogRole.POOL,
						"pool-" + poolUuid.substring(0, 8)));
				if (result) {
					JSONObject tObj = new JSONObject();
					tObj.put("isSuccess", true);
					qaArray.put(tObj);
					OCLog log = logRecord.addSuccessLog(userid, LogObject.HOST, 
							LogAction.LEAVE, infoArray.toString(), startTime, endTime);
					message.pushSuccess(userid, log.toString());
				} else {
					JSONObject tObj = new JSONObject();
					tObj.put("isSuccess", false);
					qaArray.put(tObj);
					OCLog log = logRecord.addFailedLog(userid, LogObject.HOST, 
							LogAction.LEAVE, infoArray.toString(), startTime, endTime); 
					message.pushError(userid, log.toString());
				}
			} else {
				JSONObject tObj = new JSONObject();
				tObj.put("isSuccess", false);
				qaArray.put(tObj);
			}
		}
		
		return qaArray;
	}

	public JSONArray getOneHost(String hostid) {
		return hostService.getOneHost(hostid);
	}

	public boolean updateHost(String hostId, String hostName, String hostDesc,
			String hostType) {
		return hostService.updateHost(hostId, hostName, hostDesc, hostType);
	}

	public JSONArray getAllList() {
		return hostService.getAllList();
	}

	public boolean recover(int userId, String ip, String username,
			String password, String content, String conid, String hostUuid) {
		SSH ssh = new SSH(ip, username, password);
		boolean result = false;
		OCHost master = new OCHost();
		try {
			OCHost host = hostService.getHostById(hostUuid);
			if (host.getPoolUuid() != null) {
				String masterUuid = poolService.getPoolById(host.getPoolUuid()).getPoolMaster();
				master = hostService.getHostById(masterUuid);
				if (hostUuid.equals(masterUuid)) {
					message.pushClose(userId, content, conid);
					message.pushWarning(userId, "修复主节点，请先拆除资源池");
					return false;
				}
			}
			if (!ssh.connect()) {
				message.pushClose(userId, content, conid);
				message.pushWarning(userId, "IP不存在，或者用户名密码不匹配");
				return false;
			}
			if (!ssh.execute("/etc/init.d/xend restart")) {
				message.pushClose(userId, content, conid);
				message.pushWarning(userId, "修复中出现未知错误");
			} else {
				message.pushClose(userId, content, conid);
				message.pushSuccess(userId, "修复成功");
				if (host.getPoolUuid() != null) {
					hostCore.joinToPool(master, host);
					message.pushSuccess(userId, "服务器加入资源池成功");
				}
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			message.pushClose(userId, content, conid);
			message.pushWarning(userId, "修复中出现未知错误");
		} finally {
			ssh.close();
		}
		return result;
	}

	public JSONArray getHostListForMigration(String vmuuid) {
		return hostService.getHostListForMigration(vmuuid);
	}
	
	public JSONArray getAllHostOfPool(String poolUuid) {
		return hostService.getAllHostOfPool(poolUuid);
	}

	public OCHost getOneHostFormPool(String poolUuid) {
		return hostService.getMasterOfPool(poolUuid);
	}

	public List<OCHost> getAllHostNotInPool() {
		return hostService.getAllHostNotInPool();
	}

	public OCHost getHostById(String hostid) {
		return hostService.getHostById(hostid);
	}

	public List<OCHost> getAllHostInPool(String poolUuid) {
		return hostService.getAllHostInPool(poolUuid);
	}

	public boolean activeHost(String key, String ip,String hostid,int userId) {
		boolean result = false;
		String hostPwd = "";
		OCHost host = hostService.getHostById(hostid);
		if ( host != null) {
			hostPwd = host.getHostPwd();
		}
		result = active(key, ip, "root", hostPwd);
		if (result) {
			message.pushSuccess(userId, "服务器注册成功");
		} else {
			message.pushError(userId, "服务器注册失败");
		}
		return result;
		
	}
	
	private boolean addHostToPool(int userId, String hostUuid, String poolUuid, String poolType) {
		boolean result = false;
		if (hostService.isSameType(hostUuid, poolUuid)) {
			if (poolType.equals(SystemConstant.BEUONDCLOUD)) {
				OCHost masterHost = hostService.getMasterOfPool(poolUuid);
				OCHost targetHost = hostService.getHostById(hostUuid);
				if (masterHost != null & targetHost != null) {
					if (srService.checkSREquals(masterHost.getHostUuid(),hostUuid)) {
						if (hostCore.joinToPool(masterHost, targetHost)) {
							result = true;
						}
					}
				}
			}else if(poolType.equals(SystemConstant.DOCKER)){
				OCHost host = hostService.getHostById(hostUuid);
				OCHost masterHost = hostService.getMasterOfPool(poolUuid);
				SSH ssh = hostCore.getSshConnection(host.getHostIP(), host.getHostPwd(), host.getHostType());
				SSH masterSsh = hostCore.getSshConnection(masterHost.getHostIP(), masterHost.getHostPwd(), poolType); 
				try {
					StringBuffer buffer = new StringBuffer();
					buffer.append("service docker stop; ");
					buffer.append("docker -H tcp://0.0.0.0:2375 --insecure-registry "+masterHost.getHostIP()+":5000 -d");
					System.out.println(ssh.executeWithResult(buffer.toString(), 20));
					masterSsh.execute("echo "+ host.getHostIP()+":2375 >> /opt/cluster");
					result = true;
				} catch (Exception e) {
					e.printStackTrace();
				}finally{
					ssh.close();
					masterSsh.close();
				}
			}
			hostService.setPool(hostUuid, poolUuid);
		}else{
			message.pushError(userId, "资源池和主机类型不同，不能将主机添加到资源池！");
		}
		return result;
	}

	private boolean ejectHostFromPool(Integer userId, String hostUuid) {
		boolean result = false;
		OCHost host = getHostById(hostUuid);
		String poolUuid = host.getPoolUuid();
		OCHost master = hostService.getMasterOfPool(poolUuid);
		String masterUuid = master.getHostUuid();
		if (hostUuid.equals(masterUuid)) {
			int hostNum = hostService.getAllHostInPool(poolUuid).size();
			if (hostNum > 1) {
				message.pushError(userId, "资源池内存在从节点，请先拆除从节点再拆除主节点");
				return false;
			}
		}
		if (host != null && poolUuid != null) {
			if (master != null) {
				Host ejectHost = hostCore.getHost(hostUuid);
				result = poolCore.ejectPoolConnection(poolUuid, ejectHost);
				if (result) {
					OCPool pool = poolService.getPoolById(poolUuid);
					if (host.getHostUuid().equals(masterUuid)) {
						pool.setPoolMaster(null);
						poolService.updatePool(pool);
					}
					host.setPoolUuid(null);
					result = hostService.updateHost(host);
				}
			}
		}
		return result;
	}

	private boolean createPool(String hostUuid, String poolUuid) {
		boolean result = false;
		OCHost targetHost = hostService.getHostById(hostUuid);
		OCPool pool = poolService.getPoolById(poolUuid);
		String haPath = "";
		if (targetHost.getPoolUuid() == null && pool != null) {
			haPath = hostCore.getHAPath(targetHost);
			targetHost.setPoolUuid(pool.getPoolUuid());
			pool.setPoolMaster(targetHost.getHostUuid());
			pool.setHaPath(haPath);
			result = hostService.updateHost(targetHost);
			result = poolService.updatePool(pool);
		}
		return result;
	}
	
	private boolean createDockerPool(String hostUuid, String poolUuid){
		boolean result = false;
		OCHost targetHost = hostService.getHostById(hostUuid);
		OCPool pool = poolService.getPoolById(poolUuid);
		SSH ssh = hostCore.getSshConnection(targetHost.getHostIP(), targetHost.getHostPwd(), targetHost.getHostType());
		try {
			StringBuffer buffer = new StringBuffer();
			//buffer.append("docker run -d -p 5000:5000 registry:2.0;");
			buffer.append("touch /opt/cluster;");
			buffer.append("swarm manage -H tcp://"+targetHost.getHostIP()+":2375 file:///opt/cluster &");
			result = ssh.execute(buffer.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			ssh.close();
		}
		if (targetHost.getPoolUuid() == null && pool != null) {
			targetHost.setPoolUuid(pool.getPoolUuid());
			pool.setPoolMaster(targetHost.getHostUuid());
			result = hostService.updateHost(targetHost);
			result = poolService.updatePool(pool);
		}
		return result;
	}
	
	private boolean active(String key, String ip, String username,
			String password) {
		boolean result = false;
		Map<Host, Host.Record> hostMap = hostCore.getHostMap(key, ip, username, password);
		if(hostMap== null || hostMap.isEmpty()){
			return false;
		}
		for (Host thisHost : hostMap.keySet()) {
			Host.Record hostRecord = hostMap.get(thisHost);
			String hostIp = hostRecord.address;
			if(ip != null && hostIp != null && ip.equals(hostIp)){// 通过ip相等确定激活主机
				String hostUuid = hostRecord.uuid;
				int hostMem = (int) (hostRecord.memoryTotal / MB);
				int hostCpu = hostRecord.hostCPUs.size();
				String kernelVersion = hostRecord.softwareVersion
						.get("system")
						+ " "
						+ hostRecord.softwareVersion.get("release");
				String xenVersion = "Xen "
						+ hostRecord.softwareVersion.get("Xen");
				OCHost host = hostService.getHostById(hostUuid);
				host.setHostCpu(hostCpu);
				host.setHostMem(hostMem);
				host.setKernelVersion(kernelVersion);
				host.setXenVersion(xenVersion);
				hostService.updateHost(host);
				result = true;
				break;
			}
		
		}
		return result;
	}

	/**
	 * 当该IP在数据库OC_HOST不存在时，说明该服务器尚未加入本系统管理，返回true，否则返回false
	 * 
	 * @param hostIp 此处不判断IP是否为null。若为null，则抛出异常
	 * @return
	 */
	@SuppressWarnings("unused")
	private boolean validHost(String hostIp) {
		return (hostService.getHostFromIp(hostIp) == null) ? true : false;
	}
	
}
