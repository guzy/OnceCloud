package com.beyondsphere.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author xpx
 * @class 私有网络
 * @version 2014-7-28
 */
@Entity
@Table(name = "oc_vnet")
public class Vnet {
	private String vnetUuid;
	private Integer vnetUID;
	private Integer vnetID;
	private String vnetName;
	private String vnetDesc;
	private String vifUuid;
	private String vifMac;
	/**
	 * 私有网络地址,掩码24
	 */
	private Integer vnetNet;
	/**
	 * 私有网络网关地址
	 */
	private Integer vnetGate;
	/**
	 * DHCP起始地址
	 */
	private Integer vnetStart;
	/**
	 * DHCP末地址
	 */
	private Integer vnetEnd;
	/**
	 * DHCP开关0/关 1/开
	 */
	private Integer dhcpStatus;
	/**
	 * 对应路由
	 */
	private String vnetRouter;
	private Date createDate;

	public Vnet() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Vnet(String vnetUuid, Integer vnetUID, String vnetName,
			String vnetDesc, Integer vnetNet, Integer vnetGate,
			Integer vnetStart, Integer vnetEnd, Integer dhcpStatus,
			String vnetRouter, Date createDate) {
		super();
		this.vnetUuid = vnetUuid;
		this.vnetUID = vnetUID;
		this.vnetName = vnetName;
		this.vnetDesc = vnetDesc;
		this.vnetNet = vnetNet;
		this.vnetGate = vnetGate;
		this.vnetStart = vnetStart;
		this.vnetEnd = vnetEnd;
		this.dhcpStatus = dhcpStatus;
		this.vnetRouter = vnetRouter;
		this.createDate = createDate;
	}

	@Id
	@Column(name = "vn_uuid")
	public String getVnetUuid() {
		return vnetUuid;
	}

	public void setVnetUuid(String vnetUuid) {
		this.vnetUuid = vnetUuid;
	}

	@Column(name = "vn_uid")
	public Integer getVnetUID() {
		return vnetUID;
	}

	public void setVnetUID(Integer vnetUID) {
		this.vnetUID = vnetUID;
	}

	@Column(name = "vn_name")
	public String getVnetName() {
		return vnetName;
	}

	public void setVnetName(String vnetName) {
		this.vnetName = vnetName;
	}

	@Column(name = "vn_desc")
	public String getVnetDesc() {
		return vnetDesc;
	}

	public void setVnetDesc(String vnetDesc) {
		this.vnetDesc = vnetDesc;
	}

	@Column(name = "vn_net")
	public Integer getVnetNet() {
		return vnetNet;
	}

	public void setVnetNet(Integer vnetNet) {
		this.vnetNet = vnetNet;
	}

	@Column(name = "vn_gate")
	public Integer getVnetGate() {
		return vnetGate;
	}

	public void setVnetGate(Integer vnetGate) {
		this.vnetGate = vnetGate;
	}

	@Column(name = "vn_dhcp_start")
	public Integer getVnetStart() {
		return vnetStart;
	}

	public void setVnetStart(Integer vnetStart) {
		this.vnetStart = vnetStart;
	}

	@Column(name = "vn_dhcp_end")
	public Integer getVnetEnd() {
		return vnetEnd;
	}

	public void setVnetEnd(Integer vnetEnd) {
		this.vnetEnd = vnetEnd;
	}

	@Column(name = "dhcp_status")
	public Integer getDhcpStatus() {
		return dhcpStatus;
	}

	public void setDhcpStatus(Integer dhcpStatus) {
		this.dhcpStatus = dhcpStatus;
	}

	@Column(name = "vn_router")
	public String getVnetRouter() {
		return vnetRouter;
	}

	public void setVnetRouter(String vnetRouter) {
		this.vnetRouter = vnetRouter;
	}

	@Column(name = "createdate")
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Column(name = "vn_id")
	public Integer getVnetID() {
		return vnetID;
	}

	public void setVnetID(Integer vnetID) {
		this.vnetID = vnetID;
	}

	@Column(name = "vif_uuid")
	public String getVifUuid() {
		return vifUuid;
	}

	public void setVifUuid(String vifUuid) {
		this.vifUuid = vifUuid;
	}
	
	@Column(name = "vif_mac")
	public String getVifMac() {
		return vifMac;
	}

	public void setVifMac(String vifMac) {
		this.vifMac = vifMac;
	}

	@Override
	public String toString() {
		return "Vnet [vnetUuid=" + vnetUuid + ", vnetUID=" + vnetUID
				+ ", vnetID=" + vnetID + ", vnetName=" + vnetName
				+ ", vnetDesc=" + vnetDesc + ", vifUuid=" + vifUuid
				+ ", vifMac=" + vifMac + ", vnetNet=" + vnetNet + ", vnetGate="
				+ vnetGate + ", vnetStart=" + vnetStart + ", vnetEnd="
				+ vnetEnd + ", dhcpStatus=" + dhcpStatus + ", vnetRouter="
				+ vnetRouter + ", createDate=" + createDate + "]";
	}
}
