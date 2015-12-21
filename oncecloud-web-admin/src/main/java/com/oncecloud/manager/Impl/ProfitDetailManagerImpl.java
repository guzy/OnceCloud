/**
 * @author zll
 * @time 上午10:28
 * @date 2015年5月29日
 */
package com.oncecloud.manager.Impl;


import java.util.Date;

import javax.annotation.Resource;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.oncecloud.manager.ProfitDetailManager;
import com.oncecloud.service.ProfitDetailService;

@Service("ProfitDetailManager")
public class ProfitDetailManagerImpl implements ProfitDetailManager {

	@Resource
	private ProfitDetailService profitDetailService;

	@Override
	public JSONObject getCostMonthDetail(Date start, Date end) {
		JSONObject json=new JSONObject();
		json = profitDetailService.getCostMonthDetail(start,end);
		return json;
	}

	@Override
	public JSONObject getProfitDetailList(Date start, Date end,int userid) {
		JSONObject json=new JSONObject();
		json = profitDetailService.getProfitDetailList(start,end,userid);
		return json;
	}

	@Override
	public JSONObject getProfitMonthList(Date start, Date end) {
		JSONObject json=new JSONObject();
		json = profitDetailService.getProfitMonthList(start,end);
		return json;
	}




}
