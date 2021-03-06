/**
 * @author hty
 * @time 上午9:39:37
 * @date 2014年12月5日
 */
package com.oncecloud.message;

import com.oncecloud.message.Message;
import com.oncecloud.message.MessageType;

public class EditRowIPMessage implements Message {
	
	private String messageType;
	private String rowId;
	private String network;
	private String ip;

	public EditRowIPMessage(){
		
	}
	
	public EditRowIPMessage(String rowId, String network, String ip) {
		this.setMessageType(MessageType.EDIT_ROW_IP);
		this.setRowId(rowId);
		this.setNetwork(network);
		this.setIp(ip);
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

	public String getNetwork() {
		return network;
	}

	public void setNetwork(String network) {
		this.network = network;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

}
