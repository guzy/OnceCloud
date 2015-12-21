package com.beyondsphere.service;

import java.util.Date;

import org.json.JSONArray;

/**
 * @author luogan 
 * 2015年5月19日 
 * 下午2:06:18
 */
public interface ContainerService {

	public abstract JSONArray ContainerTemplateList(int userId, int page,
			int limit, String search, String type);

	public abstract boolean deleteContainer(int userId, String containerId);

	public abstract boolean startContainer(int userId, String containerId);

	public abstract boolean stopContainer(int userId, String containerId);

	public abstract boolean contianerMakeImage(String containerId,
			String imgaeAddress, String imageName, String imageVersion);

	public abstract JSONArray queryContainerList(int userId, int page,
			int limit, String groupUuid, String search);

	/**
	 * 获取所有的容器信息
	 * 
	 * @author lining
	 * @return
	 */
	public abstract JSONArray listConstainers();

	public abstract boolean createContainer(String vmUuid, String tplUuid,
			int userId, int cpu, int memory, String name, String desc, String statistics,
			String startCommand, String link, String dockerParams, String port,Date conCreateTime);

}
