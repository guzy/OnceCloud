package com.beyondsphere.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author hty
 *
 */
@Entity
@Table(name = "oc_qa")
public class QA {
	private Integer qaId;
	private Integer qaUID;
	private String qaTitle;
	private String qaContent;
	private Integer qaTid;
	private Integer qaStatus;
	private Integer qaReply;
	private Date qaTime;

	@Id
	@Column(name = "qa_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getQaId() {
		return qaId;
	}

	public void setQaId(Integer qaId) {
		this.qaId = qaId;
	}

	@Column(name = "qa_uid")
	public Integer getQaUID() {
		return qaUID;
	}

	public void setQaUID(Integer qaUID) {
		this.qaUID = qaUID;
	}

	@Column(name = "qa_title")
	public String getQaTitle() {
		return qaTitle;
	}

	public void setQaTitle(String qaTitle) {
		this.qaTitle = qaTitle;
	}

	@Column(name = "qa_content")
	public String getQaContent() {
		return qaContent;
	}

	public void setQaContent(String qaContent) {
		this.qaContent = qaContent;
	}

	@Column(name = "qa_tid")
	public Integer getQaTid() {
		return qaTid;
	}

	public void setQaTid(Integer qaTid) {
		this.qaTid = qaTid;
	}

	@Column(name = "qa_status")
	public Integer getQaStatus() {
		return qaStatus;
	}

	public void setQaStatus(Integer qaStatus) {
		this.qaStatus = qaStatus;
	}

	@Column(name = "qa_reply")
	public Integer getQaReply() {
		return qaReply;
	}

	public void setQaReply(Integer qaReply) {
		this.qaReply = qaReply;
	}

	@Column(name = "qa_time")
	public Date getQaTime() {
		return qaTime;
	}

	public void setQaTime(Date qaTime) {
		this.qaTime = qaTime;
	}

	@Override
	public String toString() {
		return "QA [qaId=" + qaId + ", qaUID=" + qaUID + ", qaTitle=" + qaTitle
				+ ", qaContent=" + qaContent + ", qaTid=" + qaTid
				+ ", qaStatus=" + qaStatus + ", qaTime=" + qaTime + "]";
	}
}
