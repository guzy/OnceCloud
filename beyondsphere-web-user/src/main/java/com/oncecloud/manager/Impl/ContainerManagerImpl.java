package com.oncecloud.manager.Impl;

import java.util.Date;

import javax.annotation.Resource;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.oncecloud.core.ContainerCore;
import com.oncecloud.entity.OCHost;
import com.oncecloud.manager.ContainerManager;
import com.oncecloud.service.ContainerService;
import com.oncecloud.service.HostService;

/**
 * @author luogan 
 * 2015年6月19日 
 * 下午3:22:58
 */
@Service("ContainerManager")
public class ContainerManagerImpl implements ContainerManager {

	@Resource
	private ContainerCore containerCore;
	@Resource
	private ContainerService containerService;
	@Resource
	private HostService hostService;

	// 创建容器
	@Override
	public JSONObject createContainer(JSONObject jo) {
		boolean result = false;
		String command = "/bin/bash";
		JSONObject jso = containerCore.createContainer(jo.getString("tplUuid"),
				jo.getString("name"), command, jo.getInt("cpu"),
				jo.getInt("memory"), jo.getString("startCommand"),
				jo.getString("link"), jo.getString("dockerParams"));
		if (jso != null) {
			String containerStatus = jso.getString("containerstatus");
			if(containerStatus != null){
				containerStatus = containerStatus.contains("Up")==true ? "EXIST" :"DELETED";
			}
			Date conCreateTime = new Date();
			result = containerService.createContainer(jso.getString("containerid"),
					jo.getString("tplUuid"), jo.getInt("userId"),
					jo.getInt("cpu"), jo.getInt("memory"),
					jo.getString("name"),jo.getString("tplUuid"), containerStatus,
					jo.getString("startCommand"), jo.getString("link"),
					jo.getString("dockerParams"),jso.getString("containerport"),conCreateTime);
		}
		jo.put("result", result);
		return jo;
	}
	
	@Override
	public JSONObject createContainer(JSONObject jo, String clusterUuid) {
		OCHost master = hostService.getMasterOfPool(clusterUuid);
		JSONObject jso = containerCore.createContainer(master.getHostIP(), master.getHostPwd(), 
										jo.getString("tplUuid"), jo.getString("name"), jo.getString("dockerParams"));
		boolean result = false;
		if (jso != null) {
			String containerStatus = jso.getString("containerstatus");
			if(containerStatus != null){
				containerStatus = containerStatus.contains("Up")==true ? "EXIST" :"DELETED";
			}
		// docker容器创建成功后,manager调用service保存到DAO
		if (jo != null) {
			Date conCreateTime = new Date();
			result = containerService.createContainer(jso.getString("containerid"),
					jo.getString("tplUuid"), jo.getInt("userId"),
					jo.getInt("cpu"), jo.getInt("memory"),
					jo.getString("name"), jo.getString("tplUuid"), containerStatus,
					jo.getString("startCommand"), jo.getString("link"),
					//jo.getString("dockerParams"),conCreateTime);
					jo.getString("dockerParams"),jso.getString("containerport"),conCreateTime);
		}
		jo.put("result", result);
	   }
		return jo;
	}


	// 删除容器
	@Override
	public JSONObject deleteContainer(JSONObject jo) {
		boolean result = false;
		// 先调用core去调用API
		result = containerCore.deleteContainer(jo.getString("containerId"));
		// 操作成功后调用service去调用DAO
		if (result == true) {
			result = containerService.deleteContainer(jo.getInt("userId"),
					jo.getString("containerId"));
		}
		jo.put("result", result);
		return jo;
	}

	// 停止容器
	@Override
	public JSONObject stopContainer(JSONObject jo) {
		boolean result = false;
		// 先调用core去调用API
		result = containerCore.stopContainer(jo.getString("containerId"));
		// 操作成功后调用service去调用DAO
		if (result == true) {
			result = containerService.stopContainer(jo.getInt("userId"),
					jo.getString("containerId"));
		}
		jo.put("result", result);
		return jo;
	}

	// 重启容器
	@Override
	public JSONObject restartContainer(JSONObject jo) {
		boolean result = false;
		// 先调用core去调用API
		result = containerCore.startContainer(jo.getString("containerId"));
		// 操作成功后调用service去调用DAO
		if (result == true) {
			result = containerService.startContainer(jo.getInt("userId"),
					jo.getString("containerId"));
		}
		jo.put("result", result);
		return jo;
	}

}
