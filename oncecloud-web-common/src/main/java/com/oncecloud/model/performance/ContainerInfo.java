package com.oncecloud.model.performance;

import java.io.Serializable;

public class ContainerInfo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 631454201082142459L;
	
	private long time;
	private String containerName;
	private double cpuUsagePercent;
	private long memUsage;
	private Integer packageSendCount;
	private Integer packageRecvCount;
	
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public String getContainerName() {
		return containerName;
	}
	public void setContainerName(String containerName) {
		this.containerName = containerName;
	}
	public double getCpuUsagePercent() {
		return cpuUsagePercent;
	}
	public void setCpuUsagePercent(double cpuUsagePercent) {
		this.cpuUsagePercent = cpuUsagePercent;
	}
	public long getMemUsage() {
		return memUsage;
	}
	public void setMemUsage(long memUsage) {
		this.memUsage = memUsage;
	}
	public Integer getPackageSendCount() {
		return packageSendCount;
	}
	public void setPackageSendCount(Integer packageSendCount) {
		this.packageSendCount = packageSendCount;
	}
	public Integer getPackageRecvCount() {
		return packageRecvCount;
	}
	public void setPackageRecvCount(Integer packageRecvCount) {
		this.packageRecvCount = packageRecvCount;
	}
	@Override
	public String toString() {
		return "ContainerInfo [time=" + time + ", containerName="
				+ containerName + ", cpuUsagePercent=" + cpuUsagePercent
				+ ", memUsage=" + memUsage + ", packageSendCount="
				+ packageSendCount + ", packageRecvCount=" + packageRecvCount
				+ "]";
	}
	
}
