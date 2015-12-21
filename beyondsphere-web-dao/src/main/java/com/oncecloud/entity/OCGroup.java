package com.oncecloud.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "oc_group")
public class OCGroup {
	
	private String groupUuid;
	private Integer groupUid;
	private String  groupName;
	private String  groupColor;
	private String  groupLoc;
	private String  groupDes;
	private Date 	createTime;
	
	public OCGroup() {

	}
	
	public OCGroup(String groupUuid,Integer groupUid, String groupName, String groupColor,
			String groupLoc, String groupDes, Date createTime) {
		super();
		this.groupUuid = groupUuid;
		this.groupUid = groupUid;
		this.groupName = groupName;
		this.groupColor = groupColor;
		this.groupLoc = groupLoc;
		this.groupDes = groupDes;
		this.createTime = createTime;
	}
	
	@Id
	@Column(name = "group_uuid")
	public String getGroupUuid() {
		return groupUuid;
	}
	
	public void setGroupUuid(String groupUuid) {
		this.groupUuid = groupUuid;
	}
	
	@Column(name = "group_uid")
	public Integer getGroupUid(){
		return groupUid;
	}
	
	public void setGroupUid(Integer groupUid) {
		this.groupUid = groupUid;
	}
	
	@Column(name = "group_name")
	public String getGroupName() {
		return groupName;
	}
	
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
	@Column(name = "group_color")
	public String getGroupColor() {
		return groupColor;
	}
	
	public void setGroupColor(String groupColor) {
		this.groupColor = groupColor;
	}
	
	@Column(name = "group_loc")
	public String getGroupLoc() {
		return groupLoc;
	}
	
	public void setGroupLoc(String groupLoc) {
		this.groupLoc = groupLoc;
	}
	
	@Column(name = "group_des")
	public String getGroupDes() {
		return groupDes;
	}
	
	public void setGroupDes(String groupDes) {
		this.groupDes = groupDes;
	}
	
	@Column(name = "create_time")
	public Date getCreateTime() {
		return createTime;
	}
	
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "OCGroup [groupUuid=" + groupUuid + ", groupName=" + groupName
				+ ", groupColor=" + groupColor + ", groupLoc=" + groupLoc
				+ ", groupDes=" + groupDes + ", createTime=" + createTime + "]";
	}
	
}
