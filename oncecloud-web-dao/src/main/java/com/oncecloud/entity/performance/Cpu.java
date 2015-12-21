package com.oncecloud.entity.performance;

import java.io.Serializable;

public class Cpu implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1920135475340301589L;
	private Long time;
	private String uuid;
	private Integer cpuId;
	private Double usage;
	
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
	public Integer getCpuId() {
		return cpuId;
	}
	public void setCpuId(Integer cpuId) {
		this.cpuId = cpuId;
	}
	public Double getUsage() {
		return usage;
	}
	public void setUsage(Double usage) {
		this.usage = usage;
	}
	
	@Override
	public String toString() {
		return "Cpu [time=" + time + ", uuid=" + uuid + ", cpuId=" + cpuId + ", usage=" + usage + "]";
	}
}
