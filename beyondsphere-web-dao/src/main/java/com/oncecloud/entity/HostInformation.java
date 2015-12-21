package com.oncecloud.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "host_information")
public class HostInformation{
	
	private Integer id;
	private Integer userId;
	private Integer netId;
	private String ipSegment;
	private String routerIp;
	private String routerNetmask;
	private Date createDate;
	
	public HostInformation(){}
	
	public HostInformation(Integer id, Integer userId, Integer netId,
			String ipSegment, String routerIp, String routerNetmask, Date createDate) {
		super();
		this.id = id;
		this.userId = userId;
		this.netId = netId;
		this.ipSegment = ipSegment;
		this.routerIp = routerIp;
		this.routerNetmask = routerNetmask;
		this.createDate = createDate;
	}

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "user_id")
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Column(name = "net_id")
	public Integer getNetId() {
		return netId;
	}

	public void setNetId(Integer netId) {
		this.netId = netId;
	}

	@Column(name = "ip_segment")
	public String getIpSegment() {
		return ipSegment;
	}

	public void setIpSegment(String ipSegment) {
		this.ipSegment = ipSegment;
	}

	@Column(name = "router_ip")
	public String getRouterIp() {
		return routerIp;
	}

	public void setRouterIp(String routerIp) {
		this.routerIp = routerIp;
	}

	@Column(name = "router_netmask")
	public String getRouterNetmask() {
		return routerNetmask;
	}

	public void setRouterNetmask(String routerNetmask) {
		this.routerNetmask = routerNetmask;
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
		return "HostInformation [id=" + id + " userId=" + userId
			    + " netId=" + netId+ " ipSegment=" 
				+ ipSegment+ "routerIp=" + routerIp 
				+ "routerNetmask=" + routerNetmask + "]";
	}
}