package com.oncecloud.dao;

import java.util.List;

import com.oncecloud.entity.OCNetwork;

public interface NetworkDAO {
	
	/**
	 * @author lining
	 * @param page, limit, search
	 * 获取网络中一页交换机的列表
	 * 
	 */
	public abstract List<OCNetwork> getOnePageListOfNetwork(int page, int limit) throws Exception;
	
	/**
	 * @author lining
	 * @param poolUuid
	 * @return
	 * 根据资源池id获取交换机列表
	 * 
	 */
	public abstract List<OCNetwork> getNetworkListByPoolUuid(int type) throws Exception;
	
	/**
	 * @author lining
	 * @param netId
	 * 根据网络id获取交换机
	 * 
	 */
	public abstract OCNetwork getSwitch(String netId);
	
	/**
	 * @author lining
	 * @param netId
	 * 根据网络id获取交换机
	 * 
	 */
	public abstract OCNetwork getSwitchByTypeAndId(String vlanType, String vlanId) throws Exception;
	
	/**
	 * @author lining
	 * @param switchType
	 * 根据交换机的类型统计交换机数量
	 * 
	 */
	public abstract int countNetworkListByType(int vlanType) throws Exception;
	
	/**
	 * @author lining
	 * @param network
	 * 在网络中增加交换机
	 * 
	 */
	public abstract boolean insertSwitch(OCNetwork network) throws Exception;
	
	/**
	 *@author lining
	 *@param network
	 *更新网络中的switch 
	 * 
	 */
	public abstract boolean updateSwitch(OCNetwork network) throws Exception;
	
	/**
	 *@author lining
	 *@param network
	 *更新网络中的switch 
	 * 
	 */
	public abstract boolean updateVxlan(OCNetwork network) throws Exception;
	
	/**
	 * @author lining
	 * @param netId
	 * 根据网络id删除网络中的交换机
	 * 
	 */
	public abstract boolean deleteSwitchByNetid(String netId) throws Exception;
	
	/**
	 * 获取路由器私有网络列表
	 * 
	 * @param routerUuid
	 * @return
	 */
	public abstract List<OCNetwork> getVnetsOfRouter(String routerUuid);
	
	/**
	 * 获取路由器私有网络数目
	 * 
	 * @param routerUuid
	 * @param userId
	 * @return
	 */
	public abstract int countVnetsOfRouter(String routerUuid, int userId);
	
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
	public abstract boolean linkToRouter(String vnetuuid, String routerUuid,
			Integer net, Integer gate, Integer start, Integer end,
			Integer dhcpStatus, String vifUuid, String vifMac);
	
	/**
	 * 获取vlan列表
	 * @return
	 * @throws Exception 
	 */
	public abstract List<OCNetwork> getVlansOfNetwork() throws Exception;
	
	/**
	 * 获取制定路由器下的vxlan列表
	 * @return
	 * @throws Exception 
	 */
	public abstract List<OCNetwork> getVxlansByRouterid(String routerid) throws Exception;
	
	/**
	 * 私有网络离开路由器
	 * 
	 * @param vnetuuid
	 * @return
	 */
	public abstract boolean unlinkRouter(String vnetuuid);
	
	/**
	 *@author lining
	 *@param network
	 *更新网络中的switch 
	 * 
	 */
	public abstract boolean clearRouter(String routerid) throws Exception;
}
