package com.beyondsphere.dao;

import java.util.Date;
import java.util.List;

import com.beyondsphere.entity.Registry;

/**
 * @author luogan
 * 2015年7月15日
 * 下午12:11:28
 */
public interface RegistryDAO extends QueryListDAO<Registry> {

	// 
	public abstract boolean createRegistry(String regUuid, String name,String ip,String port, String status,Date createTime);
	// 
	public abstract boolean deleteRegistry(int userId, String containerId);
	//
	public abstract int countByUserId(int userId, String search);
	//
	public abstract List<Registry> getOnePageByUserId(int userId, int page,int limit, String groupUuid, String search);
}
