package com.beyondsphere.dao;

import java.util.List;

import com.beyondsphere.entity.Alarm;

public interface AlarmDAO {

	/**
	 * 增加一条告警策略,初始状态默认为活跃
	 * 
	 * @param alarmUuid
	 * @param alarmName
	 * @param alarmPeriod
	 * @param userId
	 * @return
	 */
	public abstract boolean addAlarm(String alarmUuid, String alarmName,
			int userId);

	/**
	 * 获取告警策略总数
	 *
	 * @param userId
	 * @param alarmName
	 * @return
	 */
	public abstract int countAlarmList(int userId, String alarmName);

	/**
	 * 获取告警策略
	 *
	 * @param alarmUuid
	 * @return
	 */
	public abstract Alarm getAlarm(String alarmUuid);

	/**
	 * 获取某一用户告警策略列表
	 *
	 * @param alarmUuid
	 * @param page
	 * @param limit
	 * @return
	 */
	public abstract List<Alarm> getAlarmList(int userId, String alarmName,
			int page, int limit);

	/**
	 * 删除一条策略
	 *
	 * @param alarm
	 * @return
	 */
	public abstract boolean destoryAlarm(Alarm alarm);

	/**
	 * 更新策略
	 *
	 * @param alarm
	 * @return
	 */
	public abstract boolean updateAlarm(Alarm alarm);

	/**
	 * 更新策略基本信息
	 * 
	 * @author lining
	 * @return
	 * 
	 */
	public boolean updateName(String alarmUuid, String newName,
			String description);

}