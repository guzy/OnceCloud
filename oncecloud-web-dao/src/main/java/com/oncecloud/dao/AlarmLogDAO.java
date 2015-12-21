package com.oncecloud.dao;

import com.oncecloud.entity.AlarmLog;

public interface AlarmLogDAO {
	/**
	 * 增加一条告警记录
	 * 
	 * @param logDesc
	 * @return
	 */
	public abstract boolean addAlarmLog(String logDesc, String ruleId);

	/**
	 * 获取一条告警记录
	 * 
	 * @param logUuid
	 * @return
	 */
	public abstract AlarmLog getAlarmLog(String logUuid);

	/**
	 * 修改告警次数加1
	 * 
	 * @param logUuid
	 * @param logFlag
	 * @return
	 */
	public abstract boolean updateLogFlag(String logUuid, int logFlag);

	/**
	 * 获取告警记录的上一次记录时间
	 * 
	 * @param logUuid
	 * @return
	 */
	// public abstract String getLogTime(String logUuid) ;

	/**
	 * 获取最新告警记录
	 * 
	 * @param ruleId
	 * @param logDesc
	 * @return
	 */
	public abstract AlarmLog getAlarmLogByDesc(String ruleId, String logDesc);

}
