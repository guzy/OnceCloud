package com.oncecloud.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author lining
 * 
 */

@Entity
@Table(name="oc_net")
public class OCNetwork {
	
	private Integer netId;
	private Integer vlanType;
	private Integer vlanUid;
	private Integer vlanId;
	private String vlanNet;			//私有网络网段
	private String vlanGate;		//私有网络网关
	private String vlanStart;		//dhcp起始地址
	private String vlanEnd;			//dhcp结束地址
	private Integer dhcpStatus;		//dhcp是否开启
	private String vlanRouter;		//路由器uuid
	private String vifUuid;			//网卡uuid
	private String vifMac;			//网卡的mac地址
	
	private Integer vlanStatus;
	private Date createTime;
	
	@Column(name="vlan_net")
	public String getVlanNet() {
		return vlanNet;
	}
	public void setVlanNet(String vlanNet) {
		this.vlanNet = vlanNet;
	}
	
	@Column(name="vlan_gate")
	public String getVlanGate() {
		return vlanGate;
	}
	public void setVlanGate(String vlanGate) {
		this.vlanGate = vlanGate;
	}
	
	@Column(name="vlan_start")
	public String getVlanStart() {
		return vlanStart;
	}
	public void setVlanStart(String vlanStart) {
		this.vlanStart = vlanStart;
	}
	
	@Column(name="vlan_end")
	public String getVlanEnd() {
		return vlanEnd;
	}
	
	@Column(name="net_id")
	public void setVlanEnd(String vlanEnd) {
		this.vlanEnd = vlanEnd;
	}
	
	@Column(name="dhcp_status")
	public Integer getDhcpStatus() {
		return dhcpStatus;
	}
	public void setDhcpStatus(Integer dhcpStatus) {
		this.dhcpStatus = dhcpStatus;
	}
	
	@Column(name="vlan_router")
	public String getVlanRouter() {
		return vlanRouter;
	}
	public void setVlanRouter(String vlanRouter) {
		this.vlanRouter = vlanRouter;
	}
	@Id
	@Column(name="net_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getNetId() {
		return netId;
	}
	public void setNetId(Integer netId) {
		this.netId = netId;
	}
	
	@Column(name="vlan_type")
	public Integer getvlanType() {
		return vlanType;
	}
	public void setvlanType(Integer vlanType) {
		this.vlanType = vlanType;
	}
	
	@Column(name="vlan_id")
	public Integer getvlanId() {
		return vlanId;
	}
	public void setvlanId(Integer vlanId) {
		this.vlanId = vlanId;
	}
	
	@Column(name="vlan_status")
	public Integer getvlanStatus() {
		return vlanStatus;
	}
	public void setvlanStatus(Integer vlanStatus) {
		this.vlanStatus = vlanStatus;
	}
	
	@Column(name="vif_uuid")
	public String getVifUuid() {
		return vifUuid;
	}
	public void setVifUuid(String vifUuid) {
		this.vifUuid = vifUuid;
	}
	
	@Column(name="vif_mac")
	public String getVifMac() {
		return vifMac;
	}
	public void setVifMac(String vifMac) {
		this.vifMac = vifMac;
	}
	@Column(name="create_time")
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@Column(name="vlan_uid")
	public Integer getVlanUid() {
		return vlanUid;
	}
	public void setVlanUid(Integer vlanUid) {
		this.vlanUid = vlanUid;
	}
	@Override
	public String toString() {
		return "OCNetwork [netId=" + netId + ", vlanType=" + vlanType
				+ ", vlanId=" + vlanId + ", vlanStatus=" + vlanStatus + ",createTime=" + createTime + "]";
	}
	
}
