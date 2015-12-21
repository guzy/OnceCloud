package com.oncecloud.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.oncecloud.model.AuthorityType;


/**
 * 角色，action的对应关系表  角色，跟 action 是多对多的关系
 * @author 玉和
 *
 * @Date 2014年12月4日
 */

@Entity
@Table(name = "role_action")
public class RoleAction {
    
	private Integer id;
	// 角色id
	private Integer roleId;
	
	// action的id
	private Integer actionId;
	
	// AuthorityType 枚举类型，权限的级别
	private AuthorityType authorityType;
	
	public RoleAction(){
		
	}
	
	public RoleAction(Integer roleId, Integer actionId,
			AuthorityType authorityType) {
		super();
		this.roleId = roleId;
		this.actionId = actionId;
		this.authorityType = authorityType;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "role_id")
	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	@Column(name = "action_id")
	public Integer getActionId() {
		return actionId;
	}

	public void setActionId(Integer actionId) {
		this.actionId = actionId;
	}
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "authority_type")
	public AuthorityType getAuthorityType() {
		return authorityType;
	}

	public void setAuthorityType(AuthorityType authorityType) {
		this.authorityType = authorityType;
	}

	@Override
	public String toString() {
		return "RoleAction [id=" + id + ", roleId=" + roleId + ", actionId="
				+ actionId + ", authorityType=" + authorityType + "]";
	}
    
    
}
