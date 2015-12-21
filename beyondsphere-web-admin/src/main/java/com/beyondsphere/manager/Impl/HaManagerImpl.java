package com.beyondsphere.manager.Impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.beyondsphere.manager.HaManager;
import com.oncecloud.core.HostCore;
import com.oncecloud.core.PoolHACore;
import com.oncecloud.entity.OCHa;
import com.oncecloud.entity.OCHost;
import com.oncecloud.entity.OCLog;
import com.oncecloud.entity.OCPool;
import com.oncecloud.entity.Power;
import com.oncecloud.log.LogAction;
import com.oncecloud.log.LogObject;
import com.oncecloud.log.LogRecord;
import com.oncecloud.log.LogRole;
import com.oncecloud.message.MessageUtil;
import com.oncecloud.service.DatacenterService;
import com.oncecloud.service.HaService;
import com.oncecloud.service.HostService;
import com.oncecloud.service.PoolService;
import com.oncecloud.service.PowerService;
import com.oncecloud.service.VMService;
import com.oncecloud.util.TimeUtils;

@Service("HaManager")
public class HaManagerImpl implements HaManager {

	@Resource
	private DatacenterService datacenterService;
	@Resource
	private PoolService poolService;
	@Resource
	private HostService hostService;
	@Resource
	private PowerService PowerService;
	@Resource
	private VMService vmService;
	@Resource
	private HaService haService;
	@Resource
	private PoolHACore haCore;
	@Resource
	private HostCore hostCore;
	@Resource
	private LogRecord logRecord;
	@Resource
	private MessageUtil message;
	

	public JSONArray getPoolList(int page, int limit, String search) throws Exception {
		JSONArray ja = new JSONArray();
		int totalNum = poolService.countAllPoolList(search);
		ja.put(totalNum);
		List<OCPool> poolList = poolService.getOnePagePoolListOfHA(page, limit, search);
		if (poolList != null) {
			for (OCPool pool : poolList) {
				ja.put(getPoolJsonObject(pool));
			}
		}
		return ja;
	}
	
	public JSONArray getMigrateHostList(String poolUuid) throws Exception {
		JSONArray ja = new JSONArray();
		List<OCHost> hostList = hostService.getAllHostInPool(poolUuid);
		if (hostList != null) {
			for (OCHost host : hostList) {
				JSONObject jo = new JSONObject();
				int countVMs = vmService.countVMNumberOfHost(host.getHostUuid());
				if (countVMs == 0) {
					jo.put("hostName", host.getHostName());
					jo.put("hostUuid", host.getHostUuid());
					ja.put(jo);
				}
			}
		}
		return ja;
	}
	
	public JSONObject getPoolHa(String poolUuid) {
		OCPool ocpool = poolService.getPoolById(poolUuid);
		OCHost ochost = hostService.getHostById(ocpool.getPoolMaster());
		JSONObject jsonobject = new JSONObject();
		String haPath = hostCore.getHAPath(ochost);
		jsonobject.put("hapath", haPath);
		jsonobject.put("masterip", ochost.getHostIP());
		return jsonobject;
	}

	/**
	 * @author lining
	 * @param poolUuid haPath hostIp
	 * 保存HA的信息
	 * 
	 */
	private boolean saveHaPath(String poolUuid, String haPath, String hostIp) throws Exception {
		boolean result = false;
		OCPool pool = poolService.getPoolById(poolUuid);
		pool.setHaPath(haPath);
		if (poolService.updatePool(pool)) {
			if (haService.saveHa(poolUuid, haPath)) {
				result = true;
			}
		}
		return result;
	}

	public boolean updateAccessPolicy(OCHa ha)
			throws Exception {
		ha = getSlotInfos(ha);
		return haService.updateHa(ha);
	}
	
	public JSONObject getPowerInfosOfPool(int userId, String poolUuid) throws Exception {
		List<OCHost> hosts = hostService.getAllHostInPool(poolUuid);
		JSONObject jo = new JSONObject();
		int powersNum = 0;
		if (hosts != null) {
			for (OCHost host : hosts) {
				Power power = PowerService.getPower(host.getHostUuid());
				if (power != null) {
					powersNum++;
				}
			}
			if (hosts.size() == powersNum) {
				jo.put("result", 1);
			}else {
				jo.put("result", 0);
			}
		}else {
			jo.put("result", 0);
		}
		if (jo.get("result").equals(0)) {
			message.pushWarning(userId, "电源管理未通过验证，不能启动HA！");
		}
		return jo;
	}

