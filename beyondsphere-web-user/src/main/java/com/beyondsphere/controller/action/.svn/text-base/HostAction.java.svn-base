package com.beyondsphere.controller.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beyondsphere.entity.User;
import com.beyondsphere.manager.HostManager;
import com.beyondsphere.model.CreateHostModel;
import com.beyondsphere.model.ListModel;

@RequestMapping("HostAction")
@Controller
public class HostAction {
	
	@Resource
	private HostManager hostManager;
	
	@RequestMapping(value = "/HostList", method = { RequestMethod.GET })
	@ResponseBody
	public String hostList(HttpServletRequest request, ListModel list) {
		JSONArray ja = hostManager.getHostList(list.getPage(),
				list.getLimit(), list.getSearch());
		return ja.toString();
	}
	
	@RequestMapping(value = "/Create", method = { RequestMethod.POST })
	@ResponseBody
	public String create(HttpServletRequest request, CreateHostModel hostModel) {
		User user = (User) request.getSession().getAttribute("user");
		JSONArray ja = hostManager.createHost(hostModel.getHostname(), hostModel.getHostpwd(),
				hostModel.getHostdesc(), hostModel.getHostip(), hostModel.getHosttype(), user.getUserId());
		return ja.toString();
	}
	
	@RequestMapping(value = "/AddToPool", method = { RequestMethod.POST })
	@ResponseBody
	public String addToPool(HttpServletRequest request,
			@RequestParam String uuidjsonstr, @RequestParam String hasmaster,
			@RequestParam String pooluuid) {
		User user = (User) request.getSession().getAttribute("user");
		JSONArray ja = hostManager.add2Pool(uuidjsonstr, hasmaster,
				pooluuid, user.getUserId());
		return ja.toString();
	}
	
	@RequestMapping(value = "/QueryAddress", method = { RequestMethod.GET })
	@ResponseBody
	public String queryAddress(HttpServletRequest request,
			@RequestParam String address) {
		JSONArray ja = hostManager.queryAddress(address);
		return ja.toString();
	}
	
	@RequestMapping(value = "/TablePool", method = { RequestMethod.POST })
	@ResponseBody
	public String tablePool(HttpServletRequest request,
			@RequestParam String uuidjsonstr) {
		JSONArray ja = hostManager.getTablePool(uuidjsonstr);
		return ja.toString();
	}
}
