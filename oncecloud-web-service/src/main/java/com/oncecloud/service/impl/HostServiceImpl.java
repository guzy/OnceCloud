package com.oncecloud.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.oncecloud.vsphere.VMWareUtil;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.oncecloud.constants.PowerConstant;
import com.oncecloud.dao.HostDAO;
import com.oncecloud.dao.HostSRDAO;
import com.oncecloud.dao.PoolDAO;
import com.oncecloud.dao.PowerDAO;
import com.oncecloud.dao.StorageDAO;
import com.oncecloud.dao.UserDAO;
import com.oncecloud.dao.VMDAO;
import com.oncecloud.entity.OCHost;
import com.oncecloud.entity.OCLog;
import com.oncecloud.entity.OCPool;
import com.oncecloud.entity.OCVM;
import com.oncecloud.entity.Power;
import com.oncecloud.entity.Storage;
import com.oncecloud.log.LogAction;
import com.oncecloud.log.LogObject;
import com.oncecloud.log.LogRecord;
import com.oncecloud.log.LogRole;
import com.oncecloud.message.MessageUtil;
import com.oncecloud.model.VMPlatform;
import com.oncecloud.model.VMPower;
import com.oncecloud.model.VMStatus;
import com.oncecloud.service.HostService;
import com.oncecloud.util.TimeUtils;

@Component("HostService")
public class HostServiceImpl implements HostService {
	
	@Resource
	private HostDAO hostDAO;
	@Resource
	private PoolDAO poolDAO;
	@Resource
	private PowerDAO powerDAO;
	@Resource
	private StorageDAO storageDAO;
	@Resource
	private HostSRDAO hostSRDAO;
	@Resource
	private VMDAO vmDAO;
	@Resource
	private UserDAO userDAO;
	@Resource
	private LogRecord logRecord;
	@Resource
	private MessageUtil message;
	
	public JSONArray createHost(String hostUuid, String hostName, String hostPwd,
			String hostDesc, String hostIp, String hostType, int userid) {
		Date startTime = new Date();
		JSONArray qaArray = new JSONArray();
		//添加主机类型的检验（待添加的功能）
		OCHost host = new OCHost(hostUuid, hostPwd, hostName, hostType, hostDesc, hostIp, 0,
				0, null, null, 1, new Date()); 
		//注册物理机
		boolean result = hostDAO.saveHost(host);
		if (result) {
			JSONObject tObj = new JSONObject();
			tObj.put("hostname", TimeUtils.encodeText(hostName));
			tObj.put("hostdesc", TimeUtils.encodeText(hostDesc));
			tObj.put("hostid", hostUuid);
			tObj.put("hostip", hostIp);
			tObj.put("hostcpu", host.getHostCpu());
			tObj.put("hostmem", host.getHostMem());
			tObj.put("createdate", TimeUtils.formatTime(host.getCreateDate()));
			tObj.put("poolid", "");
			tObj.put("poolname", "");
			tObj.put("srsize", 0);
			qaArray.put(tObj);
		}
		Date endTime = new Date();
		JSONArray infoArray = logRecord.createLoginfos(LogRole.HOST, hostIp);
		if (result) {
			OCLog log = logRecord.addSuccessLog(userid, LogObject.HOST, 
					LogAction.ADD, infoArray.toString(), startTime, endTime); 
			message.pushSuccess(userid, log.toString());
		} else {
			OCLog log = logRecord.addFailedLog(userid, LogObject.HOST, 
					LogAction.ADD, infoArray.toString(), startTime, endTime); 
			message.pushError(userid, log.toString());
		}
		return qaArray;
	}
	
