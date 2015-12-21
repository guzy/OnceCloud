/**
 * @author hty
 * @time 上午11:05:48
 * @date 2014年12月9日
 */
package com.oncecloud.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "oc_datacenter")
public class Datacenter {
	private String dcUuid;
	private String dcName;
	private String dcLocation;
	private String dcDesc;
	private Integer dcStatus;
	private Date createDate;

	@Id
	@Column(name = "dc_uuid")
	public String getDcUuid() {
		return dcUuid;
	}

	public void setDcUuid(String dcUuid) {
		this.dcUuid = dcUuid;
	}

	@Column(name = "dc_name")
	public String getDcName() {
		return dcName;
	}

	public void setDcName(String dcName) {
		this.dcName = dcName;
	}

	@Column(name = "dc_location")
	public String getDcLocation() {
		return dcLocation;
	}

	public void setDcLocation(String dcLocation) {
		this.dcLocation = dcLocation;
	}

	@Column(name = "dc_desc")
	public String getDcDesc() {
		return dcDesc;
	}

	public void setDcDesc(String dcDesc) {
		this.dcDesc = dcDesc;
	}

	@Column(name = "dc_status")
	public Integer getDcStatus() {
		return dcStatus;
	}

	public void setDcStatus(Integer dcStatus) {
		this.dcStatus = dcStatus;
	}

	@Column(name = "dc_createdate")
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Override
	public String toString() {
		return "Datacenter [dcUuid=" + dcUuid + ", dcName=" + dcName
				+ ", dcLocation=" + dcLocation + ", dcDesc=" + dcDesc
				+ ", dcStatus=" + dcStatus + ", createDate=" + createDate + "]";
	}
}
