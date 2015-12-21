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

import com.oncecloud.manager.SRManager;
import com.oncecloud.entity.User;
import com.oncecloud.model.ListModel;

@RequestMapping("StorageAction")
@Controller
public class StorageAction {
	
	@Resource
	private SRManager srManager;

	@RequestMapping(value = "/StorageList", method = { RequestMethod.GET })
	@ResponseBody
	public String storageList(HttpServletRequest request, ListModel list) {
		JSONArray ja = srManager.getStorageList(list.getPage(),
				list.getLimit(), list.getSearch());
		return ja.toString();
	}

	@RequestMapping(value = "/Delete", method = { RequestMethod.POST })
	@ResponseBody
	public String delete(HttpServletRequest request, @RequestParam String srid,
			@RequestParam String srname) {
		User user = (User) request.getSession().getAttribute("user");
		JSONArray ja = srManager.deleteStorage(user.getUserId(),
				srid, srname);
		return ja.toString();
	}

	@RequestMapping(value = "/Add", method = { RequestMethod.POST })
	@ResponseBody
	public String add(HttpServletRequest request, @RequestParam String srname,
			@RequestParam String srAddress, @RequestParam String srDesc,
			@RequestParam String srType, @RequestParam String srDir) {
		User user = (User) request.getSession().getAttribute("user");
		JSONArray ja = srManager.addStorage(user.getUserId(), srname,
				srAddress, srDesc, srType, srDir);
		return ja.toString();
	}

	@RequestMapping(value = "/Update", method = { RequestMethod.POST })
	@ResponseBody
	public void update(HttpServletRequest request, @RequestParam String srid,
			@RequestParam String srName, @RequestParam String srDesc) {
		User user = (User) request.getSession().getAttribute("user");
		srManager.updateStorage(user.getUserId(), srid, srName, srDesc);
	}

	@RequestMapping(value = "/QueryAddress", method = { RequestMethod.GET })
	@ResponseBody
	public String queryAddress(HttpServletRequest request, @RequestParam String address) {
		JSONArray ja = srManager.getStorageByAddress(address);
		return ja.toString();
	}

	@RequestMapping(value = "/LoadToServer", method = { RequestMethod.GET })
	@ResponseBody
	public String loadToServer(HttpServletRequest request, @RequestParam String sruuid, @RequestParam String hostuuid) {
		User user = (User) request.getSession().getAttribute("user");
		JSONArray ja = srManager.load2Server(user.getUserId(), sruuid, hostuuid);
		return ja.toString();
	}

	@RequestMapping(value = "/RealSRList", method = { RequestMethod.POST })
	@ResponseBody
	public String getRealSRList(HttpServletRequest request, @RequestParam String poolUuid) {
		JSONArray ja = srManager.getRealSRList(poolUuid);
		return ja.toString();
	}

}