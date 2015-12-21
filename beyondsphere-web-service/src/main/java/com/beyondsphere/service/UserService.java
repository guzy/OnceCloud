package com.beyondsphere.service;

import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;

import com.beyondsphere.entity.User;
import com.beyondsphere.model.PagedList;

public interface UserService {
	
	/**
	 * 检查用户登录
	 * @param userName, userPass
	 * @return
	 */
	public abstract int checkLogin(String userName, String userPass);

	/**
	 * 获取用户信息
	 * @param userName
	 * @return
	 */
	public abstract User getUser(String userName);
	
	/**
	 * 查询用户
	 * @param userName
	 * @return
	 */
	public abstract JSONArray queryUser(String userName);
	
	/**
	 * 创建用户
	 * @param userinfos
	 * @return
	 */
	public abstract JSONArray addUser(String userName,
			String userPassword, String userEmail, String userTelephone,
			String userCompany, String userLevel, int userid, String poolUuid,int roleUuid,String areaId);
	
	/**
	 * 根据id获取用户
	 * @param userId
	 * @return
	 */
	public abstract JSONObject getOneUser(int userId);
	
	/**
	 * 根据id用户
	 * @author lining
	 * @param userId
	 * @return
	 */
	public abstract User getUser(int userId);
	
	/**
	 * 更新用户信息
	 * @param userinfos
	 * @return
	 */
	public abstract void updateUser(int userId, int changeId,
			String userName, String userEmail, String userTel, String userCom,
			String userLevel,int roleUuid);
	
	/**
	 * 删除用户
	 * @param userId, changeId, userName
	 * @return
	 */
	public abstract JSONObject deleteUser(int userId, int changeId,
			String userName);

	/**
	 * 分页获取用户列表
	 * @param page, limit, search
	 * @return
	 */
	public abstract JSONArray getUserList(int page, int limit, String search);

	/**
	 * 获取用户列表
	 * @return
	 */
	public abstract JSONArray getUserList();

	public abstract void updateAreaInfo(Integer userId,
			String userActiveLocation);
	
	/**
	 * 更新用户信息
	 * @param userinfos
	 * @return
	 */
	public abstract boolean updateAdmin(int userId,
			String userPwd, String userEmail, String userTel);
	/**
	 * 获取注册企业数
	 * @return
	 */
	public abstract Integer  getEnterpriseCount(Date endDate);

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
	 * 根据用户ID查询用户地理位置信息
	 * @param userid
	 * @return
	 */
	public abstract JSONArray getUsermapByUserid(String userid);
	
	/**
	 * 添加用户
	 * 
	 * 
	 * @param user
	 * @return
	 */
	public abstract int addUser(User user);
	
    /**
     * 删除用户，是否要考虑删除用户的相关 资源
     * 
     * @param user
     * @return
     */
    public abstract int deleteUser(User user);
    
    /**
     * 修改用户的基本信息
     * 
     * @param user
     * @return
     */
    public abstract int updateUser(User user);
    
    /**
     * 将用户绑定到某个角色
     * 
     * @param roleId
     * @return
     */
    public abstract int bindRole(int userId,int roleId);
    
    /**
     * 查询分页数据
     * 
     * @param page
     * @param limit
     * @param search
     * @return
     */
    public abstract PagedList<User> getPagedUserList(int page, int limit, String search);

}
