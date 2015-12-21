/**
 * @author hty
 * @time 上午9:39:37
 * @date 2014年12月5日
 */
package com.oncecloud.message;

import com.oncecloud.message.Message;
import com.oncecloud.message.MessageType;

public class EditRowConsoleMessage implements Message {
	
	private String messageType;
	private String rowId;
	private String option;
	
	public EditRowConsoleMessage(){
		
	}
	
	public EditRowConsoleMessage(String rowId, String option) {
		this.setMessageType(MessageType.EDIT_ROW_CONSOLE);
		this.setRowId(rowId);
		this.setOption(option);
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

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}

}
