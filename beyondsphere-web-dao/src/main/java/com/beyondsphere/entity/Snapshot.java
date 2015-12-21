/**
 * @author hty
 * @time 下午12:24:46
 * @date 2014年12月11日
 */
package com.beyondsphere.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.beyondsphere.model.Statistics;

@Entity
@Table(name = "oc_snapshot")
public class Snapshot implements Statistics{

	private String snapshotId;
	private String snapshotName;
	private Integer snapshotSize;
	private Date backupDate;
	private String snapshotVm;
	private String snapshotVolume;
	private int userId;

	public Snapshot() {
	}

	public Snapshot(String snapshotId, String snapshotName, Integer snapshotSize,
			Date backupDate, String snapshotVm, String snapshotVolume, int userId) {
		this.snapshotId = snapshotId;
		this.snapshotName = snapshotName;
		this.snapshotSize = snapshotSize;
		this.backupDate = backupDate;
		this.snapshotVm = snapshotVm;
		this.snapshotVolume = snapshotVolume;
		this.userId = userId;
	}

	/**
	 * 获取备份uuid
	 * 
	 * @return
	 */
	@Id
	@Column(name = "snapshot_id")
	public String getSnapshotId() {
		return snapshotId;
	}

	/**
	 * 设置备份uuid
	 * 
	 * @param snapshotId
	 */
	public void setSnapshotId(String snapshotId) {
		this.snapshotId = snapshotId;
	}

	/**
	 * 获取备份名称
	 * 
	 * @return
	 */
	@Column(name = "snapshot_name")
	public String getSnapshotName() {
		return snapshotName;
	}

	/**
	 * 设置备份名称
	 * 
	 * @param snapshotName
	 */
	public void setSnapshotName(String snapshotName) {
		this.snapshotName = snapshotName;
	}

	/**
	 * 获取备份数量
	 * 
	 * @return
	 */
	@Column(name = "snapshot_size")
	public Integer getSnapshotSize() {
		return snapshotSize;
	}

	/**
	 * 设置备份数量
	 * 
	 * @param snapshotSize
	 */
	public void setSnapshotSize(Integer snapshotSize) {
		this.snapshotSize = snapshotSize;
	}

	/**
	 * 获取备份时间
	 * 
	 * @return
	 */
	@Column(name = "backup_date")
	public Date getBackupDate() {
		return backupDate;
	}

	/**
	 * 设置备份时间
	 * 
	 * @param backupDate
	 */
	public void setBackupDate(Date backupDate) {
		this.backupDate = backupDate;
	}

	/**
	 * 获取备份虚拟机的uuid
	 * @return
	 */
	@Column(name = "snapshot_vm")
	public String getSnapshotVm() {
		return snapshotVm;
	}

	/**
	 * 设置备份虚拟机的uuid
	 * @param snapshotVm
	 */
	public void setSnapshotVm(String snapshotVm) {
		this.snapshotVm = snapshotVm;
	}

	/**
	 * 获取备份硬盘的uuid
	 * @return
	 */
	@Column(name = "snapshot_volume")
	public String getSnapshotVolume() {
		return snapshotVolume;
	}

	/**
	 * 设置备份硬盘的uuid
	 * @param snapshotVolume
	 */
	public void setSnapshotVolume(String snapshotVolume) {
		this.snapshotVolume = snapshotVolume;
	}

	@Column(name = "user_id")
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	@Override
	@Transient
	public String getUuid() {
		return this.getSnapshotId();
	}

	@Override
	@Transient
	public String getName() {
		return this.getSnapshotName();
	}

	@Override
	@Transient
	public Date getDate() {
		return this.getBackupDate();
	}

	@Override
	@Transient
	public int getStatus() {
		return 1;
	}

	@Override
	public void setName(String name) {
		
	}

	@Override
	public void setDate(Date date) {
		
	}

	@Override
	public void setUuid(String uuid) {
		
	}

	@Override
	public void setStatus(int status) {
		
	}

}
