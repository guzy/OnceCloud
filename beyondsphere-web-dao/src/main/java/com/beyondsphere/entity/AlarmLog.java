package com.beyondsphere.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "al_log")
public class AlarmLog {

	private String logUuid;
	private String logDesc;
	private Date logTime;
	private Integer logFlag;
	private String ruleId;

	public AlarmLog() {

	}

	public AlarmLog(String logUuid, String logDesc, Date logTime,
			Integer logFlag, String ruleId) {
		this.logUuid = logUuid;
		this.logDesc = logDesc;
		this.logTime = logTime;
		this.logFlag = logFlag;
		this.ruleId = ruleId;
	}

	/**
	 * 获取告警记录UUID
	 * 
	 * @param
	 * */
	@Id
	@Column(name = "log_uuid")
	public String getLogUuid() {
		return logUuid;
	}

	/**
	 * 设置告警记录UUID
	 * 
	 * @param logUuid
	 * */
	public void setLogUuid(String logUuid) {
		this.logUuid = logUuid;
	}

	/**
	 * 获取告警记录描述
	 * 
	 * @param
	 * */
	@Column(name = "log_desc")
	public String getLogDesc() {
		return logDesc;
	}

	/**
	 * 设置告警记录描述
	 * 
	 * @param logDesc
	 * */
	public void setLogDesc(String logDesc) {
		this.logDesc = logDesc;
	}

	/**
	 * 获取第一条相同的告警记录时间
	 * 
	 * @param
	 * */
	@Column(name = "log_time")
	public Date getLogTime() {
		return logTime;
	}

	/**
	 * 设置第一条相同的告警记录时间
	 * 
	 * @param logTime
	 * */
	public void setLogTime(Date logTime) {
		this.logTime = logTime;
	}

	/**
	 * 获取相同的告警记录连续重复次数
	 * 
	 * @param
	 * */
	@Column(name = "log_flag")
	public Integer getLogFlag() {
		return logFlag;
	}

	/**
	 * 设置相同的告警记录连续重复次数
	 * 
	 * @param logFlag
	 * */
	public void setLogFlag(Integer logFlag) {
		this.logFlag = logFlag;
	}

	/**
	 * 获取告警记录对应的规则UUID
	 * 
	 * @param
	 * */
	@Column(name = "rule_id")
	public String getRuleId() {
		return ruleId;
	}

	/**
	 * 设置告警记录对应的规则UUID
	 * 
	 * @param ruleId
	 * */
	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}

	@Override
	public String toString() {
		return "AlarmLog [logUuid=" + logUuid + " logDesc=" + logDesc
				+ " logTime=" + logTime + " logFlag=" + logFlag + " ruleId="
				+ ruleId + "]";
	}
}