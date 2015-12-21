package com.oncecloud.dao;

import java.util.List;

import com.oncecloud.entity.AlarmRule;
import com.oncecloud.model.AlarmRuleConstant.RuleName;
import com.oncecloud.model.AlarmRuleConstant.RuleTerm;


public interface AlarmRuleDAO {

	/**
	 * 给相应策略增加一条规则
	 * 
	 * @param ruleId
	 * @param ruleName
	 * @param alarmUuid
	 * @param ruleTerm
	 * @param ruleThreshold
	 * @return
	 */
	public abstract boolean addRule(String ruleId, RuleName ruleName,String alarmUuid,
			RuleTerm ruleTerm,Integer ruleThreshold);
		
	/**
	 * 查询某一告警策略的规则数目
	 * 
	 * @param alarmUuid
	 * @return
	 */
	public abstract int countAlarmRulesList(String alarmUuid);

	/**
	 * 获取一条规则
	 * 
	 * @param ruleId
	 * @return
	 */
	public abstract AlarmRule getAlarmRule(String ruleId);
	
	/**
	 * 获取一条警告策略的规则列表
	 * 
	 * @param ruleId
	 * @return
	 */
	public abstract List<AlarmRule> getRules(String alarmUuid);

	/**
	 * 删除一条规则
	 * 
	 * @param alarmRule
	 * @return
	 */
	public abstract boolean removeAlarmRule(AlarmRule alarmRule) ;
	
	/**
	 * 删除一个警告策略下的所有规则
	 * 
	 * @param alarmUuid
	 * @return
	 */
	public abstract boolean removeRules(String alarmUuid);

	/**
	 * 更新规则
	 * 
	 * @param alarmRule
	 * @return
	 */
	public abstract boolean updateRule(AlarmRule alarmRule);

}
