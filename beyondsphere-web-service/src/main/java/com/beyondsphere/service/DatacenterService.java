package com.beyondsphere.service;

import java.util.Map;

import org.json.JSONArray;

import com.beyondsphere.entity.Datacenter;

public interface DatacenterService {
	
	/**
	 * 创建数据中心
	 * @param dcName, dcLocation, dcDesc, userid
	 * @return
	 */
	public abstract JSONArray createDatacenter(String dcName,
			String dcLocation, String dcDesc, int userid);
	
	/**
	 * 分页获取数据中心列表
	 * @param page, limit, search
	 * @return
	 */
	public abstract JSONArray getDatacenterList(int page, int limit,
			String search);
	
	/**
	 * 获取所有的数据中心列表
	 * @return
	 */
	public abstract JSONArray getDatacenterAllList();
	
	/**
	 * 删除数据中心
	 * @param dcId, dcName, userid
	 * @return
	 */
	public abstract JSONArray deleteDatacenter(String dcId, String dcName,
			int userid);
	
	/**
	 * 更新数据中心
	 * @param dcUuid, dcName, dcLocation, dcDesc, userid
	 */
	public abstract void updateDatacenter(String dcUuid, String dcName,
			String dcLocation, String dcDesc, int userid);
	
	/**
	 * 获取数据中心资源池列表
	 * @param dcid
	 * @return
	 */
	public abstract JSONArray getPoolList(String dcid);

	/**
	 * @author lining
	 * @param dcUuid
	 * @return dataCenter
	 * 根据数据中心的id获取数据中心
	 */
	public abstract Datacenter getDatacenterBydcUuid(String dcUuid);
	
	/**
	 * @author lining
	 * @param  dcUuid
	 * @return map
	 * 获取数据中心的信息
	 */
	public abstract Map<String, Object> getDatacenterInfos(String dcUuid);
	
	/**
	 * @author lining
	 * @param dcUuid
	 * @return
	 * 获取数据中心百分比的资源信息
	 */
	public abstract JSONArray getResourcePercentOfDatacenter(String dcUuid);
	/**
	 * 获取已消耗资源信息
	 * @return
	 */
	public abstract JSONArray getResourceOfDatacenter();
	
}
