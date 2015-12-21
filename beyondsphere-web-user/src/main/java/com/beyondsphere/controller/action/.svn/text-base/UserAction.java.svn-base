package com.beyondsphere.controller.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beyondsphere.entity.User;
import com.beyondsphere.helper.HashHelper;
import com.beyondsphere.service.UserService;

@RequestMapping("UserAction")
@Controller
public class UserAction {
	
	@Resource
	private UserService userService;
	@Resource
	private HashHelper hashHelper;
	
	@RequestMapping(value = "/OneUser", method = { RequestMethod.GET })
	@ResponseBody
	public String oneUser(HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user");
		return userService.getUser(user.getUserId()).toString();
	}
	
	@RequestMapping(value = "/Update", method = { RequestMethod.POST })
	@ResponseBody
	public int update(HttpServletRequest request,@RequestParam String userName, @RequestParam String userPwd,
			@RequestParam String userNPwd,@RequestParam String userEmail, 
			@RequestParam String userTelephone,@RequestParam String userCompany) {
		User user = (User) request.getSession().getAttribute("user");
		user.setUserName(userName);
		if (!userNPwd.equals("")) {
			user.setUserPass(hashHelper.md5Hash(userNPwd));
		}
		user.setUserMail(userEmail);
		user.setUserPhone(userTelephone);
		user.setUserCompany(userCompany);
		return userService.updateUser(user);
	}
}
