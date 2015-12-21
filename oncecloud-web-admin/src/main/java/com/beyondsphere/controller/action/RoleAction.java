/**
 * @author cyh
 * @time 上午11:05:48
 * @date 2014年12月9日
 */
package com.beyondsphere.controller.action;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beyondsphere.manager.RoleManager;
import com.oncecloud.entity.OCRole;
import com.oncecloud.entity.User;
import com.oncecloud.model.AuthNode;
import com.oncecloud.model.ListModel;
import com.oncecloud.model.PagedList;

@RequestMapping("RoleAction")
@Controller
public class RoleAction {
	
	@Resource
	private RoleManager roleManager;

	@RequestMapping(value = "/RoleList", method = { RequestMethod.GET })
	@ResponseBody
	public PagedList<OCRole> roleList(HttpServletRequest request, ListModel list) {
		return roleManager.getRoleList(list.getPage(),list.getLimit(),list.getSearch());
	}
	
	@RequestMapping(value = "/Create", method = { RequestMethod.POST })
	@ResponseBody
	public OCRole create(HttpServletRequest request, @RequestParam String roleName,
			@RequestParam String roleIntroduce, @RequestParam String roleRemarks) {
		User user = (User) request.getSession().getAttribute("user");
		int userId = user.getUserId();
		return roleManager.doCreateRole(userId,roleName,roleIntroduce,roleRemarks);
	}
	
	@RequestMapping(value = "/QueryRole", method = { RequestMethod.GET })
	@ResponseBody
	public OCRole queryRole(HttpServletRequest request, @RequestParam String roleName) {
		return roleManager.doQueryRole(roleName);
	}
	
	@RequestMapping(value = "/Delete", method = { RequestMethod.GET })
	@ResponseBody
	public String delete(HttpServletRequest request,
			@RequestParam("roleid") int roleid, @RequestParam String roleName) {
		User user = (User) request.getSession().getAttribute("user");
		int userId = user.getUserId();
		JSONObject jo = roleManager.doDeleteRole(userId, roleid,roleName);
		return jo.toString();
	}

	@RequestMapping(value = "/RoleData", method = { RequestMethod.POST })
	@ResponseBody
	public List<AuthNode> roleData(HttpServletRequest request, @RequestParam int roleid) {
		return roleManager.getRoleData(roleid);
	}
	
	@RequestMapping(value = "/SetAuth", method = { RequestMethod.POST })
	@ResponseBody
	public String setAuth(HttpServletRequest request, @RequestParam int roleid,@RequestParam String actionids) {
		User user = (User) request.getSession().getAttribute("user");
		int userId = user.getUserId();
		return roleManager.setAuth(userId,roleid,actionids);
	}
}