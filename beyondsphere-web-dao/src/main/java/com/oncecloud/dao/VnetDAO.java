package com.oncecloud.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.oncecloud.entity.Vnet;

/**
 * @author zll
 * @version 2014/08/23
 */
@Component
public interface VnetDAO {

	/**
	 * 获取用户私有网络
	 * 
	 * @param vnetUuid
	 * @return
	 */
	public Vnet getVnet(String vnetUuid);

	/**
	 * 获取私有网络名称
	 * 
	 * @param vnetuuid
	 * @return
	 */
	public String getVnetName(String vnetuuid);

	/**
	 * 获取空闲的私有网络ID
	 * 
	 * @return
	 */
	public int getFreeVnetID();
	/**
	 * 获取一页用户私有网络列表
	 * 
	 * @param userId
	 * @param page
	 * @param limit
	 * @param search
	 * @return
	 */
	public List<Vnet> getOnePageVnets(int userId, int page, int limit,
			String search) ;

	/**
	 * 获取用户私有网络列表
	 * 
	 * @param userId
	 * @return
	 */
	public List<Vnet> getVnetsOfUser(int userId) ;

	/**
	 * 获取路由器私有网络列表
	 * 
	 * @param routerUuid
	 * @return
	 */
	public List<Vnet> getVnetsOfRouter(String routerUuid) ;

	/**
	 * 获取路由器私有网络数目
	 * 
	 * @param routerUuid
	 * @param userId
	 * @return
	 */
	public int countVnetsOfRouter(String routerUuid, int userId) ;

	/**
	 * 获取用户私有网络总数
	 * 
	 * @param userId
	 * @param search
	 * @return
	 */
	public int countVnets(int userId, String search);

	/**
	 * 查看是否被占用
	 * 
	 * @param userId
	 * @param routerid
	 * @param net
	 * @return
	 */
	public int countAbleNet(int userId, String routerid, Integer net) ;

	/**
	 * 创建私有网络
	 * 
	 * @param uuid
	 * @param userId
	 * @param name
	 * @param desc
	 * @return
	 */
	public boolean createVnet(String uuid, int userId, String name, String desc) ;

	/**
	 * 删除私有网络
	 * 
	 * @param userId
	 * @param uuid
	 * @return
	 */
	public boolean removeVnet(int userId, String uuid) ;
	
	/**
	 * 更新私有网络
	 * 
	 * @param vnet
	 * @return
	 */
	public boolean updateVnet(Vnet vnet);
	
	/**
	 * 更新私有网络
	 * 
	 * @param vnetuuid
	 * @param newName
	 * @param description
	 * @return
	 */
	public boolean updateVnet(String vnetuuid, String newName,
			String description);

	/**
	 * 私有网络连接路由器
	 * 
	 * @param vnetuuid
	 * @param routerUuid
	 * @param net
	 * @param gate
	 * @param start
	 * @param end
	 * @param dhcpStatus
	 * @param mac
	 * @return
	 */
	public boolean linkToRouter(String vnetuuid, String routerUuid,
			Integer net, Integer gate, Integer start, Integer end,
			Integer dhcpStatus, String vifUuid, String vifMac);

	/**
	 * 私有网络离开路由器
	 * 
	 * @param vnetuuid
	 * @return
	 */
	public boolean unlinkRouter(String vnetuuid) ;

	public List<Vnet> getAvailableVnet(Integer userId) ;
}
