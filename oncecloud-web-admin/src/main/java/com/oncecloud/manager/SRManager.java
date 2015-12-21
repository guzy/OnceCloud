/**
 * @author hty
 * @time 上午11:05:48
 * @date 2014年12月9日
 */
package com.oncecloud.manager;

import org.json.JSONArray;

public interface SRManager {

	public abstract boolean checkSREquals(String masterHost, String targetHost);

	public abstract JSONArray addStorage(int userId, String srname,
			String srAddress, String srDesc, String srType, String srDir);

	/**
	 * 获取存储列表
	 * 
	 * @param page
	 * @param limit
	 * @param search
	 * @return
	 */
	public abstract JSONArray getStorageList(int page, int limit, String search);

	public abstract JSONArray deleteStorage(int userId, String srId,
			String srName);

	public abstract JSONArray load2Server(int userId, String srUuid,
			String hostUuid);

	public abstract JSONArray getStorageByAddress(String address);

	public abstract void updateStorage(int userId, String srId, String srName,
			String srDesc);

	public abstract JSONArray getRealSRList(String poolUuid);
}
