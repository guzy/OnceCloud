/**
 * @author hty
 * @time 下午12:30:59
 * @date 2014年12月11日
 * 
 * 高能模块 后人切勿模仿
 */
package com.oncecloud.service;

import org.json.JSONArray;
import org.json.JSONObject;

import org.Xen.API.Connection;

public interface SnapshotService {

	/**
	 * 分页获取备份列表
	 * 
	 * @param userId, page, limit, search
	 * @return
	 */
	public abstract JSONArray getSnapshotList(int userId, int page, int limit, String search);

	/**
	 * 获取备份详细信息
	 * 
	 * @param resourceUuid, resourceType
	 * @return
	 */
	public abstract JSONArray getDetailList(String resourceUuid, String resourceType);
	
	/**
	 * 获取备份的基本信息
	 * 
	 * @param userId, resourceUuid, resourceType
	 * @return
	 */
	public abstract JSONObject getBasicList(int userId, String resourceUuid,
			String resourceType);

	/**
	 * 创建虚拟机的备份
	 * 
	 * @param userId, snapshotId, snapshotName, resourceUuid, c
	 * @return
	 */
	public abstract boolean createVMSnapshot(int userId, String snapshotId,
			String snapshotName, String resourceUuid, Connection c);

	/**
	 * 创建虚拟硬盘的备份
	 * 
	 * @param userId, snapshotId, snapshotName, resourceUuid, c
	 * @return
	 */
	public abstract boolean createVolumeSnapshot(int userId, String snapshotId,
			String snapshotName, String resourceUuid, Connection c);

	/**
	 * 删除虚拟机的备份
	 * 
	 * @param userId, resourceUuid, c
	 * @return
	 */
	public abstract boolean deleteVMSnapshotSeries(int userId,
			String resourceUuid, Connection c);

	/**
	 * 删除硬盘的备份
	 * 
	 * @param userId, resourceUuid, c
	 * @return
	 */
	public abstract boolean deleteVolumeSnapshotSeries(int userId,
			String resourceUuid, Connection c);

	/**
	 * 回滚虚拟机备份
	 * 
	 * @param jo, c
	 * @return
	 */
	public abstract JSONObject rollbackVMSnapshot(JSONObject jo, Connection c);

	/**
	 * 回滚硬盘的备份
	 * 
	 * @param jo, c
	 * @return
	 */
	public abstract JSONObject rollbackVolumeSnapshot(JSONObject jo, Connection c);
	
	/**
	 * 删除虚拟机的备份点
	 * 
	 * @param jo, c
	 * @return
	 */
	public abstract boolean destoryVMSnapshot(JSONObject jo, Connection c);

	/**
	 * 删除硬盘的备份点
	 * 
	 * @param jo, c
	 * @return
	 */
	public abstract boolean destoryVolumeSnapshot(JSONObject jo, Connection c);

}
