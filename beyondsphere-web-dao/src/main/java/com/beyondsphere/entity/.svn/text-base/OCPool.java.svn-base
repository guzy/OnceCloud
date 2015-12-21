/**
 * @author hty
 * @time 上午11:05:48
 * @date 2014年12月9日
 */
package com.beyondsphere.entity;

import java.net.URLEncoder;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "oc_pool")
public class OCPool {
	private String poolUuid;
	private String poolName;
	private String poolType;
	private String poolDesc;
	private String poolMaster;
	private String dcUuid;
	private Integer poolStatus;
	private Date createDate;
    private String haPath;
    
    
    @Column(name = "ha_path")
	public String getHaPath() {
		return haPath;
	}

	public void setHaPath(String haPath) {
		this.haPath = haPath;
	}

	public OCPool() {
	}

	@Column(name = "dc_uuid")
	public String getDcUuid() {
		return dcUuid;
	}

	public void setDcUuid(String dcUuid) {
		this.dcUuid = dcUuid;
	}

	@Id
	@Column(name = "pool_uuid")
	public String getPoolUuid() {
		return poolUuid;
	}

	public void setPoolUuid(String poolUuid) {
		this.poolUuid = poolUuid;
	}

	@Column(name = "pool_name")
	public String getPoolName() {
		return poolName;
	}

	public void setPoolName(String poolName) {
		this.poolName = poolName;
	}
	
	@Column(name = "pool_type")
	public String getPoolType() {
		return poolType;
	}

	public void setPoolType(String poolType) {
		this.poolType = poolType;
	}

	@Column(name = "pool_desc")
	public String getPoolDesc() {
		return poolDesc;
	}

	public void setPoolDesc(String poolDesc) {
		this.poolDesc = poolDesc;
	}

	@Column(name = "pool_master")
	public String getPoolMaster() {
		return poolMaster;
	}

	public void setPoolMaster(String poolMaster) {
		this.poolMaster = poolMaster;
	}

	@Column(name = "create_date")
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Column(name = "pool_status")
	public Integer getPoolStatus() {
		return poolStatus;
	}

	public void setPoolStatus(Integer poolStatus) {
		this.poolStatus = poolStatus;
	}

	@Override
	public String toString() {
		return "OCPool [poolUuid=" + poolUuid + ", poolName=" + poolName 
				+ ", poolType=" + poolType + ", poolDesc=" + poolDesc 
				+ ", poolMaster=" + poolMaster
				+ ", dcUuid=" + dcUuid + ", poolStatus=" + poolStatus
				+ ", createDate=" + createDate + "]";
	}

	public String toJsonString() {
		try {
			return "{'poolUuid':'" + poolUuid 
					+ "', 'poolName':'" + URLEncoder.encode(poolName, "utf-8") 
					+ "', 'poolType':'" + URLEncoder.encode(poolType, "utf-8") 
					+ "', 'poolDesc':'" + URLEncoder.encode(poolDesc, "utf-8")
					+ "', 'poolMaster':'" + poolMaster + "', 'poolStatus':'"
					+ poolStatus + "', 'createDate':'" + createDate + "'}";
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
}
