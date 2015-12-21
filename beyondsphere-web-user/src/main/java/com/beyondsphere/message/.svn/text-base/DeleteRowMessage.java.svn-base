/**
 * @author hty
 * @time 上午9:39:37
 * @date 2014年12月5日
 */
package com.beyondsphere.message;

public class DeleteRowMessage implements Message {
	
	private String messageType;
	private String rowId;
	
	public DeleteRowMessage(){
		
	}
	
	public DeleteRowMessage(String rowId) {
		this.setMessageType(MessageType.DELETE_ROW);
		this.setRowId(rowId);
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

}
