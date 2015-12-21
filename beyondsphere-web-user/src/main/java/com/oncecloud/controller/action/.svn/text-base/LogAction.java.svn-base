package com.beyondsphere.controller.action;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beyondsphere.usercore.LogCore;
import com.beyondsphere.utils.Dispatch;
import com.beyondsphere.entity.User;
import com.beyondsphere.query.QueryList;
import com.beyondsphere.service.ContainerLogService;
import com.beyondsphere.service.LogService;

@RequestMapping("/LogAction")
@Controller
public class LogAction {

	@Resource
	private QueryList queryList;
	@Resource
	private LogService logService;
	@Resource
	private ContainerLogService containerLogService;
	@Resource
	private LogCore logCore;
	@Resource
	private Dispatch dispatch;

	@RequestMapping(value = "/LogList", method = { RequestMethod.GET })
	@ResponseBody
	public String logList(HttpServletRequest request, @RequestParam int status,
			@RequestParam int start, @RequestParam int num)
			throws ServletException, IOException {
		User user = (User) request.getSession().getAttribute("user");
		JSONArray ja = queryList.queryLogList(user.getUserId(), status, start, num);
		return ja.toString();
	}
	
	@RequestMapping(value = "/applicationLogList", method = { RequestMethod.GET })
	@ResponseBody
	public String applicationLogList(HttpServletRequest request, @RequestParam String containerId,
			@RequestParam int logtype)
			throws ServletException, IOException {
		return containerLogService.getContainerLogs(containerId, logtype).toString();
				
	}
	
	
	@RequestMapping(value="/Remove", method = {RequestMethod.POST})
	@ResponseBody
	public String remove(HttpServletRequest request, @RequestParam int logId){
		User user = (User) request.getSession().getAttribute("user");
		JSONObject jo = new JSONObject();
		jo.put("Handler", "DeleteLogHandler");
		JSONArray ja = new JSONArray();
		JSONObject params = new JSONObject();
		params.put("userId", user.getUserId());
		params.put("logId", logId);
		ja.put(0, params);
		jo.put("Params", ja);
		dispatch.post(jo);
		return ((JSONObject)((JSONArray)jo.get("Params")).get(0)).toString();
	}
	
	@RequestMapping(value="/LogToFile", method = {RequestMethod.POST})
	@ResponseBody
	public String logToFile(HttpServletRequest request){
		User user = (User) request.getSession().getAttribute("user");
		JSONObject jo = new JSONObject();
		jo.put("Handler", "ExportLogHandler");
		JSONArray ja = new JSONArray();
		JSONObject params = new JSONObject();
		params.put("userId", user.getUserId());
		ja.put(0, params);
		jo.put("Params", ja);
		dispatch.post(jo);
		System.out.println("--------"+((JSONObject)((JSONArray)jo.get("Params")).get(0)).toString());
		return ((JSONObject)((JSONArray)jo.get("Params")).get(0)).toString();
	}
	
	@RequestMapping(value="/configSaveTime", method = {RequestMethod.POST})
	@ResponseBody
	public boolean trsLogToBackup(HttpServletRequest request, @RequestParam int days){
		boolean trsLogFlag = false;
		logCore.stopTrsLog();
		logCore.trsLogToBackup(days);
		trsLogFlag = true;
		return trsLogFlag;
	}
	
	@RequestMapping(value="/cancelSaveTime", method = {RequestMethod.POST})
	@ResponseBody
	public boolean cancelTrsLog(HttpServletRequest request){
		boolean trsLogFlag = false;
		logCore.stopTrsLog();
		trsLogFlag = true;
		return trsLogFlag;
	}
	
	
}
