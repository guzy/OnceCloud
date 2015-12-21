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
import com.beyondsphere.manager.QAManager;

@RequestMapping("QAAction")
@Controller
public class QAAction {
	
	@Resource
	private QAManager qaManager;

	@RequestMapping(value = "/QuestionList", method = { RequestMethod.GET })
	@ResponseBody
	public String questionList(HttpServletRequest request, ListModel list) {
		User user = (User) request.getSession().getAttribute("user");
		JSONArray ja = qaManager.getQuestionList(user.getUserId(),
				user.getUserLevel().ordinal(), list.getPage(), list.getLimit(),
				list.getSearch());
		return ja.toString();
	}
	
	@RequestMapping(value = "/CreateQuestion", method = { RequestMethod.POST })
	@ResponseBody
	public String createQuestion(HttpServletRequest request,
			@RequestParam String title, @RequestParam String content) {
		User user = (User) request.getSession().getAttribute("user");
		JSONObject jo = qaManager.createQuestion(user.getUserId(), title, content);
		return jo.toString();
	}

	@RequestMapping(value = "/CloseQuestion", method = { RequestMethod.GET })
	@ResponseBody
	public String closeQuestion(HttpServletRequest request,
			@RequestParam int qaid) {
		User user = (User) request.getSession().getAttribute("user");
		JSONArray ja = qaManager
				.closeQuestion(user.getUserId(), qaid);
		return ja.toString();
	}

	@RequestMapping(value = "/QuestionDetail", method = { RequestMethod.GET })
	@ResponseBody
	public String questionDetail(HttpServletRequest request,
			@RequestParam int qaId) {
		JSONArray ja = qaManager.getQuestionDetail(qaId);
		return ja.toString();
	}

	@RequestMapping(value = "/Reply", method = { RequestMethod.POST })
	@ResponseBody
	public String reply(HttpServletRequest request, @RequestParam int qaId,
			@RequestParam String content) {
		User user = (User) request.getSession().getAttribute("user");
		JSONArray ja = qaManager.reply(user.getUserId(),
				user.getUserLevel().ordinal(), qaId, content);
		return ja.toString();
	}

	@RequestMapping(value = "/ReplyList", method = { RequestMethod.GET })
	@ResponseBody
	public String replyList(HttpServletRequest request, @RequestParam int qaId) {
		JSONArray ja = qaManager.getReplyList(qaId);
		return ja.toString();
	}
}