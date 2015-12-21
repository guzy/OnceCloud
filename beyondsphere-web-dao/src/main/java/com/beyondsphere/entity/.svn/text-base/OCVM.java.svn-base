/**
 * @author hty
 * @time 上午9:39:37
 * @date 2014年12月5日
 */
package com.beyondsphere.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.beyondsphere.model.Statistics;
import com.beyondsphere.model.VMPlatform;
import com.beyondsphere.model.VMPower;
import com.beyondsphere.model.VMStatus;

@Entity
@Table(name = "oc_vm")
public class OCVM implements Statistics {

	/**
	 * 虚拟机uuid,36位随机数字与字母
	 */
	private String vmUuid;
	/**
	 * 虚拟机密码，由用户指定，如未生效则为模板密码
	 */
	private String vmPwd;
	/**
	 * 虚拟机所在分组Id
	 */
	private String vmGroup;
	/**
	 * 虚拟机用户id
	 */
	private Integer vmUid;
	/**
	 * 虚拟机名称
	 */
	private String vmName;
	/**
	 * 虚拟机描述
	 */
	private String vmDesc;
	/**
	 * 虚拟机平台信息
	 */
	private VMPlatform vmPlatform;
	/**
	 * 虚拟机vlan的tag信息，由管理员指定
	 */
	private String vmVlan;
	/**
	 * 虚拟机mac，随机生成
	 */
	private String vmMac;
	/**
	 * 虚拟机内存大小，以M为单位存储
	 */
	private Integer vmMem;
	/**
	 * 虚拟机cpu个数
	 */
	private Integer vmCpu;
	/**
	 * 虚拟机硬盘信息，以G为单位
	 */
	private Integer vmDisk;
	/**
	 * 虚拟机状态信息
	 */
	private VMPower vmPower;
	
	private Integer vmType;
	/**
	 * 虚拟机是否已经删除
	 */
	private VMStatus vmStatus;
	/**
	 * 虚拟机所在物理机的uuid
	 */
	private String vmHostUuid;
	/**
	 * 虚拟机创建时间
	 */
	private Date vmCreateDate;
	/**
	 * 虚拟机上次备份时间
	 */
	private Date vmBackUpDate;
	/**
	 * 虚拟机对应模板的uuid
	 */
	private String vmTplUuid;
	
	private String alarmUuid;

	/**
	 * 虚拟机的重要性信息(1-6)
	 */
	private String vmImportance;

	public OCVM(){
		
	}

	/**
	 * 构造函数
	 * 
	 * @param vmUuid
	 * @param vmPWD
	 * @param vmGroup
	 * @param vmUID
	 * @param vmName
	 * @param vmPlatform
	 * @param vmMac
	 * @param vmMem
	 * @param vmCpu
	 * @param vmPower
	 * @param vmStatus
	 * @param createDate
	 */
	public OCVM(String vmUuid, String vmPwd, String vmGroup, Integer vmUid, String vmName,
			VMPlatform vmPlatform, String vmMac, Integer vmMem, Integer vmCpu,
			VMPower vmPower, VMStatus vmStatus, Date vmCreateDate,
			String vmTplUuid) {
		this.vmUuid = vmUuid;
		this.vmPwd = vmPwd;
		this.vmGroup = vmGroup;
		this.vmUid = vmUid;
		this.vmName = vmName;
		this.vmPlatform = vmPlatform;
		this.vmMac = vmMac;
		this.vmMem = vmMem;
		this.vmCpu = vmCpu;
		this.vmPower = vmPower;
		this.vmStatus = vmStatus;
		this.vmCreateDate = vmCreateDate;
		this.vmVlan = "-1";
		this.vmTplUuid = vmTplUuid;
	}

	/**
	 * 获取虚拟机uuid,36位随机数字与字母，主键
	 */
	@Id
	@Column(name = "vm_uuid")
	public String getVmUuid() {
		return vmUuid;
	}

	/**
	 * 设置虚拟机uuid,36位随机数字与字母
	 */
	public void setVmUuid(String vmUuid) {
		this.vmUuid = vmUuid;
	}

	/**
	 * 获取虚拟机密码，由用户指定，如未生效则为模板密码
	 */
	@Column(name = "vm_pwd")
	public String getVmPwd() {
		return vmPwd;
	}

	/**
	 * 设置虚拟机密码，由用户指定，如未生效则为模板密码
	 */
	public void setVmPwd(String vmPwd) {
		this.vmPwd = vmPwd;
	}
	
	@Column(name = "vm_group")
	public String getVmGroup() {
		return vmGroup;
	}

	public void setVmGroup(String vmGroup) {
		this.vmGroup = vmGroup;
	}

	/**
	 * 获取虚拟机用户id
	 */
	@Column(name = "vm_uid")
	public Integer getVmUid() {
		return vmUid;
	}

	/**
	 * 设置虚拟机用户id
	 */
	public void setVmUid(Integer vmUid) {
		this.vmUid = vmUid;
	}

	/**
	 * 获取虚拟机名称
	 */
	@Column(name = "vm_name")
	public String getVmName() {
		return vmName;
	}

	/**
	 * 设置虚拟机名称
	 */
	public void setVmName(String vmName) {
		this.vmName = vmName;
	}

	/**
	 * 获取虚拟机描述
	 */
	@Column(name = "vm_desc")
	public String getVmDesc() {
		return vmDesc;
	}

	/**
	 * 设置虚拟机描述
	 */
	public void setVmDesc(String vmDesc) {
		this.vmDesc = vmDesc;
	}

	/**
	 * 获取虚拟机平台信息
	 */
	@Column(name = "vm_platform")
	@Enumerated(EnumType.ORDINAL)
	public VMPlatform getVmPlatform() {
		return vmPlatform;
	}

