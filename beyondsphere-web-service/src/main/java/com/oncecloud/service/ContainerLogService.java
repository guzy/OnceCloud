package com.oncecloud.service;

import org.json.JSONObject;

public interface ContainerLogService {
	
	/**
	 * 获取容器的日志信息
	 * @author lining
	 * @param containerId
	 * @return
	 */
	public abstract JSONObject getContainerLogs(String containerId, int logType);
	
}
