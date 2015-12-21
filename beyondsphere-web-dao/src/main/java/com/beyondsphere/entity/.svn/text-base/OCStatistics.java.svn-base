package com.beyondsphere.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import com.beyondsphere.model.StatisticsType;


/**
 * @author hty
 * 
 */
@Entity
@Table(name = "oc_statistics")
public class OCStatistics {

	private String uuid;
	private String name;
	private StatisticsType type;
	private Date createDate;
	private Date deleteDate;
	private double unitPrice;
	private int userId;

	/**
	 * 获取统计资源的uuid，即为资源的uuid
	 * @return
	 */
	@Id
	@Column(name = "st_uuid")
	public String getUuid() {
		return uuid;
	}

	/**
	 * 设置统计资源的uuid，即为资源的uuid
	 * @param uuid
	 */
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	/**
	 * 获取统计资源的名称，即为资源的名称
	 * @return
	 */
	@Column(name = "st_name")
	public String getName() {
		return name;
	}

	/**
	 * 设置统计资源的名称，即为资源的名称
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取统计资源的类型
	 * @return
	 */
	@Column(name = "st_type")
	@Enumerated(EnumType.ORDINAL)
	public StatisticsType getType() {
		return type;
	}

	/**
	 * 设置统计资源的类型
	 * @param type
	 */
	public void setType(StatisticsType type) {
		this.type = type;
	}

	/**
	 * 获取统计资源的创建时间
	 * @return
	 */
	@Column(name = "st_create")
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * 设置统计资源的创建时间
	 * @param create
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * 获取统计资源的删除时间
	 * @return
	 */
	@Column(name = "st_delete")
	public Date getDeleteDate() {
		return deleteDate;
	}

	/**
	 * 设置统计资源的删除时间
	 * @param delete
	 */
	public void setDeleteDate(Date deleteDate) {
		this.deleteDate = deleteDate;
	}
	
	@Column(name = "unit_price")
	public double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}

	/**
	 * 获取统计资源的用户id
	 * @return
	 */
	@Column(name = "st_userId")
	public int getUserId() {
		return userId;
	}

	/**
	 * 设置统计资源的用户id
	 * @param userId
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}

}