	@Override
	public JSONArray createDockerHost(String hostUuid, String hostName,
			String hostPwd, String hostDesc, String hostIp, String hostType,
			int hostCpu, int hostMem, int userid) {
		Date startTime = new Date();
		JSONArray qaArray = new JSONArray();
		//添加主机类型的检验（待添加的功能）
		OCHost host = new OCHost(hostUuid, hostPwd, hostName, hostType, hostDesc, hostIp, hostMem,
				hostCpu, null, null, 1, new Date()); 
		//注册物理机
		boolean result = hostDAO.saveHost(host);
		if (result) {
			JSONObject tObj = new JSONObject();
			tObj.put("hostname", TimeUtils.encodeText(hostName));
			tObj.put("hostdesc", TimeUtils.encodeText(hostDesc));
			tObj.put("hostid", hostUuid);
			tObj.put("hostip", hostIp);
			tObj.put("hostcpu", host.getHostCpu());
			tObj.put("hostmem", host.getHostMem());
			tObj.put("hosttype", host.getHostType());
			tObj.put("createdate", TimeUtils.formatTime(host.getCreateDate()));
			tObj.put("poolid", "");
			tObj.put("poolname", "");
			tObj.put("srsize", 0);
			qaArray.put(tObj);
		}
		Date endTime = new Date();
		JSONArray infoArray = logRecord.createLoginfos(LogRole.HOST, hostIp);
		if (result) {
			OCLog log = logRecord.addSuccessLog(userid, LogObject.HOST, 
					LogAction.ADD, infoArray.toString(), startTime, endTime); 
			message.pushSuccess(userid, log.toString());
		} else {
			OCLog log = logRecord.addFailedLog(userid, LogObject.HOST, 
					LogAction.ADD, infoArray.toString(), startTime, endTime); 
			message.pushError(userid, log.toString());
		}
		return qaArray;
	}

	public JSONArray getHostList(int page, int limit, String search) {
		JSONArray qaArray = new JSONArray();
		int totalNum = hostDAO.countAllHostList(search);
		List<OCHost> hostList = hostDAO.getOnePageHostList(page, limit, search);
		qaArray.put(totalNum);
		for (OCHost result : hostList) {
			JSONObject tObj = new JSONObject();
			tObj.put("hostname", TimeUtils.encodeText(result.getHostName()));
			tObj.put("hostdesc", TimeUtils.encodeText(result.getHostDesc()));
			String hostUuid = result.getHostUuid();
			Power power = powerDAO.getPowerByHostID(hostUuid);
			if (power == null) {
				tObj.put("powerstatus", -1);
			}else {
				tObj.put("powerstatus", power.getPowerValid());
			}
			tObj.put("hostid", hostUuid);
			tObj.put("hostip", result.getHostIP());
			tObj.put("hostcpu", result.getHostCpu());
			tObj.put("hostmem", result.getHostMem());
			tObj.put("hosttype", result.getHostType());
			tObj.put("createdate", TimeUtils.formatTime(result.getCreateDate()));
			String poolId = result.getPoolUuid();
			if (poolId == null) {
				tObj.put("poolid", "");
				tObj.put("poolname", "");
			} else {
				tObj.put("poolid", poolId);
				tObj.put("poolname", TimeUtils.encodeText(poolDAO.getPool(
						poolId).getPoolName()));
			}
			tObj.put("srsize", storageDAO.getStorageSize(hostUuid));
			qaArray.put(tObj);
		}
		return qaArray;
	}

	public JSONArray getHostLoadList(int page, int limit, String searchStr,
			String srUuid) {
		int totalNum = hostDAO.countAllHostList(searchStr);
		JSONArray qaArray = new JSONArray();
		List<OCHost> hostList = hostDAO.getOnePageLoadHostList(page, limit,
				searchStr, srUuid);
		qaArray.put(totalNum);
		for (OCHost result : hostList) {
			JSONObject tObj = new JSONObject();
			tObj.put("hostname", TimeUtils.encodeText(result.getHostName()));
			tObj.put("hostdesc", TimeUtils.encodeText(result.getHostDesc()));
			tObj.put("hostid", result.getHostUuid());
			String poolId = result.getPoolUuid();
			if (poolId == null) {
				poolId = "";
			}
			tObj.put("poolid", poolId);
			qaArray.put(tObj);
		}
		return qaArray;
	}

	public JSONArray getSrOfHost(String hostUuid) {
		List<Storage> srOfHost = hostDAO.getSROfHost(hostUuid);
		JSONArray qaArray = new JSONArray();
		for (int i = 0; i < srOfHost.size(); i++) {
			Storage sr = srOfHost.get(i);
			JSONObject obj = new JSONObject();
			obj.put("sruuid", sr.getSrUuid());
			obj.put("srname", TimeUtils.encodeText(sr.getSrName()));
			obj.put("srip", sr.getSrAddress());
			obj.put("srdir", sr.getSrdir());
			obj.put("srtype", sr.getSrtype());
			qaArray.put(obj);
		}
		return qaArray;
	}

