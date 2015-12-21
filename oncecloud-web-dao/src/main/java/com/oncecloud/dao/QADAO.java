package com.oncecloud.dao;

import java.util.Date;
import java.util.List;

import com.oncecloud.entity.QA;
import com.oncecloud.entity.User;

public interface QADAO {

	/**
	 * 获取问题或答复
	 * 
	 * @param qaId
	 * @return
	 */
	public abstract QA getQuestion(int qaId);

	/**
	 * 获取一页问题列表
	 * 
	 * @param userId
	 * @param page
	 * @param limit
	 * @param search
	 * @return
	 */
	public abstract List<QA> getOnePageQuestionList(User user, int page,
			int limit, String search);

	/**
	 * 获取答复列表
	 * 
	 * @param qaTid
	 * @return
	 */
	public abstract List<Object> getAnswerList(int qaTid);

	/**
	 * 获取问题总数
	 * 
	 * @param qaUID
	 * @param search
	 * @return
	 */
	public abstract int countAllQuestionList(User user, String search);

	/**
	 * 添加问题
	 * 
	 * @param qaUID
	 * @param qaTitle
	 * @param qaContent
	 * @param qaTime
	 * @return
	 */
	public abstract int insertQuestion(int qaUID, String qaTitle,
			String qaContent, Date qaTime);

	/**
	 * 添加答复
	 * 
	 * @param qaUID
	 * @param qaContent
	 * @param qaTid
	 * @param qaStatus
	 * @param qaTime
	 * @return
	 */
	public abstract int insertAnswer(int qaUID, String qaContent, int qaTid,
			int qaStatus, Date qaTime);

	/**
	 * 关闭问题
	 * 
	 * @param qaId
	 * @return
	 */
	public abstract boolean closeQuestion(int qaId);
}
