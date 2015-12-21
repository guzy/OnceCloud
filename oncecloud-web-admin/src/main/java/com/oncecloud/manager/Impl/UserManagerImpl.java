/**
 * @author hty
 * @time 上午11:05:48
 * @date 2014年12月9日
 */
package com.oncecloud.manager.Impl;

import java.util.Date;

import javax.annotation.Resource;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.oncecloud.manager.UserManager;
import com.oncecloud.entity.User;
import com.oncecloud.message.MessageUtil;
import com.oncecloud.service.UserService;

@Service("UserManager")
public class UserManagerImpl implements UserManager{

	@Resource
	private UserService userService;
	@Resource
	private MessageUtil message;
	
	public int checkLogin(String userName, String userPass) {
		return userService.checkLogin(userName, userPass);
	}

	public User getUser(String user) {
		return userService.getUser(user);
	}

	public JSONArray getUserList(int page, int limit, String search) {
		return userService.getUserList(page, limit, search);
	}

	public JSONObject doDeleteUser(int userId, int changeId, String userName) {
		return userService.deleteUser(userId, changeId, userName);
	}
	
	public JSONObject doGetOneUser(int userId) {
		return userService.getOneUser(userId);
	}
	
	public JSONArray doCreateUser(String userName, String userPassword,
			String userEmail, String userTelephone, String userCompany,
			String userLevel, int userid, String poolUuid,int roleUuid,String areaId) {
		return userService.addUser(userName, userPassword, userEmail, userTelephone, userCompany, userLevel, userid, poolUuid, roleUuid,areaId);
	}
	
	public void doUpdateUser(int userId, int changeId, String userName,
			String userEmail, String userTel, String userCom, String userLevel,int roleUuid) {
		userService.updateUser(userId, changeId, userName, userEmail, userTel, userCom, userLevel, roleUuid);
	}
	
	public JSONArray doQueryUser(String userName) {
		return userService.queryUser(userName);
	}
	
	public JSONArray getUserList() {
		return userService.getUserList();
	}

	@Override
	public void doUpdateAreaInfo(Integer userId, String userActiveLocation) {
		userService.updateAreaInfo(userId,userActiveLocation);
	}
	
	public boolean doUpdateAdmin(int userId, String userPwd,
			String userEmail, String userTel) {
		return userService.updateAdmin(userId, userPwd, userEmail, userTel);
	}

	@Override
	public Integer getEnterpriseCount(Date endDate) {
		return userService.getEnterpriseCount(endDate);
	}

	@Override
	public JSONArray getEnterpriseList(int num) {
		return userService.getEnterpriseList(num);
	}

	@Override
	public boolean createMap(String userid, String lng, String lat) {
		boolean b = userService.createMap(userid,lng,lat);
		if(b){
			message.pushSuccess(Integer.parseInt(userid), "用户地理位置设置成功！");
		}else{
			message.pushSuccess(Integer.parseInt(userid), "用户地理位置设置失败！");
		}
		
		return b ;
	}

	@Override
	public JSONArray getUsermapByUserid(String userid) {
		
		return userService.getUsermapByUserid(userid);
	}
}
