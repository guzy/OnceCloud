/**
 * @author hty
 * @time 上午11:05:48
 * @date 2014年12月9日
 */
package com.oncecloud.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.oncecloud.constants.LogConstant;

@Entity
@Table(name = "oc_log")
public class OCLog {
	
	private Integer logId;
	private Integer logUID;
	private Integer logObject;
	private Integer logAction;
	private Integer logStatus;
	private String logInfo;
	private Date logTime;
	private Integer logElapse;

	public OCLog() {
	}
	
	public OCLog( Integer logUID, Integer logObject,
			Integer logAction, Integer logStatus, String logInfo, Date logTime,
			Integer logElapse) {
		super();
		this.logUID = logUID;
		this.logObject = logObject;
		this.logAction = logAction;
		this.logStatus = logStatus;
		this.logInfo = logInfo;
		this.logTime = logTime;
		this.logElapse = logElapse;
	}

	@Id
	@Column(name = "log_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getLogId() {
		return logId;
	}

	public void setLogId(Integer logId) {
		this.logId = logId;
	}

	@Column(name = "log_uid")
	public Integer getLogUID() {
		return logUID;
	}

	public void setLogUID(Integer logUID) {
		this.logUID = logUID;
	}

	@Column(name = "log_object")
	public Integer getLogObject() {
		return logObject;
	}

	public void setLogObject(Integer logObject) {
		this.logObject = logObject;
	}

	@Column(name = "log_action")
	public Integer getLogAction() {
		return logAction;
	}

	public void setLogAction(Integer logAction) {
		this.logAction = logAction;
	}

	@Column(name = "log_status")
	public Integer getLogStatus() {
		return logStatus;
	}

	public void setLogStatus(Integer logStatus) {
		this.logStatus = logStatus;
	}

	@Column(name = "log_info")
	public String getLogInfo() {
		return logInfo;
	}

	public void setLogInfo(String logInfo) {
		this.logInfo = logInfo;
	}

	@Column(name = "log_time")
	public Date getLogTime() {
		return logTime;
	}

	public void setLogTime(Date logTime) {
		this.logTime = logTime;
	}

	@Column(name = "log_elapse")
	public Integer getLogElapse() {
		return logElapse;
	}

	public void setLogElapse(Integer logElapse) {
		this.logElapse = logElapse;
	}

	@Override
	public String toString() {
		return LogConstant.logObject.values()[logObject].toString()
				+ LogConstant.logAction.values()[logAction].toString()
				+ LogConstant.logStatus.values()[logStatus].toString();
		
	}
	
	public String toAString() {
		return LogConstant.logAction.values()[logAction].toString()
				+ LogConstant.logObject.values()[logObject].toString()
				+ LogConstant.logStatus.values()[logStatus].toString();
	}
}
