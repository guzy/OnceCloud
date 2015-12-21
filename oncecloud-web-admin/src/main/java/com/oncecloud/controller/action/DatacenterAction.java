/**
 * @author hty
 * @time 上午11:05:48
 * @date 2014年12月9日
 */
package com.oncecloud.controller.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oncecloud.manager.DatacenterManager;
import com.oncecloud.entity.User;
import com.oncecloud.model.ListModel;

@RequestMapping("DatacenterAction")
@Controller
public class DatacenterAction {
	@Resource
	private DatacenterManager datacenterManager;
	
	@RequestMapping(value = "/DCList", method = { RequestMethod.GET })
	@ResponseBody
	public String dcList(HttpServletRequest request, ListModel list) {
		JSONArray ja = datacenterManager.getDatacenterList(
				list.getPage(), list.getLimit(), list.getSearch());
		return ja.toString();
	}

	@RequestMapping(value = "/Create", method = { RequestMethod.POST })
	@ResponseBody
	public String create(HttpServletRequest request,
			@RequestParam String dcname, @RequestParam String dclocation,
			@RequestParam String dcdesc) {
		User user = (User) request.getSession().getAttribute("user");
		JSONArray ja = datacenterManager.createDatacenter(dcname, dclocation, dcdesc, user.getUserId());
		return ja.toString();
	}
	
	@RequestMapping(value = "/Delete", method = { RequestMethod.POST })
	@ResponseBody
	public String delete(HttpServletRequest request,
			@RequestParam("dcid") String dcId,
			@RequestParam("dcname") String dcName) {
		User user = (User) request.getSession().getAttribute("user");
		JSONArray ja = datacenterManager.deleteDatacenter(dcId,
				dcName, user.getUserId());
		return ja.toString();
	}

	@RequestMapping(value = "/AllList", method = { RequestMethod.GET })
	@ResponseBody
	public String allList(HttpServletRequest request) {
		JSONArray ja = datacenterManager.getDatacenterAllList();
		return ja.toString();
	}

	@RequestMapping(value = "/Update", method = { RequestMethod.POST })
	@ResponseBody
	public void update(HttpServletRequest request,
			@RequestParam String dcuuid, @RequestParam String dcname,
			@RequestParam String dclocation, @RequestParam String dcdesc) {
		User user = (User) request.getSession().getAttribute("user");
		datacenterManager.update(dcuuid, dcname, dclocation, dcdesc, user.getUserId());
	}

	@RequestMapping(value = "/PoolList", method = { RequestMethod.GET })
	@ResponseBody
	public String poolList(HttpServletRequest request) {
		String dcid = (String) request.getSession().getAttribute("dcid");
		JSONArray ja = datacenterManager.getPoolList(dcid);
		return ja.toString();
	}

	@RequestMapping(value = "/ResourcePercent", method = { RequestMethod.GET })
	@ResponseBody
	public String resourcePercent(HttpServletRequest request){
		String dcid = (String) request.getSession().getAttribute("dcid");
		JSONArray ja = datacenterManager.getResourcePercentOfDatacenter(dcid);
		return ja.toString();
	}
	
}
