package com.oncecloud.entity;

public class PoolRecord {
	String uuid;
	String powerStatus;
	String hostUuid;
	String type;

	public PoolRecord(String uuid, String powerStatus, String hostUuid,
			String type) {
		this.uuid = uuid;
		this.powerStatus = powerStatus;
		this.hostUuid = hostUuid;
		this.type = type;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getPowerStatus() {
		return powerStatus;
	}

	public void setPowerStatus(String powerStatus) {
		this.powerStatus = powerStatus;
	}

	public String getHostUuid() {
		return hostUuid;
	}

	public void setHostUuid(String hostUuid) {
		this.hostUuid = hostUuid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "PoolRecord [uuid=" + uuid + ", powerStatus=" + powerStatus
				+ ", hostUuid=" + hostUuid + ", type=" + type + "]";
	}
}
