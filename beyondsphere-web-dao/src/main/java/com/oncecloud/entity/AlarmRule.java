package com.oncecloud.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.oncecloud.model.AlarmRuleConstant.RuleName;
import com.oncecloud.model.AlarmRuleConstant.RuleTerm;


@Entity
@Table(name = "al_rules")
public class AlarmRule{
	
	private String ruleId;
	private RuleName ruleName;
	private RuleTerm ruleTerm;
	private Integer ruleThreshold;
	private String alarmUuid;
	
	public AlarmRule(){

	}
	
	public AlarmRule(String ruleId, RuleName ruleName,String alarmUuid,
			RuleTerm ruleTerm,Integer ruleThreshold){
		this.ruleId = ruleId;
		this.alarmUuid = alarmUuid;
		this.ruleName = ruleName;
		this.ruleTerm = ruleTerm;
		this.ruleThreshold = ruleThreshold;
	}
	
	/**
	 * 获取告警规则ID
	 * @param
	 * */
	@Id
	@Column(name = "rule_id")
	public String getRuleId(){
		return ruleId;
	}
	
	/**
	 * 设置告警规则ID
	 * @param ruleId
	 * */
	public void setRuleId(String ruleId){
		this.ruleId = ruleId;
	}
	
	/**
	 * 获取告警规则监控项
	 * @param
	 * */
	@Column(name = "rule_name")
	public RuleName getRuleName(){
		return ruleName;
	}

	/**
	 * 设置告警规则监控项
	 * @param ruleName
	 * */
	public void setRuleName(RuleName ruleName){
		this.ruleName = ruleName;
	}
	
	/**
	 * 获取告警规则条件，大于或小于
	 * @param
	 * */
	@Column(name = "rule_term")
	public RuleTerm getRuleTerm(){
		return ruleTerm;
	}

	/**
	 * 设置告警规则条件，大于或小于
	 * @param ruleTerm
	 * */
	public void setRuleTerm(RuleTerm ruleTerm){
		this.ruleTerm = ruleTerm;
	}
	
	/**
	 * 获取告警规则阈值
	 * @param
	 * */
	@Column(name = "rule_threshold")
	public Integer getRuleThreshold(){
		return ruleThreshold;
	}
	
	/**
	 * 设置告警规则阈值
	 * @param ruleThreshold
	 * */
	public void setRuleThreshold(Integer ruleThreshold){
		this.ruleThreshold = ruleThreshold;
	}
	
	/**
	 * 获取告警规则属于的告警策略UUID
	 * @param 
	 * */
	@Column(name = "al_uuid")
	public String getAlarmUuid(){
		return alarmUuid;
	}
	
	/**
	 * 设置告警规则属于的告警策略UUID
	 * @param alarmUuid
	 * */
	public void setAlarmUuid(String alarmUuid){
		this.alarmUuid = alarmUuid;
	}
	
	@Override
	public String toString() {
		return "AlarmRule [ruleId=" + ruleId + " alarmUuid=" + alarmUuid
				+ " ruleTerm=" + ruleTerm + " ruleThreshold=" + ruleThreshold+ "]";
	}
}