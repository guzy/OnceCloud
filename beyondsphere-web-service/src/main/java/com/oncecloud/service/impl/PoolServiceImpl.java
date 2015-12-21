package com.oncecloud.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.oncecloud.dao.DatacenterDAO;
import com.oncecloud.dao.HostDAO;
import com.oncecloud.dao.PoolDAO;
import com.oncecloud.dao.UserDAO;
import com.oncecloud.dao.VMDAO;
import com.oncecloud.entity.OCHost;
import com.oncecloud.entity.OCLog;
import com.oncecloud.entity.OCPool;
import com.oncecloud.entity.User;
import com.oncecloud.log.LogAction;
import com.oncecloud.log.LogObject;
import com.oncecloud.log.LogRecord;
import com.oncecloud.log.LogRole;
import com.oncecloud.message.MessageUtil;
import com.oncecloud.service.PoolService;
import com.oncecloud.util.TimeUtils;

@Component("PoolService")
public class PoolServiceImpl implements PoolService {

	@Resource
	private DatacenterDAO datacenterDAO;
	@Resource
	private PoolDAO poolDAO;
	@Resource
	private HostDAO hostDAO;
	@Resource
	private VMDAO vmDAO;
	@Resource
	private UserDAO userDAO;
	@Resource
	private LogRecord logRecord;
	@Resource
	private MessageUtil message;

	public JSONArray createPool(String poolName, String poolType,
			String poolDesc, String dcUuid, String dcName, int userId) {
		String poolUuid = UUID.randomUUID().toString();
		Date startTime = new Date();
		int poolNum = poolDAO.getPoolList().size();
		if (poolNum == 0) {
			User user = userDAO.getUser(userId);
			user.setUserAllocate(poolUuid);
			userDAO.updateUser(user);
		}
		OCPool poolResult = poolDAO.createPool(poolUuid, poolName, poolType,
				poolDesc, dcUuid);
		Date endTime = new Date();
		JSONArray ja = new JSONArray();
		if (poolResult != null) {
			JSONObject jo = new JSONObject();
			jo.put("poolname", TimeUtils.encodeText(poolResult.getPoolName()));
			jo.put("poolid", poolResult.getPoolUuid());
			jo.put("dcname", TimeUtils.encodeText(dcName));
			jo.put("pooldesc", TimeUtils.encodeText(poolResult.getPoolDesc()));
			jo.put("dcuuid", dcUuid);
			String poolMaster = poolResult.getPoolMaster();
			if (poolMaster == null) {
				poolMaster = "";
			}
			jo.put("poolmaster", poolMaster);
			jo.put("createdate",
					TimeUtils.formatTime(poolResult.getCreateDate()));
			ja.put(jo);
		}

		JSONArray infoArray = logRecord.createLoginfos(LogRole.POOL, poolName);
		if (poolResult != null) {
			OCLog log = logRecord.addSuccessLog(userId, LogObject.POOL,
					LogAction.CREATE, infoArray.toString(), startTime, endTime);
			message.pushSuccess(userId, log.toString());
		} else {

			OCLog log = logRecord.addFailedLog(userId, LogObject.POOL,
					LogAction.CREATE, infoArray.toString(), startTime, endTime);
			message.pushError(userId, log.toString());
		}
		return ja;
	}

	public JSONArray checkoutPool(String poolname) {
		JSONArray ja = new JSONArray();

		boolean flag = poolDAO.checkoutpool(poolname);
		System.out .println("数据为真假："+flag);
		JSONObject jo = new JSONObject();
		jo.put("flag", flag);
		ja.put(jo);
		

		return ja;
	}