	public void unbindSr(String hostUuid, String srUuid, int userid) {
		if (hostDAO.unbindSr(hostUuid, srUuid)) {
			message.pushSuccess(userid, "解绑成功");
		} else {
			message.pushError(userid, "解绑失败");
		}
	}

	public JSONArray isSameSr(String uuidJsonStr) {
		JsonParser jp = new JsonParser();
		JsonElement je = jp.parse(uuidJsonStr);
		JsonArray ja = je.getAsJsonArray();
		JSONArray qaArray = new JSONArray();
		boolean isSame = false;
		if (ja.size() > 0) {
			if (ja.size() > 1) {
				String firstUuid = ja.get(0).getAsString();
				List<Storage> _srlist1 = hostDAO.getSROfHost(firstUuid);
				List<String> srlist1 = new ArrayList<String>();
				for (Storage storage : _srlist1) {
					srlist1.add(storage.getSrUuid());
				}
				for (int i = 1; i < ja.size(); i++) {
					String uuid = ja.get(i).getAsString();
					List<Storage> srlist2 = hostDAO.getSROfHost(uuid);
					for (Storage storage : srlist2) {
						if (srlist1.contains(storage.getSrUuid())) {
							srlist1.remove(storage.getSrUuid());
						}
					}
					if (srlist1.size() == 0) {
						isSame = true;
					} else {
						isSame = false;
					}
				}
				if (isSame) {
					JSONObject tObj1 = new JSONObject();
					tObj1.put("isSuccess", true);
					qaArray.put(tObj1);
				} else {
					JSONObject tObj = new JSONObject();
					tObj.put("isSuccess", false);
					qaArray.put(tObj);
				}
			} else {
				JSONObject tObj = new JSONObject();
				tObj.put("isSuccess", true);
				qaArray.put(tObj);
			}
		} else {
			JSONObject tObj = new JSONObject();
			tObj.put("isSuccess", false);
			qaArray.put(tObj);
		}
		return qaArray;
	}

	public JSONArray getTablePool(String uuidJsonStr) {
		JsonParser jp = new JsonParser();
		JSONArray qaArray = new JSONArray();
		JsonElement je = jp.parse(uuidJsonStr);
		JsonArray ja = je.getAsJsonArray();
		String hostUuid = ja.get(0).getAsString();
		List<Storage> _targetList = hostDAO.getSROfHost(hostUuid);
		List<String> targetList = new ArrayList<String>();
		for (Storage storage : _targetList) {
			targetList.add(storage.getSrUuid());
		}
		List<OCPool> poolList = poolDAO.getPoolList();
		for (OCPool pool : poolList) {
			String poolUuid = pool.getPoolUuid();
			String poolName = pool.getPoolName();
			String poolMaster = pool.getPoolMaster();
			String poolType = pool.getPoolType();
			if (poolMaster != null) {
				List<Storage> compareList = hostDAO.getSROfHost(poolMaster);
				for (Storage storage : compareList) {
					if (targetList.contains(storage.getSrUuid())) {
						targetList.remove(storage.getSrUuid());
					}
				}
				if (targetList.size() == 0) {
					JSONObject tObj = new JSONObject();
					tObj.put("pooluuid", poolUuid);
					tObj.put("poolname", TimeUtils.encodeText(poolName));
					tObj.put("hasmaster", 1);
					tObj.put("pooltype", poolType);
					qaArray.put(tObj);
				}
			} else {
				JSONObject tObj = new JSONObject();
				tObj.put("pooluuid", poolUuid);
				tObj.put("poolname", TimeUtils.encodeText(poolName));
				tObj.put("hasmaster", 0);
				tObj.put("pooltype", poolType);
				qaArray.put(tObj);
			}
		}
		return qaArray;
	}

	private boolean isStandaloneHost(String hostUuid){
		OCHost host = hostDAO.getHost(hostUuid);
		if(host != null){
			return host.getPoolUuid() == null || host.getPoolUuid().isEmpty();
		}else{
			throw new IllegalStateException("Host数据库中没有 uuid为[" + hostUuid +"]的记录");
		}
		
	}
	
