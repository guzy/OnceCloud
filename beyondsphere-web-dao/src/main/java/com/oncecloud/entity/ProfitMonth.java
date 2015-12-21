/**
 * @author zll
 * @time 下午14:53
 * @date 2015年5月27日
 */
package com.oncecloud.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "profit_month")
public class ProfitMonth {
	private int id;
	private int userId;
	private double profitPercent; //
	private double profitTotal;   //当月用户需要支付的费用
	private Date createTime;
	private double profitCpu;
	private double profitMem;
	private double profitVol;

	@Id
	@Column(name = "id")
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "user_id")
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	@Column(name = "profit_percent")
	public double getProfitPercent() {
		return profitPercent;
	}

	public void setProfitPercent(double profitPercent) {
		this.profitPercent = profitPercent;
	}

	@Column(name = "profit_total")
	public double getProfitTotal() {
		return profitTotal;
	}

	public void setProfitTotal(double profitTotal) {
		this.profitTotal = profitTotal;
	}

	@Column(name = "create_time")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "profit_cpu")
	public double getProfitCpu() {
		return profitCpu;
	}

	public void setProfitCpu(double profitCpu) {
		this.profitCpu = profitCpu;
	}
	
	@Column(name = "profit_mem")
	public double getProfitMem() {
		return profitMem;
	}

	public void setProfitMem(double profitMem) {
		this.profitMem = profitMem;
	}

	@Column(name = "profit_vol")
	public double getProfitVol() {
		return profitVol;
	}

	public void setProfitVol(double profitVol) {
		this.profitVol = profitVol;
	}

	@Override
	public String toString() {
		return "ProfitMonth [id=" + id + ", userId=" + userId
				+ ", profitPercent=" + profitPercent + ", profitTotal=" + profitTotal 
				+ ", createTime=" + createTime + "]";
	}

}
