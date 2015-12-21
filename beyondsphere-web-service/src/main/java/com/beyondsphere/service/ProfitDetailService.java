package com.beyondsphere.service;

import java.util.Date;

import org.json.JSONObject;


public interface ProfitDetailService {

	public abstract JSONObject getCostMonthDetail(Date start,Date end);

	public abstract JSONObject getProfitDetailList(Date start,Date end,int userid);
	
	public abstract JSONObject getProfitMonthList(Date start,Date end);
}

