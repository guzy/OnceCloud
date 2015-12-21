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
import com.beyondsphere.model.ImageCloneModel;
import com.beyondsphere.model.ListModel;
import com.beyondsphere.service.DatacenterService;
import com.beyondsphere.service.PoolService;

@RequestMapping("ClusterAction")
@Controller
public class ClusterAction {
	
	@Resource
	private PoolService poolService;
	@Resource
	private DatacenterService datacenterService;
	
	@RequestMapping(value = "/DatacenterList", method = { RequestMethod.GET })
	@ResponseBody
	public String allList(HttpServletRequest request) {
		JSONArray ja = datacenterService.getDatacenterAllList();
		return ja.toString();
	}
	
	@RequestMapping(value = "/AllCluster", method = {RequestMethod.POST })
	@ResponseBody
	public String allPool(HttpServletRequest request, ImageCloneModel imagecloneModel) {
		JSONArray ja = poolService.getAllPool();
		return ja.toString();
	}
	
	@RequestMapping(value = "/ClusterList", method = { RequestMethod.GET })
	@ResponseBody
	public String poolList(HttpServletRequest request, ListModel list) {
		
		JSONArray ja = poolService.getPoolList(list.getPage(),
				list.getLimit(), list.getSearch());
		return ja.toString();
	}
	
	@RequestMapping(value = "/Create", method = { RequestMethod.POST })
	@ResponseBody
	public String create(HttpServletRequest request, @RequestParam String poolname, 
			@RequestParam String pooltype, @RequestParam String pooldesc,
			@RequestParam String dcuuid, @RequestParam String dcname) {
		User user = (User) request.getSession().getAttribute("user");
		JSONArray ja = poolService.createPool(poolname, pooltype, pooldesc, dcuuid, dcname, user.getUserId());
		return ja.toString();
	}
	
	@RequestMapping(value = "/Update", method = { RequestMethod.POST })
	@ResponseBody
	public void update(HttpServletRequest request, @RequestParam String pooluuid, @RequestParam String poolname,
			@RequestParam String pooltype, @RequestParam String pooldesc, @RequestParam String dcuuid) {
		User user = (User) request.getSession().getAttribute("user");
		poolService.updatePool(pooluuid, poolname, pooltype, pooldesc, dcuuid, user.getUserId());
	}
	
	@RequestMapping(value = "/Delete", method = { RequestMethod.POST })
	@ResponseBody
	public String delete(HttpServletRequest request,
			@RequestParam String poolid, @RequestParam String poolname) {
		User user = (User) request.getSession().getAttribute("user");
		return poolService.deletePool(poolid, poolname, user.getUserId()).toString();
	}
	
}
