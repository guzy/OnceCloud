package com.beyondsphere.message;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.beyondsphere.util.TimeUtils;

@Component("MessageUtil")
public class MessageUtil {
	
	@Resource
	private MessagePush messagePush;
	
	/**
	 * 推送操作默认行为的消息
	 * @author lining
	 * @param userId, message
	 * 
	 */
	public void pushInfo(Integer userId, String message){
		messagePush.pushMessage(userId,
				TimeUtils.stickyToInfo(message));
	}
	
	/**
	 * 推送操作成功的消息
	 * @author lining
	 * @param userId, message
	 * 
	 */
	public void pushSuccess(Integer userId, String message) {
		messagePush.pushMessage(userId,
				TimeUtils.stickyToSuccess(message));
	}
	
	/**
	 * 推送操作警告的消息
	 * @author lining
	 * @param userId, message
	 * 
	 */
	public void pushWarning(Integer userId, String message){
		messagePush.pushMessage(userId,
				TimeUtils.stickyToWarning(message));
	}
	
	/**
	 * 推送操作失败的消息
	 * @author lining
	 * @param userId, message
	 * 
	 */
	public void pushError(Integer userId, String message){
		messagePush.pushMessage(userId,
				TimeUtils.stickyToError(message));
	}
	
	/**
	 * 推送关闭消息
	 * @author lining
	 * @param userId, content, conId
	 * @return
	 */
	public void pushClose(int userId, String content, String conid){
		messagePush.pushMessageClose(userId, content, conid);
	}
	
	/**
	 * 编辑状态消息
	 * @author lining
	 * @param userId, vmUuid, statusIcon, statusWord
	 * @return
	 */
	public void editRowStatus(Integer userId, String vmUuid, String statusIcon, String statusWord){
		messagePush.editRowStatus(userId, vmUuid, statusIcon, statusWord);
	}
	
	/**
	 * 编辑控制消息
	 * @author lining
	 * @param userId, vmUuid, option
	 * @return
	 */
	public void editRowConsole(Integer userId, String vmUuid, String option){
		messagePush.editRowConsole(userId, vmUuid, option);
	}
	
	/**
	 * 删除消息
	 * @author lining
	 * @param userId, vmUuid
	 * @return 
	 */
	public void  deleteRow(Integer userId, String vmUuid) {
		messagePush.deleteRow(userId, vmUuid);
	}
}
