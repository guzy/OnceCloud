package com.oncecloud.manager;

import org.json.JSONArray;
import org.json.JSONObject;

import com.oncecloud.entity.OCHa;

public interface HaManager {
	/**
	 * @author lining
	 * @param page limit search
	 * 获取高可用资源池列表
	 * */
	public abstract JSONArray getPoolList(int page, int limit, String search) throws Exception;
	
	/**
	 * @author lining
	 * @param poolUuid
	 * 获取高可用资源池列表
	 * */
	public abstract JSONArray getMigrateHostList(String poolUuid) throws Exception;
	
	/**
	 * @author lining
	 * @param poolUuid
	 * 获取开启HA资源池的信息
	 * 
	 */
	public abstract JSONObject getPoolHa(String poolUuid) throws Exception;
	
	/**
	 * @author lining
	 * @param Map<String object> 
	 * 更新高可用接入控制的策略
	 */
	public abstract boolean updateAccessPolicy(OCHa ha) throws Exception;
	
	/**
	 * @author lining
	 * @param poolUuid
	 * 获取高可用策略的记录
	 */
	public abstract OCHa getRecordOfHaPolicy(String poolUuid) throws Exception;
	
	/**
	 * @author lining
	 * @param poolUuid
	 * 查看资源池里的主机是否已经开启电源管理 
	 */
	public abstract JSONObject getPowerInfosOfPool(int userId, String poolUuid) throws Exception;
	
	/**
	 * @author lining
	 * @param poolUuid hapath masterIP
	 * 启用HA服务
	 */
	public abstract boolean startHaService(int userId, String poolUuid,String haPath,String masterIP);
	
	/**
	 *@author lining
	 *@param poolUuid haPath masterIP
	 *关闭HA服务 
	 */
	public abstract boolean stopHAService(int userId, String poolUuid, String haPath, String masterIP);
}
