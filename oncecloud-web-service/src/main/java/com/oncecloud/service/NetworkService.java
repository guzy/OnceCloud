package com.oncecloud.service;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.oncecloud.entity.OCNetwork;

public interface NetworkService {
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
	 * @param netId
	 * 根据网络id获取交换机
	 * 
	 */
	public abstract JSONArray getSwitch(String netId) throws Exception;
	
	/**
	 * 获取虚拟网络信息
	 * @author lining
	 * @param netId
	 * 
	 */
	public abstract OCNetwork getVnetwork(String netId);
	
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
	
	/**
	 * 根据netid获取网络信息
	 * @author lining
	 * @param netid
	 * 
	 */
	public abstract JSONObject getSwitchObj(String netId);
	
}
