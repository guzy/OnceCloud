/**
 * @author zll
 * @time 上午11:43:48
 * @date 2015年5月11日
 */
package com.beyondsphere.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.beyondsphere.model.CostPriceType;
import com.beyondsphere.model.CostState;

@Entity
@Table(name = "cost_detail")
public class CostDetail {
	private String costDetailId;
	private String costTypeId;
	private String costTypeDetailId;
	private String costDetailName;
	private String costDetailType;
	private double costUnitPrice;
	private int    costNumber;
	private CostPriceType	costPriceType;
	private CostState	costState;
	private Date costCreatetime;
	private Date costDeletetime;


	@Id
	@Column(name = "cost_detail_uuid")
	public String getCostDetailId() {
		return costDetailId;
	}


	public void setCostDetailId(String costDetailId) {
		this.costDetailId = costDetailId;
	}

	@Column(name = "cost_type_uuid")
	public String getCostTypeId() {
		return costTypeId;
	}


	public void setCostTypeId(String costTypeId) {
		this.costTypeId = costTypeId;
	}

	@Column(name = "cost_type_detail_uuid")
	public String getCostTypeDetailId() {
		return costTypeDetailId;
	}


	public void setCostTypeDetailId(String costTypeDetailId) {
		this.costTypeDetailId = costTypeDetailId;
	}

	@Column(name = "cost_detail_name")
	public String getCostDetailName() {
		return costDetailName;
	}


	public void setCostDetailName(String costDetailName) {
		this.costDetailName = costDetailName;
	}

	@Column(name = "cost_detail_type")
	public String getCostDetailType() {
		return costDetailType;
	}


	public void setCostDetailType(String costDetailType) {
		this.costDetailType = costDetailType;
	}

	@Column(name = "cost_unit_price")	
	public double getCostUnitPrice() {
		return costUnitPrice;
	}


	public void setCostUnitPrice(double costUnitPrice) {
		this.costUnitPrice = costUnitPrice;
	}

	@Column(name = "cost_number")
	public int getCostNumber() {
		return costNumber;
	}


	public void setCostNumber(int costNumber) {
		this.costNumber = costNumber;
	}

	@Column(name = "cost_price_type")
	public CostPriceType getCostPriceType() {
		return costPriceType;
	}


	public void setCostPriceType(CostPriceType costPriceType) {
		this.costPriceType = costPriceType;
	}

	@Column(name = "cost_state")
	public CostState getCostState() {
		return costState;
	}


	public void setCostState(CostState costState) {
		this.costState = costState;
	}

	@Column(name = "cost_createtime")
	public Date getCostCreatetime() {
		return costCreatetime;
	}


	public void setCostCreatetime(Date costCreatetime) {
		this.costCreatetime = costCreatetime;
	}

	@Column(name = "cost_deletetime")
	public Date getCostDeletetime() {
		return costDeletetime;
	}


	public void setCostDeletetime(Date costDeletetime) {
		this.costDeletetime = costDeletetime;
	}

	@Override
	public String toString() {
		return "costDetail [costDetailId=" + costDetailId + ", costTypeId=" + costTypeId
				+ ", costTypeDetailId=" + costTypeDetailId + ", costDetailName=" + costDetailName 
				+ ", costDetailType=" + costDetailType + ", costUnitPrice=" + costUnitPrice 
				+ ", costNumber=" + costNumber + ", costPriceType=" + costPriceType.ordinal()
				+ ", costState=" + costState.ordinal() + ", costCreatetime=" + costCreatetime 
				+ ", costDeletetime=" + costDeletetime + "]";
	}

}
