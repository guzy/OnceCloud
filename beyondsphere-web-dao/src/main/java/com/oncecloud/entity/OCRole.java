package com.oncecloud.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 角色表
 * @author 玉和
 * 
 * @Date 2014年12月4日
 */

@Entity
@Table(name = "oc_role")
public class OCRole {
	
	// 主键,自增长
	private Integer roleId; 
	
	// 角色名称
	private String roleName;
	
	// 角色说明
	private String roleIntroduce;
	
	// 角色备注（预留属性）
	private String roleRemarks;
	
	public OCRole(){
	}
	
	public OCRole(String roleName, String roleIntroduce, String roleRemarks) {
		super();
		this.roleName = roleName;
		this.roleIntroduce = roleIntroduce;
		this.roleRemarks = roleRemarks;
	}


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "role_id")
	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	@Column(name = "role_name")
	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@Column(name = "role_introduce")
	public String getRoleIntroduce() {
		return roleIntroduce;
	}

	public void setRoleIntroduce(String roleIntroduce) {
		this.roleIntroduce = roleIntroduce;
	}
	
	@Column(name = "role_remarks")
	public String getRoleRemarks() {
		return roleRemarks;
	}

	public void setRoleRemarks(String roleRemarks) {
		this.roleRemarks = roleRemarks;
	}

	@Override
	public String toString() {
		return "OCRole [roleId=" + roleId + ", roleName=" + roleName
				+ ", roleIntroduce=" + roleIntroduce + ", roleRemarks="
				+ roleRemarks + "]";
	}
	
	

}
