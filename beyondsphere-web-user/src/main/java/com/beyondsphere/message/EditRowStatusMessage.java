/**
 * @author hty
 * @time 上午9:39:37
 * @date 2014年12月5日
 */
package com.beyondsphere.message;

public class EditRowStatusMessage implements Message {
	
	private String messageType;
	private String rowId;
	private String icon;
	private String word;

	public EditRowStatusMessage(){
		
	}
	
	public EditRowStatusMessage(String rowId, String icon, String word) {
		this.setMessageType(MessageType.EDIT_ROW_STATUS);
		this.setRowId(rowId);
		this.setIcon(icon);
		this.setWord(word);
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

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

}
