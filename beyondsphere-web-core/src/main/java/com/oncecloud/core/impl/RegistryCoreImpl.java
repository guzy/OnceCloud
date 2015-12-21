package com.oncecloud.core.impl;

import java.util.List;

import javax.annotation.Resource;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.Container.Port;
import com.github.dockerjava.core.DockerClientBuilder;
import com.oncecloud.constants.LoggerConfig;
import com.oncecloud.core.RegistryCore;
import com.oncedocker.core.DockerConfig;
import com.oncedocker.core.DockerConstants;

/**
 * @author luogan 
 * 2015年7月15日 
 * 下午12:10:43
 */
@Component("RegistryCore")
public class RegistryCoreImpl implements RegistryCore {

	private static DockerClient client = null;
	@Resource
	private LoggerConfig logger;

	static {
		client = DockerClientBuilder.getInstance(
				DockerConfig.getValue(DockerConstants.ELEM_REPOURL)).build();
	}

	/**
	 * @param url
	 *            such as localhost:5000/centos
	 * @param name
	 *            , such as test, the name must be unique
	 * @param command
	 *            , default is /bin/bash
	 * 
	 *            all parameters cannot be null
	 * @return
	 */
	@Override
	public JSONObject createRegistry(String url, String name, String ip,
			String dockerParams) {
		// 创建docker容器
		CreateContainerResponse resp = client.createContainerCmd(url)
				.withName(name).exec();
		if (resp.getId() != null || !"".equals(resp.getId())) {
			client.startContainerCmd(resp.getId()).exec();
		}
		return containerInfo(resp.getId());
	}

	@Override
	public boolean deleteRegistry(String containerId) {
		boolean result = false;
		try {
			client.removeContainerCmd(containerId).exec();
			result = true;
		} catch (Exception e) {
			result = false;
		}
		return result;
	}

	@Override
	public List<com.github.dockerjava.api.model.Container> queryContainerList(
			int userId, int page, int limit, String groupUuid, String search) {
		// 查询docker的列表
		List<com.github.dockerjava.api.model.Container> containers = client
				.listContainersCmd().withShowAll(true).exec();
		return containers;
	}

	@Override
	public List<Container> listContainers() {
		return client.listContainersCmd().withShowAll(true).exec();

	}

	private JSONObject containerInfo(String containerId) {
		JSONObject jo = new JSONObject();
		if (!"".equals(containerId)) {
			boolean start = false;
			while (!start) {
				List<Container> containers = client.listContainersCmd()
						.withShowAll(true).exec();
				int flag = 0;
				for (Container container : containers) {
					if (container.getId().equals(
							containerId.substring(0, containerId.length() - 1))) {
						jo.put("containerid", container.getId());
						jo.put("containername", container.getNames());
						jo.put("containerstatus", container.getStatus());
						jo.put("containercommand", container.getCommand());
						jo.put("containerimage", container.getImage());
						Port[] ports = container.getPorts();
						StringBuffer portInfo = new StringBuffer();
						for (Port port : ports) {
							portInfo.append(port.getIp() + ":"
									+ port.getPublicPort() + "-->"
									+ port.getPrivatePort() + " ");
						}
						jo.put("containerport", portInfo.toString());
						start = true;
						break;
					}
					flag++;
				}
				if (flag >= containers.size()) {
					logger.logError("Class:Container---method:createContainer----failed");
					break;
				}
			}
		}
		return jo;
	}

}
