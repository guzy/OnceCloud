package com.oncecloud.service;

import org.json.JSONArray;

/**
 * @author luogan
 * 2015年5月19日
 * 下午2:05:59
 */
public interface RepositoryService {

	public abstract JSONArray ContainerRepoList(int userId, int page,
			int limit, String search, String type);

}
