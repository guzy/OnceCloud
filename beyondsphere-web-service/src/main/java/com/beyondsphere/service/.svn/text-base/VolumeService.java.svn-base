package com.beyondsphere.service;

import java.util.Date;
import java.util.List;

import org.Xen.API.Connection;
import org.json.JSONArray;
import org.json.JSONObject;

import com.beyondsphere.entity.Volume;



public interface VolumeService {

	
	public abstract List<Volume> getAllList();
	
	public abstract Volume getOneById(String uid);
	
	public abstract List<Volume> getListbyUserid(int userid);
	
	/**
	 * 清空虚拟机硬盘
	 * 
	 * @param c
	 * @param vmUuid
	 * @return
	 */
	public abstract boolean emptyAttachedVolume(Connection c, String vmUuid);

	/**
	 * 获取某一用户指定硬盘列表
	 * 
	 * @param userId
	 * @param page
	 * @param limit
	 * @param search
	 * @return
	 */
	public abstract JSONArray getVolumeList(int userId, int page, int limit,
			String search);

	/**
	 * 创建硬盘
	 * 
	 * @param userId
	 * @param volumeUuid
	 * @param volumeName
	 * @param volumeSize
	 * @param c
	 */
	public abstract boolean createVolume(int userId, String volumeUuid,
			String volumeName, int volumeSize, Connection c, Date startTime);

	/**
	 * 删除硬盘
	 * 
	 * @param userId
	 * @param volumeUuid
	 * @param c
	 * @return
	 */
	public abstract boolean deleteVolume(int userId, String volumeUuid,
			Connection c);

	/**
	 * 硬盘绑定在虚拟机
	 * 
	 * @param userId
	 * @param volumeUuid
	 * @param vmUuid
	 * @return
	 */
	public abstract boolean bindVolume(int userId, String volumeUuid,
			String vmUuid, Connection c);

	/**
	 * 解绑虚拟机硬盘
	 * 
	 * @param userId
	 * @param volumeUuid
	 * @param c
	 * @return
	 */
	public abstract boolean unbindVolume(int userId, String volumeUuid,
			Connection c);
	
	/**
	 * 获取虚拟机的硬盘信息
	 * @param vmUuid
	 * @return
	 */
	public JSONArray getVolumesofVm(String vmUuid);
	
	/**
	 * 获取可以使用的硬盘信息
	 * @author lining
	 * @param userId
	 * @return
	 * 
	 */
	public JSONArray getAvailableVolumes(int userId); 
	
	/**
	 * 获取硬盘的详细信息
	 * @author lining
	 * @param volumeUuid
	 * @return
	 * 
	 */
	public JSONObject getVolumeDetail(String volumeUuid); 
}

