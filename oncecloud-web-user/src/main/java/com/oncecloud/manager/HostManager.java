package com.oncecloud.manager;

import org.json.JSONArray;

public interface HostManager {
	
	/**
	 * 分页获取服务器列表
	 * @author lining
	 * @param page, limit, search
	 * @return
	 */
	public abstract JSONArray getHostList(int page, int limit, String search);
	
	/**
	 * 创建服务器
	 * @author lining
	 * @param hostName, hostPwd, hostDesc, hostIp, hostType, userId
	 * @return
	 */
	public abstract JSONArray createHost(String hostName, String hostPwd,
			String hostDesc, String hostIp, String hostType,
			int userid);
	
	/**
	 * 添加服务器到资源池
	 * @author lining
	 * @param uuidJsonStr, hasMaster, poolUuid, userid
	 * @return
	 */
	public abstract JSONArray add2Pool(String uuidJsonStr, String hasMaster,
			String poolUuid, int userid);
	
	/**
	 * 根据ip获取服务器
	 * @author lining
	 * @param address
	 * @return
	 */
	public abstract JSONArray queryAddress(String address);
	
	/**
	 * 获取添加到资源池的服务器列表
	 * @author lining
	 * @param uuidJsonStr
	 * @return
	 */
	public abstract JSONArray getTablePool(String uuidJsonStr);
}
