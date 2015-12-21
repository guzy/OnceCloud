package com.oncecloud.core;

import java.util.List;

import org.json.JSONObject;

import com.github.dockerjava.api.model.Container;

/**
 * @author luogan 
 * 2015年5月19日 
 * 下午1:55:59
 */
public interface ContainerCore {

	/**
	 * @param url
	 * @param name
	 * @param command
	 * @param cpu
	 * @param memory
	 * @param startCommand
	 * @param link
	 * @param dockerParams
	 * @return
	 * create container
	 */
	public abstract JSONObject createContainer(String url,
			String name, String command, int cpu, int memory,
			String startCommand, String link, String dockerParams);
	
	/**
	 * @param hostIp
	 * @param password
	 * @param imageName
	 * @param name
	 * @param startParams
	 * @return
	 */
	public abstract JSONObject createContainer(String hostIp, String hostPwd, String imageName,
			String name, String startParams);

	/**
	 * @param containerId
	 * @return
	 * 启动容器
	 * start container
	 */
	public abstract boolean startContainer(String containerId);

	/**
	 * @param containerId
	 * @return
	 * 停止容器
	 * stop container
	 */
	public abstract boolean stopContainer(String containerId);

	/**
	 * @param containerId
	 * @return
	 * 删除容器
	 * delete container
	 */
	public abstract boolean deleteContainer(String containerId);
	
	/**
	 * @param containerId
	 * @param imgaeAddress
	 * @param imageName
	 * @param imageVersion
	 * @return
	 * 根据容器创建镜像
	 */
	public abstract boolean contianerMakeImage(String containerId,
			String imgaeAddress, String imageName, String imageVersion);

	/**
	 * @return
	 * 获取所有的容器
	 */
	public abstract List<Container> listAllContainers();
}
