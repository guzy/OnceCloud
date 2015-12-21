package com.oncecloud.controller.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oncecloud.manager.NetworkManager;
import com.oncecloud.manager.RouterManager;
import com.oncecloud.manager.UserManager;
import com.oncecloud.entity.User;
import com.oncecloud.model.CreateRouterModel;
import com.oncecloud.service.UserService;


@RequestMapping("/RouterAction")
@Controller
public class RouterAction {
	@Resource
	private RouterManager routerManager;
	@Resource
	private UserManager userManager;
	@Resource
	private NetworkManager networkManager;
	@Resource
	private UserService userService;


	@RequestMapping(value = "/RouterList", method = { RequestMethod.GET })
	@ResponseBody
	public String routerList(HttpServletRequest request,@RequestParam int page,@RequestParam int limit,
			@RequestParam String search) {
		JSONArray ja = new JSONArray();
		ja = routerManager.getRouterList(page, limit, search);
		return ja.toString();
	}


	@RequestMapping(value = "/StartUp", method = { RequestMethod.POST })
	@ResponseBody
	public String startUp(HttpServletRequest request, @RequestParam String uuid) {
		User user = (User) request.getSession().getAttribute("user");
		routerManager.startRouter(uuid, user.getUserId(),user.getUserAllocate());
		return "success";
	}

	@RequestMapping(value = "/Destroy", method = { RequestMethod.POST })
	@ResponseBody
	public String destroy(HttpServletRequest request, @RequestParam String uuid) {
		User user = (User) request.getSession().getAttribute("user");
		if (!routerManager.isRouterHasVnets(uuid, user.getUserId())) {
			routerManager.deleteRouter(uuid, user.getUserId(),
					user.getUserAllocate());
			return "ok";
		} else {
			return "no";
		}
	}

	@RequestMapping(value = "/Create", method = { RequestMethod.POST })
	@ResponseBody
	public String create(HttpServletRequest request,CreateRouterModel createRouterModel) {
		User user=userService.getUser(Integer.valueOf(createRouterModel.getUserid()));
//		User user = (User) request.getSession().getAttribute("user");
		boolean result=routerManager.createRouter(createRouterModel.getUuid(),user.getUserId(), 
				createRouterModel.getName(),
				user.getUserAllocate());
		return String.valueOf(result);
	}

	@RequestMapping(value = "/TableRTs", method = { RequestMethod.GET })
	@ResponseBody
	public String tableRTs(HttpServletRequest request) {
		JSONArray ja =  new JSONArray();
		ja = routerManager.getRoutersOfUser();
		return ja.toString();
	}
	
	@RequestMapping(value = "/ShutDown", method = { RequestMethod.POST })
	@ResponseBody
	public String shutDown(HttpServletRequest request, @RequestParam String uuid,
			@RequestParam String force) {
		User user = (User) request.getSession().getAttribute("user");
		routerManager.shutdownRouter(uuid, force, user.getUserId(),
				user.getUserAllocate());
		return "success";
	}
	
	@RequestMapping(value = "/checkRouterip", method = { RequestMethod.POST })
	@ResponseBody
	public String checkRouterip(HttpServletRequest request, @RequestParam String routerip) {
		JSONObject obj=routerManager.checkRouterip(routerip);
		return obj.toString();
	}
	
}
