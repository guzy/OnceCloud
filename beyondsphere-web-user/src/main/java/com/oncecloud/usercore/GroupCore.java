package com.oncecloud.usercore;

import javax.annotation.Resource;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.oncecloud.service.GroupService;

@Component
public class GroupCore {
	
	@Resource
	private GroupService groupService;
	
	public JSONObject saveGroup(JSONObject jo) {
		try {
			jo.put("result", groupService.saveGroup(jo.getString("groupUuid"), jo.getInt("userId"), jo.getString("groupName"), jo.getString("groupColor"), 
								jo.getString("groupLoc"), jo.getString("groupDes")));
		} catch (JSONException e) {
			jo.put("result", false);
			e.printStackTrace();
		}
		return jo;
	}	
	
	public JSONObject bindGroup(JSONObject jo){
		try{
			jo.put("result",
					groupService.addVMtoGroup(
							jo.getInt("userId"), 
							jo.getString("rsuuidStr"), 
							jo.getString("groupUuid")
							)
				  );
		}
		catch(Exception e){
			e.printStackTrace();
			jo.put("result", false);
		}
		return jo;
	}
	
	public JSONObject removeGroup(JSONObject jo){
		try{
			jo.put("result", groupService.removeGroupFromVM(jo.getString("groupUuid")));
		}
		catch(Exception e){
			e.printStackTrace();
			jo.put("result", false);
		}
		return jo;
	}
	
	public JSONObject unbindGroup(JSONObject jo){
		try{
			jo.put("result", groupService.unbindVMFromGroup(jo.getString("vmUuid")));
		}
		catch(Exception e){
			e.printStackTrace();
			jo.put("result", false);
		}
		return jo;
	}
	
	public JSONObject modifyGroup(JSONObject jo) {
		try {
			jo.put("result", groupService.modifyGroup(jo.getString("groupUuid"), jo.getString("groupName"), jo.getString("groupColor"), 
								jo.getString("groupLoc"), jo.getString("groupDes")));
		} catch (JSONException e) {
			jo.put("result", false);
			e.printStackTrace();
		}
		return jo;
	}
	
	public JSONObject destoryGroup(JSONObject jo) {
		try {
			jo.put("result", groupService.deleteGroup(jo.getString("groupUuid")));
		} catch (JSONException e) {
			jo.put("result", false);
			e.printStackTrace();
		}
		return jo;
	}
	
}
