package com.oncecloud.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.oncecloud.dao.AlarmDAO;
import com.oncecloud.dao.AlarmRuleDAO;
import com.oncecloud.dao.VMDAO;
import com.oncecloud.entity.Alarm;
import com.oncecloud.entity.AlarmRule;
import com.oncecloud.entity.OCVM;
import com.oncecloud.model.AlarmRuleConstant;
import com.oncecloud.model.AlarmRuleConstant.RuleName;
import com.oncecloud.model.AlarmRuleConstant.RuleTerm;
import com.oncecloud.service.AlarmService;
import com.oncecloud.util.Utilities;

@Component("AlarmService")
public class AlarmServiceImpl implements AlarmService{
	
	@Resource
	private AlarmDAO alarmDAO;
	@Resource
	private AlarmRuleDAO alarmRuleDAO;
	@Resource
	private VMDAO vmDAO;
	
	public boolean saveAlarm(String alarmUuid, String alarmName,
			int userId, JSONArray alarmRuleList){
		boolean result = false;
		try{
			result = alarmDAO.addAlarm(alarmUuid, alarmName, userId);
			if (alarmRuleList.length() > 0) {
				for (int i=0; i<alarmRuleList.length(); i++){
					result &= createRule((JSONObject)alarmRuleList.get(i),alarmUuid);
				}
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	private boolean createRule(JSONObject alarmRule,String ruleAlarmUuid){
		String ruleId = UUID.randomUUID().toString();
		RuleName ruleName = AlarmRuleConstant.RuleName.values()[alarmRule.getInt("ruleName")];
		String alarmUuid = ruleAlarmUuid;
		RuleTerm ruleTerm = AlarmRuleConstant.RuleTerm.values()[alarmRule.getInt("ruleTerm")];
		Integer ruleThreshold = alarmRule.getInt("ruleThreshold");
		boolean result = false;
		try{
			result = alarmRuleDAO.addRule(ruleId, ruleName, alarmUuid,
					ruleTerm, ruleThreshold);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 用户删除一条告警策略及对应的全部规则
	 */
	public boolean destoryAlarm(String alarmUuid){
		boolean result = false;
		try{
			result = vmDAO.isNotExistAlarm(alarmUuid);
			if (result) {
				Alarm alarm = alarmDAO.getAlarm(alarmUuid);
				result = alarmDAO.destoryAlarm(alarm);
				result &= alarmRuleDAO.removeRules(alarmUuid);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 将告警策略绑定到主机
	 */
	public boolean addResourceToAlarm(int userId, String rsuuidStr,
			String alarmUuid) {
		boolean result = false;
		try {
			JSONArray jArray = new JSONArray(rsuuidStr);
			for (int i = 0; i < jArray.length(); i++) {
				String rsUuid = jArray.getString(i);
				rsUuid = rsUuid.substring(0, rsUuid.length() - 1);
				vmDAO.updateAlarm(rsUuid, alarmUuid);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		}
		return result;
	}
	
	/**
	 * 显示告警策略列表
	 */
	public JSONArray getAlarmList(int userId, String alarmName,int page, int limit){
		JSONArray ja = new JSONArray();
		int totalNum = alarmDAO.countAlarmList(userId, alarmName);
		ja.put(totalNum);
		List<Alarm> alarmList = new ArrayList<Alarm>();
		alarmList = alarmDAO.getAlarmList(userId, alarmName, page, limit);
		if (alarmList != null){
			for (int i = 0; i<alarmList.size(); i++){
				JSONObject jo = new JSONObject();
				Alarm alarm = alarmList.get(i);
				String alarmUuid = alarm.getAlarmUuid();
				int alarmCount = vmDAO.getVMsOfAlarm(userId, alarmUuid).size();
				jo.put("alarmCount", alarmCount);
				jo.put("alarmUuid",alarmUuid);
				jo.put("alarmName", Utilities.encodeText(alarm.getAlarmName()));
				jo.put("alarmTime", Utilities.encodeText(alarm.getAlarmTime()
						.toString()));
				ja.put(jo);
			}
		}
		return ja;
	}
	/**
	 * 告警策略可绑定的主机列表
	 */
	@SuppressWarnings("rawtypes")
	public JSONArray getAlarmVMList(String alarmUuid, int page, int limit,
			String search, Integer userId){
		List list = null;
		int totalNum = 0;
		JSONArray ja = new JSONArray();
		list = vmDAO.getOnePageVMsWithoutAlarm(page, limit,search, userId);
		totalNum = vmDAO.countVMsWithoutAlarm(search, userId);
		ja.put(totalNum);
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				JSONObject jo = new JSONObject();
				OCVM ocvm = (OCVM) list.get(i);
				jo.put("vmName",Utilities.encodeText(ocvm.getVmName()));
				jo.put("vmUuid", ocvm.getVmUuid() + "i");
				jo.put("vmUuidshow", "i-"+ ocvm.getVmUuid().substring(0, 8));
				ja.put(jo);
			}
		}
		return ja;
	}
	/**
	 * 显示告警策略的详细内容
	 */
	public JSONObject getAlarm(int userId, String alarmUuid){
		JSONObject jo = new JSONObject();
		Alarm alarm = alarmDAO.getAlarm(alarmUuid);
		jo.put("alarmName",alarm.getAlarmName());
		jo.put("alarmDesc", (null == alarm.getAlarmDesc()) ? "&nbsp;"
				: Utilities.encodeText(alarm.getAlarmDesc()));
		String timeUsed = Utilities.encodeText(alarm.getAlarmTime().toString());
		jo.put("alarmTime", timeUsed);
		JSONArray ja = new JSONArray();
		for (OCVM ocvm : vmDAO.getVMsOfAlarm(userId, alarmUuid)) {
			JSONObject js = new JSONObject();
			js.put("rsName", Utilities.encodeText(ocvm.getVmName()));
			js.put("rsUuid", ocvm.getVmUuid());
			ja.put(js);
		} 
		jo.put("alarmVM", ja);
		return jo;
	}
	/**
	 * 修改策略的属性（名称、描述）
	 */
	public boolean modifyAlarm(String alarmUuid, String alarmName, String alarmDesc){
		boolean result = false;
		try{
			Alarm alarm = alarmDAO.getAlarm(alarmUuid);
			alarm.setAlarmName(alarmName);
			alarm.setAlarmDesc(alarmDesc);
			result = alarmDAO.updateAlarm(alarm);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 更新绑定关系
	 */
	public boolean removeRS(String rsUuid){
		boolean result = false;
		try{
			result = vmDAO.updateAlarm(rsUuid, null);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 显示告警策略对应的规则列表
	 */
	public JSONArray getRuleList(String alarmUuid, int page, int limit){
		JSONArray ja = new JSONArray();
		int totalNum = alarmRuleDAO.countAlarmRulesList(alarmUuid);
		ja.put(totalNum);
		List<AlarmRule> ruleList = new ArrayList<AlarmRule>();
		ruleList = alarmRuleDAO.getRules(alarmUuid);
		if (ruleList != null){
			for (int i = 0; i<ruleList.size(); i++){
				JSONObject jo = new JSONObject();
				AlarmRule alarmRule = ruleList.get(i);
				jo.put("ruleCount", totalNum);
				jo.put("ruleId",alarmRule.getRuleId() );
				jo.put("ruleOrdinal", alarmRule.getRuleName().ordinal());
				jo.put("ruleName", Utilities.encodeText(alarmRule.getRuleName().name()));
				jo.put("ruleTerm", Utilities.encodeText(alarmRule.getRuleTerm().name()));
				jo.put("ruleThreshold", alarmRule.getRuleThreshold());
				ja.put(jo);
			}
		}
		return ja;
	}
	
	/**
	 * 新建规则
	 */
	public boolean createAlarmRule(String alarmUuid, String ruleId, RuleName ruleName,
			RuleTerm ruleTerm,Integer ruleThreshold){
		boolean result = false;
		try{
			result = alarmRuleDAO.addRule(ruleId, ruleName, alarmUuid,
					ruleTerm, ruleThreshold);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 修改规则
	 */
	public boolean modifyRule(String ruleId, RuleName ruleName, RuleTerm ruleTerm,
			Integer ruleThreshold){
		boolean result = false;
		try{
			AlarmRule alarmRule = alarmRuleDAO.getAlarmRule(ruleId);
			alarmRule.setRuleName(ruleName);
			alarmRule.setRuleTerm(ruleTerm);
			alarmRule.setRuleThreshold(ruleThreshold);
			result = alarmRuleDAO.updateRule(alarmRule);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 删除规则
	 */
	public  boolean deleteRule(String ruleId){
		boolean result = false;
		try{
			AlarmRule alarmRule = alarmRuleDAO.getAlarmRule(ruleId);
			result = alarmRuleDAO.removeAlarmRule(alarmRule);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
}