	/**
	 * 设置虚拟机平台信息
	 */
	public void setVmPlatform(VMPlatform vmPlatform) {
		this.vmPlatform = vmPlatform;
	}

	/**
	 * 设置虚拟机vlan的tag信息，由管理员指定
	 */
	@Column(name = "vm_vlan")
	public String getVmVlan() {
		return vmVlan;
	}

	/**
	 * 获取虚拟机vlan的tag信息，由管理员指定
	 */
	public void setVmVlan(String vmVlan) {
		this.vmVlan = vmVlan;
	}

	/**
	 * 获取虚拟机mac，随机生成
	 */
	@Column(name = "vm_mac")
	public String getVmMac() {
		return vmMac;
	}

	/**
	 * 设置虚拟机mac，随机生成
	 */
	public void setVmMac(String vmMac) {
		this.vmMac = vmMac;
	}

	/**
	 * 获取虚拟机内存大小，以M为单位存储
	 */
	@Column(name = "vm_mem")
	public Integer getVmMem() {
		return vmMem;
	}

	/**
	 * 设置虚拟机内存大小，以M为单位存储
	 */
	public void setVmMem(Integer vmMem) {
		this.vmMem = vmMem;
	}

	/**
	 * 获取虚拟机cpu个数
	 */
	@Column(name = "vm_cpu")
	public Integer getVmCpu() {
		return vmCpu;
	}

	/**
	 * 设置虚拟机cpu个数
	 */
	public void setVmCpu(Integer vmCpu) {
		this.vmCpu = vmCpu;
	}

	/**
	 * 获取虚拟机硬盘信息，以G为单位
	 */
	@Column(name = "vm_disk")
	public Integer getVmDisk() {
		return vmDisk;
	}

	/**
	 * 设置虚拟机硬盘信息，以G为单位
	 */
	public void setVmDisk(Integer vmDisk) {
		this.vmDisk = vmDisk;
	}

	/**
	 * 获取虚拟机状态信息
	 */
	@Enumerated(EnumType.ORDINAL)
	@Column(name = "vm_power")
	public VMPower getVmPower() {
		return vmPower;
	}

	/**
	 * 设置虚拟机状态信息
	 */
	public void setVmPower(VMPower vmPower) {
		this.vmPower = vmPower;
	}

	/**
	 * 获取虚拟机是否已经删除
	 */
	@Enumerated(EnumType.ORDINAL)
	@Column(name = "vm_status")
	public VMStatus getVmStatus() {
		return vmStatus;
	}

	/**
	 * 设置虚拟机是否已经删除
	 */
	public void setVmStatus(VMStatus vmStatus) {
		this.vmStatus = vmStatus;
	}

	/**
	 * 获取虚拟机所在物理机的uuid
	 */
	@Column(name = "host_uuid")
	public String getVmHostUuid() {
		return vmHostUuid;
	}

	/**
	 * 设置虚拟机所在物理机的uuid
	 */
	public void setVmHostUuid(String vmHostUuid) {
		this.vmHostUuid = vmHostUuid;
	}

	/**
	 * 获取虚拟机创建时间
	 */
	@Column(name = "create_date")
	public Date getVmCreateDate() {
		return vmCreateDate;
	}

	/**
	 * 设置虚拟机创建时间
	 */
	public void setVmCreateDate(Date vmCreateDate) {
		this.vmCreateDate = vmCreateDate;
	}

	/**
	 * 获取虚拟机上次备份时间
	 */
	@Column(name = "backup_date")
	public Date getVmBackUpDate() {
		return vmBackUpDate;
	}

	/**
	 * 设置虚拟机上次备份时间
	 */
	public void setVmBackUpDate(Date vmBackUpDate) {
		this.vmBackUpDate = vmBackUpDate;
	}

	/**
	 * 设置虚拟机对应模板的uuid
	 */
	@Column(name = "tpl_uuid")
	public String getVmTplUuid() {
		return vmTplUuid;
	}

	/**
	 * 获取虚拟机对应模板的uuid
	 */
	public void setVmTplUuid(String vmTplUuid) {
		this.vmTplUuid = vmTplUuid;
	}

	/**
	 * 获取虚拟机对应的警告策略
	 */
	@Column(name = "alarm_uuid")
	public String getAlarmUuid() {
		return alarmUuid;
	}

	/**
	 * 设置虚拟机对应的警告策略
	 */
	public void setAlarmUuid(String alarmUuid) {
		this.alarmUuid = alarmUuid;
	}

	/**
	 * 获取虚拟机的重要性信息(1-6)
	 */
	@Column(name = "vm_importance")
	public String getVmImportance() {
		return vmImportance;
	}

	/**
	 * 设置虚拟机的重要性信息(1-6)
	 */
	public void setVmImportance(String vmImportance) {
		this.vmImportance = vmImportance;
	}

	@Override
	@Transient
	public String getUuid() {
		return this.getVmUuid();
	}

	@Override
	public void setUuid(String uuid) {
		
	}

	@Override
	@Transient
	public String getName() {
		return this.getVmName();
	}

	@Override
	public void setName(String name) {
		
	}

	@Override
	@Transient
	public Date getDate() {
		return this.getVmCreateDate();
	}

	@Override
	public void setDate(Date date) {
		
	}

	@Override
	@Transient
	public int getStatus() {
		return this.getVmStatus().ordinal();
	}

	@Override
	public void setStatus(int status) {
		
	}
	
	@Column(name = "vm_type")
	public Integer getVmType() {
		return vmType;
	}

	public void setVmType(Integer vmType) {
		this.vmType = vmType;
	}

}
