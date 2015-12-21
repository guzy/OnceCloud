package com.oncecloud.message;

public class EditRowStatusForBindVolumeMessage implements Message {
	private String messageType;
	private String rowId;
	private String vmId;
	private String vmName;

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

	public String getVmId() {
		return vmId;
	}

	public void setVmId(String vmId) {
		this.vmId = vmId;
	}

	public String getVmName() {
		return vmName;
	}

	public void setVmName(String vmName) {
		this.vmName = vmName;
	}

	public EditRowStatusForBindVolumeMessage(String rowId, String vmId,
			String vmName) {
		this.setMessageType(MessageType.EDIT_ROW_STATUS_FOR_BIND_VOLUME);
		this.setRowId(rowId);
		this.setVmId(vmId);
		this.setVmName(vmName);
	}
}
