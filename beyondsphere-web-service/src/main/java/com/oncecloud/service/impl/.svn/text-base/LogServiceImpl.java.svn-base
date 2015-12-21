package com.beyondsphere.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.beyondsphere.constants.LogConstant;
import com.beyondsphere.dao.LogDAO;
import com.beyondsphere.dao.UserDAO;
import com.beyondsphere.entity.OCLog;
import com.beyondsphere.entity.User;
import com.beyondsphere.log.LogAction;
import com.beyondsphere.log.LogObject;
import com.beyondsphere.log.LogRecord;
import com.beyondsphere.log.LogRole;
import com.beyondsphere.message.MessageUtil;
import com.beyondsphere.service.LogService;
import com.beyondsphere.util.TimeUtils;
import com.beyondsphere.util.FileHandlerUtil;

@Component("LogService")
public class LogServiceImpl implements LogService {
	
	@Resource
	private LogDAO logDAO;
	@Resource
	private UserDAO userDAO;
	@Resource
	private LogRecord logRecord;
	@Resource
	private MessageUtil message;

	public JSONArray getLogList(int userId, int status, int start, int num,String search) {
		User userinfo=userDAO.getUser(userId);
		JSONArray logArray = new JSONArray();
		Date startTime = new Date();
		JSONArray infoArray = new JSONArray();
		infoArray.put(TimeUtils.createLogInfo(
				LogRole.USER, userinfo.getUserName()));
		
		List<OCLog> logList=new ArrayList<OCLog>(); 
		try {
			logList = logDAO.getLogList(userId, status, start, num,search);
			Date endTime = new Date();
			logRecord.addSuccessLog(userId, LogObject.LOG,LogAction.LOAD, infoArray.toString(), startTime, endTime); 
		} catch (Exception e) {
			Date endTime = new Date();
			OCLog log = logRecord.addFailedLog(userId, LogObject.LOG, 
					LogAction.LOAD, infoArray.toString(), startTime, endTime); 
			message.pushError(userId, log.toString());
			e.printStackTrace();
		}
		
		if (logList != null) {
			User user=null;
			for (OCLog log : logList) {
				JSONObject logObj = new JSONObject();
				logObj.put("logId", log.getLogId());
				logObj.put("logUID", log.getLogUID());
				user=new User();
				user=userDAO.getUser(log.getLogUID());
				
				logObj.put("logUName", TimeUtils.encodeText(user.getUserName()));
				int logObject = log.getLogObject();
				logObj.put("logObject", logObject);
				logObj.put("logObjectStr", TimeUtils
						.encodeText(LogConstant.logObject.values()[logObject]
								.toString()));
				int logAction = log.getLogAction();
				logObj.put("logActionStr", TimeUtils
						.encodeText(LogConstant.logAction.values()[logAction]
								.toString()));
				logObj.put("logStatus", log.getLogStatus());
				logObj.put("logInfo", log.getLogInfo());
				logObj.put("logTime", TimeUtils.formatTime(log.getLogTime()));
				logObj.put("logElapse", log.getLogElapse());
				logArray.put(logObj);
			}
		}
		return logArray;
	}

	public OCLog addLog(Integer logUID, Integer logObject, Integer logAction,
			Integer logStatus, String logInfo, Date logTime, Integer logElapse) {
		OCLog log = new OCLog();
		log.setLogUID(logUID);
		log.setLogObject(logObject);
		log.setLogAction(logAction);
		log.setLogStatus(logStatus);
		log.setLogInfo(logInfo);
		log.setLogTime(logTime);
		log.setLogElapse(logElapse);
		return logDAO.insertLog(log);
	}

	@Override
	public boolean deleteLog(int logId) {
		return logDAO.deleteLog(logId);
	}

	@Override
	public JSONObject logToFile(int userId) {
		String filename = FileHandlerUtil.exportFile(getLogs(userId, 4, 0, 10), "log.xls", "Excel");
		JSONObject jo = new JSONObject();
		jo.put("filename", filename);
		return jo;
	}

	@Override
	public boolean deleteLogByTime(Date logTime) {
		return logDAO.deleteLogByTime(logTime);
	}
	
	private List<OCLog> getLogs(int logUID, int logStatus, int start, int num) {
		return logDAO.getLogList(logUID, logStatus, start, num,"");
	}
}
