/**
 * @author hty
 * @time 上午11:05:48
 * @date 2014年12月9日
 */
package com.beyondsphere.manager.Impl;

import java.util.List;

import javax.annotation.Resource;

import org.json.JSONArray;
import org.springframework.stereotype.Service;

import com.beyondsphere.manager.PoolManager;
import com.oncecloud.core.PoolCore;
import com.oncecloud.entity.OCHost;
import com.oncecloud.entity.OCPool;
import com.oncecloud.entity.PoolRecord;
import com.oncecloud.message.MessageUtil;
import com.oncecloud.service.HostService;
import com.oncecloud.service.PoolService;
import com.oncecloud.service.VMService;

@Service("poolManager")
public class PoolManagerImpl implements PoolManager {

	@Resource
	private PoolService poolService;
	@Resource
	private HostService hostService;
	@Resource
	private VMService vmService;
	@Resource
	private PoolCore poolCore;
	@Resource
	private MessageUtil message;

	public JSONArray createPool(String poolName, String poolType, String poolDesc,
			String dcUuid, String dcName, int userId) {
		return poolService.createPool(poolName, poolType, poolDesc, dcUuid, dcName, userId);
	}
	 
	public JSONArray checkout(String poolname){
		return poolService.checkoutPool(poolname);
	}
	public JSONArray getPoolList(int page, int limit, String search) {
		return poolService.getPoolList(page, limit, search);
	}

	public JSONArray deletePool(String poolId, String poolName, int userId) {
		return poolService.deletePool(poolId, poolName, userId);
	}

	public void updatePool(String poolUuid, String poolName, String poolType, String poolDesc,
			String dcUuid, int userId) {
		poolService.updatePool(poolUuid, poolName, poolType, poolDesc, dcUuid, userId);
	}

	public JSONArray getAllPool() {
		return poolService.getAllPool();
	}

	public void keepAccordance(int userId, String poolUuid) {
		boolean result = false;
		List<PoolRecord> prList = poolCore.getPoolRecordList(poolUuid);
		if (prList != null) {
			try {
				for (PoolRecord sr : prList) {
					String uuid = sr.getUuid();
					String powerStatus = sr.getPowerStatus();
					String hostUuid = sr.getHostUuid();
					int power = powerStatus.equals("Running") ? 1 : 0;
					String type = sr.getType();
					if (type.equals("instance")) {
						vmService.updatePowerAndHost(uuid, power, hostUuid);
					}
				}
				result = true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (result) {
			message.pushSuccess(userId, "资源池状态已保持一致");
		} else {
			message.pushError(userId, "资源池状态未保持一致");
		}
	}

	public void repool(String poolUuid, int userId) {
		OCHost masterHost = hostService.getMasterOfPool(poolUuid);
		if (poolCore.create(poolUuid)) {
			message.pushSuccess(userId, "主节点恢复成功");
		}else {
			message.pushError(userId, "主节点恢复失败");
		}
		List<OCHost> lisHosts = hostService.getAllHostInPool(poolUuid); 
		for (OCHost ocHost : lisHosts) {
			String thisHostUuid = ocHost.getHostUuid();
			if (thisHostUuid.equals(masterHost.getHostUuid())) {
				continue;
			} else {
				
				poolCore.joinPool(userId, masterHost, ocHost);
			}
		}
	}

	public int countAllPoolList(String poolname) {
		return poolService.countAllPoolList(poolname);
	}

	public List<OCPool> getOnePagePoolList(int page, int limit, String search) {
		return poolService.getOnePagePoolList(page, limit, search);
	}

	public List<OCPool> getOnePagePoolListOfHA(int page, int limit,
			String search) {
		return poolService.getOnePagePoolListOfHA(page, limit, search);
	}

	public OCPool getPoolById(String poolUuid) {
		return poolService.getPoolById(poolUuid);
	}

	public boolean updatePool(OCPool pool) {
		return poolService.updatePool(pool);
	}
}
