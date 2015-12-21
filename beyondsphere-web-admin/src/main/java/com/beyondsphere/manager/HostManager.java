/**
 * @author hty
 * @time 上午11:05:48
 * @date 2014年12月9日
 */
package com.beyondsphere.manager;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.oncecloud.entity.OCHost;

public interface HostManager {

	public final static String DEFAULT_USER = "root";
	public final static int DEFAULT_PORT = 9363;

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
	 * 分页获取服务器列表
	 * @author lining
	 * @param page, limit, search
	 * @return
	 */
	public abstract JSONArray getHostList(int page, int limit, String search);

	/**
	 * 分页获取加载相同存储的服务器列表
	 * @author lining
	 * @param page, limit, search
	 * @return
	 */
	public abstract JSONArray getHostLoadList(int page, int limit,
			String searchStr, String srUuid);
	
	/**
	 * 获取服务器的存储
	 * @author lining
	 * @param hostUuid
	 */
	public abstract JSONArray getSrOfHost(String hostUuid);
	
	/**
	 * 卸载服务器存储
	 * @author lining
	 * @param hostUuid, srUuid, userid
	 */
	public abstract JSONObject unbindSr(String hostUuid, String srUuid, int userid);
	
	/**
	 * 判断是否同一个存储
	 * @author lining
	 * @param uuidJsonStr
	 * @return
	 */
	public abstract JSONArray isSameSr(String uuidJsonStr);
	
	/**
	 * 获取添加到资源池的服务器列表
	 * @author lining
	 * @param uuidJsonStr
	 * @return
	 */
	public abstract JSONArray getTablePool(String uuidJsonStr);
	
	/**
	 * 删除服务器
	 * @author lining
	 * @param hostId, hostName, userid
	 * @return
	 */
	public abstract JSONArray deleteHost(String hostId, String hostName,
			int userid);
	
	/**
	 * 根据ip获取服务器
	 * @author lining
	 * @param address
	 * @return
	 */
	public abstract JSONArray queryAddress(String address);
	
	/**
	 * 添加服务器到资源池
	 * @author lining
	 * @param uuidJsonStr, hasMaster, poolUuid, userid
	 * @return
	 */
	public abstract JSONArray add2Pool(String uuidJsonStr, String hasMaster,
			String poolUuid, int userid);
	
	/**
	 * 移除资源池的服务器
	 * @author lining
	 * @param hostUuid, userid
	 * @return
	 */
	public abstract JSONArray r4Pool(String hostUuid, int userid);
	
	/**
	 * 获取服务器
	 * @author lining
	 * @param hostid
	 * @return
	 */
	public abstract JSONArray getOneHost(String hostid);
	
	/**
	 * 获取资源池中的服务器
	 * @author lining
	 * @param poolUuid
	 * @return
	 */
	public abstract OCHost getOneHostFormPool(String poolUuid);
	
	/**
	 * 更新服务器
	 * @author lining
	 * @param hostId, hostName, hostDesc, hostType
	 * @return
	 */
	public abstract boolean updateHost(String hostId, String hostName,
			String hostDesc, String hostType);

	/**
	 * 获取所有的服务器
	 * @author lining
	 * @return
	 */
	public abstract JSONArray getAllList();
	
	/**
	 * 修复服务器
	 * @author lining
	 * @param userId, ip, username, password, content, conid, hostUuid
	 * @return
	 */
	public abstract boolean recover(int userId, String ip, String username,
			String password, String content, String conid, String hostUuid);
	
	/**
	 * 获取指定迁移的服务器
	 * @author lining
	 * @param vmuuid
	 * @return
	 */
	public abstract JSONArray getHostListForMigration(String vmuuid);
	
	/**
	 * 获取未加入资源池的服务器列表
	 * @author lining
	 * @return
	 */
	public abstract List<OCHost> getAllHostNotInPool();
	
	/**
	 * @author lining
	 * @param poolUuid
	 * 获取同一个资源池内所有的host
	 */
	public abstract List<OCHost> getAllHostInPool(String poolUuid);
	
	/**
	 * @author lining
	 * @param poolUuid
	 * 获取同一个资源池内所有的host
	 */
	public abstract JSONArray getAllHostOfPool(String poolUuid);
	
	/**
	 * 获取服务器
	 * @author lining
	 * @param hostid
	 * @return
	 */
	public abstract OCHost getHostById(String hostid);
	
	/**
	 * 注册服务器
	 * @author lining
	 * @param key, ip, hostid, userId
	 * @return
	 */
	public abstract boolean activeHost(String key, String ip, String hostid, int userId);
}
