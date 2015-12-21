package com.oncecloud.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.oncecloud.dao.UserDAO;
import com.oncecloud.dao.VMDAO;
import com.oncecloud.entity.OCVM;
import com.oncecloud.entity.User;
import com.oncecloud.service.MapLocationService;
@Service("MapLocationService")
public class MapLocationServiceImpl implements MapLocationService {
	@Resource
	private UserDAO userDAO;
	@Resource
	private VMDAO vmdao;
	@Override
	public JSONArray getAllUsermap() {
		List<User> users = userDAO.getUserList();
		JSONArray ja = new JSONArray();
		for(User user:users){
			JSONObject jo = new JSONObject();
			if(user.getLng()!=null&&!user.getLng().equals("")&&user.getLat()!=null&&!user.getLat().equals("")){
				jo.put("lng", user.getLng());
				jo.put("lat", user.getLat());
				jo.put("username", user.getUserName());
				List<OCVM> ocvms = vmdao.getVMsByUser(user.getUserId());
				jo.put("ocvms", ocvms);
				ja.put(jo);
			}
			
		}
		return ja;
	}

}
