package com.beyondsphere.core;

public interface PoolHACore {
	
	/**
	 * 停止资源池的高可用
	 * @author lining
	 * @param poolUuid
	 * @return boolean
	 */
	public boolean stop(String poolUuid);
	
	/**
	 * 启用资源池的高可用
	 * @author lining
	 * @param poolUuid(资源池id), haPath(高可用路径）, masterIp(主节点ip)
	 * @return boolean
	 */
	public boolean start(String poolUuid,String haPath,String masterIP);
	
	/**
	 * 判断资源池是否开启高可用
	 * @author lining
	 * @param poolUuid
	 * @return boolean
	 */
	public boolean isStarted(String poolUuid);

}
