package com.oncecloud.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.oncecloud.dao.GroupDAO;
import com.oncecloud.dao.VMDAO;
import com.oncecloud.entity.OCGroup;
import com.oncecloud.entity.OCVM;
import com.oncecloud.message.MessagePush;
import com.oncecloud.message.MessageUtilities;
import com.oncecloud.service.GroupService;
import com.oncecloud.util.Utilities;

@Service("GroupService")
public class GroupServiceImpl implements GroupService {
	
	@Resource
	private GroupDAO groupDAO;
	@Resource
	private VMDAO vmDAO;
	@Resource
	private MessagePush messagePush;
	
	@Override
	public JSONArray getGroupJson(int userId, int page, int limit) {
		List<OCGroup> groupList = groupDAO.getGroupList(userId, page, limit);
		JSONArray ja = new JSONArray();
		int totalNum = groupDAO.countGroupNum();
		ja.put(totalNum);
		if (groupList != null) {
			for(OCGroup group : groupList){
				JSONObject jo = objectToJson(group);
				ja.put(jo);
			}
		}
		return ja;
	}

	@Override
	public JSONArray getGroupJson(int userId) {
		List<OCGroup> groupList = groupDAO.getGroupList(userId);
		JSONArray ja = new JSONArray();
		if (groupList != null) {
			for(OCGroup group : groupList){
				JSONObject jo = objectToJson(group);
				ja.put(jo);
			}
		}
		return ja;
	}
	
	@Override
	public List<OCGroup> getGroupList(int userId, int page, int limit) {
		return groupDAO.getGroupList(userId, page, limit);
	}

	@Override
	public JSONArray getGroupJson() {
		List<OCGroup> groupList = groupDAO.getAllGroups();
		JSONArray ja = new JSONArray();
		int totalNum = groupDAO.countGroupNum();
		ja.put(totalNum);
		if (groupList != null) {
			for(OCGroup group : groupList){
				JSONObject jo = objectToJson(group);
				ja.put(jo);
			}
		}
		return ja;
	}
	
	@Override
	public List<OCGroup> getGroupList() {
		return groupDAO.getAllGroups();
	}

	@Override
	public OCGroup getGroup(int userId, String groupUuid) {
		return groupDAO.getGroupById(groupUuid);
	}
	
	@Override
	public JSONObject getGroupInfo(int userId, String groupUuid) {
		OCGroup group = groupDAO.getGroupById(groupUuid);
		JSONObject jo = new JSONObject();
		if (group != null) {
			jo = objectToJson(group);
		}
		int vmNumOfGroup = vmDAO.getVMsOfGroup(userId, groupUuid).size();
		jo.put("vmTotal", vmNumOfGroup);
		return jo;
	}

	@Override
	public boolean saveGroup(String groupUuid, int userId, String groupName, String groupColor,
			String groupLoc, String groupDes) {
		boolean result = false;
		OCGroup group = new OCGroup(groupUuid, userId, groupName, groupColor, groupLoc, groupDes, new Date());
		result = groupDAO.insertGroup(group);
		return result;
	}

	@Override
	public boolean modifyGroup(String groupUuid, String groupName, String groupColor,
			String groupLoc, String groupDes) {
		boolean result = false;
		OCGroup group = groupDAO.getGroupById(groupUuid);
		if (group != null) {
			group.setGroupName(groupName);
			group.setGroupColor(groupColor);
			group.setGroupLoc(groupLoc);
			group.setGroupDes(groupDes);
		}
		result = groupDAO.updateGroup(group);
		return result;
	}

	@Override
	public boolean deleteGroup(String groupUuid) {
		boolean result = false;
		if (vmDAO.isNotExistGroup(groupUuid)) {
			result = groupDAO.deleteGroup(groupUuid);
		}else {
			messagePush.pushMessage(1, MessageUtilities.stickyToSuccess("分组内存在虚拟机资源"));
		}
		return result;
	}
	
	private JSONObject objectToJson(OCGroup group){
		JSONObject jo = new JSONObject();
		jo.put("groupUuid", group.getGroupUuid());
		jo.put("groupName", group.getGroupName());
		jo.put("groupColor", group.getGroupColor());
		jo.put("groupLoc", group.getGroupLoc());
		jo.put("groupDes", group.getGroupDes());
		jo.put("createTime", Utilities.formatTime(group.getCreateTime()));
		return jo;
	}

	@Override
	public boolean addVMtoGroup(int userId, String rsuuidStr, String groupUuid) {
		boolean result = false;
		try {
			JSONArray jArray = new JSONArray(rsuuidStr);
			for (int i = 0; i < jArray.length(); i++) {
				String rsUuid = jArray.getString(i);
				//rsUuid = rsUuid.substring(0, rsUuid.length() - 1);
				result = vmDAO.updateGroup(rsUuid, groupUuid);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		}
		return result;
	}

	@Override
	public JSONArray getVMGroupList(int page, int limit, Integer userId) {
		JSONArray ja = new JSONArray();
		int totalNum = vmDAO.countVMsWithoutGroup(userId);
		List<OCVM> VMList = vmDAO.getOnePageVMsWithoutGroup(page, limit, userId);
		ja.put(totalNum);
		if (VMList != null) {
			for (OCVM ocvm : VMList) {
				JSONObject jo = new JSONObject();
				jo.put("vmid", ocvm.getVmUuid());
				jo.put("vmname", Utilities.encodeText(ocvm.getVmName()));
				ja.put(jo);
			}
		}
		return ja;
	}

	@Override
	public JSONArray getVMListOfGroup(int page, int limit, String groupUuid, int userId) {
		JSONArray ja = new JSONArray();
		int totalNum = vmDAO.getVMsOfGroup(userId, groupUuid).size();
		List<OCVM> VMList = vmDAO.getVMsOfGroup(page, limit, userId, groupUuid);
		ja.put(totalNum);
		if (VMList != null) {
			for (OCVM ocvm : VMList) {
				JSONObject jo = new JSONObject();
				jo.put("vmid", ocvm.getVmUuid());
				jo.put("vmname", Utilities.encodeText(ocvm.getVmName()));
				jo.put("state", ocvm.getVmPower().ordinal());
				String timeUsed = Utilities.encodeText(Utilities
						.dateToUsed(ocvm.getVmCreateDate()));
				jo.put("timeUsed", timeUsed);
				ja.put(jo);
			}
		}
		return ja;
	}

	@Override
	public boolean removeGroupFromVM(String groupUuid) {
		return vmDAO.cleanVMGroup(groupUuid);
	}

	@Override
	public boolean unbindVMFromGroup(String vmUuid) {
		return vmDAO.updateGroup(vmUuid, null);
	}

}
