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

import com.oncecloud.helper.AccordanceHelper;
import com.oncecloud.manager.PoolManager;
import com.oncecloud.entity.User;
import com.oncecloud.model.ImageCloneModel;
import com.oncecloud.model.ListModel;

@RequestMapping("PoolAction")
@Controller
public class PoolAction {

	@Resource
	private PoolManager poolManager;
	@Resource
	private AccordanceHelper accordanceHelper;

	@RequestMapping(value = "/PoolList", method = { RequestMethod.GET })
	@ResponseBody
	public String poolList(HttpServletRequest request, ListModel list) {
		JSONArray ja = poolManager.getPoolList(list.getPage(), list.getLimit(),
				list.getSearch());
		return ja.toString();
	}

	@RequestMapping(value = "/Delete", method = { RequestMethod.POST })
	@ResponseBody
	public String delete(HttpServletRequest request,
			@RequestParam String poolid, @RequestParam String poolname) {
		User user = (User) request.getSession().getAttribute("user");
		JSONArray ja = poolManager.deletePool(poolid, poolname,
				user.getUserId());
		return ja.toString();
	}

	@RequestMapping(value = "/Create", method = { RequestMethod.POST })
	@ResponseBody
	public String create(HttpServletRequest request,
			@RequestParam String poolname, @RequestParam String pooltype,
			@RequestParam String pooldesc, @RequestParam String dcuuid,
			@RequestParam String dcname) {
		User user = (User) request.getSession().getAttribute("user");
		JSONArray ja = poolManager.createPool(poolname, pooltype, pooldesc,
				dcuuid, dcname, user.getUserId());
		return ja.toString();
	}

	@RequestMapping(value = "/checkout", method = { RequestMethod.POST })
	@ResponseBody
	public String checkout(HttpServletRequest request,
			@RequestParam String poolname) {
		
		JSONArray ja = poolManager.checkout(poolname);
		System.out.println("json数据多少："+ja.toString());
		return ja.toString();
		
	}

	@RequestMapping(value = "/Update", method = { RequestMethod.POST })
	@ResponseBody
	public void update(HttpServletRequest request,
			@RequestParam String pooluuid, @RequestParam String poolname,
			@RequestParam String pooltype, @RequestParam String pooldesc,
			@RequestParam String dcuuid) {
		User user = (User) request.getSession().getAttribute("user");
		poolManager.updatePool(pooluuid, poolname, pooltype, pooldesc, dcuuid,
				user.getUserId());
	}

	@RequestMapping(value = "/AllPool", method = { RequestMethod.POST })
	@ResponseBody
	public String allPool(HttpServletRequest request,
			ImageCloneModel imagecloneModel) {
		JSONArray ja = poolManager.getAllPool();
		return ja.toString();
	}

	@RequestMapping(value = "/KeepAccordance", method = { RequestMethod.POST })
	@ResponseBody
	public void keepAccordance(HttpServletRequest request,
			@RequestParam String poolUuid) {
		User user = (User) request.getSession().getAttribute("user");
		poolManager.keepAccordance(user.getUserId(), poolUuid);
	}

	@RequestMapping(value = "/ManageAc", method = { RequestMethod.GET })
	@ResponseBody
	public void StartAc(HttpServletRequest request, @RequestParam int time,
			@RequestParam boolean flag) {
		accordanceHelper.setFlag(flag);
		if (flag) {
			accordanceHelper.setTIMER(time * 1000);
			accordanceHelper.keepAccordance();
		}
	}

	@RequestMapping(value = "/RePool", method = { RequestMethod.POST })
	@ResponseBody
	public void rePool(HttpServletRequest request, @RequestParam String poolUuid) {
		User user = (User) request.getSession().getAttribute("user");
		poolManager.repool(poolUuid, user.getUserId());
	}

}
