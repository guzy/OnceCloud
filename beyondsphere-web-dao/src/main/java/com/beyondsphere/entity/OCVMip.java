package com.beyondsphere.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "oc_vm_ip")
public class OCVMip {
	
	private Integer ipId;
	private String vmMac;
	private String vmIp;
	private Integer vmVlan;
	private String vmNetmask;
	private String vmGateway;
	private String vmDns;

	
	@Id
	@Column(name = "ip_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getIpId() {
		return ipId;
	}
	
	public void setIpId(Integer ipId) {
		this.ipId = ipId;
	}
	
	@Column(name = "vm_mac")
	public String getVmMac() {
		return vmMac;
	}
	
	public void setVmMac(String vmMac) {
		this.vmMac = vmMac;
	}
	
	@Column(name = "vm_ip")
	public String getVmIp() {
		return vmIp;
	}
	
	public void setVmIp(String vmIp) {
		this.vmIp = vmIp;
	}
	
	@Column(name = "vm_vlan")
	public Integer getVmVlan() {
		return vmVlan;
	}
	
	public void setVmVlan(Integer vmVlan) {
		this.vmVlan = vmVlan;
	}
	
	
	@Column(name = "vm_netmask")
	public String getVmNetmask() {
		return vmNetmask;
	}

	public void setVmNetmask(String vmNetmask) {
		this.vmNetmask = vmNetmask;
	}
	
	@Column(name = "vm_gateway")
	public String getVmGateway() {
		return vmGateway;
	}

	public void setVmGateway(String vmGateway) {
		this.vmGateway = vmGateway;
	}
	
	@Column(name = "vm_dns")
	public String getVmDns() {
		return vmDns;
	}

	public void setVmDns(String vmDns) {
		this.vmDns = vmDns;
	}

	@Override
	public String toString() {
		return "OCVMip [ipId=" + ipId + ", vmMac=" + vmMac + ", vmIp=" + vmIp
				+ ", vmVlan=" + vmVlan +", vmNetmask=" + vmNetmask
				+ ", vmGateway=" + vmGateway +", vmDns=" + vmDns + "]";
	}
	
	
	
}
