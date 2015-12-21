/**
 * @author hty
 * @time 上午9:39:37
 * @date 2014年12月5日
 */
package com.beyondsphere.message;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * @author hehai
 * @version 2013/12/25
 */
@Component
public class MessagePush {
	private final static Logger logger = Logger.getLogger(MessagePush.class);

	@Resource
	private MessageWebSocketHandler messageWebSocketHandler;

	public void pushMessage(int userId, String content) {
		logger.info("User [" + userId + "] Content [" + content + "]");
		messageWebSocketHandler.sendMessageToUser(userId,
				new StickyMessage(content));
	}

	public void pushMessageClose(int userId, String content, String conid) {
		messageWebSocketHandler.sendMessageToUser(userId,
				new StickyMessageClose(content, conid));
	}

	public void deleteRow(int userId, String rowId) {
		logger.info("User [" + userId + "] RowId [" + rowId + "]");
		messageWebSocketHandler.sendMessageToUser(userId,
				new DeleteRowMessage(rowId));
	}

	public void editRowStatus(int userId, String rowId, String icon, String word) {
		logger.info("User [" + userId + "] RowId [" + rowId + "] Icon [" + icon
				+ "] Word [" + word + "]");
		messageWebSocketHandler.sendMessageToUser(userId,
				new EditRowStatusMessage(rowId, icon, word));
	}

	public void editRowStatusForUnbindVolume(int userId, String rowId) {
		messageWebSocketHandler.sendMessageToUser(userId,
				new EditRowStatusForUnbindVolumeMessage(rowId));
	}

	public void editRowStatusForBindVolume(int userId, String rowId,
			String vmId, String vmName) {
		messageWebSocketHandler.sendMessageToUser(userId,
				new EditRowStatusForBindVolumeMessage(rowId, vmId, vmName));
	}

	public void editRowIP(int userId, String rowId, String network, String ip) {
		messageWebSocketHandler.sendMessageToUser(userId,
				new EditRowIPMessage(rowId, network, ip));
	}

	public void editRowConsole(int userId, String rowId, String option) {
		messageWebSocketHandler.sendMessageToUser(userId,
				new EditRowConsoleMessage(rowId, option));
	}

	public void editRowCpuMem(int userId, String rowId, String cpu, String mem) {
		messageWebSocketHandler.sendMessageToUser(userId,
				new EditCpuMem(rowId, cpu, mem));
	}

	public void editRowMacPlateform(int userId, String rowId, String mac,
			String plateform) {
		messageWebSocketHandler.sendMessageToUser(userId,
				new EditMacPlateform(rowId, mac, plateform));
	}
}
