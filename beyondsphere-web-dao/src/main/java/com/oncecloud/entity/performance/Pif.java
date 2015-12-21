package com.oncecloud.entity.performance;

import java.io.Serializable;

public class Pif implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4930843628076054976L;
	private Long time;
	private String uuid;
	private Integer pifId;
	private Double rxd;
	private Double txd;

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Integer getPifId() {
		return pifId;
	}

	public void setPifId(Integer pifId) {
		this.pifId = pifId;
	}

	public Double getRxd() {
		return rxd;
	}

	public void setRxd(Double rxd) {
		this.rxd = rxd;
	}

	public Double getTxd() {
		return txd;
	}

	public void setTxd(Double txd) {
		this.txd = txd;
	}

	@Override
	public String toString() {
		return "Pif [time=" + time + ", uuid=" + uuid + ", pifId=" + pifId + ", rxd=" + rxd + ", txd=" + txd + "]";
	}

}