	public JSONArray getPoolList(int page, int limit, String search) {
		JSONArray ja = new JSONArray();
		int totalNum = countAllPoolList(search);
		ja.put(totalNum);
		List<OCPool> poolList = getOnePagePoolList(page, limit, search);
		if (poolList != null) {
			for (OCPool result : poolList) {
				JSONObject jo = new JSONObject();
				jo.put("poolname", TimeUtils.encodeText(result.getPoolName()));
				jo.put("pooldesc", TimeUtils.encodeText(result.getPoolDesc()));
				String poolUuid = result.getPoolUuid();
				jo.put("poolid", poolUuid);
				String poolMaster = result.getPoolMaster();
				if (poolMaster == null) {
					jo.put("poolmaster", "");
					jo.put("mastername", "");
				} else {
					jo.put("poolmaster", poolMaster);
					jo.put("mastername", TimeUtils.encodeText(hostDAO.getHost(
							poolMaster).getHostName()));
				}
				String dcuuid = result.getDcUuid();
				if (dcuuid == null) {
					jo.put("dcname", "");
					jo.put("dcuuid", "");
				} else {
					jo.put("dcuuid", dcuuid);
					jo.put("dcname",
							TimeUtils.encodeText(datacenterDAO.getDatacenter(
									dcuuid).getDcName()));
				}
				jo.put("createdate",
						TimeUtils.formatTime(result.getCreateDate()));
				List<Integer> volumeList = this.getPoolVolume(poolUuid);
				jo.put("totalcpu", volumeList.get(0));
				jo.put("totalmem", volumeList.get(1));
				ja.put(jo);
			}
		}
		return ja;
	}

	public JSONArray deletePool(String poolId, String poolName, int userId) {
		boolean result = false;
		Date startTime = new Date();
		int hostOfPool = hostDAO.getHostListOfPool(poolId).size();
		if (hostOfPool != 0) {
			message.pushWarning(userId, "资源池中存在服务器，请移除服务器再删除！");
		} else {
			result = poolDAO.deletePool(poolId);
			Date endTime = new Date();
			JSONArray infoArray = logRecord.createLoginfos(LogRole.POOL,
					poolName);
			if (result) {
				OCLog log = logRecord.addSuccessLog(userId, LogObject.POOL,
						LogAction.DELETE, infoArray.toString(), startTime,
						endTime);
				message.pushSuccess(userId, log.toString());
			} else {

				OCLog log = logRecord.addFailedLog(userId, LogObject.POOL,
						LogAction.DELETE, infoArray.toString(), startTime,
						endTime);
				message.pushError(userId, log.toString());
			}
		}
		JSONArray ja = new JSONArray();
		JSONObject jo = new JSONObject();
		jo.put("result", result);
		ja.put(jo);
		return ja;
	}

	public void updatePool(String poolUuid, String poolName, String poolType,
			String poolDesc, String dcUuid, int userId) {
		boolean result = poolDAO.updatePool(poolUuid, poolName, poolType,
				poolDesc, dcUuid);
		if (result) {
			message.pushSuccess(userId, "资源池更新成功");
		} else {
			message.pushError(userId, "资源池更新失败");
		}
	}

	public boolean updatePool(OCPool pool) {
		return poolDAO.update(pool);
	}

	public JSONArray getAllPool() {
		JSONArray ja = new JSONArray();
		List<OCPool> poolList = poolDAO.getPoolList();
		if (poolList != null) {
			for (OCPool pool : poolList) {
				JSONObject jo = new JSONObject();
				jo.put("poolname", TimeUtils.encodeText(pool.getPoolName()));
				jo.put("poolid", pool.getPoolUuid());
				ja.put(jo);
			}
		}
		return ja;
	}

	public List<OCPool> getPoolList() {
		return poolDAO.getPoolList();
	}

	public int countAllPoolList(String poolname) {
		return poolDAO.countAllPoolList(poolname);
	}

	public OCPool getPoolById(String poolUuid) {
		return poolDAO.getPool(poolUuid);
	}

	public List<OCPool> getOnePagePoolList(int page, int limit, String search) {
		return poolDAO.getOnePagePoolList(page, limit, search);
	}

	public List<OCPool> getOnePagePoolListOfHA(int page, int limit,
			String search) {
		return poolDAO.getOnePagePoolListOfHA(page, limit, search);
	}

	private List<Integer> getPoolVolume(String poolUuid) {
		int totalCpu = 0;
		int totalMemory = 0;
		List<OCHost> hostList = hostDAO.getHostListOfPool(poolUuid);
		if (hostList != null) {
			for (OCHost host : hostList) {
				totalCpu += host.getHostCpu();
				totalMemory += host.getHostMem();
			}
		}
		List<Integer> volumeList = new ArrayList<Integer>();
		volumeList.add(totalCpu);
		volumeList.add(totalMemory);
		return volumeList;
	}

	public Map<String, Object> getMaxCpuAndMemOfPool(String poolUuid) {
		return vmDAO.getMaxCpuAndMemByPoolUuid(poolUuid);
	}

}
