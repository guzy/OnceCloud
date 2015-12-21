package com.oncecloud.controller.action;

import javax.annotation.Resource;

import org.json.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oncecloud.manager.MapLocationManager;
/**
 * 获取所有用户地理位置信息
 * @author mayh
 *
 */
@Controller
@RequestMapping(value="MapLocationAction")
public class MapLocationAction {
	@Resource
	MapLocationManager locationManager;
	@RequestMapping(value = "/getAllUsermap", method = { RequestMethod.GET })
	@ResponseBody
	public String getAllUsermap(){
		JSONArray ja = null;
		try{
			ja = locationManager.getAllUsermap();
		}catch(Exception e){
			e.printStackTrace();
		}
		return ja.toString();
	}
}
