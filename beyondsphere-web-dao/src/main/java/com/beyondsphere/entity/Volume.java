/**
 * @author zll
 * @time 下午13:31
 * @date 2015年5月19日
 */
package com.beyondsphere.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import com.beyondsphere.model.VolumeStatus;


@Entity
@Table(name = "oc_volume")
public class Volume{
	
	private String volumeUuid;
	private String volumeName;
	private Integer volumeUID;
	private Integer volumeSize;
	private String volumeDependency;
	private String volumeDescription;
	private Date createDate;
	private Date backupDate;
	private VolumeStatus volumeStatus;

	public Volume() {
		
	}

	public Volume(String volumeUuid, String volumeName, Integer volumeUID,
			Integer volumeSize, Date createDate, VolumeStatus volumeStatus) {
		this.volumeUuid = volumeUuid;
		this.volumeName = volumeName;
		this.volumeUID = volumeUID;
		this.volumeSize = volumeSize;
		this.createDate = createDate;
		this.volumeStatus = volumeStatus;
	}

	/**
	 * 获取硬盘uuid
	 * @return
	 */
	@Id
	@Column(name = "volume_uuid")
	public String getVolumeUuid() {
		return volumeUuid;
	}

	/**
	 * 设置硬盘uuid
	 * @param volumeUuid
	 */
	public void setVolumeUuid(String volumeUuid) {
		this.volumeUuid = volumeUuid;
	}

	/**
	 * 获取硬盘名称
	 * @return
	 */
	@Column(name = "volume_name")
	public String getVolumeName() {
		return volumeName;
	}

	/**
	 * 设置硬盘名称
	 * @param volumeName
	 */
	public void setVolumeName(String volumeName) {
		this.volumeName = volumeName;
	}

	/**
	 * 获取硬盘用户id
	 * @return
	 */
	@Column(name = "volume_uid")
	public Integer getVolumeUID() {
		return volumeUID;
	}

	/**
	 * 设置硬盘用户id
	 * @param volumeUID
	 */
	public void setVolumeUID(Integer volumeUID) {
		this.volumeUID = volumeUID;
	}

	/**
	 * 获取硬盘大小，以G为单位
	 * @return
	 */
	@Column(name = "volume_size")
	public Integer getVolumeSize() {
		return volumeSize;
	}

	/**
	 * 设置硬盘大小，以G为单位
	 * @param volumeSize
	 */
	public void setVolumeSize(Integer volumeSize) {
		this.volumeSize = volumeSize;
	}

	/**
	 * 获取硬盘所挂载的虚拟机的uuid
	 * @return
	 */
	@Column(name = "volume_dependency")
	public String getVolumeDependency() {
		return volumeDependency;
	}

	/**
	 * 设置硬盘所挂载的虚拟机的uuid
	 * @param volumeDependency
	 */
	public void setVolumeDependency(String volumeDependency) {
		this.volumeDependency = volumeDependency;
	}

	/**
	 * 获取硬盘描述信息
	 * @return
	 */
	@Column(name = "volume_description")
	public String getVolumeDescription() {
		return volumeDescription;
	}

	/**
	 * 设置硬盘描述信息
	 * @param volumeDescription
	 */
	public void setVolumeDescription(String volumeDescription) {
		this.volumeDescription = volumeDescription;
	}

	/**
	 * 获取硬盘创建时间
	 * @return
	 */
	@Column(name = "create_date")
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * 设置硬盘创建时间
	 * @param createDate
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * 获取硬盘备份时间
	 * @return
	 */
	@Column(name = "backup_date")
	public Date getBackupDate() {
		return backupDate;
	}

	/**
	 * 设置硬盘备份时间
	 * @param backupDate
	 */
	public void setBackupDate(Date backupDate) {
		this.backupDate = backupDate;
	}

	/**
	 * 设置硬盘状态信息
	 * @return
	 */
	@Column(name = "volume_status")
	@Enumerated(EnumType.ORDINAL)
	public VolumeStatus getVolumeStatus() {
		return volumeStatus;
	}

	/**
	 * 获取硬盘状态信息
	 * @param volumeStatus
	 */
	public void setVolumeStatus(VolumeStatus volumeStatus) {
		this.volumeStatus = volumeStatus;
	}

	@Override
	public String toString() {
		return "Volume [volumeUuid=" + volumeUuid + ", volumeName="
				+ volumeName + ", volumeUID=" + volumeUID + ", volumeSize="
				+ volumeSize + ", volumeDependency=" + volumeDependency
				+ ", volumeDescription=" + volumeDescription + ", createDate="
				+ createDate + ", backupDate=" + backupDate + "]";
	}

}