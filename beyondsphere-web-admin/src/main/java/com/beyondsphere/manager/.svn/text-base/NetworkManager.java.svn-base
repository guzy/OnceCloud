package com.beyondsphere.manager;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.beyondsphere.entity.OCNetwork;
public interface NetworkManager {
	
	/**
	 * @author lining
	 * @param page, limit, search
	 * 获取网络中一页交换机的列表
	 * 
	 */
	public abstract JSONArray getOnePageListOfNetwork(int page, int limit, int vlanType) throws Exception;
	
	
	/**
	 * @author lining
	 * @param poolUuid
	 * @return
	 * 根据资源池id获取交换机列表
	 * 
	 */
	public abstract JSONArray getNetworkListByPoolUuid(int type) throws Exception;
	
	/**
	 * @author lining
	 * 获取资源池列表
	 * @return
	 * 
	 */
	public abstract JSONArray getPool() throws Exception;
	
	/**
	 * @author lining
	 * @param netId
	 * 根据网络id获取交换机
	 * 
	 */
	public abstract JSONArray getSwitch(String netId) throws Exception;
	
	/**
	 * @author lining
	 * @param netId
	 * 根据网络id获取交换机
	 * 
	 */
	public abstract OCNetwork getSwitchByTypeAndId(String vlanType, String vlanId) throws Exception;
	
	/**
	 * @author lining
	 * @param page, limit, search
	 * 获取网络中一页交换机的列表
	 * 
	 */
	public abstract List<OCNetwork> getOnePageListOfNetwork(int page, int limit) throws Exception;
	
	/**
	 * @author lining
	 * @param network
	 * 在网络中增加交换机
	 * 
	 */
	public abstract boolean createSwitch(OCNetwork network, int userId) throws Exception;
	
	/**
	 *@author lining
	 *@param network
	 *更新网络中的switch 
	 * 
	 */
	public abstract boolean updateSwitch(OCNetwork network, int userId) throws Exception;
	
	/**
	 * @author lining
	 * @param netId
	 * 根据网络id删除网络中的交换机
	 * 
	 */
	public abstract boolean destorySwitchByNetid(String netId, int userId) throws Exception;
	
	public abstract boolean isRouterHasVnets(String routerUuid, int userId);
	
	/**
	 * 连接vlan到路由器
	 * @param userId
	 * @param vnetUuid
	 * @param routerId
	 * @param net
	 * @param gate
	 * @param start
	 * @param end
	 * @param dhcpState
	 * @param content
	 * @param conid
	 * @return
	 */
	public abstract JSONObject linkRouter(int userid,String vnetUuid, String routerId,
			Integer net, Integer gate, Integer start, Integer end,
			Integer dhcpState, String content, String conid);
	
	public abstract JSONArray getVlansOfNetwork() throws Exception;
	
	public abstract JSONArray getVxlansByRouterid(String routerid) throws Exception;
	
	public abstract JSONObject unlinkRouter(String vnetUuid, int userId);
	
	public abstract JSONObject checkRouter(String routerid);
	
	public abstract JSONObject clearRouter(String routerid);

}
