/**
 * @author zll
 * @time 上午11:43:48
 * @date 2015年5月11日
 */
package com.beyondsphere.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "cost_type")
public class CostType {
	private String costTypeId;
	private String costTypeName;
	private String costTypeDes;


	@Id
	@Column(name = "cost_type_uuid")
	public String getCostTypeId() {
		return costTypeId;
	}


	public void setCostTypeId(String costTypeId) {
		this.costTypeId = costTypeId;
	}

	@Column(name = "cost_type_name")
	public String getCostTypeName() {
		return costTypeName;
	}


	public void setCostTypeName(String costTypeName) {
		this.costTypeName = costTypeName;
	}

	@Column(name = "cost_type_des")
	public String getCostTypeDes() {
		return costTypeDes;
	}


	public void setCostTypeDes(String costTypeDes) {
		this.costTypeDes = costTypeDes;
	}

	@Override
	public String toString() {
		return "CostType [costTypeId=" + costTypeId + ", costTypeName=" + costTypeName
				+ ", costTypeDes=" + costTypeDes + "]";
	}

}
