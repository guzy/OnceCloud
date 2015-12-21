package com.oncecloud.core.impl;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.Xen.API.Connection;
import org.Xen.API.VDI;
import org.Xen.API.VMUtil;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.oncecloud.core.ISOCore;
import com.oncecloud.core.constant.Constant;
import com.oncecloud.entity.OCPool;
import com.oncecloud.model.ISOModel;

@Component("ISOCore")
public class ISOCoreImpl implements ISOCore {
	
	@Resource
	private Constant constant;

	public List<ISOModel> getISOModelOfPool(List<ISOModel> list, OCPool pool) {
		Connection conn;
		try {
			conn = constant.getConnectionFromPool(pool.getPoolUuid());
			Set<VDI> vdis = VMUtil.getISOs(conn);
			for (VDI vdi : vdis) {
				ISOModel model =new ISOModel();
				String nameLabel = vdi.getNameLabel(conn);
				if (nameLabel.contains(".iso")) {
					model.setIsoName(nameLabel);
					model.setPoolName(pool.getPoolName());
					model.setPoolId(pool.getPoolUuid());
					list.add(model);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public JSONArray getISOArray(String poolUuid) {
		JSONArray ja = new JSONArray();
		Connection conn = null;
		try {
			conn = constant.getConnectionFromPool(poolUuid);
			Set<VDI> vdis = VMUtil.getISOs(conn);
			for (VDI vdi : vdis) {
				String nameLabel = vdi.getNameLabel(conn);
				if (nameLabel.contains(".iso")||nameLabel.contains("cd-rom")) {
					// String location = vdi.getLocation(conn);
					String uuid = vdi.getUuid(conn);
					JSONObject jo = new JSONObject();
					jo.put("name", nameLabel);
					// jo.put("location", location);
					jo.put("uuid", uuid);
					ja.put(jo);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ja;
	}
	
}
