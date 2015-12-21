/**
 * @author hty
 * @time 上午11:05:48
 * @date 2014年12月9日
 */
package com.oncecloud.controller.action;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oncecloud.manager.LogManager;
import com.oncecloud.entity.User;

@RequestMapping("LogAction")
@Controller
public class LogAction {
	
	@Resource
	private LogManager logManager;

	@RequestMapping(value = "/LogList", method = { RequestMethod.GET })
	@ResponseBody
	public String logList(HttpServletRequest request, @RequestParam int status,
			@RequestParam int start, @RequestParam int num,@RequestParam String search)
			throws ServletException, IOException {
		User user = (User) request.getSession().getAttribute("user");
		int userId = user.getUserId();
		JSONArray ja = logManager.getLogList(userId, status, start,
				num,search);
		return ja.toString();
	}
}