	// support standalone host deletion
	public JSONArray deleteHost(String hostId, String hostName, int userid) {
		Date startTime = new Date();
		JSONArray qaArray = new JSONArray();
		boolean result = false;
		if(isStandaloneHost(hostId)){
			// delete directly
			result = deleteHostDb(hostId, hostName, userid, startTime);
		}else{
			// host in a pool
			String poolUuid = userDAO.getUser(userid).getUserAllocate();
			OCPool pool = poolDAO.getPool(poolUuid);
			if (hostId.equals(pool.getPoolMaster())) {
				int hostNumOfPool = hostDAO.getHostListOfPool(poolUuid).size();
				if (hostNumOfPool > 1) {
					message.pushWarning(userid, "资源池内存在从节点，请清空后再删除主节点！");
				}else {
					result = deleteHostDb(hostId, hostName, userid, startTime);
				}
			}else {
				int vmofHost = vmDAO.getVmListByhostUuid(hostId).size();
				if (vmofHost != 0) {
					message.pushWarning(userid, "服务器中内包含正在使用的资源，请清空资源再删除！");
				}else {
					result = deleteHostDb(hostId, hostName, userid, startTime);
				}
			}
		}
	
		JSONObject tObj = new JSONObject();
		tObj.put("result", result);
		qaArray.put(tObj);
		return qaArray;
	}

	private boolean deleteHostDb(String hostId, String hostName, int userid,
			Date startTime) {
		boolean result;
		result = hostDAO.deleteHost(hostId);
		Date endTime = new Date();
		JSONArray infoArray = logRecord.createLoginfos(LogRole.HOST, hostName);
		if (result) {
			OCLog log = logRecord.addSuccessLog(userid, LogObject.HOST, 
					LogAction.DELETE, 
					infoArray.toString(), startTime, endTime); 
			message.pushSuccess(userid, log.toString());
		} else {
			OCLog log = logRecord.addFailedLog(userid, LogObject.HOST, 
					LogAction.DELETE, 
					infoArray.toString(), startTime, endTime);
			message.pushError(userid, log.toString());
		}
		return result;
	}

	public JSONArray queryAddress(String address) {
		OCHost query = hostDAO.getHostFromIp(address);
		JSONObject tObj = new JSONObject();
		JSONArray qaArray = new JSONArray();
		if (query != null) {
			tObj.put("exist", true);
		} else {
			tObj.put("exist", false);
		}
		qaArray.put(tObj);
		return qaArray;
	}
	
	public OCHost getHostFromIp(String address){
		return hostDAO.getHostFromIp(address);
	}
	
	public boolean setPool(String hostUuid, String poolUuid){
		return hostDAO.setPool(hostUuid, poolUuid);
	}

	public JSONArray add2Pool(String uuidJsonStr, String hasMaster,
			String poolUuid, int userid) {
		// TODO Auto-generated method stub
		return null;
	}

	public JSONArray r4Pool(String hostUuid, int userid) {
		// TODO Auto-generated method stub
		return null;
	}

	public JSONArray getOneHost(String hostid) {
		OCHost ochost = this.getHostById(hostid);
		JSONArray qaArray = new JSONArray();
		JSONObject jo = new JSONObject();
		jo.put("hostName", TimeUtils.encodeText(ochost.getHostName()));
		jo.put("createDate", TimeUtils.formatTime(ochost.getCreateDate()));
		String timeUsed = TimeUtils.encodeText(TimeUtils.dateToUsed(ochost
				.getCreateDate()));
		jo.put("useDate", timeUsed);
		jo.put("hostCPU", ochost.getHostCpu());
		jo.put("hostMemory", ochost.getHostMem());
		jo.put("hostIP", ochost.getHostIP());
		jo.put("hostUuid", hostid);
		jo.put("hostPwd", ochost.getHostPwd());
		jo.put("hostDesc", TimeUtils.encodeText(ochost.getHostDesc()));
		jo.put("hostkernel", ochost.getKernelVersion());
		jo.put("hostXen", ochost.getXenVersion());
		String poolid = ochost.getPoolUuid();
		if (poolid == null || poolid == "") {
			jo.put("poolId", "");
			jo.put("poolName", "");
		} else {
			OCPool ocpool = poolDAO.getPool(poolid);
			jo.put("poolId", ocpool.getPoolUuid());
			jo.put("poolName", TimeUtils.encodeText(ocpool.getPoolName()));
		}
		qaArray.put(jo);
		Set<String> srlist = hostSRDAO.getSRList(hostid);
		for (String sr : srlist) {
			JSONObject josr = new JSONObject();
			Storage srdao = storageDAO.getStorage(sr);
			josr.put("srId", srdao.getSrUuid());
			josr.put("srName", TimeUtils.encodeText(srdao.getSrName()));
			qaArray.put(josr);
		}
		return qaArray;
	}

