/**
 * @author hty
 * @time 上午11:05:48
 * @date 2014年12月9日
 */
package com.oncecloud.manager;

import org.json.JSONArray;

import com.oncecloud.entity.Area;

public interface AreaManager {

	public abstract boolean doCreateArea(Integer userId,String areaId, String areaName,
			String areaDomain, String areaDesc);

	public abstract boolean doDeleteArea(Integer userId,String areaId);

	public abstract void doUpdateArea(Integer userId,String areaId, String areaName,
			String areaDomain, String areaDesc);

	public abstract JSONArray getAreaList(int page, int limit, String search);

	public abstract JSONArray getAreaAllList();

	public abstract Area getArea(String areaId);
	
	public abstract JSONArray doQueryArea(String areaDomain);

}