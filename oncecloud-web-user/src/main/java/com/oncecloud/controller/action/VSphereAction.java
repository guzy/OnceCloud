package com.oncecloud.controller.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oncecloud.entity.User;
import com.oncecloud.service.VSphereService;
/**
 * vsphere虚拟机操作
 * ShutdownVM 关机
 * StartVM 开机
 * DestoryVM 销毁
 * @author mayh
 *
 */
@RequestMapping(value="VSphereAction")
@Controller
public class VSphereAction {
	@Resource
	private VSphereService vSphereService;
	@RequestMapping(value = "/ShutdownVM", method = { RequestMethod.GET })
	@ResponseBody
	public void shutdownVM(HttpServletRequest request,
			@RequestParam String uuid, @RequestParam String force) {
		User user = (User) request.getSession().getAttribute("user");
		vSphereService.shutdownVM(uuid,user.getUserId());
	}
	
	@RequestMapping(value = "/StartVM", method = { RequestMethod.GET })
	@ResponseBody
	public void startVM(HttpServletRequest request, @RequestParam String uuid) {
		User user = (User) request.getSession().getAttribute("user");
		vSphereService.startVM(uuid,user.getUserId());
	}
	@RequestMapping(value = "/DestoryVM", method = { RequestMethod.GET })
	@ResponseBody
	public void vmDestory(HttpServletRequest request,
			@RequestParam String uuid) {
		User user = (User) request.getSession().getAttribute("user");
		int userId = user.getUserId();
		vSphereService.vmDestory(userId, uuid);
	}
}