	public OCHost getMasterOfPool(String poolUuid) {
		OCHost master = null;
		if (poolUuid != null) {
			OCPool pool = poolDAO.getPool(poolUuid);
			if (pool != null) {
				String masterUuid = pool.getPoolMaster();
				if (masterUuid != null) {
					master = getHostById(masterUuid);
				}
			}
		}
		return master;
	}

	public boolean updateHost(String hostId, String hostName, String hostDesc,
			String hostType) {
		return hostDAO.updateHost(hostId, hostName, hostDesc, hostType);
	}
	
	public boolean updateHost(OCHost host) {
		return hostDAO.update(host);
	}

	public JSONArray getAllList() {
		JSONArray ja = new JSONArray();
		List<OCHost> list = hostDAO.getAllHost();
		if (list != null) {
			for (OCHost oh : list) {
				JSONObject jo = new JSONObject();
				jo.put("hostName", oh.getHostName());
				jo.put("hostUuid", oh.getHostUuid());
				jo.put("ip", oh.getHostIP());
				ja.put(jo);
			}
		}
		return ja;
	}

	public boolean recover(int userId, String ip, String username,
			String password, String content, String conid, String hostUuid) {
		// TODO Auto-generated method stub
		return false;
	}

	public JSONArray getHostListForMigration(String vmuuid) {
		String hostuuid = vmDAO.getVM(vmuuid).getVmHostUuid();
		OCHost ochost = this.getHostById(hostuuid);
		String pooluuid = ochost.getPoolUuid();
		List<OCHost> list = hostDAO.getHostListOfPool(pooluuid);
		JSONArray ja = new JSONArray();
		if (list.size() > 0) {
			for (OCHost oh : list) {
				if (oh.getHostUuid().equals(hostuuid))
					continue;
				JSONObject jo = new JSONObject();
				jo.put("ip", oh.getHostIP());
				jo.put("hostUuid", oh.getHostUuid());
				ja.put(jo);
			}
		}
		return ja;
	}

	public List<OCHost> getAllHostNotInPool() {
		return hostDAO.getAllHost();
	}

	public List<OCHost> getAllHostInPool(String poolUuid) {
		return hostDAO.getHostListOfPool(poolUuid);
	}

	public JSONArray getAllHostOfPool(String poolUuid) {
		List<OCHost> list = hostDAO.getHostListOfPool(poolUuid);
		JSONArray ja = new JSONArray();
		if (list.size() > 0) {
			for (OCHost oh : list) {
				JSONObject jo = new JSONObject();
				jo.put("ip", oh.getHostIP());
				jo.put("hostuuid", oh.getHostUuid());
				jo.put("hostname", oh.getHostName());
				ja.put(jo);
			}
		}
		return ja;
	}

	public OCHost getHostById(String hostid) {
		return hostDAO.getHost(hostid);
	}

	public boolean activeHost(String key, String ip, String hostid, int userId) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isSameType(String hostUuid, String poolUuid) {
		boolean result = false;
		OCHost host = hostDAO.getHost(hostUuid);
		OCPool pool = poolDAO.getPool(poolUuid);
		if (host.getHostType().equals(pool.getPoolType())) {
			result = true;
		}
		return result;
	}

	@Override
	public List<OCHost> getAllHost() {
		
		return hostDAO.getAllHost();
	}

