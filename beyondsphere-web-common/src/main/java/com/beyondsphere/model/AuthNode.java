package com.beyondsphere.model;


/**
 * @author 玉和
 * 用于界面 权限tree 展示用的 节点对象
 */
public class AuthNode {
    
	private int id;
	private int pId;
	private String name;
	private int actionType;
	private boolean checked;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getpId() {
		return pId;
	}
	public void setpId(int pId) {
		this.pId = pId;
	}
	
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getActionType() {
		return actionType;
	}
	public void setActionType(int actionType) {
		this.actionType = actionType;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	
	
	public AuthNode(int id, int pId, String name, int actionType,
			boolean checked) {
		super();
		this.id = id;
		this.pId = pId;
		this.name = name;
		this.actionType = actionType;
		this.checked = checked;
	}
	public AuthNode(){}
}
