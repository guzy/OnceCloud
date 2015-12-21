package com.oncecloud.dao;

import java.util.List;

import com.oncecloud.entity.RoleAction;

/**
 * 角色，Action关联表的 DAO
 * @author 玉和
 *
 * @Date 2014年12月4日
 */
public interface RoleActionDAO {

	/**
	 * 保存对象
	 * @param roleAction
	 * @return 
	 * @throws OnceDAOException
	 */
	public abstract boolean addRoleAction(RoleAction roleAction);
	    
	/**
	 * 修改对象
	 * @param roleAction
	 * @return 
	 * @throws OnceDAOException
	 */
    public abstract boolean editRoleAction(RoleAction roleAction);
    
    /**
	 * 移除对象
	 * @param roleAction
	 * @return 
	 * @throws OnceDAOException
	 */
    public abstract boolean removeRoleAction(RoleAction roleAction);
    
    /**
	 * 根据主键 获取对象
	 * @param id
	 * @return 
	 * @throws OnceDAOException
	 */
    public abstract RoleAction getRoleActionByID(int id);
    
    /**
   	 * 根据角色Id 获取分页列表
   	 * @param page
   	 * @param limit
   	 * @param roleId
   	 * @return List<RoleAction>
   	 * @throws OnceDAOException
   	 */
    public abstract List<RoleAction> getPagedRoleActionList(int page, int limit, int roleId);
    
    /**
	 * 根据角色id 获取列表的总记录数
	 * 
	 * @param roleId
	 * @return
	 */
	public int countAllRoleActionList(int roleId);

	 /**
	 * 根据action id 删除action记录
	 * 
	 * @param actionId
	 * @return
	 */
	public abstract boolean deleteByActionId(int actionId);

	 /**
		 * 根据role id 删除action记录
		 * 
		 * @param roleid
		 * @return
		 */
	public abstract boolean deleteByRoleId(int roleid);
}
