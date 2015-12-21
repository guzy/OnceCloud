package com.oncecloud.dao;

import java.util.List;

import com.oncecloud.entity.HostInformation;

public interface HostInformationDAO {
	
	/**
	 * 分页获取主机列表
	 * @param page, limit, search
	 * @return list
	 */
	public abstract List<HostInformation> getOnePageList(int page, int limit);
	
	/**
	 * 查询总数
	 * @param search
	 * @return
	 */
	public abstract int countAllHostList();
	
	/**
	 * 保存录入信息
	 * @param info
	 * @return
	 */
	public abstract boolean saveInformation(HostInformation info);
	
	/**
	 * 删除记录
	 * @param infoid
	 * @return
	 */
	public abstract boolean doDeleteInfo(int infoid);
	
	/**
	 * 获取记录
	 * @param infoid
	 * @return
	 */
	public abstract HostInformation getInformation(int infoid);
	
	/**
	 * 获取记录
	 * @param infoid
	 * @return
	 */
	public abstract HostInformation getInformationByUserid(int userid);
	
	/**
	 * 获取所有记录
	 * @return
	 */
	public abstract List<HostInformation> getAllList();
	
	/**
	 * 获取所有记录
	 * @return
	 */
	public abstract List<HostInformation> getAllListbyNetid(int netid);
}
