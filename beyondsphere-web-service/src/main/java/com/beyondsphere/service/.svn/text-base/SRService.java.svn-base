package com.beyondsphere.service;

import org.json.JSONArray;

import com.beyondsphere.entity.HostSR;
import com.beyondsphere.entity.Storage;

public interface SRService {
	
	/**
	 * 检查是否同一个存储
	 * @param masterHost, targetHost
	 * @return
	 */
	public abstract boolean checkSREquals(String masterHostUuid, String targetHostUuid);
	
	/**
	 * 添加存储
	 * @param userId, srname, srAddress, srDesc, srType, srDir
	 * @return
	 */
	public abstract JSONArray addStorage(int userId, String srname,
			String srAddress, String srDesc, String srType, String srDir);
	
	/**
	 * 加载存储
	 * @author lining
	 * @param hostUuid, srUuid
	 * @return
	 */
	public abstract HostSR addStrToHost(String hostUuid, String srUuid);
	
	/**
	 * 获取存储列表
	 * @param page
	 * @param limit
	 * @param search
	 * @return
	 */
	public abstract JSONArray getStorageList(int page, int limit, String search);
	
	/**
	 * 获取存储
	 * @param srId
	 * @return
	 */
	public abstract Storage getStorage(String srId);
	
	/**
	 * 卸载存储
	 * @param userId, srId, srName
	 * @return
	 */
	public abstract JSONArray deleteStorage(int userId, String srId,
			String srName);

	/**
	 * 根据ip获取存储
	 * @param address
	 * @return
	 */
	public abstract JSONArray getStorageByAddress(String address);

	/**
	 * 更新存储信息
	 * @param userId, srId, srName, srDesc
	 * @return
	 */
	public abstract void updateStorage(int userId, String srId, String srName,
			String srDesc);
}
