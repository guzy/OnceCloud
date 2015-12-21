/**
 * @author hty
 * @time 上午11:05:48
 * @date 2014年12月9日
 */
package com.oncecloud.controller.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oncecloud.entity.User;
import com.oncecloud.model.CreateAlarmModel;
import com.oncecloud.model.CreateAlarmRuleModel;
import com.oncecloud.model.ListModel;
import com.oncecloud.query.QueryList;
import com.oncecloud.utils.Dispatch;

@RequestMapping("/AlarmAction")
@Controller
public class AlarmAction {

	@Resource
	private QueryList queryList;
	@Resource
	private Dispatch dispatch;

	@RequestMapping(value = "/Create", method = { RequestMethod.POST })
	@ResponseBody
	public void create(HttpServletRequest request,
			CreateAlarmModel createAlarmModel) {
		User user = (User) request.getSession().getAttribute("user");
		JSONObject jo = new JSONObject();
		jo.put("Handler", "CreateAlarmHandler");
		JSONArray ja = new JSONArray();
		JSONObject params = new JSONObject();
		params.put("alarmUuid", createAlarmModel.getAlarmUuid());
		params.put("alarmName",createAlarmModel.getAlarmName());
		params.put("alarmRules", createAlarmModel.getAlarmRules());
		params.put("userId", user.getUserId());
		ja.put(0, params);
		jo.put("Params", ja);
		dispatch.post(jo);
	}
	
	@RequestMapping(value = "/CreateRule", method = { RequestMethod.POST })
	@ResponseBody
	public void createRule(HttpServletRequest request,
			CreateAlarmRuleModel createAlarmRuleModel) {
		User user = (User) request.getSession().getAttribute("user");
		JSONObject jo = new JSONObject();
		jo.put("Handler", "CreateAlarmRuleHandler");
		JSONArray ja = new JSONArray();
		JSONObject params = new JSONObject();
		params.put("userId", user.getUserId());
		params.put("alarmUuid", createAlarmRuleModel.getAlarmUuid());
		params.put("ruleId",createAlarmRuleModel.getRuleId());
		params.put("ruleName",createAlarmRuleModel.getRuleName());
		params.put("ruleTerm",createAlarmRuleModel.getRuleTerm());
		params.put("ruleThreshold", createAlarmRuleModel.getRuleThreshold());
		ja.put(0, params);
		jo.put("Params", ja);
		dispatch.post(jo);
	}
	
	@RequestMapping(value = "/AlarmList", method = { RequestMethod.GET })
	@ResponseBody
	public String alarmList(HttpServletRequest request, ListModel list) {
		User user = (User) request.getSession().getAttribute("user");
		JSONArray ja = queryList.queryAlarmList( user.getUserId(), list.getSearch(), list.getPage(),  list.getLimit());
		return ja.toString();
	}
	
	@RequestMapping(value = "/Destory", method = { RequestMethod.GET })
	@ResponseBody
	public String destory(HttpServletRequest request,@RequestParam String alarmUuid) {
		User user = (User) request.getSession().getAttribute("user");
		JSONObject jo = new JSONObject();
		jo.put("Handler", "DestoryAlarmHandler");
		JSONArray ja = new JSONArray();
		JSONObject params = new JSONObject();
		params.put("userId", user.getUserId());
		params.put("alarmUuid", alarmUuid);
		ja.put(0, params);
		jo.put("Params", ja);
		dispatch.post(jo);
		return ((JSONObject)((JSONArray)jo.get("Params")).get(0)).get("result").toString();
		
	}
	
	@RequestMapping(value = "/DeleteRule", method = { RequestMethod.GET })
	@ResponseBody
	public String deleteRule(HttpServletRequest request,@RequestParam String ruleId) {
		User user = (User) request.getSession().getAttribute("user");
		JSONObject jo = new JSONObject();
		jo.put("Handler", "DeleteRuleHandler");
		JSONArray ja = new JSONArray();
		JSONObject params = new JSONObject();
		params.put("userId", user.getUserId());
		params.put("ruleId", ruleId);
		ja.put(0, params);
		jo.put("Params", ja);
		dispatch.post(jo);
		return ((JSONObject)((JSONArray)jo.get("Params")).get(0)).toString();
	}
	
