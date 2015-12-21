package com.oncecloud.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.oncecloud.model.LogConstant;

@Entity
@Table(name = "oc_log_backup")
public class OCLogBackup {
	
	private Integer logId;
	private Integer logUID;
	private Integer logObject;
	private Integer logAction;
	private Integer logStatus;
	private String logInfo;
	private Date logTime;
	private Integer logElapse;

	public OCLogBackup() {}

	/**
	 * 获取系统日志主键，自增整型
	 * @return
	 */
	@Id
	@Column(name = "log_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getLogId() {
		return logId;
	}

	/**
	 * 设置系统日志主键
	 * @param logId
	 */
	public void setLogId(Integer logId) {
		this.logId = logId;
	}

	/**
	 * 设置日志所有者id
	 * @return
	 */
	@Column(name = "log_uid")
	public Integer getLogUID() {
		return logUID;
	}

	/**
	 * 获取日志所有者id
	 * @param logUID
	 */
	public void setLogUID(Integer logUID) {
		this.logUID = logUID;
	}

	/**
	 * 获取日志对象
	 * @return
	 */
	@Column(name = "log_object")
	public Integer getLogObject() {
		return logObject;
	}

	/**
	 * 设置日志对象
	 * @param logObject
	 */
	public void setLogObject(Integer logObject) {
		this.logObject = logObject;
	}

	/**
	 * 获取日志行为
	 * @return
	 */
	@Column(name = "log_action")
	public Integer getLogAction() {
		return logAction;
	}

	/**
	 * 设置日志行为
	 * @param logAction
	 */
	public void setLogAction(Integer logAction) {
		this.logAction = logAction;
	}

	/**
	 * 获取日志状态
	 * @return
	 */
	@Column(name = "log_status")
	public Integer getLogStatus() {
		return logStatus;
	}

	/**
	 * 设置日志状态
	 * @param logStatus
	 */
	public void setLogStatus(Integer logStatus) {
		this.logStatus = logStatus;
	}
	
	/**
	 * 获取日志信息
	 * @return
	 */
	@Column(name = "log_info")
	public String getLogInfo() {
		return logInfo;
	}

	/**
	 * 设置日志信息
	 * @param logInfo
	 */
	public void setLogInfo(String logInfo) {
		this.logInfo = logInfo;
	}
	
	/**
	 * 获取日志时间
	 * @return
	 */
	@Column(name = "log_time")
	public Date getLogTime() {
		return logTime;
	}

	/**
	 * 设置日志时间
	 * @param logTime
	 */
	public void setLogTime(Date logTime) {
		this.logTime = logTime;
	}

	/**
	 * 获取日志过期时间
	 * @return
	 */
	@Column(name = "log_elapse")
	public Integer getLogElapse() {
		return logElapse;
	}

	/**
	 * 设置日志过期时间
	 * @param logElapse
	 */
	public void setLogElapse(Integer logElapse) {
		this.logElapse = logElapse;
	}
	
	@Override
	public String toString() {
		return LogConstant.logObject.values()[logObject].toString()
				+ LogConstant.logAction.values()[logAction].toString()
				+ LogConstant.logStatus.values()[logStatus].toString();
	}
}
