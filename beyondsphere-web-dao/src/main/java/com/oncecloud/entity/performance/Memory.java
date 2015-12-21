package com.oncecloud.entity.performance;

import java.io.Serializable;

public class Memory implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2443598016488571132L;
	private Long time;
	private String uuid;
	private Double totalSize;
	private Double freeSize;
	
	public Long getTime() {
		return time;
	}
	public void setTime(Long time) {
		this.time = time;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public Double getTotalSize() {
		return totalSize;
	}
	public void setTotalSize(Double totalSize) {
		this.totalSize = totalSize;
	}
	public Double getFreeSize() {
		return freeSize;
	}
	public void setFreeSize(Double freeSize) {
		this.freeSize = freeSize;
	}
}
