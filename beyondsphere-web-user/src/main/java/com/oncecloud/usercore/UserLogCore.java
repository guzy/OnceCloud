package com.oncecloud.usercore;

import java.util.Date;

import javax.annotation.Resource;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.oncecloud.service.LogService;
import com.oncecloud.util.Utilities;

@Component("UserLogCore")
public class UserLogCore {
	@Resource
	private LogService logService;
	
	public JSONObject deleteLog(JSONObject jo) {
		Date startTime = new Date();
		try {
			jo.put("result", logService.deleteLog(jo.getInt("logId")));
		} catch (JSONException e) {
			jo.put("result", false);
			e.printStackTrace();
		}finally{
			jo.put("startTime", startTime);
			Date endTime = new Date();
			jo.put("elapse",
					Utilities.timeElapse(startTime, endTime));
		}
		return jo;
	}	
	
	public JSONObject exportLog(JSONObject jo) {
		Date startTime = new Date();
		try {
			String filename = logService.logToFile(jo.getInt("userId")).getString("filename");
			if (filename!=null && !"".equals(filename)) {
				jo.put("result", true);
				jo.put("filename", filename);
			}
			
		} catch (JSONException e) {
			jo.put("result", false);
			e.printStackTrace();
		}finally{
			jo.put("startTime", startTime);
			Date endTime = new Date();
			jo.put("elapse",
					Utilities.timeElapse(startTime, endTime));
		}
		return jo;
	}	
}