	@SuppressWarnings("unused")
	@Override
	public JSONArray createVSphereHost(String hostUuid, String hostName,
			String hostPwd, String hostDesc, String hostIp, String hostType,
			int userid) {
		try {
			//创建连接
			VMWareUtil.connect("https://"+hostIp+"/sdk","root",hostPwd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//主机属性
		List<Map<String, Object>> str = VMWareUtil.printHostProductDetails();
		
		Integer hostMem = 0;
		Integer hostCpu = 0;
		String kernelVersion="";
		for(Map<String, Object> map:str){
			hostUuid = (String) map.get("summary.hardware.uuid");
			hostMem = (int)(Double.parseDouble(String.valueOf( map.get("summary.hardware.memorySize")==null?0:map.get("summary.hardware.memorySize")))/1024/1024);
			hostCpu = (int)Double.parseDouble(String.valueOf( map.get("summary.hardware.numCpuCores")==null?0:map.get("summary.hardware.numCpuCores")));
			kernelVersion=(String) map.get("summary.config.product.osType");
		}
//		if(str.length()>0)
//		{
//			String[] hostKey2Value = str.split(",");
//			
//			hostUuid = hostKey2Value[11];
//			hostMem = (int)(Long.parseLong(hostKey2Value[7])/1024/1024);
//			hostCpu = (int)Long.parseLong(hostKey2Value[9]);
//			kernelVersion=hostKey2Value[3];
//		}
		
		Date startTime = new Date();
		JSONArray qaArray = new JSONArray();
		//添加主机类型的检验（待添加的功能）
		OCHost host = new OCHost(hostUuid, hostPwd, hostName, hostType, hostDesc, hostIp, hostMem,
				hostCpu, kernelVersion, null, 1, new Date()); 
		//注入虚拟机
		boolean result = hostDAO.saveHost(host);
		if (result) {
			JSONObject tObj = new JSONObject();
			tObj.put("hostname", TimeUtils.encodeText(hostName));
			tObj.put("hostdesc", TimeUtils.encodeText(hostDesc));
			tObj.put("hostid", hostUuid);
			tObj.put("hostip", hostIp);
			tObj.put("hostcpu", host.getHostCpu());
			tObj.put("hostmem", host.getHostMem());
			tObj.put("createdate", TimeUtils.formatTime(host.getCreateDate()));
			tObj.put("poolid", "");
			tObj.put("poolname", "");
			tObj.put("srsize", 0);
			qaArray.put(tObj);
		}
		
		//所有虚拟机
		List<Map<String,Object>> data0 = new ArrayList<Map<String,Object>>();
		data0 = VMWareUtil.vmHardwareinfo();
		
		for(Map<String,Object> map:data0){
			String vmUuid=(String) map.get("vmuuids");
			String vmPWD="onceas";//onceas
			String vmName=(String) map.get("vmname");
			String vmMac=(String) map.get("mac");
			int vmMem = (int) map.get("vmmemry");
			int vmCpu = (int) map.get("vmcpu");
			int vmPower=PowerConstant.POWER_CREATE;//PowerConstant.POWER_RUNNING
			int disk = (int) map.get("vmdisck");
			if(map.containsKey("")){
				
			}
			OCVM vm = new OCVM();
			vm.setVmUuid(vmUuid);
			vm.setVmPwd(vmPWD);
			vm.setVmName(vmName);
			vm.setVmMac(vmMac);
			vm.setVmMem(vmMem);
			vm.setVmDisk(disk);
			vm.setVmCpu(vmCpu);
			vm.setVmVlan("-1");//
			try {
				String propertyStr = VMWareUtil.callWaitForUpdatesEx("60", "1",vmName)  ;
				String[] pros = propertyStr.split(":");
				if("POWERED_ON".equals(pros[1])){
					vmPower = PowerConstant.POWER_RUNNING;
				}
				if("POWERED_OFF".equals(pros[1])){
					vmPower = PowerConstant.POWER_HALTED;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			vm.setVmPower(VMPower.values()[vmPower]);
			vm.setVmHostUuid(hostUuid);
			vm.setVmCreateDate(new Date());
			vm.setVmPlatform(VMPlatform.LINUX);
			vm.setVmStatus(VMStatus.EXIST);
			vm.setVmUid(userid);
			boolean b = vmDAO.saveVmFromVSphere(vm);
		}
		Date endTime = new Date();
		JSONArray infoArray = logRecord.createLoginfos(LogRole.HOST, hostIp);
		if (result) {
			OCLog log = logRecord.addSuccessLog(userid, LogObject.HOST, 
					LogAction.ADD, infoArray.toString(), startTime, endTime); 
			message.pushSuccess(userid, log.toString());
		} else {
			OCLog log = logRecord.addFailedLog(userid, LogObject.HOST, 
					LogAction.ADD, infoArray.toString(), startTime, endTime); 
			message.pushError(userid, log.toString());
		}
		return qaArray;
	}

}
