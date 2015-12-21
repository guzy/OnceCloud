package com.beyondsphere.manager;

import org.json.JSONArray;
import org.json.JSONObject;



public interface CostMonthManager {
	
	/**
	 * @author zll
	 * 获取价格信息
	 * 
	 */
	public abstract JSONObject getPriceList();
	
	/**
	 * 获取资源使用信息列表
	 * @param page
	 * @param limit
	 * @param type
	 * @return
	 */
	public JSONArray profitMonthList();
	
	public JSONObject getProfitMonth();
	
	
	public JSONObject getCostMonthList();
}
