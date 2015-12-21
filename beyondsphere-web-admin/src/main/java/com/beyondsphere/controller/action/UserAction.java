/**
 * @author hty
 * @time 上午11:05:48
 * @date 2014年12月9日
 */
package com.beyondsphere.controller.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beyondsphere.model.ListModel;
import com.beyondsphere.entity.User;
import com.beyondsphere.manager.UserManager;

@RequestMapping("UserAction")
@Controller
public class UserAction {
	
	@Resource
	private UserManager userManager;

	@RequestMapping(value = "/UserList", method = { RequestMethod.GET })
	@ResponseBody
	public String userList(HttpServletRequest request, ListModel list) {
		JSONArray ja = userManager.getUserList(list.getPage(),
				list.getLimit(), list.getSearch());
		return ja.toString();
	}
	

	@RequestMapping(value = "/Delete", method = { RequestMethod.GET })
	@ResponseBody
	public String delete(HttpServletRequest request,
			@RequestParam("userid") int changeId, @RequestParam String username) {
		User user = (User) request.getSession().getAttribute("user");
		JSONObject jo = userManager.doDeleteUser(user.getUserId(),changeId,
				username);
		return jo.toString();
	}

	@RequestMapping(value = "/OneUser", method = { RequestMethod.GET })
	@ResponseBody
	public String oneUser(HttpServletRequest request,
			@RequestParam("userid") int userid) {
		JSONObject jo = userManager.doGetOneUser(userid);
		return jo.toString();
	}

	@RequestMapping(value = "/Create", method = { RequestMethod.POST })
	@ResponseBody
	public String create(HttpServletRequest request,
			@RequestParam String userName, @RequestParam String userPassword,
			@RequestParam String userEmail, @RequestParam String userTelephone,
			@RequestParam String userCompany, @RequestParam String userLevel,
			@RequestParam String poolUuid,@RequestParam int roleUuid,@RequestParam String areaId) {
		User user = (User) request.getSession().getAttribute("user");
		JSONArray ja = userManager.doCreateUser(userName,
				userPassword, userEmail, userTelephone, userCompany, userLevel,
				user.getUserId(), poolUuid,roleUuid,areaId);
		return ja.toString();
	}

	@RequestMapping(value = "/Update", method = { RequestMethod.POST })
	@ResponseBody
	public void update(HttpServletRequest request,
			@RequestParam String userName, @RequestParam int changeId,
			@RequestParam String userEmail, @RequestParam String userTelephone,
			@RequestParam String userCompany, @RequestParam String userLevel,@RequestParam int roleUuid) {
		User user = (User) request.getSession().getAttribute("user");
		userManager.doUpdateUser(user.getUserId(),
				changeId, userName, userEmail, userTelephone, userCompany,
				userLevel,roleUuid);
	}
	
    //
	@RequestMapping(value = "/UpdateAreaInfo", method = { RequestMethod.POST })
	@ResponseBody
	public void updateAreaInfo(HttpServletRequest request,
			@RequestParam String userActiveLocation) {
		User user = (User) request.getSession().getAttribute("user");
		userManager.doUpdateAreaInfo(user.getUserId(),userActiveLocation);
		//跳转时清除本地session
		if(request.getSession().getAttribute("user")!=null){
			request.getSession().removeAttribute("user");
		}
		if(request.getSession().getAttribute("area")!=null){
			request.getSession().removeAttribute("area");
		}
		
	}
	
	@RequestMapping(value = "/UpdateAdmin", method = { RequestMethod.POST })
	@ResponseBody
	public String update(HttpServletRequest request,
			@RequestParam String userPwd,@RequestParam String userEmail, 
			@RequestParam String userTelephone) {
		
		User user = (User) request.getSession().getAttribute("user");
		if(userPwd==null||"".equals(userPwd)){
			userPwd=user.getUserPass();
		}
		boolean result=userManager.doUpdateAdmin(user.getUserId(),userPwd, userEmail, userTelephone);
		if(result){
			user.setUserPass(userPwd);
			user.setUserMail(userEmail);
			user.setUserPhone(userTelephone);
			request.getSession().setAttribute("user", user);
		}
		return String.valueOf(result);
	}
	
	
	@RequestMapping(value = "/QueryUser", method = { RequestMethod.GET })
	@ResponseBody
	public String queryUser(HttpServletRequest request, @RequestParam String userName) {
		JSONArray ja = userManager.doQueryUser(userName);
		return ja.toString();
	}

	@RequestMapping(value = "/UserListSave", method = { RequestMethod.GET })
	@ResponseBody
	public String list(HttpServletRequest request) {
		JSONArray ja = userManager.getUserList();
		return ja.toString();
	}
	
	@RequestMapping(value = "/QueryAdminPass", method = { RequestMethod.POST })
	@ResponseBody
	public String QueryAdminPass(HttpServletRequest request,@RequestParam String userPwd) {
		User user = (User) request.getSession().getAttribute("user");
		boolean result=user.getUserPass().equals(userPwd);
		return String.valueOf(result);
	}
	
	
	@RequestMapping(value = "/createMap", method = { RequestMethod.POST })
	@ResponseBody
	public String createMap(HttpServletRequest request,
			@RequestParam String userid, @RequestParam String lng,@RequestParam String lat) {
		boolean result=userManager.createMap(userid,lng,lat);
		return String.valueOf(result);
	}
	@RequestMapping(value = "/getUsermap", method = { RequestMethod.POST })
	@ResponseBody
	public String getUsermapByUserid(HttpServletRequest request,
			@RequestParam String userid) {
		JSONArray ja = userManager.getUsermapByUserid(userid);
		return ja.toString();
	}
	
	
	@RequestMapping(value = "/allUsers", method = { RequestMethod.GET })
	@ResponseBody
	public String allUsers(HttpServletRequest request) {
		JSONArray ja = userManager.getUserList();
		return ja.toString();
	}
}
