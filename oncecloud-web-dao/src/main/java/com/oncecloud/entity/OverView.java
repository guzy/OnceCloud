package com.oncecloud.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "over_view")
public class OverView {
	private Integer viewId;
	private Integer viewDc;
	private Integer viewRack;
	private Integer viewPool;
	private Integer viewServer;
	private Integer viewSr;
	private Integer viewVm;
	private Integer viewOutip;
	private Integer viewDhcp;
	private Integer viewFirewall;
	private Integer viewImage;

	public OverView() {
	}

	@Override
	public String toString() {
		return "OverView [viewId=" + viewId + ", viewDc=" + viewDc
				+ ", viewRack=" + viewRack + ", viewPool=" + viewPool
				+ ", viewServer=" + viewServer + ", viewSr=" + viewSr
				+ ", viewVm=" + viewVm + ", viewOutip=" + viewOutip
				+ ", viewDhcp=" + viewDhcp + ", viewFirewall=" + viewFirewall
				+ ", viewImage=" + viewImage + "]";
	}

	public OverView(Integer viewId, Integer viewDc, Integer viewRack,
			Integer viewPool, Integer viewServer, Integer viewSr,
			Integer viewVm, Integer viewOutip, Integer viewDhcp,
			Integer viewFirewall, Integer viewImage) {
		super();
		this.viewId = viewId;
		this.viewDc = viewDc;
		this.viewRack = viewRack;
		this.viewPool = viewPool;
		this.viewServer = viewServer;
		this.viewSr = viewSr;
		this.viewVm = viewVm;
		this.viewOutip = viewOutip;
		this.viewDhcp = viewDhcp;
		this.viewFirewall = viewFirewall;
		this.viewImage = viewImage;
	}

	@Id
	@Column(name = "view_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getViewId() {
		return viewId;
	}

	public void setViewId(Integer viewId) {
		this.viewId = viewId;
	}

	@Column(name = "view_dc")
	public Integer getViewDc() {
		return viewDc;
	}

	public void setViewDc(Integer viewDc) {
		this.viewDc = viewDc;
	}

	@Column(name = "view_rack")
	public Integer getViewRack() {
		return viewRack;
	}

	public void setViewRack(Integer viewRack) {
		this.viewRack = viewRack;
	}

	@Column(name = "view_pool")
	public Integer getViewPool() {
		return viewPool;
	}

	public void setViewPool(Integer viewPool) {
		this.viewPool = viewPool;
	}

	@Column(name = "view_server")
	public Integer getViewServer() {
		return viewServer;
	}

	public void setViewServer(Integer viewServer) {
		this.viewServer = viewServer;
	}

	@Column(name = "view_sr")
	public Integer getViewSr() {
		return viewSr;
	}

	public void setViewSr(Integer viewSr) {
		this.viewSr = viewSr;
	}

	@Column(name = "view_vm")
	public Integer getViewVm() {
		return viewVm;
	}

	public void setViewVm(Integer viewVm) {
		this.viewVm = viewVm;
	}

	@Column(name = "view_outip")
	public Integer getViewOutip() {
		return viewOutip;
	}

	public void setViewOutip(Integer viewOutip) {
		this.viewOutip = viewOutip;
	}

	@Column(name = "view_dhcp")
	public Integer getViewDhcp() {
		return viewDhcp;
	}

	public void setViewDhcp(Integer viewDhcp) {
		this.viewDhcp = viewDhcp;
	}

	@Column(name = "view_firewall")
	public Integer getViewFirewall() {
		return viewFirewall;
	}

	public void setViewFirewall(Integer viewFirewall) {
		this.viewFirewall = viewFirewall;
	}

	@Column(name = "view_image")
	public Integer getViewImage() {
		return viewImage;
	}

	public void setViewImage(Integer viewImage) {
		this.viewImage = viewImage;
	}

}
