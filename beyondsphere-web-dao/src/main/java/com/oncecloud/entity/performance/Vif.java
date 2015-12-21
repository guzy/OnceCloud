package com.oncecloud.entity.performance;

import java.io.Serializable;

public class Vif implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -283580538594522712L;
	private Long time;
	private String uuid;
	private Integer vifId;
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

	public Integer getVifId() {
		return vifId;
	}

	public void setVifId(Integer vifId) {
		this.vifId = vifId;
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
		return "Vif [time=" + time + ", uuid=" + uuid + ", vifId=" + vifId + ", rxd=" + rxd + ", txd=" + txd + "]";
	}
}
