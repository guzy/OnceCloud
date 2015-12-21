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

import com.beyondsphere.manager.CostMonthManager;
import com.beyondsphere.manager.StatisticsManager;
import com.oncecloud.util.TimeUtils;

@RequestMapping("ProfitAction")
@Controller
public class ProfitAction {

	@Resource
	private StatisticsManager statisticsManager;
	@Resource
	private CostMonthManager costMonthManager;
	
	final static int MONTHNUM=6;

	/**
	 * 资源统计列表（实时收益详情列表）
	 * @param request
	 * @param userid
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	@RequestMapping(value = "profitList", method = { RequestMethod.GET })
	@ResponseBody
	public String profitlist(HttpServletRequest request, @RequestParam Integer userid,
			@RequestParam String startTime,@RequestParam String endTime) {
		Date startDate=null;
		Date endDate=null;
		if(!"".equals(startTime)){
			startDate=TimeUtils.stringToDate(startTime);
		}
		if(!"".equals(endTime)){
			endDate=TimeUtils.AddDayForDate(TimeUtils.stringToDate(endTime),1);		
		}
		//硬件资源列表统计
		JSONObject returnobj=statisticsManager.getProfitListData(startDate, endDate, userid);
		return returnobj.toString();
	}

	/**
	 * 硬件资源本年度折线统计图
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "profitChart", method = { RequestMethod.GET })
	@ResponseBody
	public String profitChart(HttpServletRequest request) {
		JSONObject obj=new JSONObject();
		obj=costMonthManager.getProfitMonth();
		return obj.toString();
		
	}
	
	/**
	 * 成本资源统计饼图
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "profitPie", method = { RequestMethod.GET })
	@ResponseBody
	public String profitPie(HttpServletRequest request) {
		JSONObject obj=new JSONObject();
		obj=statisticsManager.getProfitPieData();
		return obj.toString();
	}
	
	/**
	 * 成本资源统计折线图
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "costYearChart", method = { RequestMethod.GET })
	@ResponseBody
	public String costYearChart(HttpServletRequest request) {
		JSONObject obj=new JSONObject();
		obj=costMonthManager.getCostMonthList();
		return obj.toString();
		
	}
}


