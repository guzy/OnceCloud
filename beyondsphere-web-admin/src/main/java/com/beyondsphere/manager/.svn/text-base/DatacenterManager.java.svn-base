/**
 * @author hty
 * @time 上午11:05:48
 * @date 2014年12月9日
 */
package com.beyondsphere.manager;

import java.util.Map;

import org.json.JSONArray;

import com.beyondsphere.entity.Datacenter;

public interface DatacenterManager {

	public abstract JSONArray createDatacenter(String dcName,
			String dcLocation, String dcDesc, int userid);

	public abstract JSONArray getDatacenterList(int page, int limit,
			String search);

	public abstract JSONArray getDatacenterAllList();

	public abstract JSONArray deleteDatacenter(String dcId, String dcName,
			int userid);

	public abstract void update(String dcUuid, String dcName,
			String dcLocation, String dcDesc, int userid);

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