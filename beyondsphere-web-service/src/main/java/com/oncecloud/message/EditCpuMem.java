package com.oncecloud.message;

public class EditCpuMem implements Message {

	private String messageType;
	private String rowId;
	private String cpu;
	private String mem;

	public EditCpuMem(String rowId, String cpu, String mem) {
		this.messageType = MessageType.EDIT_ROW_CPUMEM;
		this.rowId = rowId;
		this.cpu = cpu;
		this.mem = mem;
	}
	
	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public String getRowId() {
		return rowId;
	}

	public void setRowId(String rowId) {
		this.rowId = rowId;
	}

	public String getCpu() {
		return cpu;
	}

	public void setCpu(String cpu) {
		this.cpu = cpu;
	}

	public String getMem() {
		return mem;
	}

	public void setMem(String mem) {
		this.mem = mem;
	}

}
