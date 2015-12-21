package com.oncecloud.controller.action;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oncecloud.manager.CostMonthManager;

@RequestMapping("CostMonthAction")
@Controller
public class CostMonthAction {
	
	@Resource
	private CostMonthManager costMonthManager;
	
	@RequestMapping(value = "/priceList", method = { RequestMethod.GET })
	@ResponseBody
	public String priceList(HttpServletRequest request){
		return costMonthManager.getPriceList().toString();
	}
	
	@RequestMapping(value = "/profitMonthList", method = { RequestMethod.GET })
	@ResponseBody
	public String profitMonthList(HttpServletRequest request){
		JSONArray ja = costMonthManager.profitMonthList();
		return ja.toString();
	}
	
	
}
