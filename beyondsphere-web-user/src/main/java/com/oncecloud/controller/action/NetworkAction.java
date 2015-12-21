package com.oncecloud.controller.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oncecloud.query.QueryList;

@RequestMapping("/VnetAction")
@Controller
public class NetworkAction {
	
	@Resource
	private QueryList queryList;
	
	@RequestMapping(value = "/VnetDetail", method = { RequestMethod.GET })
	@ResponseBody
	public String vnetDetail(HttpServletRequest request, @RequestParam String uuid) {
		JSONObject jo = queryList.queryNetworkById(uuid);
		return jo.toString();
	}
	
}
