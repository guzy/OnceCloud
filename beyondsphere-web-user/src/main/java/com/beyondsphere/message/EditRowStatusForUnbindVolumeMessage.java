/**
 * @author hty
 * @time 上午9:39:37
 * @date 2014年12月5日
 */
package com.beyondsphere.message;

public class EditRowStatusForUnbindVolumeMessage implements Message {
	
	private String messageType;
	private String rowId;

	public EditRowStatusForUnbindVolumeMessage(){
		
	}
	
	public EditRowStatusForUnbindVolumeMessage(String rowId) {
		this.setMessageType(MessageType.EDIT_ROW_STATUS_FOR_UNBIND_VOLUME);
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
