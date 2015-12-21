package com.beyondsphere.controller.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beyondsphere.manager.PerformanceManager;

@RequestMapping("PerformanceAction")
@Controller
public class PerformanceAction {

	@Resource
	private PerformanceManager performanceManger;

	@RequestMapping(value = "/CPU", method = { RequestMethod.GET })
	@ResponseBody
	public String cpu(HttpServletRequest request, @RequestParam String uuid,
			@RequestParam String type) {
		JSONObject jo = performanceManger.getCpu(uuid, type);
		return jo.toString();
	}

	@RequestMapping(value = "/Memory", method = { RequestMethod.GET })
	@ResponseBody
	public String memory(HttpServletRequest request, @RequestParam String uuid,
			@RequestParam String type) {
		JSONArray ja = performanceManger.getMemory(uuid, type);
		return ja.toString();
	}

	@RequestMapping(value = "/PIF", method = { RequestMethod.GET })
	@ResponseBody
	public String pif(HttpServletRequest request, @RequestParam String uuid,
			@RequestParam String type) {
		JSONObject jo = performanceManger.getPif(uuid, type);
		return jo.toString();
	}
	
	@RequestMapping(value = "/VIF", method = { RequestMethod.GET })
	@ResponseBody
	public String vif(HttpServletRequest request, @RequestParam String uuid,
			@RequestParam String type) {
		JSONObject jo = performanceManger.getVif(uuid, type);
		return jo.toString();
	}
	
	@RequestMapping(value = "/VBD", method = { RequestMethod.GET })
	@ResponseBody
	public String vbd(HttpServletRequest request, @RequestParam String uuid,
			@RequestParam String type) {
		JSONObject jo = performanceManger.getVbd(uuid, type);
		return jo.toString();
	}
}
