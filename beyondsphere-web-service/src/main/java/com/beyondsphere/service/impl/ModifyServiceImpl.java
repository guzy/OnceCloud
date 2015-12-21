package com.beyondsphere.service.impl;

import javax.annotation.Resource;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.beyondsphere.dao.AlarmDAO;
import com.beyondsphere.dao.GroupDAO;
import com.beyondsphere.dao.ImageDAO;
import com.beyondsphere.dao.VMDAO;
import com.beyondsphere.dao.VolumeDAO;
import com.beyondsphere.entity.OCGroup;
import com.beyondsphere.service.ModifyService;

@Service
public class ModifyServiceImpl implements ModifyService {
	
	@Resource
	private VMDAO vmDAO;
	@Resource
	private VolumeDAO volumeDAO;
	@Resource
	private ImageDAO imageDAO;
	@Resource
	private AlarmDAO alarmDAO;
	@Resource
	private GroupDAO groupDAO;
	
	@Override
	public JSONObject modifyBasicInfo(String modifyUuid, String modifyName,
			String modifyDesc, String modifyType) {
		JSONObject jo = new JSONObject();
		if (modifyType.equals("instance")) {
			vmDAO.updateName(modifyUuid, modifyName, modifyDesc);
			jo.put("isSuccess", true);
		} else if (modifyType.equals("volume")) {
			volumeDAO.updateName(modifyUuid, modifyName, modifyDesc);
			jo.put("isSuccess", true);
		} else if (modifyType.equals("image")) {
			imageDAO.updateName(modifyUuid, modifyName, modifyDesc);
			jo.put("isSuccess", true);
		} else if (modifyType.equals("al")) {
			alarmDAO.updateName(modifyUuid, modifyName, modifyDesc);
			jo.put("isSuccess", true);
		} else if (modifyType.equals("group")){
			OCGroup group = groupDAO.getGroupById(modifyUuid);
			group.setGroupName(modifyName);
			group.setGroupDes(modifyDesc);
			groupDAO.updateGroup(group);
			jo.put("isSuccess", true);
		}
		return jo;
	}

}
