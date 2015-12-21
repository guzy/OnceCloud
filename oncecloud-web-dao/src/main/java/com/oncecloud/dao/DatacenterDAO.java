/**
 * @author hty
 * @time 上午11:05:48
 * @date 2014年12月9日
 */
package com.oncecloud.dao;

import java.util.List;

import com.oncecloud.entity.Datacenter;

public interface DatacenterDAO {

	/**
	 * 根据datacenter名称模糊查询数据中心个数
	 * @param search
	 * @return int
	 */
	public abstract int countAllDatacenter(String search);
	
	/**
	 * 创建datacenter
	 * @param dcName, dcLocation, dcDesc
	 * @return Datacenter
	 */
	public abstract Datacenter createDatacenter(String dcName,
			String dcLocation, String dcDesc);

	/**
	 * 根据数据中心ID删除数据中心
	 * @param dcUuid
	 * @return boolean
	 */
	public abstract boolean deleteDatacenter(String dcUuid);

	/**
	 * 获取数据中心的列表
	 * @return List
	 */
	public abstract List<Datacenter> getAllPageDCList();
	
	/**
	 * 非本模块使用，获取dc信息
	 * @param dcUuid
	 * @return
	 */
	public abstract Datacenter getDatacenter(String dcUuid);

	/**
	 * 分页获取数据中心列表
	 * @param page limit search
	 * @return list
	 */
	public abstract List<Datacenter> getOnePageDCList(int page, int limit,
			String search);

	/**
	 * 更新数据中心的信息
	 * @param dcUuid, dcName, dcLocation, dcDesc
	 */
	public abstract boolean updateDatacenter(String dcUuid, String dcName,
			String dcLocation, String dcDesc);

}