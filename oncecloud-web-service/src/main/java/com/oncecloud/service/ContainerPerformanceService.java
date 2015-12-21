package com.oncecloud.service;

import org.json.JSONArray;


public interface ContainerPerformanceService {
	
	/**
	 * 获取容器的监测信息
	 * @author lining
	 * @param containerName
	 * @return
	 */
	public abstract JSONArray getContainerInfos(String containerName);
	
}
