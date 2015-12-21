package com.oncecloud.message;

public class UpFileMessage implements Message {
	private String messageType;
	private String content;

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public UpFileMessage(String content) {
		this.setMessageType(MessageType.UP_FILE);
		this.setContent(content);
	}
}
