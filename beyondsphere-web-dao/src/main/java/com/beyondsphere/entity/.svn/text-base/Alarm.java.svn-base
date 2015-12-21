package com.beyondsphere.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "alarm")
public class Alarm{
	
	private String alarmUuid;
	private String alarmName;
	private String alarmDesc;
	private Date alarmTime;
	private Integer alarmUid;
	
	public Alarm(){}
	
	public Alarm(String alarmUuid,String alarmName,Date alarmTime,Integer alarmUid){
		this.alarmUuid = alarmUuid;
		this.alarmName = alarmName;
		this.alarmTime = alarmTime;
		this.alarmUid = alarmUid;
	}

	/**
	 * 获取告警策略Uuid
	 * @param
	 * */
	@Id
	@Column(name = "al_uuid")
	public String getAlarmUuid(){
		return alarmUuid;
	}

	/**
	 * 设置告警策略Uuid，由36位数字和字母组成
	 * @param alarmUuid
	 * */
	public void setAlarmUuid(String alarmUuid){
		this.alarmUuid = alarmUuid;
	}

	/**
	 * 获取策略名称
	 * @param 
	 * */
	@Column(name = "al_name")
	public String getAlarmName(){
		return alarmName;
	}

	/**
	 * 设置策略名称
	 * @param alarmName
	 * */
	public void setAlarmName(String alarmName){
		this.alarmName = alarmName;
	}
	
	/**
	 * 获取策略描述
	 * @param 
	 * */
	@Column(name = "al_desc")
	public String getAlarmDesc(){
		return alarmDesc;
	}

	/**
	 * 设置策略描述 
	 * @param alarmDesc
	 * */
	public void setAlarmDesc(String alarmDesc){
		this.alarmDesc = alarmDesc;
	}
	
	/**
	 * 获取策略创建时间
	 * @param 
	 * */
	@Column(name = "al_time")
	public Date getAlarmTime(){
		return alarmTime;
	}
	
	/**
	 * 设置策略创建时间
	 * @param alarmTime
	 * */
	public void setAlarmTime(Date alarmTime){
		this.alarmTime = alarmTime;
	}
	
	/**
	 * 获取创建该策略的用户id
	 * @param 
	 * */
	@Column(name = "al_uid")
	public Integer getAlarmUid(){
		return alarmUid;
	}

	/**
	 * 设置创建该策略的用户id
	 * @param alarmUid
	 * */
	public void setAlarmUid(Integer alarmUid){
		this.alarmUid = alarmUid;
	}
	
	@Override
	public String toString() {
		return "Alarm [alarmUuid=" + alarmUuid + " alarmName=" + alarmName
			    + " alarmTime=" + alarmTime+ " alarmUid=" 
				+ alarmUid+ "alarmDesc=" + alarmDesc + "]";
	}
}