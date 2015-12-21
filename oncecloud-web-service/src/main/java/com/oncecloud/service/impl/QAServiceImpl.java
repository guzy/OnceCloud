package com.oncecloud.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.oncecloud.dao.QADAO;
import com.oncecloud.dao.UserDAO;
import com.oncecloud.entity.QA;
import com.oncecloud.entity.User;
import com.oncecloud.message.MessagePush;
import com.oncecloud.message.MessageUtil;
import com.oncecloud.message.MessageUtilities;
import com.oncecloud.service.QAService;
import com.oncecloud.util.TimeUtils;
import com.oncecloud.util.Utilities;

@Component("QAService")
public class QAServiceImpl implements QAService {

	@Resource
	private QADAO qaDAO;
	@Resource
	private UserDAO userDAO;
	@Resource
	private MessageUtil message;
	@Resource
	private MessagePush messagePush;

	/**
	 * 获取用户问题列表
	 * 
	 * @param userId
	 * @param userLevel
	 * @param page
	 * @param limit
	 * @param search
	 * @return
	 */
	public JSONArray getQuestionList(int userId, int userLevel, int page,
			int limit, String search) {
		JSONArray ja = new JSONArray();
		User user = userDAO.getUser(userId);
		int total = qaDAO.countAllQuestionList(user, search);
		ja.put(total);
		List<QA> qaList = qaDAO.getOnePageQuestionList(user, page,
				limit, search);
		if (qaList != null) {
			for (QA qa : qaList) {
				JSONObject qaObj = new JSONObject();
				qaObj.put("qaId", qa.getQaId());
				if (userLevel == 0) {
					qaObj.put("qaUserName", userDAO.getUser(qa.getQaUID()).getUserName());
				}
				qaObj.put("qaTitle", TimeUtils.encodeText(qa.getQaTitle()));
				qaObj.put("qaSummary", TimeUtils.encodeText(TimeUtils
						.getSummary(qa.getQaContent())));
				qaObj.put("qaStatus", qa.getQaStatus());
				qaObj.put("qaReply", qa.getQaReply());
				qaObj.put("qaTime", TimeUtils.formatTime(qa.getQaTime()));
				ja.put(qaObj);
			}
		}
		return ja;
	}

	public JSONArray closeQuestion(int userId, int qaId) {
		JSONArray ja = new JSONArray();
		boolean result = qaDAO.closeQuestion(qaId);
		JSONObject jo = new JSONObject();
		jo.put("isSuccess", result);
		ja.put(jo);
		if (result == true) {
			message.pushSuccess(userId, "表单关闭成功");
		} else {
			message.pushError(userId, "表单关闭失败");
		}
		return ja;
	}

	public JSONObject createQuestion(int userId, String qaTitle,
			String qaContent) {
		JSONObject jo = new JSONObject();
		Date qaTime = new Date();
		int qaId = qaDAO.insertQuestion(userId, qaTitle, qaContent,
				qaTime);
		jo.put("qaId", qaId);
		if (qaId > 0) {
			jo.put("qaSummary",
					TimeUtils.encodeText(TimeUtils.getSummary(qaContent)));
			jo.put("qaTime", TimeUtils.formatTime(qaTime));
			message.pushSuccess(userId, "表单提交成功");
		} else {
			message.pushError(userId, "表单提交失败");
		}
		return jo;
	}

	public JSONArray reply(int userId, int userLevel, int qaTid, String qaContent) {
		JSONArray ja = new JSONArray();
		Date qaTime = new Date();
		int status = 3;
		if (userLevel == 0) {
			status = 2;
		}
		int qaId = qaDAO.insertAnswer(userId, qaContent, qaTid, status,
				qaTime);
		JSONObject jo = new JSONObject();
		jo.put("isSuccess", qaId);
		// push message
		if (qaId > 0) {
			jo.put("qaContent", TimeUtils.encodeText(qaContent));
			jo.put("qaTime", TimeUtils.formatTime(qaTime));
			message.pushSuccess(userId, "回复提交成功");
		} else {
			message.pushError(userId, "回复提交失败");
		}
		ja.put(jo);
		return ja;
	}

	public JSONArray getQuestionDetail(int qaId) {
		JSONArray ja = new JSONArray();
		QA qa = qaDAO.getQuestion(qaId);
		if (qa != null) {
			JSONObject qaObj = new JSONObject();
			qaObj.put("qaTitle", TimeUtils.encodeText(qa.getQaTitle()));
			qaObj.put("qaContent", TimeUtils.encodeText(qa.getQaContent()));
			qaObj.put("qaStatus", qa.getQaStatus());
			qaObj.put("qaTime", TimeUtils.formatTime(qa.getQaTime()));
			ja.put(qaObj);
		}
		return ja;
	}

	public JSONArray getReplyList(int qaId) {
		JSONArray ja = new JSONArray();
		List<Object> qaList = qaDAO.getAnswerList(qaId);
		if (qaList != null) {
			for (Object qa : qaList) {
				Object[] qaO = (Object[]) qa;
				JSONObject qaObj = new JSONObject();
				qaObj.put("qaUser", (String) qaO[0]);
				qaObj.put("qaContent", TimeUtils.encodeText((String) qaO[1]));
				qaObj.put("qaStatus", (Integer) qaO[2]);
				qaObj.put("qaTime", TimeUtils.formatTime((Date) qaO[3]));
				ja.put(qaObj);
			}
		}
		return ja;
	}

	public JSONArray getQuestionList(int userId, int page, int limit,
			String search) {
		JSONArray ja = new JSONArray();
		User user = userDAO.getUser(userId);
		int total = qaDAO.countAllQuestionList(user, search);
		ja.put(total);
		List<QA> qaList = qaDAO.getOnePageQuestionList(user, page, limit, search);
		if (qaList != null) {
			for (QA qa : qaList) {
				JSONObject qaObj = new JSONObject();
				qaObj.put("qaId", qa.getQaId());
				qaObj.put("qaTitle", Utilities.encodeText(qa.getQaTitle()));
				qaObj.put("qaSummary", Utilities.encodeText(Utilities
						.getSummary(qa.getQaContent())));
				qaObj.put("qaStatus", qa.getQaStatus());
				qaObj.put("qaReply", qa.getQaReply());
				qaObj.put("qaTime", Utilities.formatTime(qa.getQaTime()));
				ja.put(qaObj);
			}
		}
		return ja;
	}
	
	public JSONArray reply(int userId, int qaTid, String qaContent) {
		JSONArray ja = new JSONArray();
		Date qaTime = new Date();
		int status = 3;
		int qaId = qaDAO.insertAnswer(userId, qaContent, qaTid, status,
				qaTime);
		JSONObject jo = new JSONObject();
		jo.put("isSuccess", qaId);
		if (qaId > 0) {
			jo.put("qaContent", Utilities.encodeText(qaContent));
			jo.put("qaTime", Utilities.formatTime(qaTime));
			messagePush.pushMessage(userId,
					MessageUtilities.stickyToSuccess("回复提交成功"));
		} else {
			messagePush.pushMessage(userId,
					MessageUtilities.stickyToSuccess("回复提交失败"));
		}
		ja.put(jo);
		return ja;
	}
}
