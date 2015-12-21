package com.beyondsphere.dao;

import java.util.List;

import com.beyondsphere.entity.OCGroup;

public interface GroupDAO {
	
	/**
	 * 获取分组总数
	 * @author lining
	 * @return 
	 */
	public abstract int countGroupNum();
	
	/**
	 * 获取分组列表
	 * @author lining
	 * @param page, limit, search
	 * @return
	 */
	public abstract List<OCGroup> getGroupList(int userId, int page, int limit);
	
	/**
	 * 获取分组列表
	 * @author lining
	 * @param page, limit, search
	 * @return
	 */
	public abstract List<OCGroup> getGroupList(int userId);
	
	/**
	 * 获取所有的分组信息
	 * @author lining
	 * @return
	 */
	public abstract List<OCGroup> getAllGroups();
	
	/**
	 * 获取分组信息
	 * @author lining
	 * @param groupUuid
	 * @return
	 */
	public abstract OCGroup getGroupById(String groupUuid);
	
	/**
	 * 添加分组
	 * @author lining
	 * @param group
	 * @return
	 */
	public abstract boolean insertGroup(OCGroup group);
	
	/**
	 * 修改分组
	 * @author lining
	 * @param group
	 * @return
	 */
	public abstract boolean updateGroup(OCGroup group);
	
	/**
	 * 删除分组
	 * @author lining
	 * @param groupUuid
	 * @return
	 */
	public abstract boolean deleteGroup(String groupUuid);
	
}
