package com.oncecloud.manager.Impl;

import java.util.Date;

import javax.annotation.Resource;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.oncecloud.core.RegistryCore;
import com.oncecloud.manager.RegistryManager;
import com.oncecloud.service.RegistryService;

/**
 * @author luogan
 * 2015年7月13日
 * 下午12:10:31
 */
@Service("RegistryManager")
public class RegistryManagerImpl implements RegistryManager {

	@Resource
	private RegistryCore registryCore;
	@Resource
	private RegistryService registryService;
	
	@Override
	public JSONObject createRegistry(JSONObject jo) {
		
		boolean result = false;
		// manager调用core然后去调用API
		JSONObject jso = registryCore.createRegistry(jo.getString("imageUuid"),jo.getString("name"),jo.getString("ip"),jo.getString("port"));
		// docker容器创建成功后,manager调用service保存到DAO,把dockerAPI取出来的值保持到数据库里
		if (jso != null) {
			String containerStatus = jso.getString("containerstatus");
			if(containerStatus != null){
				containerStatus = containerStatus.contains("Up")==true ? "EXIST" :"DELETED";
			}
			Date createTime = new Date();
			result = registryService.createRegistry(jso.getString("containerid"),jo.getString("name"),jo.getString("ip"),jo.getString("port"),containerStatus,createTime);
		}
		
		jo.put("result", result);
		return jo;
	}
	
	@Override
	public JSONObject deleteRegistry(JSONObject jo) {
		
		boolean result = false;
		// 先调用core去调用API
		result = registryCore.deleteRegistry(jo.getString("uuid"));
		// 操作成功后调用service去调用DAO
		if (result == true) {
			result = registryService.deleteRegistry(jo.getInt("userId"),jo.getString("uuid"));
		}
		jo.put("result", result);
		return jo;
	}

}
