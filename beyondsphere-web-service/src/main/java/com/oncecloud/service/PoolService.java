package com.oncecloud.service;

import java.util.List;
import java.util.Map;

import org.json.JSONArray;

import com.oncecloud.entity.OCPool;

public interface PoolService {
	
	/**
	 * 创建资源池
	 * @author lining
	 * @param poolName, poolType, poolDesc, dcUuid, dcName, userId
	 * @return
	 */
	public abstract JSONArray createPool(String poolName, String poolType, String poolDesc,
			String dcUuid, String dcName, int userId);
	public abstract JSONArray checkoutPool(String poolname);
	
	/**
	 * 分页获取资源池列表
	 * @author lining
	 * @param page, limit, search
	 * @return
	 */
	public abstract JSONArray getPoolList(int page, int limit, String search);
	
	/**
	 * 删除资源池
	 * @author lining
	 * @param poolId, poolName, userId
	 * @return
	 */
	public abstract JSONArray deletePool(String poolId, String poolName,
			int userId);
	
	/**
	 * 更新资源池
	 * @author lining
	 * @param poolUuid, poolName, poolType, poolDesc, dcUuid, userId
	 * @return
	 */
	public abstract void updatePool(String poolUuid, String poolName, String poolType,
			String poolDesc, String dcUuid, int userId);
	
	/**
	 * 更新资源池
	 * @author lining
	 * @param pool
	 * @return
	 */
	public abstract boolean updatePool(OCPool pool);
	
	/**
	 * 获取所有的资源池
	 * @author lining
	 * @return
	 */
	public abstract JSONArray getAllPool();
	
	/**
	 * 获取所有的资源池
	 * @author lining
	 * @return
	 */
	public abstract List<OCPool> getPoolList();
	
	/**
	 * @author lining
	 * @param poolname
	 * 获取资源池的个数
	 * @return int
	 */
	public abstract int countAllPoolList(String poolname);
	
	/**
	 * @author lining
	 * @param poolname
	 * 获取资源池
	 * @return int
	 */
	public abstract OCPool getPoolById(String poolUuid);
	
	/**
	 * @author lining
	 * @param page limit search
	 * 资源池的分页展示
	 * @return list
	 */
	public abstract List<OCPool> getOnePagePoolList(int page, int limit, String search);
	
	/**
	 * @author lining
	 * @param page limit search
	 * 资源池的分页展示
	 * @return list
	 */
	public abstract List<OCPool> getOnePagePoolListOfHA(int page, int limit, String search);
	
	/**
	 * 获取资源池最大cpu和memory
	 * @author lining
	 * @param poolUuid
	 * @return
	 */
	public abstract Map<String, Object> getMaxCpuAndMemOfPool(String poolUuid);
	
}
