/**
 * @author hty
 * @time 上午11:05:48
 * @date 2014年12月9日
 */
package com.oncecloud.manager;

import org.json.JSONArray;

public interface LogManager {

	/**
	 * 获取日志列表
	 * @author lining
	 * @param userId, status, start, num
	 * @return
	 */
	public abstract JSONArray getLogList(int userId, int status, int start,
			int num,String search);
	
}