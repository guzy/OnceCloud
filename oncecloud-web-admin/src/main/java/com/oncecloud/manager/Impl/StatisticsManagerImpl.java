package com.oncecloud.manager.Impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.oncecloud.manager.StatisticsManager;
import com.oncecloud.entity.OCStatistics;
import com.oncecloud.service.StatisticsService;

@Component("StatisticsManager")
public class StatisticsManagerImpl implements StatisticsManager {

	@Resource
	private StatisticsService statisticsService;

	public JSONArray getOnePageStatistics(Map<String, Object> params, int page,
			int limit) {
		return statisticsService.getOnePageStatistics(params, page, limit);
	}
	
	public boolean addResource(String uuid, String name, int type, Date create,
			int userId) {
		return statisticsService.addResource(uuid, name, type, create, userId);
	}

	public boolean updateResource(String uuid, int userId, Date delete) {
		return statisticsService.updateResource(uuid, userId, delete);
	}

	@Override
	public List<OCStatistics> getListForDate(Date startTime) {
		return statisticsService.getListForDate(startTime);
	}

	@Override
	public List<OCStatistics> getListByUserId(Integer userid) {
		List<OCStatistics> list=statisticsService.getListByUserId(userid);
		return list;
	}

	@Override
	public JSONObject getProfitListData(Date startTime, Date endTime, int userid) {
		return statisticsService.getProfitListData(startTime, endTime, userid);
	}

	@Override
	public JSONObject getProfitChartData(Date startTime, Date endTime) {
		return statisticsService.getProfitChartData(startTime, endTime);
	}

	@Override
	public JSONObject getProfitPieData() {
		return statisticsService.getProfitPieData();
	}

	@Override
	public JSONObject income(Date startTime) {
		return statisticsService.income(startTime);
	}

}
