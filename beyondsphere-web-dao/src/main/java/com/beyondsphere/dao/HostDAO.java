/**
 * @author hty
 * @time 上午11:05:48
 * @date 2014年12月9日
 */
package com.beyondsphere.dao;

import java.util.List;

import com.beyondsphere.entity.OCHost;
import com.beyondsphere.entity.Storage;

public interface HostDAO {
	
	/**
	 * 模糊查询主机个数
	 * @param search
	 * @return int
	 */
	public abstract int countAllHostList(String search);
	
	/**
	 * 获取主机列表
	 * @return list
	 */	
	public abstract List<OCHost> getAllHost();
	
	/**
	 * 获取主机
	 * @param hostUuid
	 * @return OCHost
	 */
	public abstract OCHost getHost(String hostUuid);
	
	/**
	 * 不创建事物获取主机
	 * @param hostUuid
	 * @return OCHost
	 */
	public abstract OCHost getHostNoTransactional(String hostUuid);

	/**
	 * 根据hostIP获取主机
	 * @param hostIp
	 * @return OCHost
	 */	
	public abstract OCHost getHostFromIp(String hostIp);
	
	/**
	 * 获取资源池内的主机
	 * @param poolUuid
	 * @return list
	 */
	public abstract List<OCHost> getHostListOfPool(String poolUuid);
	
	/**
	 * 分页获取主机列表
	 * @param page, limit, search
	 * @return list
	 */
	public abstract List<OCHost> getOnePageHostList(int page, int limit,
			String search);

	/**
	 * 分页获取不是绑定同一个物理存储的主机
	 * @param page, limit, search, sruuid
	 * @return boolean
	 */
	public abstract List<OCHost> getOnePageLoadHostList(int page, int limit,
			String search, String sruuid);

	/**
	 * 获取主机内存储的信息
	 * @param hostUuid
	 * @return list
	 */
	public abstract List<Storage> getSROfHost(String hostUuid);

	/**
	 * 更新主机内资源池信息
	 * @param hostUuid, poolUuid
	 * @return boolean
	 */
	public abstract boolean setPool(String hostUuid, String poolUuid);

	/**
	 * 更新主机的物理存储
	 * @param hostUuid, srUuid
	 * @return boolean
	 */
	public abstract boolean unbindSr(String hostUuid, String srUuid);

	/**
	 * 保存主机
	 * @param host
	 * @return boolean
	 */
	public abstract boolean saveHost(OCHost host);

	/**
	 * 更新主机
	 * @param hostId, hostName, hostDesc, hostType
	 * @return boolean
	 */
	public abstract boolean updateHost(String hostId, String hostName,
			String hostDesc, String hostType);

	/**
	 * 删除主机
	 * @param hostId
	 * @return boolean
	 */
	public abstract boolean deleteHost(String hostId);
	
	/**
	 * 更新主机
	 * @param host
	 * @return boolean
	 */
	public abstract boolean update(OCHost host);

	/**
	 * 获取不在资源池内的主机列表
	 * @return list
	 */
	public abstract List<OCHost> getAllHostNotInPool();
}
