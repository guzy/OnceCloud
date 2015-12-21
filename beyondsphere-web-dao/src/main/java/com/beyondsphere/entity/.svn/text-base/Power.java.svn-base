/**
 * @author hty
 * @time 上午11:05:48
 * @date 2014年12月9日
 */
package com.beyondsphere.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "power")
public class Power {

	 private String powerUuid;
	 private String hostUuid;
	 private String motherboardIP;
	 private Integer powerPort;
	 private String powerUsername;
	 private String powerPassword;
	 private Integer powerValid;
	 
	 
	 public Power(){}
	 
	public Power(String powerUuid, String hostUuid, String motherboardIP,
			Integer powerPort, String powerUsername, String powerPassword,
			Integer powerValid) {
		super();
		this.powerUuid = powerUuid;
		this.hostUuid = hostUuid;
		this.motherboardIP = motherboardIP;
		this.powerPort = powerPort;
		this.powerUsername = powerUsername;
		this.powerPassword = powerPassword;
		this.powerValid = powerValid;
	}
	@Id
	@Column(name = "power_uuid")
	public String getPowerUuid() {
		return powerUuid;
	}
	public void setPowerUuid(String powerUuid) {
		this.powerUuid = powerUuid;
	}
	
	@Column(name = "host_uuid")
	public String getHostUuid() {
		return hostUuid;
	}
	public void setHostUuid(String hostUuid) {
		this.hostUuid = hostUuid;
	}
	
	@Column(name = "motherboard_ip")
	public String getMotherboardIP() {
		return motherboardIP;
	}
	public void setMotherboardIP(String motherboardIP) {
		this.motherboardIP = motherboardIP;
	}
	
	@Column(name = "power_port")
	public Integer getPowerPort() {
		return powerPort;
	}
	public void setPowerPort(Integer powerPort) {
		this.powerPort = powerPort;
	}
	
	@Column(name = "power_username")
	public String getPowerUsername() {
		return powerUsername;
	}
	public void setPowerUsername(String powerUsername) {
		this.powerUsername = powerUsername;
	}
	
	@Column(name = "power_password")
	public String getPowerPassword() {
		return powerPassword;
	}
	public void setPowerPassword(String powerPassword) {
		this.powerPassword = powerPassword;
	}
	
	@Column(name = "power_valid")
	public Integer getPowerValid() {
		return powerValid;
	}
	public void setPowerValid(Integer powerValid) {
		this.powerValid = powerValid;
	}
	
	
	@Override
	public String toString() {
		return "Power [powerUuid=" + powerUuid + ", hostUuid=" + hostUuid
				+ ", motherboardIP=" + motherboardIP + ", powerPort="
				+ powerPort + ", powerUsername=" + powerUsername
				+ ", powerPassword=" + powerPassword + ", powerValid="
				+ powerValid + "]";
	}
	 
	 
}
