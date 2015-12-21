package com.oncecloud.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * @author 玉和
 * 2014-12-13
 */
@Entity
@Table(name = "oc_iso")
public class ISO {
   public String isoUuid;
   public String isoName;
   /// iso 的存储路径
   public String isoFileSrc;
   public Date createDate;
   // 谁上传的
   public Integer userId;
   // 备注
   public String isoRemarks;
   // 状态 0 正常 1 删除
   public Integer isoStatus;
   
	@Id
   @Column(name = "iso_uuid")
	public String getIsoUuid() {
		return isoUuid;
	}
	public void setIsoUuid(String isoUuid) {
		this.isoUuid = isoUuid;
	}
	
	@Column(name = "iso_name")
	public String getIsoName() {
		return isoName;
	}
	public void setIsoName(String isoName) {
		this.isoName = isoName;
	}
	
	@Column(name = "iso_filesrc")
	public String getIsoFileSrc() {
		return isoFileSrc;
	}
	public void setIsoFileSrc(String isoFileSrc) {
		this.isoFileSrc = isoFileSrc;
	}
	
	@Column(name = "createdate")
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	@Column(name = "userid")
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	@Column(name = "iso_remarks")
	public String getIsoRemarks() {
		return isoRemarks;
	}
	public void setIsoRemarks(String isoRemarks) {
		this.isoRemarks = isoRemarks;
	}
	
	@Column(name = "iso_status")
	public Integer getIsoStatus() {
		return isoStatus;
	}
	public void setIsoStatus(Integer isoStatus) {
		this.isoStatus = isoStatus;
	}
	
	public ISO(String isoUuid, String isoName, String isoFileSrc,
			Date createDate, Integer userId, String isoRemarks) {
		super();
		this.isoUuid = isoUuid;
		this.isoName = isoName;
		this.isoFileSrc = isoFileSrc;
		this.createDate = createDate;
		this.userId = userId;
		this.isoRemarks = isoRemarks;
		this.isoStatus =0;
	}
	public ISO() {
		super();
	}
	
	@Override
	public String toString() {
		return "ISO [isoUuid=" + isoUuid + ", isoName=" + isoName
				+ ", isoFileSrc=" + isoFileSrc + ", createDate=" + createDate
				+ ", userId=" + userId + ", isoRemarks=" + isoRemarks + "]";
	}
}
