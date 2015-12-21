package com.oncecloud.service;

import java.util.List;

import org.json.JSONObject;

import com.oncecloud.entity.OCAction;
import com.oncecloud.entity.OCRole;
import com.oncecloud.entity.RoleAction;
import com.oncecloud.model.AuthNode;
import com.oncecloud.model.PagedList;
import com.oncecloud.vi.RoleActionView;

public interface RoleService {
	
	/**
	 * 分页获取用户角色列表
	 * @author lining
	 * @param page, limit, search
	 * @return
	 */
	public abstract PagedList<OCRole> getRoleList(int page, int limit, String search);

	/**
	 * 增加用户角色
	 * @author lining
	 * @param userId, roleName, roleIntroduce, roleRemarks
	 * @return
	 */
	public abstract OCRole addRole(int userId,String roleName,String roleIntroduce, String roleRemarks);

	/**
	 * 增加用户角色
	 * @author lining
	 * @param roleName
	 * @return
	 */
	public abstract OCRole getRole(String roleName);

	/**
	 * 删除用户角色
	 * @param userId
	 * @param roleid
	 * @param roleName
	 * @return
	 */
	public abstract JSONObject deleteRole(int userId, int roleid,
			String roleName);

	/**
	 * 组织权限树数据
	 * @param roleid
	 * @return
	 */
	public abstract List<AuthNode> getRoleData(int roleid);


	/**
	 * 配置新权限
	 * @param roleid
	 * @param actionids
	 * @return
	 */
	public abstract String setAuth(int userId,int roleid, String actionids);
	
	 /**
	  * 添加角色
	  * 
    * @param role
    * @return
    */
   public abstract int addRole(OCRole role);
   
   /**
    * 删除角色，删除角色之前，要先移除挂载在该角色下面的用户,一定要先验证
    * 
    * @param role
    * @return
    */
   public abstract int deleteRole(OCRole role);
   
   /**
    * 新增 OCAction
    * 
    * @param action
    * @return
    */
   public abstract int addAction(OCAction action);
   
   /**
    * 删除Actiion，删除Action的同时，要删除RoleAction表里面的关联记录。不验证，直接连带删除
    * 
    * @param actionId
    * @return
    */
   public abstract int deleteAction(int actionId);
   
   /**
    * 修改Action的基本信息
    * 
    * @param action
    * @return
    */
   public abstract int updateAction(OCAction action);
   
   /**
    * 编辑角色的权限，也就是维护RoleAction表记录，因为前台界面是勾选，为了方便维护，每次保存，都是把之前关于该角色的权限信心删除，然后重新插入新的
    * 
    * @param roleAction
    * @return
    */
   public abstract int saveAuthority(List<RoleAction> roleAction);
   
   /** 
    *  查询角色分页数据
    *  
    * @param page
    * @param limit
    * @param search
    * @return
    */
   public abstract PagedList<OCRole> getPagedRoleList(int page, int limit, String search);
   
   /**
    *  查询角色权限分页数据
    *  
    * @param page
    * @param limit
    * @param roleId
    * @return
    */
   public abstract PagedList<RoleActionView> getPagedRoleActionList(int page, int limit, int roleId);
}
