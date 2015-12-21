/**
 * @author hty
 * @time 上午11:05:48
 * @date 2014年12月9日
 */
package com.beyondsphere.entity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "oc_host")
public class OCHost {
	private String hostUuid;
	private String hostPwd;
	private String hostName;
	private String hostType;
	private String hostDesc;
	private String hostIP;
	private Integer hostMem;
	private Integer hostCpu;
	private String kernelVersion;
	private String xenVersion;
	private Integer hostStatus;
	private String poolUuid;
	private Date createDate;

	public OCHost() {
	};

	public OCHost(String hostUuid, String hostPwd, String hostName, String hostType,
			String hostDesc, String hostIP, Integer hostMem, Integer hostCpu,
			String kernelVersion, String xenVersion, Integer hostStatus, Date createDate) {
		super();
		this.hostUuid = hostUuid;
		this.hostPwd = hostPwd;
		this.hostName = hostName;
		this.hostType = hostType;
		this.hostDesc = hostDesc;
		this.hostIP = hostIP;
		this.hostMem = hostMem;
		this.hostCpu = hostCpu;
		this.kernelVersion = kernelVersion;
		this.xenVersion = xenVersion;
		this.hostStatus = hostStatus;
		this.createDate = createDate;
	}

	@Id
	@Column(name = "host_uuid")
	public String getHostUuid() {
		return hostUuid;
	}

	public void setHostUuid(String hostUuid) {
		this.hostUuid = hostUuid;
	}

	@Column(name = "host_pwd")
	public String getHostPwd() {
		return hostPwd;
	}

	public void setHostPwd(String hostPwd) {
		this.hostPwd = hostPwd;
	}

	@Column(name = "host_name")
	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	
	@Column(name = "host_type")
	public String getHostType() {
		return hostType;
	}

	public void setHostType(String hostType) {
		this.hostType = hostType;
	}

	@Column(name = "host_desc")
	public String getHostDesc() {
		return hostDesc;
	}

	public void setHostDesc(String hostDesc) {
		this.hostDesc = hostDesc;
	}

	@Column(name = "host_ip")
	public String getHostIP() {
		return hostIP;
	}

	public void setHostIP(String hostIP) {
		this.hostIP = hostIP;
	}

	@Column(name = "host_mem")
	public Integer getHostMem() {
		return hostMem;
	}

	public void setHostMem(Integer hostMem) {
		this.hostMem = hostMem;
	}

	@Column(name = "host_cpu")
	public Integer getHostCpu() {
		return hostCpu;
	}

	public void setHostCpu(Integer hostCpu) {
		this.hostCpu = hostCpu;
	}

	@Column(name = "kernel_version")
	public String getKernelVersion() {
		return kernelVersion;
	}

	public void setKernelVersion(String kernelVersion) {
		this.kernelVersion = kernelVersion;
	}

	@Column(name = "xen_version")
	public String getXenVersion() {
		return xenVersion;
	}

	public void setXenVersion(String xenVersion) {
		this.xenVersion = xenVersion;
	}

	@Column(name = "host_status")
	public Integer getHostStatus() {
		return hostStatus;
	}

	public void setHostStatus(Integer hostStatus) {
		this.hostStatus = hostStatus;
	}

	@Column(name = "pool_uuid")
	public String getPoolUuid() {
		return poolUuid;
	}

	public void setPoolUuid(String poolUuid) {
		this.poolUuid = poolUuid;
	}

	@Column(name = "create_date")
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Override
	public String toString() {
		return "OCHost [hostUuid=" + hostUuid + ", hostPwd=" + hostPwd
				+ ", hostName=" + hostName + ", hostType=" + hostType + ", hostDesc=" + hostDesc
				+ ", hostIP=" + hostIP + ", hostMem=" + hostMem + ", hostCpu="
				+ hostCpu + ", kernelVersion=" + kernelVersion
				+ ", xenVersion=" + xenVersion + ", hostStatus=" + hostStatus
				+ ", poolUuid=" + poolUuid + ", createDate=" + createDate + "]";
	}

	public String toJsonString() {
		try {
			return "{'hostUuid':'" + hostUuid + "', 'hostPwd':'" + hostPwd
					+ "', 'hostName':'" + URLEncoder.encode(hostName, "utf-8")
					+ "', 'hostType':'" + URLEncoder.encode(hostType, "utf-8")
					+ "', 'hostDesc':'" + URLEncoder.encode(hostDesc, "utf-8")
					+ "', 'hostIP':'" + hostIP + "', 'hostMem':'" + hostMem
					+ "', 'hostCpu':'" + hostCpu + "', 'kernelVersion':'"
					+ kernelVersion + "', 'xenVersion':'" + xenVersion
					+ "', 'hostStatus':'" + hostStatus + "', 'poolUuid':'"
					+ poolUuid + "', 'createDate':'" + createDate + "'}";
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "";
		}
	}
}
