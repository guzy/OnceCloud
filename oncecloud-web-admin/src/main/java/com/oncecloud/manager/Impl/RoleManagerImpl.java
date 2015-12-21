/**
 * @author cyh
 * @time 上午11:05:48
 * @date 2014年12月9日
 */
package com.oncecloud.manager.Impl;

import java.util.List;

import javax.annotation.Resource;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.oncecloud.manager.RoleManager;
import com.oncecloud.entity.OCRole;
import com.oncecloud.model.AuthNode;
import com.oncecloud.model.PagedList;
import com.oncecloud.service.RoleService;

@Service("RoleManager")
public class RoleManagerImpl implements RoleManager{
	
	@Resource
	private RoleService roleService;

	public PagedList<OCRole> getRoleList(int page, int limit, String search) {
		return roleService.getPagedRoleList(page, limit, search);
	}

	public OCRole doCreateRole(int userId,String roleName, String roleIntroduce,
			String roleRemarks) {
		return roleService.addRole(userId, roleName, roleIntroduce, roleRemarks);
	}

	public OCRole doQueryRole(String roleName) {
		return roleService.getRole(roleName);
	}

	public JSONObject doDeleteRole(int userId, int roleid, String roleName) {
		return roleService.deleteRole(userId, roleid, roleName);
	}

	public List<AuthNode> getRoleData(int roleid) {
		return roleService.getRoleData(roleid);
	}

	public String setAuth(int userId,int roleid, String actionids) {
		return roleService.setAuth(userId, roleid, actionids);
	}

}
