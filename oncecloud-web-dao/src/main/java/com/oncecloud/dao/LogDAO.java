/**
 * @author hty
 * @time 上午11:05:48
 * @date 2014年12月9日
 */
package com.oncecloud.dao;

import java.util.Date;
import java.util.List;

import com.oncecloud.entity.OCLog;

public interface LogDAO {

	/**
	 * 获取日志列表
	 * @param logUID, logStatus, start, num
	 * @return list
	 */
	public abstract List<OCLog> getLogList(int logUID, int logStatus, int start, int num,String search);
	
	/**
	 * 插入日志信息
	 * @param log
	 * @return OCLog
	 */
	public abstract OCLog insertLog(OCLog log);
	
	/**
	 * 插入日志信息
	 * @param logUID, logObject, logAction, logStatus, logInfo, logTime, logElapse
	 * @return OCLog
	 */
	public abstract OCLog insertLog(int logUID, int logObject, int logAction,
			int logStatus, String logInfo, Date logTime, int logElapse);
	
	public boolean deleteLog(int logId);
	
	public boolean deleteLogByTime(Date logTime);
}
