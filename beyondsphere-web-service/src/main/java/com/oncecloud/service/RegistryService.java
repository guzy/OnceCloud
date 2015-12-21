package com.oncecloud.service;

import java.util.Date;

import org.json.JSONArray;

/**
 * @author luogan
 * 2015年7月15日
 * 下午12:09:40
 */
public interface RegistryService {
	
	public abstract JSONArray regRepoList(int userId, int page,int limit, String search, String type);
	
	public abstract boolean createRegistry(String regUuid, String name,String ip, String port,String status,Date createTime);

	public abstract JSONArray registryList(int userId, int page,int limit, String search, String type);

	public abstract boolean deleteRegistry(int userId, String containerId);

}
