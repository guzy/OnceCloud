/**
 * @author zll
 * @time 上午10:25
 * @date 2015年5月29日
 */
package com.oncecloud.manager;

import java.util.Date;

import org.json.JSONObject;


public interface ProfitDetailManager {
	
	public abstract JSONObject getCostMonthDetail(Date start,Date end);

	public abstract JSONObject getProfitDetailList(Date start,Date end,int userid);
	
	//获取每个用户一段时间的总的消费
	public abstract JSONObject getProfitMonthList(Date start,Date end);
}