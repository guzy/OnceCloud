package com.beyondsphere.manager;

import org.json.JSONArray;
import org.json.JSONObject;

public interface QAManager {
	/**
	 * 获取用户问题列表
	 * @author lining
	 * @param userId, userLevel, page, limit, search
	 * @return
	 */
	public abstract JSONArray getQuestionList(int userId, int userLevel,
			int page, int limit, String search);

	/**
	 * 关闭问题
	 * @author lining
	 * @param userId, qaId
	 * @return
	 */
	public abstract JSONArray closeQuestion(int userId, int qaId);

	/**
	 * 创建问题
	 * @author lining
	 * @param userId, qaTitle, qaContent
	 * @return
	 */
	public abstract JSONObject createQuestion(int userId, String qaTitle,
			String qaContent);
	
	/**
	 * 回复问题
	 * @author lining
	 * @param userId, userLevel, qaTid, qaContent
	 * @return
	 */
	public abstract JSONArray reply(int userId, int userLevel, int qaTid,
			String qaContent);
	
	/**
	 * 获取问题详情
	 * @author lining
	 * @param qaId
	 * @return
	 */
	public abstract JSONArray getQuestionDetail(int qaId);
	
	/**
	 * 获取回复列表
	 * @author lining
	 * @param qaId
	 * @return
	 */
	public abstract JSONArray getReplyList(int qaId);
	
}
