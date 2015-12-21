package com.beyondsphere.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author hty
 */
@Entity
@Table(name = "oc_vdi")
public class OCVDI {
	private String vdiUuid;
	private String tplUuid;
	private Date createDate;

	@Id
	@Column(name = "vdi_uuid")
	public String getVdiUuid() {
		return vdiUuid;
	}

	public void setVdiUuid(String vdiUuid) {
		this.vdiUuid = vdiUuid;
	}

	@Column(name = "tpl_uuid")
	public String getTplUuid() {
		return tplUuid;
	}

	public void setTplUuid(String tplUuid) {
		this.tplUuid = tplUuid;
	}

	@Column(name = "create_date")
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Override
	public String toString() {
		return "OCVDI [vdiUuid=" + vdiUuid + ", tplUuid=" + tplUuid
				+ ", createDate=" + createDate + "]";
	}
}
