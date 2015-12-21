package com.beyondsphere.service;

import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;

import com.beyondsphere.entity.OCLog;

public interface LogService {
	
	/**
	 * 获取日志列表
	 * @author lining
	 * @param userId, status, start, num
	 * @return
	 */
	public abstract JSONArray getLogList(int userId, int status, int start,
			int num,String search);
	
	/**
	 * 增加日志
	 * @author lining
	 * @param logUID, logObject, logAction, logStatus, logInfo, logTime, logElapse
	 * @return
	 */
	public abstract OCLog addLog(Integer logUID, Integer logObject, Integer logAction,
			Integer logStatus, String logInfo, Date logTime, Integer logElapse);
	
	/**
	 * 删除日志列表
	 * @author lining
	 * @param logId
	 * 
	 */
	public abstract boolean deleteLog(int logId);
	
	/**
	 * 删除日志列表根据日期
	 * @author lining
	 * @param logId
	 * 
	 */
	public abstract boolean deleteLogByTime(Date logTime);
	
	/**
	 * 将文件转换为对应的格式并返回文件名
	 * @author lining
	 * 
	 */
	public JSONObject logToFile(int userId);
}
