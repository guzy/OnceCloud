/**
 * @author hty
 * @time 上午11:05:48
 * @date 2014年12月9日
 */
package com.beyondsphere.controller.action;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beyondsphere.model.ListModel;
import com.beyondsphere.entity.User;
import com.beyondsphere.manager.AreaManager;

@RequestMapping("AreaAction")
@Controller
public class AreaAction {

	@Resource
	private AreaManager areaManager;

	@RequestMapping(value = "/Create", method = { RequestMethod.POST })
	@ResponseBody
	public String create(HttpServletRequest request,
			@RequestParam String areaId, @RequestParam String areaName,
			@RequestParam String areaDomain, @RequestParam String areaDesc) {
		 User user = (User) request.getSession().getAttribute("user");
		boolean result = false;
		result = areaManager.doCreateArea(user.getUserId(),areaId, areaName, areaDomain,
				areaDesc);

		return String.valueOf(result);
	}

	@RequestMapping(value = "/Delete", method = { RequestMethod.GET })
	@ResponseBody
	public String delete(HttpServletRequest request, @RequestParam String areaId) {
		User user = (User) request.getSession().getAttribute("user");
		boolean result = false;
		result = areaManager.doDeleteArea(user.getUserId(),areaId);

		return String.valueOf(result);
	}

	@RequestMapping(value = "/Update", method = { RequestMethod.POST })
	@ResponseBody
	public void update(HttpServletRequest request, @RequestParam String areaId,
			@RequestParam String areaName, @RequestParam String areaDomain,
			@RequestParam String areaDesc) {
		User user = (User) request.getSession().getAttribute("user");
		areaManager.doUpdateArea(user.getUserId(),areaId, areaName, areaDomain, areaDesc);
	}

	@RequestMapping(value = "/AreaList", method = { RequestMethod.GET })
	@ResponseBody
	public String areaList(HttpServletRequest request, ListModel list) {
		JSONArray ja = areaManager.getAreaList(list.getPage(), list.getLimit(),
				list.getSearch());
		return ja.toString();
	}
	
	@RequestMapping(value = "/AreaAllList", method = { RequestMethod.GET })
	@ResponseBody
	public String areaAllList(HttpServletRequest request) {
		JSONArray ja = areaManager.getAreaAllList();
		return ja.toString();
	}
	
	@RequestMapping(value = "/QueryArea", method = { RequestMethod.GET })
	@ResponseBody
	public String queryUser(HttpServletRequest request, @RequestParam String areaDomain) {
		JSONArray ja = areaManager.doQueryArea(areaDomain);
		return ja.toString();
	}
	
}