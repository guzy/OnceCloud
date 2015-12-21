/**
 * 
 * @author zll
 * @time 下午14：45
 * @date 2015年5月27日
 */
package com.oncecloud.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "cost_month")
public class CostMonth {
	private int id;
	private double totalCost;	//每个月总投入
	private Date createTime;	//记录时间
	private double costShare;	//均摊的总的费用（每月总成本* (实际CPU使用率) * 1.5）
	private double costCpu; 	//每月分摊总cpu成本
	private double costMem;		//每月分摊总内存成本
	private double costVol;     //每月分摊总硬盘成本

	@Id
	@Column(name = "id")
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name = "total_cost")
	public double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}

	@Column(name = "create_time")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
	@Column(name = "cost_share")
	public double getCostShare() {
		return costShare;
	}

	public void setCostShare(double costShare) {
		this.costShare = costShare;
	}

	@Column(name = "cost_cpu")
	public double getCostCpu() {
		return costCpu;
	}

	public void setCostCpu(double costCpu) {
		this.costCpu = costCpu;
	}

	@Column(name = "cost_mem")
	public double getCostMem() {
		return costMem;
	}

	public void setCostMem(double costMem) {
		this.costMem = costMem;
	}

	@Column(name = "cost_vol")
	public double getCostVol() {
		return costVol;
	}

	public void setCostVol(double costVol) {
		this.costVol = costVol;
	}

	@Override
	public String toString() {
		return "CostMonth [id=" + id + ", totalCost=" + totalCost
				+ ", createTime=" + createTime + "]";
	}

}
