package com.oncecloud.service;


import com.oncecloud.entity.OCHa;

public interface HaService {
	
	/**
	 * 保存高可用
	 * @author lining
	 * @param poolUuid, haPath
	 * @return
	 */
	public abstract boolean saveHa(String poolUuid, String haPath);
	
	/**
	 * 保存高可用
	 * @author lining
	 * @param poolUuid, haPath
	 * @return
	 */
	public abstract boolean updateHa(OCHa ha);
	
	/**
	 * 删除高可用
	 * @author lining
	 * @param poolUuid
	 * @return
	 */
	public abstract boolean cancelHa(String poolUuid);
	
	/**
	 * @author lining
	 * @param poolUuid
	 * 获取高可用策略的记录
	 */
	public abstract OCHa getRecordOfHaPolicy(String poolUuid) ;
	
}
