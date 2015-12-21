/**
 * @author hty
 * @time 上午11:05:48
 * @date 2014年12月9日
 */
package com.beyondsphere.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "host_sr")
public class HostSR {
	private Integer bindId;
	private String hostUuid;
	private String srUuid;

	@Id
	@Column(name = "bind_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getBindId() {
		return bindId;
	}

	public void setBindId(Integer bindId) {
		this.bindId = bindId;
	}

	@Column(name = "host_uuid")
	public String getHostUuid() {
		return hostUuid;
	}

	public void setHostUuid(String hostUuid) {
		this.hostUuid = hostUuid;
	}

	@Column(name = "sr_uuid")
	public String getSrUuid() {
		return srUuid;
	}

	public void setSrUuid(String srUuid) {
		this.srUuid = srUuid;
	}

	@Override
	public String toString() {
		return "HostSR [bindId=" + bindId + ", hostUuid=" + hostUuid
				+ ", srUuid=" + srUuid + "]";
	}
}
