/**
 * @author hty
 * @time 上午11:05:48
 * @date 2014年12月9日
 */
package com.beyondsphere.dao;

import java.util.List;

import com.beyondsphere.entity.Storage;

public interface StorageDAO {

	/**
	 * 获取存储
	 * 
	 * @param srUuid
	 * @return
	 */
	public abstract Storage getStorage(String srUuid);

	/**
	 * 获取存储大小
	 * 
	 * @param hostUuid
	 * @return
	 */
	public abstract int getStorageSize(String hostUuid);

	/**
	 * 获取一页存储列表
	 * 
	 * @param page
	 * @param limit
	 * @param search
	 * @return
	 */
	public abstract List<Storage> getOnePageStorageList(int page, int limit,
			String search);

	/**
	 * 获取主机存储列表
	 * 
	 * @param hostUuid
	 * @return
	 */
	public abstract List<Storage> getStorageListOfHost(String hostUuid);

	/**
	 * 获取对应目标IP的存储
	 * 
	 * @param address
	 * @return
	 */
	public abstract Storage getStorageOfAddress(String address);

	/**
	 * 获取存储总数
	 * 
	 * @param search
	 * @return
	 */
	public abstract int countAllStorageList(String search);

	/**
	 * 获取挂载该存储的服务器数目
	 * 
	 * @param srUuid
	 * @return
	 */
	public abstract int countHostsOfStorage(String srUuid);

	/**
	 * 创建存储
	 * 
	 * @param srname
	 * @param srAddress
	 * @param srDesc
	 * @param srType
	 * @param srDir
	 * @param rackid
	 * @return
	 */
	public abstract Storage createStorage(String srname, String srAddress,
			String srDesc, String srType, String srDir);

	/**
	 * 删除存储
	 * 
	 * @param storageId
	 * @return
	 */
	public abstract boolean removeStorage(String storageId);

	/**
	 * 更新存储
	 * 
	 * @param srId
	 * @param srName
	 * @param srDesc
	 * @param rackId
	 * @return
	 */
	public abstract boolean updateSR(String srId, String srName, String srDesc);

}
