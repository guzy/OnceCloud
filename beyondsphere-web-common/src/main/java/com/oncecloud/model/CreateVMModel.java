package com.oncecloud.model;

public class CreateVMModel {

	private String imageUuid;
	private int cpu;
	private double memory;
	private String vmName;
	private String password;
	private String vmUuid;
	private String startCommand;
	private String sysPort;
	private String mapPort;
	private String link;
	private String dockerParams;
	private String clusterUuid;
	

	public String getClusterUuid() {
		return clusterUuid;
	}

	public void setClusterUuid(String clusterUuid) {
		this.clusterUuid = clusterUuid;
	}

	public String getImageUuid() {
		return imageUuid;
	}

	public void setImageUuid(String imageUuid) {
		this.imageUuid = imageUuid;
	}

	public int getCpu() {
		return cpu;
	}

	public void setCpu(int cpu) {
		this.cpu = cpu;
	}

	public double getMemory() {
		return memory;
	}

	public void setMemory(double memory) {
		this.memory = memory;
	}

	public String getVmName() {
		return vmName;
	}

	public void setVmName(String vmName) {
		this.vmName = vmName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getVmUuid() {
		return vmUuid;
	}

	public void setVmUuid(String vmUuid) {
		this.vmUuid = vmUuid;
	}

	public String getStartCommand() {
		return startCommand;
	}

	public void setStartCommand(String startCommand) {
		this.startCommand = startCommand;
	}

	public String getSysPort() {
		return sysPort;
	}

	public void setSysPort(String sysPort) {
		this.sysPort = sysPort;
	}

	public String getMapPort() {
		return mapPort;
	}

	public void setMapPort(String mapPort) {
		this.mapPort = mapPort;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getDockerParams() {
		return dockerParams;
	}

	public void setDockerParams(String dockerParams) {
		this.dockerParams = dockerParams;
	}

}
