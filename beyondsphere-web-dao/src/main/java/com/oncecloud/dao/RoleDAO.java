package com.oncecloud.dao;

import java.util.List;

import com.oncecloud.entity.OCRole;


/**
 * 角色的 DAO
 * 
 * @author 玉和
 * 
 * @Date 2014年12月4日
 */
public interface RoleDAO {

	/**
	 * 保存OCRole对象
	 * 
	 * @param role
	 * @return
	 * @throws OnceDAOException
	 */
	public abstract boolean addOCRole(OCRole role);

	/**
	 * 修改对象
	 * 
	 * @param role
	 * @return
	 * @throws OnceDAOException
	 */
	public abstract boolean editOCRole(OCRole role);

	/**
	 * 移除对象
	 * 
	 * @param role
	 * @return
	 * @throws OnceDAOException
	 */
	public abstract boolean removeOCRole(OCRole role);

	/**
	 * 根据主键 获取 Role对象
	 * 
	 * @param id
	 * @return
	 * @throws OnceDAOException
	 */
	public abstract OCRole getRoleByID(int id);

	/**
	 * 分页获取角色列表
	 * 
	 * @param page
	 * @param limit
	 * @param search
	 * @return List<OCRole>
	 * @throws OnceDAOException
	 */
	public abstract List<OCRole> getPagedRoleList(int page, int limit,
			String search);

	/**
	 * 获取列表的总记录数
	 * 
	 * @param search
	 * @return
	 */
	public int countAllRoleList(String search);

	
	public abstract OCRole getRole(String roleName);
}
