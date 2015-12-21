/**
 * @author hty
 * @time 上午11:05:48
 * @date 2014年12月9日
 */
package com.beyondsphere.manager;

import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;

import com.oncecloud.entity.User;

public interface UserManager {

	public abstract int checkLogin(String userName, String userPass);

	public abstract User getUser(String user);

	public abstract JSONArray doQueryUser(String userName);

	public abstract JSONArray doCreateUser(String userName,
			String userPassword, String userEmail, String userTelephone,
			String userCompany, String userLevel, int userid, String poolUuid,int roleUuid,String areaId);

	public abstract JSONObject doGetOneUser(int userId);

	public abstract void doUpdateUser(int userId, int changeId,
			String userName, String userEmail, String userTel, String userCom,
			String userLevel,int roleUuid);

	public abstract JSONObject doDeleteUser(int userId, int changeId,
			String userName);

	/**
	 * 获取用户列表
	 * 
	 * @param page
	 * @param limit
	 * @param search
	 * @return
	 */
	public abstract JSONArray getUserList(int page, int limit, String search);

	public abstract JSONArray getUserList();

	public abstract void doUpdateAreaInfo(Integer userId,
			String userActiveLocation);
	public abstract boolean doUpdateAdmin(int userId,
			String userPwd, String userEmail, String userTel);
	//获取企业个数
	public abstract Integer getEnterpriseCount(Date endTime);
	
	//近期注册企业列表
	public abstract JSONArray getEnterpriseList(int num);
	/**
	 * 设置地理位置
	 * @param userid//用户id
	 * @param lng//经度
	 * @param lat//纬度
	 * @return
	 */
	public abstract boolean createMap(String userid, String lng, String lat);
	/**
	 * 根据userid查询用户地图位置信息
	 * @param userid
	 * @return
	 */
	public abstract JSONArray getUsermapByUserid(String userid);

}