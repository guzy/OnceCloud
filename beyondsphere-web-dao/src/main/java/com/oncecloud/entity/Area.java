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
@Table(name = "oc_area")
public class Area {
	private String areaId;
	private String areaName;
	private String areaDomain;
	private String areaDesc;
	private Date areaCreateTime;

	public Area() {
	}
	

	public Area(String areaId, String areaName, String areaDomain,
			String areaDesc, Date areaCreateTime) {
		super();
		this.areaId = areaId;
		this.areaName = areaName;
		this.areaDomain = areaDomain;
		this.areaDesc = areaDesc;
		this.areaCreateTime = areaCreateTime;
	}


	@Id
	@Column(name = "area_uuid")
	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	@Column(name = "area_name")
	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	@Column(name = "area_domain")
	public String getAreaDomain() {
		return areaDomain;
	}

	public void setAreaDomain(String areaDomain) {
		this.areaDomain = areaDomain;
	}

	@Column(name = "area_desc")
	public String getAreaDesc() {
		return areaDesc;
	}

	public void setAreaDesc(String areaDesc) {
		this.areaDesc = areaDesc;
	}

	@Column(name = "area_createtime")
	public Date getAreaCreateTime() {
		return areaCreateTime;
	}

	public void setAreaCreateTime(Date areaCreateTime) {
		this.areaCreateTime = areaCreateTime;
	}

	@Override
	public String toString() {
		return "Area [areaId=" + areaId + ", areaName=" + areaName
				+ ", areaDomain=" + areaDomain + ", areaDesc=" + areaDesc
				+ ", areaCreateTime=" + areaCreateTime + "]";
	}

}
