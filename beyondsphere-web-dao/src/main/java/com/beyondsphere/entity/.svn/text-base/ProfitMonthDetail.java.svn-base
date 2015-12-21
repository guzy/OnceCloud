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
@Table(name = "profit_month_detail")
public class ProfitMonthDetail {
	private int id;
	private int userId;
	private String costDetailId;
	private double detailProfit;   //用户需要支付的费用
	private Date createTime;

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

	@Column(name = "cost_detail_uuid")
	public String getCostDetailId() {
		return costDetailId;
	}

	public void setCostDetailId(String costDetailId) {
		this.costDetailId = costDetailId;
	}

	@Column(name = "detail_profit")
	public double getDetailProfit() {
		return detailProfit;
	}

	public void setDetailProfit(double detailProfit) {
		this.detailProfit = detailProfit;
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
		return "ProfitMonthDetail [id=" + id + ", userId=" + userId
				+ ", costDetailId=" + costDetailId + ", detailProfit=" + detailProfit 
				+ ", createTime=" + createTime + "]";
	}

}
