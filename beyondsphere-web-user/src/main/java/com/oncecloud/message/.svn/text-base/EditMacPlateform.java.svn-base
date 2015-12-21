/**
 * @author hty
 * @time 上午9:39:37
 * @date 2014年12月5日
 */
package com.beyondsphere.message;

public class EditMacPlateform implements Message {

	private String messageType;
	private String rowId;
	private String mac;
	private String plateform;

	public EditMacPlateform(){
		
	}
	
	public EditMacPlateform(String rowId, String mac, String plateform) {
		this.messageType = MessageType.EDIT_ROW_MAC;
		this.rowId = rowId;
		this.mac = mac;
		this.plateform = plateform;
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

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getPlateform() {
		return plateform;
	}

	public void setPlateform(String plateform) {
		this.plateform = plateform;
	}

}
