package com.beyondsphere.usercore;

import java.util.Date;

import javax.annotation.Resource;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.beyondsphere.service.AlarmService;
import com.beyondsphere.util.Utilities;
import com.beyondsphere.model.AlarmRuleConstant.RuleName;
import com.beyondsphere.model.AlarmRuleConstant.RuleTerm;

@Component
public class AlarmCore {

	@Resource
	private AlarmService alarmService;

	public JSONObject saveAlarm(JSONObject jo) {
		Date startTime = new Date();
		try{
			jo.put("result",
					alarmService.saveAlarm(
							jo.getString("alarmUuid"), 
							jo.getString("alarmName"),
							jo.getInt("userId"),
							jo.getJSONArray("alarmRules")
							 )
				  );
		}catch(Exception e){
			e.printStackTrace();
			jo.put("result", false);
		}finally{
			jo.put("startTime", startTime);
			Date endTime = new Date();
			jo.put("elapse",
					Utilities.timeElapse(startTime, endTime));
		}
		return jo;
	}
	
	public JSONObject destoryAlarm(JSONObject jo) {
		Date startTime = new Date();
		try{
			jo.put("result",
					alarmService.destoryAlarm(
							jo.getString("alarmUuid")
							 )
				  );
		}catch(Exception e){
			e.printStackTrace();
			jo.put("result", false);
		}
		finally{
			jo.put("startTime", startTime);
			Date endTime = new Date();
			jo.put("elapse",
					Utilities.timeElapse(startTime, endTime));
		}
		return jo;
	}
	
	public JSONObject deleteRule(JSONObject jo) {
		Date startTime = new Date();
		try{
			jo.put("result",
					alarmService.deleteRule(
							jo.getString("ruleId")
							 )
				  );
		}catch(Exception e){
			e.printStackTrace();
			jo.put("result", false);
		}finally{
			jo.put("startTime", startTime);
			Date endTime = new Date();
			jo.put("elapse",
					Utilities.timeElapse(startTime, endTime));
		}
		return jo;
	}
	
	public JSONObject modifyAlarm(JSONObject jo) {
		Date startTime = new Date();
		try{
			jo.put("result",
					alarmService.modifyAlarm(
							jo.getString("modifyUuid"), 
							jo.getString("modifyName"),
							jo.getString("modifyDesc")
							 )
				  );
		
		}catch(Exception e){
			e.printStackTrace();
			jo.put("result", false);
		}finally{
			jo.put("startTime", startTime);
			Date endTime = new Date();
			jo.put("elapse",
					Utilities.timeElapse(startTime, endTime));
		}
		return jo;
	}

	public JSONObject modifyRule(JSONObject jo){
		Date startTime = new Date();
		try{
			jo.put("result",
					alarmService.modifyRule(
							jo.getString("ruleId"),
							RuleName.values()[Integer.parseInt(jo.getString("ruleName"))],
							RuleTerm.values()[Integer.parseInt(jo.getString("ruleTerm"))],
							jo.getInt("ruleThreshold")
							)
				  );
		
		}catch(Exception e){
			e.printStackTrace();
			jo.put("result", false);
		}finally{
			jo.put("startTime", startTime);
			Date endTime = new Date();
			jo.put("elapse",
					Utilities.timeElapse(startTime, endTime));
		}
		return jo;
	}
	
	public JSONObject createAlarmRule(JSONObject jo) {
		Date startTime = new Date();
		try{
			jo.put("result",
					alarmService.createAlarmRule(
							jo.getString("alarmUuid"),
							jo.getString("ruleId"),
							RuleName.values()[Integer.parseInt(jo.getString("ruleName"))],
							RuleTerm.values()[Integer.parseInt(jo.getString("ruleTerm"))],
							jo.getInt("ruleThreshold")
							 )
				  );
		}catch(Exception e){
			e.printStackTrace();
			jo.put("result", false);
		}finally{
			jo.put("startTime", startTime);
			Date endTime = new Date();
			jo.put("elapse",
					Utilities.timeElapse(startTime, endTime));
		}
		return jo;
	}
	
	public JSONObject bindAlarm(JSONObject jo){
		Date startTime = new Date();
		try{
			jo.put("result",
					alarmService.addResourceToAlarm(
							jo.getInt("userId"), 
							jo.getString("rsuuidStr"), 
							jo.getString("alarmUuid")
							)
				  );
		}catch(Exception e){
			e.printStackTrace();
			jo.put("result", false);
		}finally{
			jo.put("startTime", startTime);
			Date endTime = new Date();
			jo.put("elapse",
					Utilities.timeElapse(startTime, endTime));
		}
		return jo;
		}
	
	public JSONObject unBindAlarm(JSONObject jo){
		Date startTime = new Date();
		try{
			jo.put("result",
					alarmService.removeRS(jo.getString("rsUuid")));
		}catch(Exception e){
			e.printStackTrace();
			jo.put("result", false);
		}finally{
			jo.put("startTime", startTime);
			Date endTime = new Date();
			jo.put("elapse",
					Utilities.timeElapse(startTime, endTime));
		}
		return jo;
	}
}
