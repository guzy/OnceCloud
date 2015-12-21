/**
 * @author hty
 * @time 上午11:05:48
 * @date 2014年12月9日
 */
package com.oncecloud.manager;

import java.util.List;

import org.json.JSONArray;

import com.oncecloud.entity.OCPool;

public interface PoolManager {

	public abstract JSONArray createPool(String poolName, String poolType,
			String poolDesc, String dcUuid, String dcName, int userId);

	public abstract JSONArray checkout(String poolname);

	public abstract JSONArray getPoolList(int page, int limit, String search);

	public abstract JSONArray deletePool(String poolId, String poolName,
			int userId);

	public abstract void updatePool(String poolUuid, String poolName,
			String poolType, String poolDesc, String dcUuid, int userId);

	public abstract boolean updatePool(OCPool pool);

	public abstract JSONArray getAllPool();

	public abstract void keepAccordance(int userId, String poolUuid);

	public abstract void repool(String poolUuid, int userId);

	/**
	 * @author lining
	 * @param poolname
	 *            获取资源池的个数
	 * @return int
	 */
	public abstract int countAllPoolList(String poolname);

	/**
	 * @author lining
	 * @param poolname
	 *            获取资源池
	 * @return int
	 */
	public abstract OCPool getPoolById(String poolUuid);

	/**
	 * @author lining
	 * @param page
	 *            limit search 资源池的分页展示
	 * @return list
	 */
	public abstract List<OCPool> getOnePagePoolList(int page, int limit,
			String search);

	/**
	 * @author lining
	 * @param page
	 *            limit search 资源池的分页展示
	 * @return list
	 */
	public abstract List<OCPool> getOnePagePoolListOfHA(int page, int limit,
			String search);
}
