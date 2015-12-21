package com.beyondsphere.controller.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beyondsphere.entity.User;
import com.beyondsphere.query.QueryList;
import com.beyondsphere.utils.Dispatch;
import com.beyondsphere.model.GroupModel;
import com.beyondsphere.model.ListModel;

@RequestMapping("/GroupAction")
@Controller
public class GroupAction {
	
	@Resource
	private QueryList queryList;
	@Resource
	private Dispatch dispatch;
	
	@RequestMapping(value = "/GroupList", method = { RequestMethod.GET })
	@ResponseBody
	public String groupList(HttpServletRequest request, ListModel list) {
		User user = (User) request.getSession().getAttribute("user");
		JSONArray ja = queryList.queryGroupList(user.getUserId(), list.getPage(),  list.getLimit());
		return ja.toString();
	}
	
	@RequestMapping(value = "/AllList", method = { RequestMethod.GET })
	@ResponseBody
	public String groupList(HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user");
		JSONArray ja = queryList.queryGroupList(user.getUserId());
		return ja.toString();
	}
	
	@RequestMapping(value = "/VMList", method = { RequestMethod.GET })
	@ResponseBody
	public String vmList(HttpServletRequest request, ListModel list) {
		User user = (User) request.getSession().getAttribute("user");
		int userId = user.getUserId();
		JSONArray ja = queryList.queryGroupVMList(list.getPage(),
				list.getLimit(), userId);
		return ja.toString();
	}
	
	@RequestMapping(value = "/VMListOfGroup", method = { RequestMethod.GET })
	@ResponseBody
	public String vmListOfGroup(HttpServletRequest request, ListModel list) {
		User user = (User) request.getSession().getAttribute("user");
		int userId = user.getUserId();
		JSONArray ja = queryList.queryVMListOfGroup(list.getPage(), list.getLimit(), userId, list.getUuid());
		return ja.toString();
	}
	
	@RequestMapping(value= "/GetGroup", method = {RequestMethod.GET})
	@ResponseBody
	public String getGroup(HttpServletRequest request, @RequestParam String groupUuid){
		User user = (User) request.getSession().getAttribute("user");
		JSONObject jo = queryList.queryGroup(user.getUserId(), groupUuid);
		return jo.toString();
	}
	
	@RequestMapping(value = "/Create", method = { RequestMethod.POST })
	@ResponseBody
	public void addGroup(HttpServletRequest request, GroupModel groupModel){
		User user = (User) request.getSession().getAttribute("user");
		JSONObject jo = new JSONObject();
		jo.put("Handler", "CreateGroupHandler");
		JSONArray ja = new JSONArray();
		JSONObject params = new JSONObject();
		params.put("groupUuid", groupModel.getGroupUuid());
		params.put("groupName", groupModel.getGroupName());
		params.put("groupColor", groupModel.getGroupColor());
		params.put("groupLoc", groupModel.getGroupLoc());
		params.put("groupDes", groupModel.getGroupDes());
		params.put("userId", user.getUserId());
		ja.put(0, params);
		jo.put("Params", ja);
		dispatch.post(jo);
	}
	
	@RequestMapping(value = "/ModifyGroup", method = { RequestMethod.POST })
	@ResponseBody
	public String modifyAlarm(HttpServletRequest request, GroupModel groupModel) {
		User user = (User) request.getSession().getAttribute("user");
		JSONObject jo = new JSONObject();
		jo.put("Handler", "ModifyGroupHandler");
		JSONArray ja = new JSONArray();
		JSONObject params = new JSONObject();
		params.put("userId", user.getUserId());
		params.put("groupUuid", groupModel.getGroupUuid());
		params.put("groupName", groupModel.getGroupName());
		params.put("groupColor", groupModel.getGroupColor());
		params.put("groupLoc", groupModel.getGroupLoc());
		params.put("groupDes", groupModel.getGroupDes());
		ja.put(0, params);
		jo.put("Params", ja);
		dispatch.post(jo);
		return ((JSONObject)((JSONArray)jo.get("Params")).get(0)).get("result").toString();
	}
	
	@RequestMapping(value = "/Destory", method = { RequestMethod.GET })
	@ResponseBody
	public String destory(HttpServletRequest request,@RequestParam String groupUuid) {
		User user = (User) request.getSession().getAttribute("user");
		JSONObject jo = new JSONObject();
		jo.put("Handler", "DestoryGroupHandler");
		JSONArray ja = new JSONArray();
		JSONObject params = new JSONObject();
		params.put("userId", user.getUserId());
		params.put("groupUuid", groupUuid);
		ja.put(0, params);
		jo.put("Params", ja);
		dispatch.post(jo);
		return ((JSONObject)((JSONArray)jo.get("Params")).get(0)).get("result").toString();
	}
	
	@RequestMapping(value = "/BindGroup", method = { RequestMethod.POST })
	@ResponseBody
	public void bindAlarm(HttpServletRequest request,
			@RequestParam String groupUuid, @RequestParam String rsuuidStr) {
		User user = (User) request.getSession().getAttribute("user");
		JSONObject jo = new JSONObject();
		jo.put("Handler", "BindGroupHandler");
		JSONArray ja = new JSONArray();
		JSONObject params = new JSONObject();
		params.put("userId",  user.getUserId());
		params.put("groupUuid", groupUuid);
		params.put("rsuuidStr", rsuuidStr);
		ja.put(0, params);
		jo.put("Params", ja);
		dispatch.post(jo);
	}
	
	@RequestMapping(value = "/RemoveFromGroup", method = { RequestMethod.POST })
	@ResponseBody
	public void unBindAlarm(HttpServletRequest request,@RequestParam String groupUuid) {
		User user = (User) request.getSession().getAttribute("user");
		JSONObject jo = new JSONObject();
		jo.put("Handler", "removeGroupHandler");
		JSONArray ja = new JSONArray();
		JSONObject params = new JSONObject();
		params.put("userId",  user.getUserId());
		params.put("groupUuid", groupUuid);
		ja.put(0, params);
		jo.put("Params", ja);
		dispatch.post(jo);
	}
	
	@RequestMapping(value = "/UnBindGroup", method = { RequestMethod.POST })
	@ResponseBody
	public void removeFromAlarm(HttpServletRequest request,@RequestParam String vmUuid) {
		User user = (User) request.getSession().getAttribute("user");
		JSONObject jo = new JSONObject();
		jo.put("Handler", "UnBindGroupHandler");
		JSONArray ja = new JSONArray();
		JSONObject params = new JSONObject();
		params.put("userId",  user.getUserId());
		params.put("vmUuid", vmUuid);
		ja.put(0, params);
		jo.put("Params", ja);
		dispatch.post(jo);
	}
	
}
