/**
 * @author hty
 * @time 上午11:05:48
 * @date 2014年12月9日
 */
package com.beyondsphere.manager;


import java.util.List;

import org.json.JSONObject;

import com.beyondsphere.entity.OCRole;
import com.beyondsphere.model.AuthNode;
import com.beyondsphere.model.PagedList;

public interface RoleManager {

	/**
	 * 获取用户列表
	 * 
	 * @param page
	 * @param limit
	 * @param search
	 * @return
	 */
	public abstract PagedList<OCRole> getRoleList(int page, int limit, String search);

	
	public abstract OCRole doCreateRole(int userId,String roleName,String roleIntroduce, String roleRemarks);


	public abstract OCRole doQueryRole(String roleName);


	public abstract JSONObject doDeleteRole(int userId, int roleid,
			String roleName);


	/**
	 * @param roleid
	 * 组织权限树数据
	 */
	public abstract List<AuthNode> getRoleData(int roleid);


	/**
	 * @param roleid
	 * @param actionids
	 * 配置新权限
	 */
	public abstract String setAuth(int userId,int roleid, String actionids);

}