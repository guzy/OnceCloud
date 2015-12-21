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

import com.beyondsphere.entity.User;
import com.beyondsphere.service.ProfitManager;
import com.beyondsphere.util.Utilities;

@RequestMapping("ProfitAction")
@Controller
public class ProfitAction {

	@Resource
	private ProfitManager profitManager;

	/**
	 * 实时消费列表
	 * @param request
	 * @param userid
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	@RequestMapping(value = "profitList", method = { RequestMethod.GET })
	@ResponseBody
	public String profitlist(HttpServletRequest request, @RequestParam Integer userid) {
		//上个月第一天
		Date startDate=Utilities.getFirstDayOfMonth(1);
		//上个月最后一天（下个月第一天）
		Date endDate=Utilities.getFirstDayOfMonth(0);
		
		JSONObject returnobj=new JSONObject();
		returnobj=profitManager.profitlist(startDate, endDate, userid);
		return returnobj.toString();
	}
	/*@RequestMapping(value = "profitList", method = { RequestMethod.GET })
	@ResponseBody
	public String profitlist(HttpServletRequest request, @RequestParam Integer userid,
			@RequestParam String startTime,@RequestParam String endTime) {
		Date startDate=null;
		Date endDate=null;
		if(!"".equals(startTime)){
			startDate=Utilities.stringToDate(startTime);
		}
		if(!"".equals(endTime)){
			endDate=Utilities.AddDayForDate(Utilities.stringToDate(endTime),1);		
		}
		
		JSONObject returnobj=new JSONObject();
		returnobj=profitManager.profitlist(startDate, endDate, userid);
		return returnobj.toString();
	}*/
	
	/**
	 * 消费统计饼图
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "profitPie", method = { RequestMethod.GET })
	@ResponseBody
	public String profitPie(HttpServletRequest request) {
		User user=(User) request.getSession().getAttribute("user");
		JSONObject obj=profitManager.profitPie(user.getUserId());
		return obj.toString();
	}

}
