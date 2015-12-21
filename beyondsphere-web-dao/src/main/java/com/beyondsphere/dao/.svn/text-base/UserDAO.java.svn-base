/**
 * @author hty
 * @time 上午11:05:48
 * @date 2014年12月9日
 */
package com.beyondsphere.dao;

import java.util.Date;
import java.util.List;

import com.beyondsphere.entity.User;

public interface UserDAO {

	/**
	 * 获取用户
	 * 
	 * @param userId
	 * @return
	 */
	public abstract User getUser(int userId);

	public abstract User getUserNoTransactional(int userId);

	/**
	 * 获取用户（通过用户名）
	 * 
	 * @param userName
	 * @return
	 */
	public abstract User getUser(String userName);

	/**
	 * 获取一页用户列表
	 * 
	 * @param page
	 * @param limit
	 * @param search
	 * @return
	 */
	public abstract List<User> getOnePageUserList(int page, int limit,
			String search);

	/**
	 * 获取全部用户列表
	 * 
	 * @param searchStr
	 * @return
	 */
	public abstract List<User> getCompanyUserList(String search);

	/**
	 * 获取用户总数
	 * 
	 * @param search
	 * @return
	 */
	public abstract int countAllUserList(String search);

	/**
	 * 添加用户
	 * 
	 * @param userName
	 * @param userPass
	 * @param userMail
	 * @param userPhone
	 * @param userCompany
	 * @param userLevel
	 * @param userDate
	 */
	public abstract int insertUser(String userName, String userPass,
			String userMail, String userPhone, String userCompany,
			int userLevel, Date userDate, String poolUuid,int roleUuid,String areaId);

	/**
	 * 禁用用户
	 * 
	 * @param userId
	 * @return
	 */
	public abstract boolean disableUser(int userId);

//	/**
//	 * 申请代金券
//	 * 
//	 * @param userId
//	 * @param voucher
//	 * @return
//	 */
//	public abstract boolean applyVoucher(int userId, int voucher);

	/**
	 * 确认代金券
	 * 
	 * @param userId
	 * @return
	 */
	public abstract boolean confirmVoucher(int userId);

	/**
	 * 拒绝代金券
	 * 
	 * @param userid
	 * @return
	 */
	public abstract boolean denyVoucher(int userId);

	/**
	 * 更新用户
	 * 
	 * @param userid
	 * @param userName
	 * @param userMail
	 * @param userPhone
	 * @param userCompany
	 * @param userLevel
	 * @return
	 */
	public abstract boolean updateUser(Integer userid, String userName,
			String userMail, String userPhone, String userCompany, int userLevel,int roleUuid);
	
	/**
	 * 更新用户
	 * 
	 * @param user
	 */
	public abstract boolean updateUser(User user);

//	/**
//	 * 更新用户余额
//	 * 
//	 * @param userid
//	 * @param bill
//	 * @return
//	 */
//	public abstract boolean updateBalance(int userId, double bill);

	public abstract List<User> getUserList();
	
	/**
	 * 根据角色id判断是否还有用户在使用该角色
	 * 
	 * @param roleid
	 * @return
	 */
	public abstract int getCountInRole(int roleid);

	public abstract boolean updateAreaInfo(Integer userId,
			String userActiveLocation);
	/**
	 * 更新用户
	 * 
	 * @param userid
	 * @param userPass
	 * @param userMail
	 * @param userPhone
	 * @return
	 */
	public abstract boolean updateAdmin(Integer userid, String userPass,
			String userMail, String userPhone);
	
	//获取企业个数
	public abstract Integer getEnterpriseCount(Date endTime);
	
	public abstract List<User> getEnterpriseList(int num);
	/**
	 * 设置地理位置
	 * @param userid//用户id
	 * @param lng//经度
	 * @param lat//纬度
	 * @return
	 */
	public abstract boolean userSetMap(String userid, String lng, String lat);

	/**
	 * 保存user对象
	 * 
	 * @param user
	 * @return
	 * @throws OnceDAOException
	 */
	public abstract boolean addUser(User user);
	
	/**
	 * 移除对象
	 * 
	 * @param user
	 * @return
	 * @throws OnceDAOException
	 */
	public abstract boolean removeUser(User user);

}