	@RequestMapping(value = "/GetAlarm", method = { RequestMethod.GET })
	@ResponseBody
	public String getAlarm(HttpServletRequest request,@RequestParam String alarmUuid) {
		User user = (User) request.getSession().getAttribute("user");
		JSONObject jo = queryList.queryAlarmBasicList(user.getUserId(), alarmUuid);
		return jo.toString();
	}
	
	@RequestMapping(value = "/RuleList", method = { RequestMethod.GET })
	@ResponseBody
	public String ruleList(HttpServletRequest request, @RequestParam String alarmUuid,
			@RequestParam Integer page, @RequestParam Integer limit) {
		JSONArray ja = queryList.queryRuleList(alarmUuid, page, limit);
		return ja.toString();
	}
	
	@RequestMapping(value = "/ModifyAlarm", method = { RequestMethod.POST })
	@ResponseBody
	public String modifyAlarm(HttpServletRequest request,@RequestParam String modifyUuid,
			@RequestParam String modifyName, @RequestParam String modifyDesc) {
		User user = (User) request.getSession().getAttribute("user");
		JSONObject jo = new JSONObject();
		jo.put("Handler", "ModifyAlarmHandler");
		JSONArray ja = new JSONArray();
		JSONObject params = new JSONObject();
		modifyDesc = modifyDesc.trim();
		if (modifyDesc.equals("")) {
			modifyDesc = null;
		} else if (modifyDesc.length() > 80) {
			modifyDesc = modifyDesc.substring(0, 79);
		}
		params.put("userId", user.getUserId());
		params.put("modifyUuid", modifyUuid);
		params.put("modifyName", modifyName);
		params.put("modifyDesc", modifyDesc);
		ja.put(0, params);
		jo.put("Params", ja);
		dispatch.post(jo);
		return ((JSONObject)((JSONArray)jo.get("Params")).get(0)).get("result").toString();
	}
	
	@RequestMapping(value = "/ModifyRule", method = { RequestMethod.POST })
	@ResponseBody
	public void modifyRule(HttpServletRequest request,
			@RequestParam String ruleName, @RequestParam String ruleTerm,
			@RequestParam int ruleThreshold, @RequestParam String ruleId) {
		User user = (User) request.getSession().getAttribute("user");
		JSONObject jo = new JSONObject();
		jo.put("Handler", "ModifyRuleHandler");
		JSONArray ja = new JSONArray();
		JSONObject params = new JSONObject();
		params.put("userId", user.getUserId());
		params.put("ruleId", ruleId);
		params.put("ruleName", ruleName);
		params.put("ruleTerm", ruleTerm);
		params.put("ruleThreshold",ruleThreshold);
		ja.put(0, params);
		jo.put("Params", ja);
		dispatch.post(jo);
	}
	
	@RequestMapping(value = "/AlarmVMList", method = { RequestMethod.GET })
	@ResponseBody
	public String alarmVMList(HttpServletRequest request, ListModel list,
			@RequestParam String alarmUuid) {
		User user = (User) request.getSession().getAttribute("user");
		JSONArray ja = this.queryList.queryAlarmVMList(alarmUuid,
				list.getPage(), list.getLimit(), list.getSearch(), user.getUserId());
		return ja.toString();
	}
	
	@RequestMapping(value = "/BindAlarm", method = { RequestMethod.POST })
	@ResponseBody
	public void bindAlarm(HttpServletRequest request,
			@RequestParam String alarmUuid, @RequestParam String rsuuidStr) {
		User user = (User) request.getSession().getAttribute("user");
		JSONObject jo = new JSONObject();
		jo.put("Handler", "BindAlarmHandler");
		JSONArray ja = new JSONArray();
		JSONObject params = new JSONObject();
		params.put("userId",  user.getUserId());
		params.put("alarmUuid", alarmUuid);
		params.put("rsuuidStr", rsuuidStr);
		ja.put(0, params);
		jo.put("Params", ja);
		dispatch.post(jo);
	}
	
	@RequestMapping(value = "/UnBindAlarm", method = { RequestMethod.POST })
	@ResponseBody
	public void unBindAlarm(HttpServletRequest request,@RequestParam String rsUuid) {
		User user = (User) request.getSession().getAttribute("user");
		JSONObject jo = new JSONObject();
		jo.put("Handler", "UnBindAlarmHandler");
		JSONArray ja = new JSONArray();
		JSONObject params = new JSONObject();
		params.put("userId",  user.getUserId());
		params.put("rsUuid", rsUuid);
		ja.put(0, params);
		jo.put("Params", ja);
		dispatch.post(jo);
	}
	
}
