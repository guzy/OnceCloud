package com.beyondsphere.usercore;

import javax.annotation.Resource;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.beyondsphere.message.MessagePush;
import com.beyondsphere.message.MessageUtilities;


@Component("MessageUserCore")
public class MessageCore {
	
	@Resource
	private MessagePush messagePush;

	public JSONObject createVMMessage(JSONObject jo) {
		try {
			int userId = jo.getInt("userId");
			String vmUuid = jo.getString("uuid");
			String log = jo.getString("log");
			if (jo.getBoolean("result")) {
				messagePush.editRowStatus(userId, vmUuid, "running",
						"正常运行");
				messagePush.editRowConsole(userId, vmUuid, "add");
				messagePush.editRowMacPlateform(userId, vmUuid,
						jo.getString("mac"), jo.getString("plateform"));
				messagePush.pushMessage(userId,
						MessageUtilities.stickyToSuccess(log));
			} else {
				messagePush.deleteRow(userId, vmUuid);
				messagePush.pushMessage(userId,
						MessageUtilities.stickyToError(log.toString()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jo;
	}

	public JSONObject shutdownVMMessage(JSONObject jo) {
		try {
			int userId = jo.getInt("userId");
			String uuid = jo.getString("uuid");
			String log = jo.getString("log");
			if (jo.getBoolean("result")) {
				messagePush.editRowStatus(userId, uuid, "stopped","已关机");
				messagePush.pushMessage(userId,
						MessageUtilities.stickyToSuccess(log.toString()));
			} else {
				messagePush.editRowStatus(userId, uuid, "running",
						"正常运行");
				messagePush.editRowConsole(userId, uuid, "add");
				messagePush.pushMessage(userId,
						MessageUtilities.stickyToError(log.toString()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jo;
	}

	public JSONObject startVMMessage(JSONObject jo) {
		try {
			int userId = jo.getInt("userId");
			String uuid = jo.getString("uuid");
			String log = jo.getString("log");
			if (jo.getBoolean("result")) {
				messagePush.editRowStatus(userId, uuid, "running",
						"正常运行");
				messagePush.editRowConsole(userId, uuid, "add");
				messagePush.pushMessage(userId,
						MessageUtilities.stickyToSuccess(log.toString()));
			} else {
				messagePush.editRowStatus(userId, uuid, "stopped",
						"已关机");
				messagePush.pushMessage(userId,
						MessageUtilities.stickyToError(log.toString()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jo;
	}

	public JSONObject restartVMMessage(JSONObject jo) {
		try {
			int userId = jo.getInt("userId");
			String uuid = jo.getString("uuid");
			String log = jo.getString("log");
			if (jo.getBoolean("result")) {
				messagePush.editRowStatus(userId, uuid, "running",
						"正常运行");
				messagePush.editRowConsole(userId, uuid, "add");
				messagePush.pushMessage(userId,
						MessageUtilities.stickyToSuccess(log.toString()));
			} else {
				messagePush.editRowStatus(userId, uuid, "running",
						"正常运行");
				messagePush.editRowConsole(userId, uuid, "add");
				messagePush.pushMessage(userId,
						MessageUtilities.stickyToError(log.toString()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jo;
	}

	public JSONObject deleteVMMessage(JSONObject jo) {
		try {
			int userId = jo.getInt("userId");
			String uuid = jo.getString("uuid");
			String log = jo.getString("log");
			if (jo.getBoolean("result")) {
				messagePush.deleteRow(userId, uuid);
				messagePush.pushMessage(userId,
						MessageUtilities.stickyToSuccess(log.toString()));
			} else {
				messagePush.pushMessage(userId,
						MessageUtilities.stickyToError(log.toString()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jo;
	}

	public JSONObject adjustVMMessage(JSONObject jo) {
		try {
			int userId = jo.getInt("userId");
			int cpu = jo.getInt("cpu");
			int mem = jo.getInt("mem");
			String uuid = jo.getString("uuid");
			String content = jo.getString("content");
			String conid = jo.getString("conid");
			if (jo.getBoolean("result")) {
				messagePush.pushMessageClose(userId, content, conid);
				messagePush.editRowCpuMem(userId, uuid,
						String.valueOf(cpu), String.valueOf(mem));
				messagePush.pushMessage(userId,
						MessageUtilities.stickyToSuccess("配置修改成功"));
			} else {
				messagePush.pushMessageClose(userId, content, conid);
				messagePush.pushMessage(userId,
						MessageUtilities.stickyToError("配置修改失败"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jo;
	}

	public JSONObject defaultMessage(JSONObject jo) {
		try {
			int userId = jo.getInt("userId");
			String log = jo.getString("log");
			if (jo.getBoolean("result")) {
				messagePush.pushMessage(userId,
						MessageUtilities.stickyToSuccess(log.toString()));
			} else {
				messagePush.pushMessage(userId,
						MessageUtilities.stickyToError(log.toString()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jo;
	}

	public JSONObject createVolume(JSONObject jo) {
		try {
			int userId = jo.getInt("userId");
			String log = jo.getString("log");
			String volumeUuid = jo.getString("uuid");
			if (jo.getBoolean("result")) {
				messagePush.editRowStatus(userId, volumeUuid,
						"running", "可用");
				messagePush.pushMessage(userId,
						MessageUtilities.stickyToSuccess(log.toString()));
			} else {
				messagePush.deleteRow(userId, volumeUuid);
				messagePush.pushMessage(userId,
						MessageUtilities.stickyToError(log.toString()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jo;
	}

	public JSONObject deleteVolume(JSONObject jo) {
		try {
			int userId = jo.getInt("userId");
			String log = jo.getString("log");
			String volumeUuid = jo.getString("uuid");
			if (jo.getBoolean("result")) {
				messagePush.deleteRow(userId, volumeUuid);
				messagePush.pushMessage(userId,
						MessageUtilities.stickyToSuccess(log.toString()));
			} else {
				messagePush.editRowStatus(userId, volumeUuid,
						"running", "可用");
				messagePush.pushMessage(userId,
						MessageUtilities.stickyToError(log.toString()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jo;
	}

	public JSONObject bindVolume(JSONObject jo) {
		try {
			int userId = jo.getInt("userId");
			String log = jo.getString("log");
			String volumeUuid = jo.getString("volumeUuid");
			if (jo.getBoolean("result")) {
				messagePush.editRowStatus(userId, volumeUuid,
						"using", "使用中");
				messagePush.editRowStatusForBindVolume(userId,
						volumeUuid, jo.getString("vmUuid"),
						jo.getString("vmName"));
				messagePush.pushMessage(userId,
						MessageUtilities.stickyToSuccess(log.toString()));
			} else {
				messagePush.editRowStatus(userId, volumeUuid,
						"running", "可用");
				messagePush.pushMessage(userId,
						MessageUtilities.stickyToError(log.toString()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jo;
	}

	public JSONObject unbindVolume(JSONObject jo) {
		try {
			int userId = jo.getInt("userId");
			String log = jo.getString("log");
			String volumeUuid = jo.getString("volumeUuid");
			if (jo.getBoolean("result")) {
				messagePush.editRowStatus(userId, volumeUuid,
						"running", "可用");
				messagePush.editRowStatusForUnbindVolume(userId,
						volumeUuid);
				messagePush.pushMessage(userId,
						MessageUtilities.stickyToSuccess(log.toString()));
			} else {
				messagePush.editRowStatus(userId, volumeUuid,
						"using", "使用中");
				messagePush.pushMessage(userId,
						MessageUtilities.stickyToError(log.toString()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jo;
	}
	
	public JSONObject createGroup(JSONObject jo){
		int userId = jo.getInt("userId");
		if (jo.getBoolean("result")) {
			messagePush.pushMessage(userId, MessageUtilities.stickyToSuccess("创建分组成功"));
		}else {
			messagePush.pushMessage(userId, MessageUtilities.stickyToError("创建分组失败"));
		}
		return jo;
	}
	
	public JSONObject modifyGroup(JSONObject jo){
		int userId = jo.getInt("userId");
		if (jo.getBoolean("result")) {
			messagePush.pushMessage(userId, MessageUtilities.stickyToSuccess("修改分组成功"));
		}else {
			messagePush.pushMessage(userId, MessageUtilities.stickyToError("修改分组失败"));
		}
		return jo;
	}
	
	public JSONObject deleteGroup(JSONObject jo){
		int userId = jo.getInt("userId");
		if (jo.getBoolean("result")) {
			messagePush.pushMessage(userId, MessageUtilities.stickyToSuccess("删除分组成功"));
		}else {
			messagePush.pushMessage(userId, MessageUtilities.stickyToError("删除分组失败"));
		}
		return jo;
	}
	
	public JSONObject bindGroup(JSONObject jo){
		int userId = jo.getInt("userId");
		if (jo.getBoolean("result")) {
			messagePush.pushMessage(userId, MessageUtilities.stickyToSuccess("绑定分组成功"));
		}else {
			messagePush.pushMessage(userId, MessageUtilities.stickyToError("绑定分组失败"));
		}
		return jo;
	}
	
	public JSONObject unbindGroup(JSONObject jo){
		int userId = jo.getInt("userId");
		if (jo.getBoolean("result")) {
			messagePush.pushMessage(userId, MessageUtilities.stickyToSuccess("解除分组成功"));
		}else {
			messagePush.pushMessage(userId, MessageUtilities.stickyToError("解除分组失败"));
		}
		return jo;
	}
	
	public JSONObject createAlarm(JSONObject jo){
		int userId = jo.getInt("userId");
		if (jo.getBoolean("result")) {
			messagePush.pushMessage(userId, MessageUtilities.stickyToSuccess("创建警告成功"));
		}else{
			messagePush.pushMessage(userId, MessageUtilities.stickyToError("创建警告失败"));
		}
		return jo;
	}
	
	public JSONObject createAlarmRule(JSONObject jo){
		int userId = jo.getInt("userId");
		if (jo.getBoolean("result")) {
			messagePush.pushMessage(userId, MessageUtilities.stickyToSuccess("添加警告策略成功"));
		}else{
			messagePush.pushMessage(userId, MessageUtilities.stickyToError("添加警告策略失败"));
		}
		return jo;
	}
	
	public JSONObject modifyAlarm(JSONObject jo){
		int userId = jo.getInt("userId");
		if (jo.getBoolean("result")) {
			messagePush.pushMessage(userId, MessageUtilities.stickyToSuccess("修改警告成功"));
		}else {
			messagePush.pushMessage(userId, MessageUtilities.stickyToError("修改警告失败"));
		}
		return jo;
	}
	
	public JSONObject deleteAlarm(JSONObject jo){
		int userId = jo.getInt("userId");
		if (jo.getBoolean("result")) {
			messagePush.pushMessage(userId, MessageUtilities.stickyToSuccess("删除警告成功"));
		}else {
			messagePush.pushMessage(userId, MessageUtilities.stickyToError("删除警告失败"));
		}
		return jo;
	}
	
	public JSONObject bindAlarm(JSONObject jo){
		int userId = jo.getInt("userId");
		if (jo.getBoolean("result")) {
			messagePush.pushMessage(userId, MessageUtilities.stickyToSuccess("绑定警告成功"));
		}else {
			messagePush.pushMessage(userId, MessageUtilities.stickyToError("绑定警告失败"));
		}
		return jo;
	}
	
	public JSONObject unbindAlarm(JSONObject jo){
		int userId = jo.getInt("userId");
		if (jo.getBoolean("result")) {
			messagePush.pushMessage(userId, MessageUtilities.stickyToSuccess("解除警告成功"));
		}else {
			messagePush.pushMessage(userId, MessageUtilities.stickyToError("解除警告失败"));
		}
		return jo;
	}
	
	public JSONObject modifyRule(JSONObject jo){
		int userId = jo.getInt("userId");
		if (jo.getBoolean("result")) {
			messagePush.pushMessage(userId, MessageUtilities.stickyToSuccess("修改警告规则成功"));
		}else {
			messagePush.pushMessage(userId, MessageUtilities.stickyToError("修改警告规则失败"));
		}
		return jo;
	}
	
	public JSONObject deleteRule(JSONObject jo){
		int userId = jo.getInt("userId");
		if (jo.getBoolean("result")) {
			messagePush.pushMessage(userId, MessageUtilities.stickyToSuccess("删除警告规则成功"));
		}else {
			messagePush.pushMessage(userId, MessageUtilities.stickyToError("删除警告规则失败"));
		}
		return jo;
	}
	
	public JSONObject deleteLog(JSONObject jo){
		int userId = jo.getInt("userId");
		if (jo.getBoolean("result")) {
			messagePush.pushMessage(userId, MessageUtilities.stickyToSuccess("删除日志成功"));
		}else {
			messagePush.pushMessage(userId, MessageUtilities.stickyToError("删除日志失败"));
		}
		return jo;
	}
	
	public JSONObject exportLog(JSONObject jo){
		int userId = jo.getInt("userId");
		if (jo.getBoolean("result")) {
			messagePush.pushMessage(userId, MessageUtilities.stickyToSuccess("导出日志成功"));
		}else {
			messagePush.pushMessage(userId, MessageUtilities.stickyToError("导出日志失败"));
		}
		return jo;
	}
	

}
