package com.oncecloud.manager.Impl;


import javax.annotation.Resource;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.oncecloud.manager.CostMonthManager;
import com.oncecloud.service.CostMonthService;

@Service("CostMonthManager")
public class CostMonthManagerImpl implements CostMonthManager {

	@Resource
	private CostMonthService costMonthService;
	
	public JSONObject getPriceList() {
		return costMonthService.getPriceList();
	}

	@Override
	public JSONArray profitMonthList() {
		return costMonthService.profitMonthList();
	}

	@Override
	public JSONObject getProfitMonth() {
		return costMonthService.getProfitMonth();
	}

	@Override
	public JSONObject getCostMonthList() {
		return costMonthService.getCostMonthList();
	}


}
