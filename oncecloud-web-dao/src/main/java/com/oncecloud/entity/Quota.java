package com.oncecloud.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "oc_quota")
public class Quota {
	private Integer quotaID;
	private Integer quotaUID;
	private Integer quotaIP;
	private Integer quotaVM;
	private Integer quotaSnapshot;
	private Integer quotaImage;
	private Integer quotaDiskN;
	private Integer quotaDiskS;
	private Integer quotaSsh;
	private Integer quotaFirewall;
	private Integer quotaRoute;
	private Integer quotaVlan;
	private Integer quotaLoadBalance;
	private Integer quotaBandwidth;
	private Integer quotaMemory;
	private Integer quotaCpu;
	private Integer quotaType;

	public Quota() {
	}

	public Quota(Integer quotaUID, Integer quotaIP, Integer quotaVM,
			Integer quotaSnapshot, Integer quotaImage, Integer quotaDiskN,
			Integer quotaDiskS, Integer quotaSsh, Integer quotaFirewall,
			Integer quotaRoute, Integer quotaVlan, Integer quotaLoadBalance,
			Integer quotaBandwidth, Integer quotaMemory, Integer quotaCpu,
			Integer quotaType) {
		this.quotaUID = quotaUID;
		this.quotaIP = quotaIP;
		this.quotaVM = quotaVM;
		this.quotaSnapshot = quotaSnapshot;
		this.quotaImage = quotaImage;
		this.quotaDiskN = quotaDiskN;
		this.quotaDiskS = quotaDiskS;
		this.quotaSsh = quotaSsh;
		this.quotaFirewall = quotaFirewall;
		this.quotaRoute = quotaRoute;
		this.quotaVlan = quotaVlan;
		this.quotaLoadBalance = quotaLoadBalance;
		this.quotaBandwidth = quotaBandwidth;
		this.quotaMemory = quotaMemory;
		this.quotaCpu = quotaCpu;
		this.quotaType = quotaType;
	}

	@Id
	@Column(name = "quota_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getQuotaID() {
		return quotaID;
	}

	public void setQuotaID(Integer quotaID) {
		this.quotaID = quotaID;
	}

	@Column(name = "quota_uid")
	public Integer getQuotaUID() {
		return quotaUID;
	}

	public void setQuotaUID(Integer quotaUID) {
		this.quotaUID = quotaUID;
	}

	@Column(name = "quota_ip")
	public Integer getQuotaIP() {
		return quotaIP;
	}

	public void setQuotaIP(Integer quotaIP) {
		this.quotaIP = quotaIP;
	}

	@Column(name = "quota_vm")
	public Integer getQuotaVM() {
		return quotaVM;
	}

	public void setQuotaVM(Integer quotaVM) {
		this.quotaVM = quotaVM;
	}

	@Column(name = "quota_snapshot")
	public Integer getQuotaSnapshot() {
		return quotaSnapshot;
	}

	public void setQuotaSnapshot(Integer quotaSnapshot) {
		this.quotaSnapshot = quotaSnapshot;
	}

	@Column(name = "quota_image")
	public Integer getQuotaImage() {
		return quotaImage;
	}

	public void setQuotaImage(Integer quotaImage) {
		this.quotaImage = quotaImage;
	}

	@Column(name = "quota_disk_n")
	public Integer getQuotaDiskN() {
		return quotaDiskN;
	}

	public void setQuotaDiskN(Integer quotaDiskN) {
		this.quotaDiskN = quotaDiskN;
	}

	@Column(name = "quota_disk_s")
	public Integer getQuotaDiskS() {
		return quotaDiskS;
	}

	public void setQuotaDiskS(Integer quotaDiskS) {
		this.quotaDiskS = quotaDiskS;
	}

	@Column(name = "quota_ssh")
	public Integer getQuotaSsh() {
		return quotaSsh;
	}

	public void setQuotaSsh(Integer quotaSsh) {
		this.quotaSsh = quotaSsh;
	}

	@Column(name = "quota_firewall")
	public Integer getQuotaFirewall() {
		return quotaFirewall;
	}

	public void setQuotaFirewall(Integer quotaFirewall) {
		this.quotaFirewall = quotaFirewall;
	}

	@Column(name = "quota_route")
	public Integer getQuotaRoute() {
		return quotaRoute;
	}

	public void setQuotaRoute(Integer quotaRoute) {
		this.quotaRoute = quotaRoute;
	}

	@Column(name = "quota_vlan")
	public Integer getQuotaVlan() {
		return quotaVlan;
	}

	public void setQuotaVlan(Integer quotaVlan) {
		this.quotaVlan = quotaVlan;
	}

	@Column(name = "quota_loadbalance")
	public Integer getQuotaLoadBalance() {
		return quotaLoadBalance;
	}

	public void setQuotaLoadBalance(Integer quotaLoadBalance) {
		this.quotaLoadBalance = quotaLoadBalance;
	}

	@Column(name = "quota_bandwidth")
	public Integer getQuotaBandwidth() {
		return quotaBandwidth;
	}

	public void setQuotaBandwidth(Integer quotaBandwidth) {
		this.quotaBandwidth = quotaBandwidth;
	}

	@Column(name = "quota_memory")
	public Integer getQuotaMemory() {
		return quotaMemory;
	}

	public void setQuotaMemory(Integer quotaMemory) {
		this.quotaMemory = quotaMemory;
	}

	@Column(name = "quota_cpu")
	public Integer getQuotaCpu() {
		return quotaCpu;
	}

	public void setQuotaCpu(Integer quotaCpu) {
		this.quotaCpu = quotaCpu;
	}

	@Column(name = "quota_type")
	public Integer getQuotaType() {
		return quotaType;
	}

	public void setQuotaType(Integer quotaType) {
		this.quotaType = quotaType;
	}

	@Override
	public String toString() {
		return "Quota [quotaID=" + quotaID + ", quotaUID=" + quotaUID
				+ ", quotaIP=" + quotaIP + ", quotaVM=" + quotaVM
				+ ", quotaSnapshot=" + quotaSnapshot + ", quotaImage="
				+ quotaImage + ", quotaDiskN=" + quotaDiskN + ", quotaDiskS="
				+ quotaDiskS + ", quotaSsh=" + quotaSsh + ", quotaFirewall="
				+ quotaFirewall + ", quotaRoute=" + quotaRoute + ", quotaVlan="
				+ quotaVlan + ", quotaLoadBalance=" + quotaLoadBalance
				+ ", quotaBandwidth=" + quotaBandwidth + ", quotaMemory="
				+ quotaMemory + ", quotaCpu=" + quotaCpu + ", quotaType="
				+ quotaType + "]";
	}
}
