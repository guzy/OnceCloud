package com.oncecloud.core;

import java.util.List;

import org.Xen.API.Host;

import com.oncecloud.entity.OCHost;
import com.oncecloud.entity.PoolRecord;

public interface PoolCore {
	
	/**
	 * 从物理服务器获取资源池记录列表
	 * @author lining
	 * @param poolUuid
	 * @return
	 */
	public List<PoolRecord> getPoolRecordList(String poolUuid);
	
	/**
	 * 连接资源池
	 * @author lining
	 * @param poolUuid
	 * @return
	 */
	public boolean create(String poolUuid);
	
	/**
	 * 断开资源池连接
	 * @author lining
	 * @param poolUuid
	 * @return
	 */
	public boolean ejectPoolConnection(String poolUuid, Host host);
	
	/**
	 * 加入资源池
	 * @author lining
	 * @param host
	 * @return
	 */
	public boolean joinPool(Integer userId, OCHost master, OCHost host);
}
