package com.oncecloud.manager;

public interface PerformanceVSphereManger {
	/**
	 * vsphere监测虚拟机
	 */
	public abstract void addPerformanceData(String type);
}
