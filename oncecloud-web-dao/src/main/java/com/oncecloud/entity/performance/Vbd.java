package com.oncecloud.entity.performance;

import java.io.Serializable;

public class Vbd implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8520061750853244691L;
	private Long time;
	private String uuid;
	private String vbdId;
	private Double read;
	private Double write;

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

	public String getVbdId() {
		return vbdId;
	}

	public void setVbdId(String vbdId) {
		this.vbdId = vbdId;
	}

	public Double getRead() {
		return read;
	}

	public void setRead(Double read) {
		this.read = read;
	}

	public Double getWrite() {
		return write;
	}

	public void setWrite(Double write) {
		this.write = write;
	}

	@Override
	public String toString() {
		return "Vbd [time=" + time + ", uuid=" + uuid + ", vbdId=" + vbdId + ", read=" + read + ", write=" + write
				+ "]";
	}
}
