/**
 * @author zll
 * @time 上午11:43:48
 * @date 2015年5月11日
 */
package com.oncecloud.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "cost_type_detail")
public class CostTypeDetail {
	private String costTypeDetailId;
	private String costTypeId;
	private String costTypeDetailName;
	private String costTypeDetailDes;


	@Id
	@Column(name = "cost_type_detail_uuid")
	public String getCostTypeDetailId() {
		return costTypeDetailId;
	}


	public void setCostTypeDetailId(String costTypeDetailId) {
		this.costTypeDetailId = costTypeDetailId;
	}

	@Column(name = "cost_type_uuid")
	public String getCostTypeId() {
		return costTypeId;
	}


	public void setCostTypeId(String costTypeId) {
		this.costTypeId = costTypeId;
	}

	@Column(name = "cost_type_detail_name")
	public String getCostTypeDetailName() {
		return costTypeDetailName;
	}


	public void setCostTypeDetailName(String costTypeDetailName) {
		this.costTypeDetailName = costTypeDetailName;
	}

	@Column(name = "cost_type_detail_des")
	public String getCostTypeDetailDes() {
		return costTypeDetailDes;
	}


	public void setCostTypeDetailDes(String costTypeDetailDes) {
		this.costTypeDetailDes = costTypeDetailDes;
	}

	@Override
	public String toString() {
		return "costTypeDetail [costTypeDetailId=" + costTypeDetailId + ", costTypeId=" + costTypeId
				+ ", costTypeDetailName=" + costTypeDetailName +", costTypeDetailDes=" + costTypeDetailDes + "]";
	}
}
