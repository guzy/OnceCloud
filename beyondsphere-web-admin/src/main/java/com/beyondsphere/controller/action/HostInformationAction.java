package com.beyondsphere.controller.action;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beyondsphere.entity.HostInformation;
import com.beyondsphere.model.ListModel;
import com.beyondsphere.manager.HostInformationManager;

/**
 * 
 * @author zll
 *	用于统计用户创建路由的信息
 */
@Controller
@RequestMapping(value="HostInformation")
public class HostInformationAction {
	@Resource
	private HostInformationManager hostInformationManager; 
	
	@RequestMapping(value = "/InformationList", method = { RequestMethod.GET })
	@ResponseBody
	public String accountingList(HttpServletRequest request, ListModel list) {
		JSONArray ja = null;
		try{
			ja = hostInformationManager.getOnePageList(list.getPage(), list.getLimit());
		}catch(Exception e){
			e.printStackTrace();
		}
		return ja.toString();
	}
	
	
	@RequestMapping(value = "/saveInformation", method = { RequestMethod.POST })
	@ResponseBody
	public String saveInformation(HttpServletRequest request,@RequestParam String ipSegment,@RequestParam int userId,
			@RequestParam int netId,@RequestParam String routerIp,@RequestParam String routerNetmask) {
		boolean result=false;
		
		HostInformation info=new HostInformation();
		info.setIpSegment(ipSegment);
		info.setUserId(userId);
		info.setNetId(netId);
		info.setRouterIp(routerIp);
		info.setRouterNetmask(routerNetmask);
		info.setCreateDate(new Date());
		
		try{
			result = hostInformationManager.saveInformation(info);
		}catch(Exception e){
			e.printStackTrace();
		}
		return String.valueOf(result);
	}
	
	
	@RequestMapping(value = "/Delete", method = { RequestMethod.GET })
	@ResponseBody
	public String delete(HttpServletRequest request,@RequestParam int infoid) {
		boolean result = hostInformationManager.doDeleteInfo(infoid);
		return String.valueOf(result);
	}
	
	@RequestMapping(value = "/unBindUsers", method = { RequestMethod.GET })
	@ResponseBody
	public String unBindUsers(HttpServletRequest request) {
		JSONArray ja = hostInformationManager.getUnBindUsers();
		return ja.toString();
	}
	
	@RequestMapping(value = "/BindUsers", method = { RequestMethod.GET })
	@ResponseBody
	public String BindUsers(HttpServletRequest request) {
		JSONArray ja = hostInformationManager.getBindUsers();
		return ja.toString();
	}
	
	@RequestMapping(value = "/checkSwitch", method = { RequestMethod.GET })
	@ResponseBody
	public String checkSwitch(HttpServletRequest request,@RequestParam int netid) {
		JSONObject ja =hostInformationManager.checkSwitch(netid);
		return ja.toString();
	}
	
	@RequestMapping(value = "/unRouterBindUsers", method = { RequestMethod.GET })
	@ResponseBody
	public String getunBindRouterUsers(HttpServletRequest request) {
		JSONArray ja = hostInformationManager.getunBindRouterUsers();
		return ja.toString();
	}
}
