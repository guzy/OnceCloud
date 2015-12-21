/**
 * @author zll
 * @time 下午14:53
 * @date 2015年5月27日
 */
package com.beyondsphere.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "cost_month_detail")
public class CostMonthDetail {
	private int id;
	private String costTypeId;
	private String costDetailId;
	private double detailCost;
	private Date createTime;

	@Id
	@Column(name = "id")
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "cost_type_uuid")
	public String getCostTypeId() {
		return costTypeId;
	}

	public void setCostTypeId(String costTypeId) {
		this.costTypeId = costTypeId;
	}

	@Column(name = "cost_detail_uuid")
	public String getCostDetailId() {
		return costDetailId;
	}

	public void setCostDetailId(String costDetailId) {
		this.costDetailId = costDetailId;
	}

	@Column(name = "detail_cost")
	public double getDetailCost() {
		return detailCost;
	}

	public void setDetailCost(double detailCost) {
		this.detailCost = detailCost;
	}

	@Column(name = "create_time")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "CostMonthDetail [id=" + id + ", costTypeId=" + costTypeId
				+ ", costDetailId=" + costDetailId + ", detailCost=" + detailCost 
				+ ", createTime=" + createTime + "]";
	}

}
