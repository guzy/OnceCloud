package com.beyondsphere.log;

import java.util.Date;

import javax.annotation.Resource;

import org.json.JSONArray;
import org.springframework.stereotype.Component;

import com.beyondsphere.entity.OCLog;
import com.beyondsphere.service.LogService;
import com.beyondsphere.util.TimeUtils;

@Component("LogRecord")
public class LogRecord {
	
	@Resource
	private LogService logService;
	
	public  OCLog addSuccessLog(Integer userId, Integer logRole, Integer logAction, 
			String logInfo, Date startTime, Date endTime) {
		return logService.addLog(userId, logRole, 
				logAction, LogStatus.SUCCESS, 
				logInfo, startTime, TimeUtils.timeElapse(startTime, endTime));
	}
	
	public OCLog addFailedLog(Integer userId, Integer logRole, Integer logAction, 
			String logInfo, Date startTime, Date endTime ) {
		return logService.addLog(userId, logRole, 
				logAction, LogStatus.FAILED, 
				logInfo, startTime, TimeUtils.timeElapse(startTime, endTime));
	}
	
	public JSONArray createLoginfos(String key, String value){
		JSONArray infoArray = new JSONArray();
		infoArray.put(TimeUtils.createLogInfo(key, value));
		return infoArray;
	}
	
}
