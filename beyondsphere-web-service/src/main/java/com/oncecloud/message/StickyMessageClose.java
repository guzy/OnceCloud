package com.oncecloud.message;

public class StickyMessageClose implements Message {
	private String messageType;
	private String content;
	private String conid;

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

	public String getConid() {
		return conid;
	}

	public void setConid(String conid) {
		this.conid = conid;
	}

	public StickyMessageClose(String content, String conid) {
		this.setMessageType(MessageType.STICKY_CLOSE);
		this.setContent(content);
		this.setConid(conid);
	}
}
