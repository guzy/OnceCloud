package com.beyondsphere.dao;

import java.util.Date;
import java.util.List;


import com.beyondsphere.entity.Router;

public interface RouterDAO {
	public abstract Router getRouter(String routerUuid) ;

	public abstract void updateRouter(Router router);
	
	/**
	 * 获取路由器名称
	 * 
	 * @param routerUuid
	 * @return
	 */
	public abstract String getRouterName(String routerUuid) ;

	/**
	 * 获取使用中的路由器
	 * 
	 * @param routerUuid
	 * @return
	 */
	public abstract Router getAliveRouter(String routerUuid);

	/**
	 * 获取一页用户路由器列表
	 * 
	 * @param userId
	 * @param page
	 * @param limit
	 * @param search
	 * @return
	 */
	public abstract List<Router> getOnePageRouters(int page, int limit,
			String search);

	/**
	 * 获取一页管理员路由器列表
	 * 
	 * @param page
	 * @param limit
	 * @param host
	 * @param importance
	 * @return
	 */
	public abstract List<Router> getOnePageRoutersOfAdmin(int page, int limit,
			String host, int importance);

	/**
	 * 获取一页未设置监控警告的路由器列表
	 * 
	 * @param page
	 * @param limit
	 * @param search
	 * @param userId
	 * @return
	 */
	public abstract List<Router> getOnePageRoutersWithoutAlarm(int page, int limit,
			String search, int userId);

	/**
	 * 获取一页没有绑定公网IP的路由器列表
	 * 
	 * @param page
	 * @param limit
	 * @param search
	 * @param userId
	 * @return
	 */
	public abstract List<Router> getOnePageRoutersWithoutEip(int page, int limit,
			String search, int userId);

	/**
	 * 获取对应监控警告的路由器列表
	 * 
	 * @param routerUID
	 * @param alarmUuid
	 * @return
	 */
	public abstract List<Router> getRoutersOfAlarm(int routerUID, String alarmUuid);

	/**
	 * 获取用户路由器列表
	 * @return
	 */
	public abstract List<Router> getAllRouters();

	/**
	 * 获取用户路由器总数
	 * 
	 * @param userId
	 * @param search
	 * @return
	 */
	public abstract int countRouters(String search);

	/**
	 * 获取管理员路由器总数
	 * 
	 * @param host
	 * @param importance
	 * @return
	 */
	public abstract int countRoutersOfAdmin(String host, int importance) ;

	/**
	 * 获取未设置监控警告的路由器总数
	 * 
	 * @param search
	 * @param routerUID
	 * @return
	 */
	public abstract int countRoutersWithoutAlarm(String search, int uuid);

	/**
	 * 获取未绑定公网IP的路由器总数
	 * 
	 * @param search
	 * @param userId
	 * @return
	 */
	public abstract int countRoutersWithoutEIP(String search, int userId) ;

	/**
	 * 预创建路由器
	 * 
	 * @param uuid
	 * @param pwd
	 * @param userId
	 * @param name
	 * @param mac
	 * @param capacity
	 * @param fwuuid
	 * @param power
	 * @param status
	 * @param createDate
	 * @return
	 */
	public abstract boolean preCreateRouter(String uuid, String pwd, int userId,
			String name, String mac, int power,
			int status, Date createDate);

	/**
	 * 删除路由器
	 * 
	 * @param userId
	 * @param uuid
	 */
	public abstract void removeRouter(int userId, String uuid);

	/**
	 * 更新路由器
	 * 
	 * @param userId
	 * @param uuid
	 * @param pwd
	 * @param power
	 * @param firewallId
	 * @param hostUuid
	 * @param ip
	 */
	public abstract void updateRouter(int userId, String uuid, String pwd, int power,
			 String hostUuid, String ip) ;

	/**
	 * 更新路由器状态
	 * 
	 * @param routerUuid
	 * @param status
	 * @return
	 */
	public abstract boolean updateStatus(String routerUuid, int status);

	/**
	 * 更新路由器电源状态
	 * 
	 * @param uuid
	 * @param powerStatus
	 * @return
	 */
	public abstract boolean updatePowerStatus(String uuid, int powerStatus) ;

	public abstract boolean updateImportance(String uuid, int routerImportance);

	/**
	 * 更新路由器防火墙
	 * 
	 * @param routerUuid
	 * @param firewallId
	 */
	public abstract void updateFirewall(String routerUuid, String firewallId);
	
	/**
	 * 更新路由器防火墙
	 * 
	 * @param routerUuid
	 * @param innerfirewallId
	 */
	public abstract void updateInnerFirewall(String routerUuid, String innerfirewallId);

	/**
	 * 更新路由器名称和描述
	 * 
	 * @param routeruuid
	 * @param routerName
	 * @param routerDesc
	 * @return
	 */
	public abstract boolean updateName(String routeruuid, String routerName,
			String routerDesc);
	/*
	 * 更新路由器所在服务器
	 * 
	 * @param uuid
	 * @param hostUuid
	 * @return
	 */
	public abstract boolean updateHostUuid(String uuid, String hostUuid);

	/**
	 * 更新路由器监控警告
	 * 
	 * @param routerUuid
	 * @param alarmUuid
	 */
	public abstract void updateAlarm(String routerUuid, String alarmUuid);

	/**
	 * 是否有路由器具有该监控警告
	 * 
	 * @param alarmUuid
	 * @return
	 */
	public abstract boolean isNotExistAlarm(String alarmUuid);

	/**
	 * 更新路由器电源状态和所在服务器
	 * 
	 * @param session
	 * @param uuid
	 * @param power
	 * @param hostUuid
	 */
	public abstract void updatePowerAndHostNoTransaction(String uuid, int power,
			String hostUuid);
	
}
