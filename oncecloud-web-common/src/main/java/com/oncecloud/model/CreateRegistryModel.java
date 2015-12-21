package com.oncecloud.model;

import java.util.Date;

/**
 * @author luogan 2015年7月13日 下午12:00:35
 */
public class CreateRegistryModel {

	private String regId;
	private String regName;
	private String regIP;
	private String regPort;
	private String regStatus;
	private Date regCreateTime;
	private String imageUuid;

	public String getRegId() {
		return regId;
	}

	public void setRegId(String regId) {
		this.regId = regId;
	}

	public String getRegName() {
		return regName;
	}

	public void setRegName(String regName) {
		this.regName = regName;
	}

	public String getRegIP() {
		return regIP;
	}

	public void setRegIP(String regIP) {
		this.regIP = regIP;
	}

	public String getRegPort() {
		return regPort;
	}

	public void setRegPort(String regPort) {
		this.regPort = regPort;
	}

	public String getRegStatus() {
		return regStatus;
	}

	public void setRegStatus(String regStatus) {
		this.regStatus = regStatus;
	}

	public Date getRegCreateTime() {
		return regCreateTime;
	}

	public void setRegCreateTime(Date regCreateTime) {
		this.regCreateTime = regCreateTime;
	}

	public String getImageUuid() {
		return imageUuid;
	}

	public void setImageUuid(String imageUuid) {
		this.imageUuid = imageUuid;
	}

}
