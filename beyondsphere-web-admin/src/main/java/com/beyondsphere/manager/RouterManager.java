package com.beyondsphere.manager;


import org.json.JSONArray;
import org.json.JSONObject;

public interface RouterManager {
	/**
	 * 创建路由器
	 * @param userId
	 * @return
	 */
	public abstract JSONArray routerQuota(int userId) ;

	/**
	 * 创建路由器
	 * @param uuid
	 * @param userId
	 * @param name
	 * @param capacity
	 * @param fwuuid
	 * @param poolUuid
	 */
	public abstract boolean createRouter(String uuid, int userId, String name,String poolUuid);
	
	
	/**
	 * 启动路由器
	 * @param uuid
	 * @param userId
	 * @param poolUuid
	 */
	public abstract void startRouter(String uuid, int userId, String poolUuid) ;
	
	/**
	 * 销毁路由
	 * @param uuid
	 * @param userId
	 * @param poolUuid
	 */
	public abstract void deleteRouter(String uuid, int userId, String poolUuid);
	
	/**
	 * 获取用户路由器列表
	 * 
	 * @param userId
	 * @param page
	 * @param limit
	 * @param search
	 * @return
	 */
	public abstract JSONArray getRouterList(int page, int limit,
			String search);
	
	/**
	 * 获取用户路由器列表
	 * @param userId
	 * @return
	 */
	public abstract JSONArray getRoutersOfUser();
	
	//
	public abstract JSONObject doLinkRouter(int userId, String vnetUuid,
			String routerUuid, int net, int gate, int start, int end,
			int dhcpState);
	public abstract boolean isRouterHasVnets(String routerUuid, int userId);
	
	
	public abstract void shutdownRouter(String uuid, String force, int userId,
			String poolUuid);
	
	public abstract JSONObject doUnlinkRouter(String vnetUuid, int userId);
	
	public abstract JSONObject checkRouterip(String routerip);

}
