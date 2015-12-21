package com.oncecloud.service;

import org.json.JSONArray;
import org.json.JSONObject;

import com.oncecloud.model.AlarmRuleConstant.RuleName;
import com.oncecloud.model.AlarmRuleConstant.RuleTerm;

public interface AlarmService{

	/**
	 * 新建告警策略
	 * @param alarmUuid, alarmName, userId, alarmRuleList
	 * @return
	 */
	public abstract boolean saveAlarm(String alarmUuid, String alarmName,
			int userId, JSONArray alarmRuleList);
	
	/**
	 * 用户删除一条告警策略及对应的全部规则
	 * @param alarmUuid
	 * @return
	 */
	public abstract boolean destoryAlarm(String alarmUuid);

	/**
	 * 将告警策略绑定到主机
	 * @param userId, rsuuidStr, alarmUuid
	 * @return
	 */
	public abstract boolean addResourceToAlarm(int userId, String rsuuidStr,
			String alarmUuid);
	
	/**
	 * 显示告警策略列表
	 * @param userId, alarmName, page, limit
	 * @return
	 */
	public abstract JSONArray getAlarmList(int userId, String alarmName,int page, int limit);

	/**
	 * 告警策略可绑定的主机列表
	 * @param alarmUuid, page, limit, search, userId
	 * @return
	 */
	public abstract JSONArray getAlarmVMList(String alarmUuid, int page, int limit,
			String search, Integer userId);

	/**
	 * 显示告警策略的详细内容
	 * @param userId, alarmUuid
	 * @return
	 */
	public abstract JSONObject getAlarm(int userId, String alarmUuid);

	/**
	 * 修改策略的属性（名称、描述）
	 * @param alarmUuid, alarmName, alarmDesc
	 * @return
	 */
	public abstract boolean modifyAlarm(String alarmUuid, String alarmName, String alarmDesc);	

	/**
	 * 更新绑定关系
	 * @param rsUuid
	 * @return
	 */
	public abstract boolean removeRS(String rsUuid);

	/**
	 * 显示告警策略对应的规则列表
	 * @param alarmUuid, page, limit
	 * @return
	 */
	public abstract JSONArray getRuleList(String alarmUuid, int page, int limit);
	
	/**
	 * 新建规则
	 * @param alarmUuid, ruleId, ruleName, ruleTerm, ruleThreshold
	 * @return
	 */
	public abstract boolean createAlarmRule(String alarmUuid, String ruleId, RuleName ruleName,
			RuleTerm ruleTerm,Integer ruleThreshold);

	/**
	 * 修改规则
	 * @param ruleId, ruleName, ruleTerm, ruleThreshold
	 * @return
	 */
	public abstract boolean modifyRule(String ruleId, RuleName ruleName, RuleTerm ruleTerm,
			Integer ruleThreshold);

	/**
	 * 删除规则
	 * @param ruleId
	 * @return
	 */
	public abstract boolean deleteRule(String ruleId);
	
}
