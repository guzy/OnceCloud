package com.oncecloud.message;

public class EditRowStatusForUnbindVolumeMessage implements Message {
	private String messageType;
	private String rowId;

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

	public EditRowStatusForUnbindVolumeMessage(String rowId) {
		this.setMessageType(MessageType.EDIT_ROW_STATUS_FOR_UNBIND_VOLUME);
		this.setRowId(rowId);
	}
}
