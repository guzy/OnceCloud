package com.oncecloud.message;

public class DeleteRowMessage implements Message {
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

	public DeleteRowMessage(String rowId) {
		this.setMessageType(MessageType.DELETE_ROW);
		this.setRowId(rowId);
	}
}
