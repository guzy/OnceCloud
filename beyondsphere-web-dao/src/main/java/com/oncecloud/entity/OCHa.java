package com.oncecloud.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author lining
 */

@Entity
@Table (name = "oc_ha")
public class OCHa {
	
	/*资源池的ID*/
	private String  poolUuid;
	/*高可用的路径*/
	private String  haPath;
	/*启用高可用的标志*/
	private Integer haStartFlag;
	/*启用主机监控的标志*/
	private Integer hostMonitor;
	/*启用接入控制策略的标志*/
	private Integer accessControlPolicy;
	/*故障主机切换容量*/
	private Integer leftHost;
	/*插槽策略*/
	private Integer slotPolicy;
	/*插槽cpu容量*/
	private Integer slotCpu;
	/*插槽内存容量*/
	private Integer slotMemory;
	/*预留cpu的百分比*/
	private Integer cpuPercent;
	/*预留memory的百分比*/
	private Integer memoryPercent;
	/*指定迁移主机的ID*/
	private String  migratoryHostUuid;
	/*启用高可用的时间*/
	private Date    startDate;
	
	@Id
	@Column (name = "pool_uuid")
	public String getPoolUuid() {
		return poolUuid;
	}



	public void setPoolUuid(String poolUuid) {
		this.poolUuid = poolUuid;
	}


	@Column (name = "ha_path")
	public String getHaPath() {
		return haPath;
	}



	public void setHaPath(String haPath) {
		this.haPath = haPath;
	}


	@Column (name = "ha_start_flag")
	public Integer getHaStartFlag() {
		return haStartFlag;
	}



	public void setHaStartFlag(Integer haStartFlag) {
		this.haStartFlag = haStartFlag;
	}


	@Column (name = "host_monitor")
	public Integer getHostMonitor() {
		return hostMonitor;
	}



	public void setHostMonitor(Integer hostMonitor) {
		this.hostMonitor = hostMonitor;
	}


	@Column (name = "access_control_policy")
	public Integer getAccessControlPolicy() {
		return accessControlPolicy;
	}



	public void setAccessControlPolicy(Integer accessControlPolicy) {
		this.accessControlPolicy = accessControlPolicy;
	}


	@Column (name = "left_host")
	public Integer getLeftHost() {
		return leftHost;
	}



	public void setLeftHost(Integer leftHost) {
		this.leftHost = leftHost;
	}
	
	
	@Column (name = "slot_policy")
	public Integer getSlotPolicy() {
		return slotPolicy;
	}



	public void setSlotPolicy(Integer slotPolicy) {
		this.slotPolicy = slotPolicy;
	}



	@Column (name = "slot_cpu")
	public Integer getSlotCpu() {
		return slotCpu;
	}



	public void setSlotCpu(Integer slotCpu) {
		this.slotCpu = slotCpu;
	}


	@Column (name = "slot_memory")
	public Integer getSlotMemory() {
		return slotMemory;
	}



	public void setSlotMemory(Integer slotMemory) {
		this.slotMemory = slotMemory;
	}


	@Column (name = "cpu_percent")
	public Integer getCpuPercent() {
		return cpuPercent;
	}



	public void setCpuPercent(Integer cpuPercent) {
		this.cpuPercent = cpuPercent;
	}


	@Column (name = "memory_percent")
	public Integer getMemoryPercent() {
		return memoryPercent;
	}



	public void setMemoryPercent(Integer memoryPercent) {
		this.memoryPercent = memoryPercent;
	}


	@Column (name = "migratory_host_uuid")
	public String getMigratoryHostUuid() {
		return migratoryHostUuid;
	}



	public void setMigratoryHostUuid(String migratoryHostUuid) {
		this.migratoryHostUuid = migratoryHostUuid;
	}


	@Column (name = "start_date")
	public Date getStartDate() {
		return startDate;
	}



	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}



	@Override
	public String toString() {
		return "OCHa [poolUuid=" + poolUuid + ", haPath=" + haPath
				+ ", haStartFlag=" + haStartFlag + ", hostMonitor="
				+ hostMonitor + ", accessControlPolicy=" + accessControlPolicy
				+ ", leftHost=" + leftHost + ", slotCpu=" + slotCpu
				+ ", slotMemory=" + slotMemory + ", cpuPercent=" + cpuPercent
				+ ", memoryPercent=" + memoryPercent + ", migratoryHostUuid="
				+ migratoryHostUuid + ", startDate=" + startDate + "]";
	}
	
	
}
