package com.beyondsphere.controller.action;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beyondsphere.manager.StatisticsManager;

/**
 * @author hty
 * 
 */
@RequestMapping(value="StatisticsAction")
@Controller
public class StatisticsAction {
	
	@Resource
	private StatisticsManager statisticsManager;

	@RequestMapping(value = "/List", method = { RequestMethod.GET })
	@ResponseBody
	public String statistics(HttpServletRequest request,
			@RequestParam int page, @RequestParam int limit,
			@RequestParam String type, @RequestParam int userId, @RequestParam String start_date, @RequestParam String end_date) {
		Map<String, Object> params = new HashMap<String, Object>();
		JSONArray ja = new JSONArray();
		params.put("userId", userId);
		params.put("type", type);
		params.put("start_date", start_date);
		params.put("end_date", end_date);
		ja = statisticsManager.getOnePageStatistics(params, page, limit);
		return ja.toString();
	}
}