	public boolean startHaService(int userId, String poolUuid, String haPath, String masterIP) {
		//保存一条启动数据记录
		try {
			Date startTime = new Date();
			//开启线程监听
			if(haCore.start(poolUuid, haPath, masterIP)){
				boolean flag = saveHaPath(poolUuid, haPath, masterIP);
				startHaLog(startTime, poolUuid, userId, flag);
				return true;
			}else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean stopHAService(int userId, String poolUuid, String haPath, String masterIP) {
		try {
			Date startTime = new Date();
			if (haCore.stop(poolUuid)) {
				boolean flag = cancelHaPath(poolUuid);
				stopHaLog(startTime, poolUuid, userId, flag);
				return true;
			}else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}		
	}
	
	public OCHa getRecordOfHaPolicy(String poolUuid){
		return haService.getRecordOfHaPolicy(poolUuid);
	}
	
	/**
	 * @author lining
	 * @param poolUuid haPath hostIp
	 * 删除HA的信息
	 * 
	 */
	private boolean cancelHaPath(String poolUuid) throws Exception {
		return haService.cancelHa(poolUuid);
	}
	
	/**
	 * @author lining
	 * @param pool
	 * @return
	 * 转化object 为 JSONObject对象
	 * 
	 */
	private JSONObject getPoolJsonObject(OCPool pool){
		JSONObject jo = new JSONObject();
		jo.put("poolname", TimeUtils.encodeText(pool.getPoolName()));
		jo.put("pooldesc", TimeUtils.encodeText(pool.getPoolDesc()));
		String poolUuid = pool.getPoolUuid();
		jo.put("poolid", poolUuid);
		String poolMaster = pool.getPoolMaster();
		if (poolMaster == null) {
			jo.put("poolmaster", "");
			jo.put("mastername", "");
		} else {
			jo.put("poolmaster", poolMaster);
			jo.put("mastername",
					TimeUtils.encodeText(hostService.getHostById(poolMaster).getHostName()));
		}
		String dcuuid = pool.getDcUuid();
		if (dcuuid == null) {
			jo.put("dcname", "");
			jo.put("dcuuid", "");
		} else {
			jo.put("dcuuid", dcuuid);
			jo.put("dcname", TimeUtils.encodeText(datacenterService.getDatacenterBydcUuid(dcuuid).getDcName()));
		}
		jo.put("createdate", TimeUtils.formatTime(pool.getCreateDate()));
		List<OCHost> hostLists = hostService.getAllHostInPool(poolUuid);
		jo.put("totalcpu", getTotalCpu(hostLists));
		jo.put("totalmem", getTotalMem(hostLists));
		jo = getPoolJSONObject(jo, poolUuid);
		return jo;
	}
	
	/**
	 * @author lining
	 * @param jsonObject
	 * 获取添加HA策略的资源池json对象
	 * 
	 */
	private JSONObject getPoolJSONObject(JSONObject poolJSONObject, String poolUuid) {
		OCHa ha = getRecordOfHaPolicy(poolUuid);
		if (ha != null) {
			poolJSONObject.put("hastartflag", ha.getHaStartFlag());
			poolJSONObject.put("hostmonitor", ha.getHostMonitor());
			poolJSONObject.put("accessflag", ha.getAccessControlPolicy());
			poolJSONObject.put("lefthost", ha.getLeftHost());
			poolJSONObject.put("slotpolicy", ha.getSlotPolicy());
			poolJSONObject.put("slotcpu", ha.getSlotCpu());
			poolJSONObject.put("slotmemory", ha.getSlotMemory());
			poolJSONObject.put("cpupercent", ha.getCpuPercent());
			poolJSONObject.put("memorypercent", ha.getMemoryPercent());
			poolJSONObject.put("migratoryhostid", ha.getMigratoryHostUuid());
		}else {
			poolJSONObject.put("hastartflag", 0);
			poolJSONObject.put("hostmonitor", 0);
			poolJSONObject.put("accessflag", 4);
			poolJSONObject.put("lefthost", 1);
			poolJSONObject.put("slotpolicy", 0);
			poolJSONObject.put("slotcpu", 0);
			poolJSONObject.put("slotmemory", 0);
			poolJSONObject.put("cpupercent", 25);
			poolJSONObject.put("memorypercent", 25);
			poolJSONObject.put("migratoryhostid", 25);
		}
		return poolJSONObject;
	}
	
	/**
	 * @author lining
	 * @param hostlist
	 * 获取资源池内cpu的总数
	 */
	private int getTotalCpu(List<OCHost> hostLists){
		int totalCpu = 0;
		if (hostLists != null) {
			for (OCHost host : hostLists) {
				totalCpu += host.getHostCpu();
			}
		}
		return totalCpu;
	}
	
	/**
	 * @author lining
	 * @param hostlist
	 * 获取一个资源池内内存的大小
	 */
	private int getTotalMem(List<OCHost> hostLists){
		int totalMem = 0;
		if (hostLists != null) {
			for (OCHost host : hostLists) {
				totalMem += host.getHostMem();
			}
		}
		return totalMem;
	}
	
	/**
	 * @author lining
	 * 计算默认插槽数量和预留主机数量
	 */
	private OCHa getSlotInfos (OCHa ha){
		String poolUuid = ha.getPoolUuid();
		if (ha.getAccessControlPolicy()==1) {
			List<OCHost> hostList =hostService.getAllHostInPool(poolUuid);
			int maxCpu=0, maxMem=0;
			Map<String, Object> resultMap =	poolService.getMaxCpuAndMemOfPool(poolUuid);
			//获取同一个资源池中最大的cpu个数和内存容量
			if (ha.getSlotPolicy() == 1) {
				maxCpu = Integer.parseInt(resultMap.get("maxCpu").toString());
				maxMem = Integer.parseInt(resultMap.get("maxMem").toString());
			}else {
				maxCpu = Integer.parseInt(ha.getSlotCpu().toString());
				maxMem = Integer.parseInt(ha.getSlotMemory().toString());
			}
			//获取资源池中现有的vm数量
			int vmNumbers = countVms(hostList);
			//定义一个插槽的数组，装载每个主机中可能容纳的插槽个数
			int leftHost = getLeftHostNumber(hostList, maxCpu, maxMem, vmNumbers);
			ha.setLeftHost(leftHost);
			ha.setSlotCpu(maxCpu);
			ha.setSlotMemory(maxMem);
		}
		return ha;
	}
	
	/**
	 * @author lining
	 * 计算同一个资源池中所有的vm个数
	 * 
	 */
	private int countVms(List<OCHost> hosts){
		int vmOfNumbers = 0;
		if (hosts != null) {
			for (OCHost host:hosts) {
				vmOfNumbers += vmService.countVMNumberOfHost(host.getHostUuid());
			}
		}
		return vmOfNumbers;
	}
	
	/**
	 * @author lining
	 * 计算预留主机数量
	 * 
	 */
	private int getLeftHostNumber(List<OCHost> hosts, int maxCpu, int maxMem, int vmNumbers) {
		int[] slots = new int[hosts.size()];
		int i = 0;
		int totalSlots = 0;
		int leftHost = 0;
		for(OCHost host:hosts){
			int cpuSlot = host.getHostCpu()/maxCpu;
			int memSlot = host.getHostMem()/maxMem;
			if (cpuSlot > memSlot) {
				slots[i] = memSlot;
			}else {
				slots[i] = cpuSlot;
			}
			totalSlots += slots[i];
			i++;
		}
		//对生成的数组升序排序
		Arrays.sort(slots);
		for (int j = slots.length-1; j >= 0; j--) {
			if (totalSlots-slots[j]>=vmNumbers) {
				leftHost++;
			}else {
				break;
			}
		}
		return leftHost;
	}
	
	/**
	 * @author lining
	 * @param startTime poolUuid userId result
	 * 日志操作和推送消息
	 * 
	 */
	private void startHaLog(Date startTime, String poolUuid, int userId, boolean flag){
		Date endTime = new Date();
		JSONArray infoArray = logRecord.createLoginfos(LogRole.HA, poolUuid);
		if (flag) {
			OCLog log = logRecord.addSuccessLog(userId, LogObject.HA, 
					LogAction.ENABLE, infoArray.toString(), startTime, endTime);
			message.pushSuccess(userId, log.toString());
		} else {
			OCLog log = logRecord.addFailedLog(userId, LogObject.HA, 
					LogAction.ENABLE, 
					infoArray.toString(), startTime, endTime);
			message.pushError(userId, log.toString());
		}
	}
	
	/**
	 * @author lining
	 * @param startTime poolUuid userId result
	 * 日志操作和推送消息
	 * 
	 */
	private void stopHaLog(Date startTime, String poolUuid, int userId, boolean flag){
		Date endTime = new Date();
		JSONArray infoArray = logRecord.createLoginfos(LogRole.HA, poolUuid);
		if (flag) {
			OCLog log = logRecord.addSuccessLog(userId, LogObject.HA, 
					LogAction.DISABLE, 
					infoArray.toString(), startTime, endTime);
			message.pushSuccess(userId, log.toString());
		} else {
			OCLog log = logRecord.addFailedLog(userId, LogObject.HA, 
					LogAction.DISABLE, 
					infoArray.toString(), startTime, endTime);
			message.pushError(userId, log.toString());
		}
	}
	
}
