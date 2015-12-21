package com.oncecloud.model;

public class CreateForeModel {
	private String name;
	private String foreUuid;
	private String lbUuid;
	private String protoCol;
	private int port;
	private int policy;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getForeUuid() {
		return foreUuid;
	}
	public void setForeUuid(String foreUuid) {
		this.foreUuid = foreUuid;
	}
	public String getLbUuid() {
		return lbUuid;
	}
	public void setLbUuid(String lbUuid) {
		this.lbUuid = lbUuid;
	}
	public String getProtoCol() {
		return protoCol;
	}
	public void setProtoCol(String protoCol) {
		this.protoCol = protoCol;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public int getPolicy() {
		return policy;
	}
	public void setPolicy(int policy) {
		this.policy = policy;
	}
	
	
}
