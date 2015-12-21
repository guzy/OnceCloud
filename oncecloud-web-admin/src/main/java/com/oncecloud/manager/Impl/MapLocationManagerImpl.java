package com.oncecloud.manager.Impl;

import javax.annotation.Resource;

import org.json.JSONArray;
import org.springframework.stereotype.Service;

import com.oncecloud.manager.MapLocationManager;
import com.oncecloud.service.MapLocationService;
@Service("MapLocationManager")
public class MapLocationManagerImpl implements MapLocationManager {
	@Resource
	MapLocationService locationService;
	@Override
	public JSONArray getAllUsermap() {
		return locationService.getAllUsermap();
	}
	
}
