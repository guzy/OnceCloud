package com.oncecloud.vi;

import com.oncecloud.entity.OCAction;
import com.oncecloud.entity.RoleAction;


/**
 * 权限 viewModel
 * @author 玉和
 *
 * @Date 2014年12月9日
 */

public class RoleActionView {
    
	private RoleAction roleAction;
	
	private OCAction ocaction;

	public RoleAction getRoleAction() {
		return roleAction;
	}

	public void setRoleAction(RoleAction roleAction) {
		this.roleAction = roleAction;
	}

	public OCAction getOcaction() {
		return ocaction;
	}

	public void setOcaction(OCAction ocaction) {
		this.ocaction = ocaction;
	}
	
}
