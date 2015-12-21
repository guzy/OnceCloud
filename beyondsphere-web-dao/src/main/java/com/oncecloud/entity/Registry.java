package com.oncecloud.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author luogan
 * 2015年7月13日
 * 上午11:52:21
 */
@Entity
@Table(name = "oc_registry")
public class Registry {
	private String regId;
	private String regName;
	private String regIP;
	private String regPort;
	private String regStatus;
	private Date regCreateTime;

	public Registry() {
	}

	public Registry(String regUuid, String name, String ip, String port,
			String status,Date createTime) {
		this.regId = regUuid;
		this.regName = name;
		this.regIP = ip;
		this.regPort =port;
		this.regStatus = status;
		this.regCreateTime = createTime;
	}

	@Id
	@Column(name = "reg_uuid")
	public String getRegId() {
		return regId;
	}

	public void setRegId(String regId) {
		this.regId = regId;
	}

	@Column(name = "reg_name")
	public String getRegName() {
		return regName;
	}

	public void setRegName(String regName) {
		this.regName = regName;
	}

	@Column(name = "reg_ip")
	public String getRegIP() {
		return regIP;
	}

	public void setRegIP(String regIP) {
		this.regIP = regIP;
	}

	@Column(name = "reg_port")
	public String getRegPort() {
		return regPort;
	}

	public void setRegPort(String regPort) {
		this.regPort = regPort;
	}

	@Column(name = "reg_status")
	public String getRegStatus() {
		return regStatus;
	}

	public void setRegStatus(String regStatus) {
		this.regStatus = regStatus;
	}

	@Column(name = "reg_createTime")
	public Date getRegCreateTime() {
		return regCreateTime;
	}

	public void setRegCreateTime(Date regCreateTime) {
		this.regCreateTime = regCreateTime;
	}

	@Override
	public String toString() {
		return "Registry [regId=" + regId + ", regName=" + regName + ", regIP="
				+ regIP + ", regPort=" + regPort + ", regStatus=" + regStatus
				+ ", regCreateTime=" + regCreateTime + "]";
	}

}