package com.beyondsphere.controller.action;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beyondsphere.entity.User;
import com.beyondsphere.query.QueryList;

/**
 * @author hty
 * 
 */
@RequestMapping("/StatisticsAction")
@Controller
public class StatisticsAction {
	
	@Resource
	private QueryList queryList;

	@RequestMapping(value = "/List", method = { RequestMethod.GET })
	@ResponseBody
	public String statistics(HttpServletRequest request,
			@RequestParam int page, @RequestParam int limit,
			@RequestParam String type, @RequestParam String start_date, @RequestParam String end_date) {
		User user = (User) request.getSession().getAttribute("user");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", user.getUserId());
		params.put("type", type);
		params.put("start_date", start_date);
		params.put("end_date", end_date);
		return queryList.queryStatistics(params, page, limit).toString();
	}
	
}
