/**
 * @author hty
 * @time 上午11:05:48
 * @date 2014年12月9日
 */
package com.beyondsphere.manager.Impl;


import javax.annotation.Resource;

import org.json.JSONArray;
import org.springframework.stereotype.Service;

import com.beyondsphere.manager.AreaManager;
import com.oncecloud.entity.Area;
import com.oncecloud.service.AreaService;

@Service("AreaManager")
public class AreaManagerImpl implements AreaManager {

	@Resource
	private AreaService areaService;

	@Override
	public boolean doCreateArea(Integer userId,String areaId, String areaName,
			String areaDomain, String areaDesc) {
		boolean result = false;
		result = areaService.doCreateArea(userId,areaId, areaName, areaDomain,
				areaDesc);

		return result;
	}

	@Override
	public boolean doDeleteArea(Integer userId,String areaId) {
		boolean result = false;
		result = areaService.doDeleteArea(userId,areaId);

		return result;
	}

	@Override
	public void doUpdateArea(Integer userId,String areaId, String areaName, String areaDomain,
			String areaDesc) {

		areaService.doUpdateArea(userId,areaId, areaName, areaDomain, areaDesc);
	}

	@Override
	public JSONArray getAreaList(int page, int limit, String search) {

		return areaService.getAreaList(page, limit, search);
	}

	@Override
	public JSONArray getAreaAllList() {
		
		return areaService.getAreaAllList();
	}

	@Override
	public Area getArea(String areaId) {
		Area area =areaService.getArea(areaId);
		return area;
	}

	@Override
	public JSONArray doQueryArea(String areaDomain) {
		return areaService.queryArea(areaDomain);
	}

}
