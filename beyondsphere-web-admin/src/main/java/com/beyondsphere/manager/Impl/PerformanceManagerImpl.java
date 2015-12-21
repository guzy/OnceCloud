package com.beyondsphere.manager.Impl;

import javax.annotation.Resource;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.beyondsphere.manager.PerformanceManager;
import com.beyondsphere.service.PerformanceService;

@Service("PerformanceManager")
public class PerformanceManagerImpl implements PerformanceManager {
	
	@Resource
	private PerformanceService performanceService;

	public JSONObject getCpu(String uuid, String type) {
		return performanceService.getCpu(uuid, type);
	}

	public JSONArray getMemory(String uuid, String type) {
		return performanceService.getMemory(uuid, type);
	}

	public JSONObject getVbd(String uuid, String type) {
		return performanceService.getVbd(uuid, type);
	}

	public JSONObject getVif(String uuid, String type) {
		return performanceService.getVif(uuid, type);
	}

	public JSONObject getPif(String uuid, String type) {
		return performanceService.getPif(uuid, type);
	}
	
}
