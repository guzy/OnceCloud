package com.beyondsphere.dao;

import java.util.Date;
import java.util.List;

import com.beyondsphere.entity.OCVM;

/**
 * @author luogan 
 * 2015年5月19日 
 * 下午2:13:17
 */
public interface ContainerDAO extends QueryListDAO<OCVM> {

	// 查询容器列表
	//public abstract boolean queryContainerList(int userId);
	// 保存创建容器
	public abstract boolean createContainer(String vmUuid, String tplUuid, int userId,
			int cpu, int memory, String pwd,String name, String desc, String statistics, String port, Date conCreateTime);
	// 停止容器
	public abstract boolean stopContainer(int userId, String containerId);
	// 删除容器
	public abstract boolean deleteContainer(int userId, String containerId);
	// 启动容器
	public abstract boolean startContainer(int userId, String containerId);
	//
	public abstract int countByUserId(int userId, String search);
	//
	public abstract List<OCVM> getOnePageByUserId(int userId, int page,
			int limit, String groupUuid, String search);
}
