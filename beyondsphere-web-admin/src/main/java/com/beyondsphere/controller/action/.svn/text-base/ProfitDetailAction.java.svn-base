package com.beyondsphere.controller.action;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beyondsphere.manager.ProfitDetailManager;
import com.beyondsphere.util.TimeUtils;

@RequestMapping("ProfitDetailAction")
@Controller
public class ProfitDetailAction {

	@Resource
	private ProfitDetailManager profitDetailManager;
	
	final static int MONTHNUM=6;

	@RequestMapping(value = "monthdetailList", method = { RequestMethod.GET })
	@ResponseBody
	public String monthdetailList(HttpServletRequest request) {
		//上个月第一天
		Date startDate=TimeUtils.getFirstDayOfMonth(1);
		//上个月最后一天（下个月第一天）
		Date endDate=TimeUtils.getFirstDayOfMonth(0);
		//投入列表统计
		JSONObject returnobj=profitDetailManager.getCostMonthDetail(startDate, endDate);
		return returnobj.toString();
	}
	

	@RequestMapping(value = "profitmonthList", method = { RequestMethod.GET })
	@ResponseBody
	public String profitmonthList(HttpServletRequest request) {
		//上个月第一天
		Date startDate=TimeUtils.getFirstDayOfMonth(1);
		//上个月最后一天（下个月第一天）
		Date endDate=TimeUtils.getFirstDayOfMonth(0);
		
		//收益列表统计
		JSONObject returnobj=profitDetailManager.getProfitMonthList(startDate, endDate);
		return returnobj.toString();
	}
	
	@RequestMapping(value = "profitdetaillist", method = { RequestMethod.GET })
	@ResponseBody
	public String profitdetaillist(HttpServletRequest request,@RequestParam Integer userid) {
		//上个月第一天
		Date startDate=TimeUtils.getFirstDayOfMonth(1);
		//上个月最后一天（下个月第一天）
		Date endDate=TimeUtils.getFirstDayOfMonth(0);
		//用户详细计费列表统计
		JSONObject returnobj=profitDetailManager.getProfitDetailList(startDate, endDate,userid);
		return returnobj.toString();
	}

}


