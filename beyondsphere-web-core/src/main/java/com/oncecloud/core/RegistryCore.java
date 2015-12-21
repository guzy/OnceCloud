package com.oncecloud.core;

import java.util.List;

import org.json.JSONObject;

import com.github.dockerjava.api.model.Container;

/**
 * @author luogan
 * 2015年7月15日
 * 下午12:10:34
 */
public interface RegistryCore {

	public abstract JSONObject createRegistry(String url,String name,String ip,String port);

	public abstract boolean deleteRegistry(String containerId);
	//
	public abstract List<Container> queryContainerList(int userId, int page,
			int limit, String groupUuid, String search);

	public abstract List<Container> listContainers();
}
