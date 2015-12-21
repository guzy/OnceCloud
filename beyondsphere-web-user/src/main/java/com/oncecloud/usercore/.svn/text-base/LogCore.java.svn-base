package com.beyondsphere.usercore;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.beyondsphere.entity.OCLog;
import com.beyondsphere.helper.LogHelper;
import com.beyondsphere.service.LogBackupService;
import com.beyondsphere.service.LogService;
import com.beyondsphere.util.Utilities;
import com.beyondsphere.model.LogConstant;
import com.beyondsphere.model.LogConstant.logObject;

@Component("LogUserCore")
public class LogCore {
	
	private static Map<String,LogHelper> logMaps=new HashMap<String,LogHelper>();
	
	@Resource
	private LogService logService;
	@Resource
	private LogBackupService logBackupService;
	@Resource
	private LogHelper logHelper;
	
	private OCLog getLog(JSONObject jo, logObject type, int operate,
			String infoName) {
		OCLog log = new OCLog();
		int userId = jo.getInt("userId");
		Date startTime = (Date) jo.get("startTime");
		int elapse = jo.getInt("elapse");
		JSONArray infoArray = new JSONArray();
		infoArray.put(Utilities.createLogInfo(type.toString(), infoName));
		if (jo.getBoolean("result")) {
			log = logService.addLog(userId, type.ordinal(),
					operate, LogConstant.logStatus.Succeed.ordinal(),
					infoArray.toString(), startTime, elapse);
		} else {
			log = logService.addLog(userId, type.ordinal(),
					operate, LogConstant.logStatus.Failed.ordinal(),
					infoArray.toString(), startTime, elapse);
		}
		return log;
	}

	
	public JSONObject createVMLog(JSONObject jo) {
		OCLog log = new OCLog();
		JSONArray infoArray = new JSONArray();
		try {
			infoArray.put(Utilities.createLogInfo(
					LogConstant.logObject.Instance.toString(),
					"i-" + jo.getString("uuid").substring(0, 8)));
			infoArray.put(Utilities.createLogInfo("配置", jo.getInt("cpu")
					+ " 核， " + jo.getDouble("memory") + " GB"));
			infoArray.put(Utilities.createLogInfo(
					LogConstant.logObject.Images.toString(), "image-"
							+ jo.getString("tplUuid").substring(0, 8)));

			if (jo.getBoolean("result")) {
				log = logService.addLog(jo.getInt("userId"),
						LogConstant.logObject.Instance.ordinal(),
						LogConstant.logAction.Create.ordinal(),
						LogConstant.logStatus.Succeed.ordinal(),
						infoArray.toString(), (Date) jo.get("start"),
						jo.getInt("elapse"));
			} else {
				infoArray.put(Utilities.createLogInfo("原因",
						jo.getString("error")));
				log = logService.addLog(jo.getInt("userId"),
						LogConstant.logObject.Instance.ordinal(),
						LogConstant.logAction.Create.ordinal(),
						LogConstant.logStatus.Failed.ordinal(),
						infoArray.toString(), (Date) jo.get("start"),
						jo.getInt("elapse"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		jo.put("log", log.toString());
		return jo;
	}
	
	public JSONObject updateVmLog(JSONObject jo){
		try {
			jo.put("log", this.getLog(jo, LogConstant.logObject.Instance, 
									LogConstant.logAction.Update.ordinal(), 
									"i-" + jo.getString("uuid").substring(0, 8)).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jo;
	}

	public JSONObject shutdownVMLog(JSONObject jo) {
		try {
			jo.put("log",
					this.getLog(jo, LogConstant.logObject.Instance,
							LogConstant.logAction.ShutDown.ordinal(),
							"i-" + jo.getString("uuid").substring(0, 8))
							.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jo;
	}

	public JSONObject startVMLog(JSONObject jo) {
		try {
			jo.put("log",
					this.getLog(jo, LogConstant.logObject.Instance,
							LogConstant.logAction.Start.ordinal(),
							"i-" + jo.getString("uuid").substring(0, 8))
							.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jo;
	}

	public JSONObject restartVMLog(JSONObject jo) {
		try {
			jo.put("log",
					this.getLog(jo, LogConstant.logObject.Instance,
							LogConstant.logAction.Restart.ordinal(),
							"i-" + jo.getString("uuid").substring(0, 8))
							.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jo;
	}

	public JSONObject deleteVMLog(JSONObject jo) {
		try {
			jo.put("log",
					this.getLog(jo, LogConstant.logObject.Instance,
							LogConstant.logAction.Destroy.ordinal(),
							"i-" + jo.getString("uuid").substring(0, 8))
							.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jo;
	}

	public JSONObject createImageLog(JSONObject jo) {
		try {
			jo.put("log",
					this.getLog(jo, LogConstant.logObject.Images,
							LogConstant.logAction.Create.ordinal(),
							jo.getString("imageName")).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jo;
	}

	public JSONObject deleteImageLog(JSONObject jo) {
		try {
			jo.put("log",
					this.getLog(jo, LogConstant.logObject.Images,
							LogConstant.logAction.Delete.ordinal(),
							jo.getString("imageName")).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jo;
	}

	private OCLog getSnapshotLog(JSONObject jo, int operate, boolean flag,
			String info) {
		OCLog log = new OCLog();
		int userId = jo.getInt("userId");
		Date startTime = (Date) jo.get("startTime");
		int elapse = jo.getInt("elapse");
		JSONArray infoArray = new JSONArray();
		infoArray.put(Utilities.createLogInfo(
				LogConstant.logObject.Snapshot.toString(),
				"bk-" + info.substring(0, 8)));
		if (jo.getString("resourceType").equals("instance")) {
			if (flag) {
				infoArray
						.put(Utilities.createLogInfo(
								LogConstant.logObject.Instance.toString(), "i-"
										+ jo.getString("resourceUuid")
												.substring(0, 8)));
			} else {
				infoArray.put(Utilities.createLogInfo(
						LogConstant.logObject.Instance.toString(), "不存在"));
			}
		} else {
			if (flag) {
				infoArray
						.put(Utilities.createLogInfo(
								LogConstant.logObject.Volume.toString(), "vol-"
										+ jo.getString("resourceUuid")
												.substring(0, 8)));
			} else {
				infoArray.put(Utilities.createLogInfo(
						LogConstant.logObject.Volume.toString(), "不存在"));
			}
		}
		if (jo.getBoolean("result")) {
			log = logService.addLog(userId,
					LogConstant.logObject.Snapshot.ordinal(), operate,
					LogConstant.logStatus.Succeed.ordinal(), infoArray.toString(),
					startTime, elapse);
		} else {
			log = logService.addLog(userId,
					LogConstant.logObject.Snapshot.ordinal(), operate,
					LogConstant.logStatus.Failed.ordinal(), infoArray.toString(),
					startTime, elapse);
		}
		return log;
	}

	public JSONObject createSnapshot(JSONObject jo) {
		try {
			jo.put("log",
					this.getSnapshotLog(jo, LogConstant.logAction.Create.ordinal(),
							true, jo.getString("uuid")).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jo;
	}

	public JSONObject deleteSnapshotSeries(JSONObject jo) {
		try {
			jo.put("log",
					this.getSnapshotLog(jo, LogConstant.logAction.Delete.ordinal(),
							true, jo.getString("snapshotId")).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jo;
	}

	public JSONObject rollbackSnapshot(JSONObject jo) {
		try {
			jo.put("log",
					this.getSnapshotLog(jo, LogConstant.logAction.Rollback.ordinal(),
							jo.getBoolean("exist"), jo.getString("snapshotId"))
							.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jo;
	}

	public JSONObject destorySnapshot(JSONObject jo) {
		try {
			jo.put("log",
					this.getSnapshotLog(jo, LogConstant.logAction.Delete.ordinal(),
							true, jo.getString("resourceUuid")).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jo;
	}

	public JSONObject createVolume(JSONObject jo) {
		try {
			jo.put("log",
					this.getLog(jo, LogConstant.logObject.Volume,
							LogConstant.logAction.Create.ordinal(),
							"vol-" + jo.getString("uuid").substring(0, 8))
							.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jo;
	}

	public JSONObject deleteVolume(JSONObject jo) {
		try {
			jo.put("log",
					this.getLog(jo, LogConstant.logObject.Volume,
							LogConstant.logAction.Delete.ordinal(),
							"vol-" + jo.getString("uuid").substring(0, 8))
							.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jo;
	}

	public JSONObject bindVolume(JSONObject jo) {
		try {
			jo.put("log",
					this.getLog(jo, LogConstant.logObject.Volume,
							LogConstant.logAction.Binding.ordinal(),
							"vol-" + jo.getString("volumeUuid").substring(0, 8))
							.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jo;
	}

	public JSONObject unbindVolume(JSONObject jo) {
		try {
			jo.put("log",
					this.getLog(jo, LogConstant.logObject.Volume,
							LogConstant.logAction.Unload.ordinal(),
							"vol-" + jo.getString("volumeUuid").substring(0, 8))
							.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jo;
	}
	
	public JSONObject createAlarmLog(JSONObject jo){
		try {
			jo.put("log", this.getLog(jo, LogConstant.logObject.AlarmLog, 
						LogConstant.logAction.Create.ordinal(),
						"al-" + jo.getString("alarmUuid").substring(0, 8))
					.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jo;
	}
	
	public JSONObject createAlarmRuleLog(JSONObject jo){
		try {
			jo.put("log", this.getLog(jo, LogConstant.logObject.AlarmRuleLog, 
						LogConstant.logAction.Create.ordinal(),
						"al-" + jo.getString("ruleId").substring(0, 8))
					.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jo;
	}
	
	public JSONObject modifyAlarmLog(JSONObject jo){
		try {
			jo.put("log", this.getLog(jo, LogConstant.logObject.AlarmLog, 
						LogConstant.logAction.Adjust.ordinal(),
						"al-" + jo.getString("alarmUuid").substring(0, 8))
					.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jo;
	}
	
	public JSONObject deleteAlarmLog(JSONObject jo){
		try {
			jo.put("log", this.getLog(jo, LogConstant.logObject.AlarmLog, 
						LogConstant.logAction.Delete.ordinal(),
						"al-" + jo.getString("alarmUuid").substring(0, 8))
					.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jo;
	}
	
	public JSONObject bindAlarmLog(JSONObject jo){
		try {
			jo.put("log", this.getLog(jo, LogConstant.logObject.AlarmLog, 
						LogConstant.logAction.Binding.ordinal(),
						"al-" + jo.getString("alarmUuid").substring(0, 8))
					.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jo;
	}
	
	public JSONObject unbindAlarmLog(JSONObject jo){
		try {
			jo.put("log", this.getLog(jo, LogConstant.logObject.AlarmLog, 
						LogConstant.logAction.Unbundling.ordinal(),
						"al-" + jo.getString("alarmUuid").substring(0, 8))
					.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jo;
	}
	
	public JSONObject modifyRuleLog(JSONObject jo){
		try {
			jo.put("log", this.getLog(jo, LogConstant.logObject.AlarmRuleLog, 
						LogConstant.logAction.Adjust.ordinal(),
						"al-" + jo.getString("ruleId").substring(0, 8))
					.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jo;
	}
	
	public JSONObject deleteRuleLog(JSONObject jo){
		try {
			jo.put("log", this.getLog(jo, LogConstant.logObject.AlarmRuleLog, 
						LogConstant.logAction.Delete.ordinal(),
						"al-" + jo.getString("ruleId").substring(0, 8))
					.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jo;
	}
	
	public JSONObject deleteLog(JSONObject jo){
		try {
			jo.put("log", this.getLog(jo, LogConstant.logObject.Log, 
						LogConstant.logAction.Delete.ordinal(),
						"l-" + String.valueOf(jo.getInt("logId")))
					.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jo;
	}
	
	public JSONObject exportLog(JSONObject jo){
		try {
			jo.put("log", this.getLog(jo, LogConstant.logObject.Log, 
						LogConstant.logAction.Export.ordinal(),
						""));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jo;
	}
	
	public JSONObject checkLogin(JSONObject jo){
		try {
			jo.put("log", this.getLog(jo, LogConstant.logObject.User, 
						LogConstant.logAction.Login.ordinal(),
						jo.getString("userName")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jo;
	}
	
	public JSONObject Logout(JSONObject jo){
		try {
			jo.put("log", this.getLog(jo, LogConstant.logObject.User, 
						LogConstant.logAction.Exit.ordinal(),
						"u-"+String.valueOf(jo.getInt("userId"))));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jo;
	}
	
	public void trsLogToBackup(int days){
		stopTrsLog();
		logHelper.setDays(0-days);
		logHelper.setTrsFlg(true);
		logMaps.put("log", logHelper);
		Thread thread = new Thread(logHelper);
		thread.start();
	}
	
	public void stopTrsLog() {
		LogHelper logHelper = logMaps.get("log");
		if (logHelper != null) {
			logHelper.setTrsFlg(false);
			logMaps.remove("log");
		}
	}
	
}
