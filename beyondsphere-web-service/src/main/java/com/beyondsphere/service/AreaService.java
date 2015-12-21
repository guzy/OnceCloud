package com.beyondsphere.service;

import org.json.JSONArray;

import com.beyondsphere.entity.Area;

public interface AreaService {

	public abstract boolean doCreateArea(Integer userId,String areaId, String areaName,
			String areaDomain, String areaDesc);

	public abstract boolean doDeleteArea(Integer userId,String areaId);

	public abstract void doUpdateArea(Integer userId,String areaId, String areaName,
			String areaDomain, String areaDesc);

	public abstract JSONArray getAreaList(int page, int limit, String search);
	
	public abstract JSONArray getAreaAllList();
	
	public abstract Area getArea(String areaId);
	
	public abstract JSONArray queryArea(String areaDomain);
	
}

