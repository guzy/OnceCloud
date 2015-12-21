package com.oncecloud.service;

import java.util.Date;

import org.json.JSONObject;

public interface ProfitManager {
	
	/**
	 * 获取实时消费列表
	 * @return
	 */
	public abstract JSONObject profitlist(Date startDate,Date ensDate,int userid);
	
	/**
	 * 获取实时消费统计图
	 * @return
	 */
	public abstract JSONObject profitPie(int userid);
	
}
