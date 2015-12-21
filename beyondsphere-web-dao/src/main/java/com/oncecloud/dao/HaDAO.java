package com.oncecloud.dao;

import com.oncecloud.entity.OCHa;

public interface HaDAO {
	
	/**
	 * @author lining
	 * @param poolUuid String 资源池id
	 * @return OCHa对象
	 * 根据资源池的ID获取资源池高可用的数据
	 */
	public abstract OCHa getHa(String poolUuid);
	
	/**
	 * @author lining
	 * @param poolUuid String 资源池id
	 * @return int
	 * 根据资源池的ID查看该资源池是否已经启用高可用
	 */
	public abstract int countHaByPoolUuid(String poolUuid);
	
	
	/**
	 * @author lining
	 * @param  paramMap Map<String,Object> 创建资源池需要的数据
	 * @return boolean
	 * 根据参数创建资源池高可用的记录
	 */
	public abstract boolean insertHa(OCHa ha);
	
	/**
	 * @author lining
	 * @param  paramMap Map<String,Object> 更新资源池需要的数据
	 * @return boolean
	 * 根据参数更行资源池高可用的记录
	 */
	public abstract boolean updateHa(OCHa ha);
	
	/**
	 * @author lining
	 * @param poolUuid String 资源池id 
	 * @return boolean
	 * 根据资源池的id删除高可用
	 */
	public abstract boolean deleteHa(String poolUuid);
}
