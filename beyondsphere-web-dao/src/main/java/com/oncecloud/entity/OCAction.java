package com.oncecloud.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.oncecloud.model.ActionType;


/**
 * Action 信息表
 * @author 玉和
 *
 * @Date 2014年12月4日
 */
@Entity
@Table(name = "oc_action")
public class OCAction {
	
	// 主键,自增长
	private Integer actionId; 
	
	// action名称
	private String actionName;
	
	// action说明
	private String actionIntroduce;
	
	// action的 相对Url, 这是Action的请求地址，也判断权限的依据，必须是唯一的，
	private String actionRelativeUrl;
	
	// action 的类型，这是一个枚举，见：ActionType
	private ActionType actionType;
	
	// action对应的前台对象(如果存在的话），在html中的id
	private String actionHtmlId;
	
	// action对应的前台对象(如果存在的话），在html中的name
	private String actionHtmlName;
	
	// action对应的前台对象(如果存在的话），在html中的父级对象id，可以控制显示隐藏
	private String actionHtmlParentObj;
	
	// action的备注信息
	private String actionRemarks;
	
	// action 所属页面的id
	private Integer parentId;
	
	public OCAction(){
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "action_id")
	public Integer getActionId() {
		return actionId;
	}

	public void setActionId(Integer actionId) {
		this.actionId = actionId;
	}

	@Column(name = "action_name")
	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	@Column(name = "action_introduce")
	public String getActionIntroduce() {
		return actionIntroduce;
	}

	public void setActionIntroduce(String actionIntroduce) {
		this.actionIntroduce = actionIntroduce;
	}

	@Column(name = "action_relative_url")
	public String getActionRelativeUrl() {
		return actionRelativeUrl;
	}

	public void setActionRelativeUrl(String actionRelativeUrl) {
		this.actionRelativeUrl = actionRelativeUrl;
	}

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "action_type")
	public ActionType getActionType() {
		return actionType;
	}

	public void setActionType(ActionType actionType) {
		this.actionType = actionType;
	}

	@Column(name = "action_html_id")
	public String getActionHtmlId() {
		return actionHtmlId;
	}

	public void setActionHtmlId(String actionHtmlId) {
		this.actionHtmlId = actionHtmlId;
	}

	@Column(name = "action_html_name")
	public String getActionHtmlName() {
		return actionHtmlName;
	}

	public void setActionHtmlName(String actionHtmlName) {
		this.actionHtmlName = actionHtmlName;
	}

	@Column(name = "action_html_parentobj")
	public String getActionHtmlParentObj() {
		return actionHtmlParentObj;
	}

	public void setActionHtmlParentObj(String actionHtmlParentObj) {
		this.actionHtmlParentObj = actionHtmlParentObj;
	}

	@Column(name = "action_remarks")
	public String getActionRemarks() {
		return actionRemarks;
	}

	public void setActionRemarks(String actionRemarks) {
		this.actionRemarks = actionRemarks;
	}

	@Column(name = "parent_id")
	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	@Override
	public String toString() {
		return "OCAction [actionId=" + actionId + ", actionName=" + actionName
				+ ", actionIntroduce=" + actionIntroduce
				+ ", actionRelativeUrl=" + actionRelativeUrl + ", actionType="
				+ actionType + ", actionHtmlId=" + actionHtmlId
				+ ", actionHtmlName=" + actionHtmlName
				+ ", actionHtmlParentObj=" + actionHtmlParentObj
				+ ", actionRemarks=" + actionRemarks + "]";
	}

	
}
