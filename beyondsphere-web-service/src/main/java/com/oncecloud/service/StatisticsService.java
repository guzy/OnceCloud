package com.oncecloud.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.oncecloud.entity.OCStatistics;

public interface StatisticsService {
	
	/**
	 * 分页获取资源统计列表
	 * @author lining
	 * @param params, page, limit
	 * @return
	 */
	public abstract JSONArray getOnePageStatistics(Map<String, Object> params, int page, int limit);

	/**
	 * 添加资源的统计信息
	 * @author lining
	 * @param uuid, name, type, create, userId
	 */
	public abstract boolean addResource(String uuid, String name, int type,
			Date create, int userId);
	
	/**
	 * 更新资源的统计信息
	 * @author lining
	 * @param uuid, userId, delete
	 * @return
	 */
	public abstract boolean updateResource(String uuid, int userId, Date delete);
	
	/**
	 * 查询资源的列表
	 * @author lining
	 * @param startTime
	 * @return
	 */
	public abstract List<OCStatistics>  getListForDate(Date startTime);
	
	/**
	 * 查询资源的列表
	 * @param userid<int>
	 * @return List<OCStatistics>
	 */
	public abstract List<OCStatistics>  getListByUserId(int userid);
	
	/**
	 * 硬件资源收益分析
	 * @param startTime
	 * @param endTime
	 * @param userid
	 * @return JSONArray
	 */
	public abstract JSONObject getProfitListData(Date startTime,Date endTime,int userid);
	
	/**
	 * 硬件资源收益分析折线图
	 * @param startTime
	 * @param endTime
	 * @return JSONArray
	 */
	public abstract JSONObject getProfitChartData(Date startTime,Date endTime);
	
	/**
	 * 资源成本统计饼图
	 * @return JSONObject
	 */
	public abstract JSONObject getProfitPieData();
	
	/**
	 * 近几个个月收益变化统计
	 * @param startTime<Date>
	 * @return JSONObject
	 */
	public abstract JSONObject income(Date startTime);
	
	/**
	 * 添加统计资源的信息
	 * 
	 * @author lining
	 * @param uuid, name, type, create, totalPrince, userId
	 * @return
	 */
	public abstract boolean addResource(String uuid, String name, int type,
			Date create, double totalPrince, int userId);
}
