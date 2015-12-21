package com.oncecloud.dao;

import java.util.List;

import com.oncecloud.model.performance.ContainerInfo;

public interface ContainerPerformanceDAO {
	
	
	/**
	 * 获取容器的监测信息
	 * @author lining
	 * @param containerName
	 * @return
	 */
	public abstract List<ContainerInfo> getContainerInfos(String containerName);
}
