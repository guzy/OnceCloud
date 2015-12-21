package com.oncecloud.manager.Impl;


import javax.annotation.Resource;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.oncecloud.manager.QAManager;
import com.oncecloud.service.QAService;

@Service("QAManager")
public class QAManagerImpl implements QAManager{
	
	@Resource
	private QAService qaService;
	
	public JSONArray getQuestionList(int userId, int userLevel, int page,
			int limit, String search) {
		return qaService.getQuestionList(userId, userLevel, page, limit, search);
	}

	public JSONArray closeQuestion(int userId, int qaId) {
		return qaService.closeQuestion(userId, qaId);
	}

	public JSONObject createQuestion(int userId, String qaTitle,
			String qaContent) {
		return qaService.createQuestion(userId, qaTitle, qaContent);
	}

	public JSONArray reply(int userId, int userLevel, int qaTid, String qaContent) {
		return qaService.reply(userId, userLevel, qaTid, qaContent);
	}

	public JSONArray getQuestionDetail(int qaId) {
		return qaService.getQuestionDetail(qaId);
	}

	public JSONArray getReplyList(int qaId) {
		return qaService.getReplyList(qaId);
	}
}
