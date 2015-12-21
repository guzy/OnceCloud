package com.oncecloud.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author luogan
 * 2015年7月13日
 * 上午11:31:33
 */
@Entity
@Table(name = "oc_repository")
public class Repository {
	private String repoId;
	private String repoName;
	private String repoVersion;
	private String repoAddress;
	private Date repoCreateTime;

	public Repository(String repoId, String repoName, String repoVersion,
			String repoAddress, Date repoCreateTime) {
		super();
		this.repoId = repoId;
		this.repoName = repoName;
		this.repoVersion = repoVersion;
		this.repoAddress = repoAddress;
		this.repoCreateTime = repoCreateTime;
	}

	public Repository() {
	}

	@Id
	@Column(name = "repo_uuid")
	public String getRepoId() {
		return repoId;
	}

	public void setRepoId(String repoId) {
		this.repoId = repoId;
	}

	@Column(name = "repo_name")
	public String getRepoName() {
		return repoName;
	}

	public void setRepoName(String repoName) {
		this.repoName = repoName;
	}

	@Column(name = "repo_version")
	public String getRepoVersion() {
		return repoVersion;
	}

	public void setRepoVersion(String repoVersion) {
		this.repoVersion = repoVersion;
	}

	@Column(name = "repo_address")
	public String getRepoAddress() {
		return repoAddress;
	}

	public void setRepoAddress(String repoAddress) {
		this.repoAddress = repoAddress;
	}

	@Column(name = "repo_createTime")
	public Date getRepoCreateTime() {
		return repoCreateTime;
	}

	public void setRepoCreateTime(Date repoCreateTime) {
		this.repoCreateTime = repoCreateTime;
	}

	@Override
	public String toString() {
		return "Repository [repoId=" + repoId + ", repoName=" + repoName
				+ ", repoVersion=" + repoVersion + ", repoAddress="
				+ repoAddress + ", repoCreateTime=" + repoCreateTime + "]";
	}

}
