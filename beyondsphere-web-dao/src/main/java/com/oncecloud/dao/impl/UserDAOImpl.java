/**
 * @author hty
 * @time 上午11:05:48
 * @date 2014年12月9日
 */
package com.oncecloud.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import com.oncecloud.constants.LogConstant;
import com.oncecloud.dao.UserDAO;
import com.oncecloud.entity.User;
import com.oncecloud.helper.HashHelper;
import com.oncecloud.helper.SessionHelper;
import com.oncecloud.model.UserLevel;
import com.oncecloud.model.UserStatus;

@Component("UserDAO")
public class UserDAOImpl implements UserDAO {
	
	@Resource
	private SessionHelper sessionHelper;
	@Resource
	private HashHelper hashHelper;

	/**
	 * 获取用户
	 * 
	 * @param userId
	 * @return
	 */
	public User getUser(int userId) {
		User user = null;
		Session session = null;
		try {
			session = sessionHelper.getPlatformSession();
			session.beginTransaction();
			Query query = session
					.createQuery("from User where userId = :userId");
			query.setInteger("userId", userId);
			user = (User) query.uniqueResult();
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Get user infos failed by user id.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return user;
	}

	public User getUserNoTransactional(int userId) {
		User user = null;
		Session session = null;
		try {
			session = sessionHelper.getPlatformSession();
			Query query = session
					.createQuery("from User where userId = :userId");
			query.setInteger("userId", userId);
			user = (User) query.uniqueResult();
		} catch (Exception e) {
			loginfo("Get user infos without transactional failed.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return user;
	}

	/**
	 * 获取用户（通过用户名）
	 * 
	 * @param userName
	 * @return
	 */
	public User getUser(String userName) {
		User user = null;
		Session session = null;
		try {
			session = sessionHelper.getPlatformSession();
			session.beginTransaction();
			Query query = session
					.createQuery("from User where userName = :userName and userStatus > 0");
			query.setString("userName", userName);
			user = (User) query.uniqueResult();
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Get user infos failed by user name.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return user;
	}

/*	*//**
	 * 获取一页用户列表
	 * 
	 * @param page
	 * @param limit
	 * @param search
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<User> getOnePageUserList(int page, int limit, String search) {
		List<User> userList = null;
		Session session = null;
		try {
			session = sessionHelper.getPlatformSession();
			session.beginTransaction();
			int startPos = (page - 1) * limit;
			String queryString = "from User where userName like :search and userLevel != 0 and userStatus = 1 order by userDate desc";
			Query query = session.createQuery(queryString);
			query.setString("search", "%" + search + "%");
			query.setFirstResult(startPos);
			query.setMaxResults(limit);
			userList = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Get user list failed by page.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return userList;
	}

	/**
	 * 获取全部用户列表
	 * 
	 * @param searchStr
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<User> getCompanyUserList(String search) {
		List<User> userList = null;
		Session session = null;
		try {
			session = sessionHelper.getPlatformSession();
			session.beginTransaction();
			String queryString = "from User where userName like :search "
					+ "and userLevel != 0 and userStatus = 1 order by userDate desc";
			Query query = session.createQuery(queryString);
			query.setString("search", "%" + search + "%");
			userList = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Get user infos failed by user level.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return userList;
	}

	/**
	 * 获取用户总数
	 * 
	 * @param search
	 * @return
	 */
	public int countAllUserList(String search) {
		int count = 0;
		Session session = null;
		try {
			session = sessionHelper.getPlatformSession();
			session.beginTransaction();
			String queryString = "select count(*) from User where userName like :search and userId != 1 and userStatus = 1";
			Query query = session.createQuery(queryString);
			query.setString("search", "%" + search + "%");
			count = ((Number) query.uniqueResult()).intValue();
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Count user list failed.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return count;
	}

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
	public int insertUser(String userName, String userPass, String userMail,
			String userPhone, String userCompany, int userLevel, Date userDate, String poolUuid,int roleUuid,String areaId) {
		Session session = null;
		int userId = -1;
		try {
			User user = new User();
			user.setUserName(userName);
			user.setUserPass(hashHelper.md5Hash(userPass));
			user.setUserMail(userMail);
			user.setUserPhone(userPhone);
			user.setUserCompany(userCompany);
			user.setUserLevel(UserLevel.values()[userLevel]);
			user.setUserStatus(UserStatus.USING);
			user.setUserDate(userDate);
			user.setUserBalance(0.0);
			user.setUserAllocate(poolUuid);
			user.setUserRoleId(roleUuid);
			user.setUserLocation(areaId);
			//如果是管理员，所属区域也是第一次登录活跃区域
			if(userLevel==0){
				user.setUserActiveLocation(areaId);
			}
			session = sessionHelper.getPlatformSession();
			session.beginTransaction();
			session.save(user);
			userId = user.getUserId();
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Insert user infos failed.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return userId;
	}

	/**
	 * 禁用用户
	 * 
	 * @param userId
	 * @return
	 */
	public boolean disableUser(int userId) {
		boolean result = false;
		User delUser = getUser(userId);
		if (delUser != null) {
			delUser.setUserStatus(UserStatus.DELETED);
			Session session = null;
			try {
				session = sessionHelper.getPlatformSession();
				session.beginTransaction();
				session.delete(delUser);
				session.getTransaction().commit();
				result = true;
			} catch (Exception e) {
				loginfo("Disabled user failed.");
				e.printStackTrace();
				if (session.isOpen()) {
					session.getTransaction().rollback();
				}
			}
		}
		return result;
	}

	/**
	 * 确认代金券
	 * 
	 * @param userId
	 * @return
	 */
	public boolean confirmVoucher(int userId) {
		boolean result = false;
		User user = this.getUser(userId);
		if (user.getUserLevel() == UserLevel.USER) {
			Session session = null;
			try {
				user.setUserBalance(user.getUserBalance()
						+ user.getUserVoucher());
				user.setUserVoucher(null);
				session = sessionHelper.getPlatformSession();
				session.beginTransaction();
				session.update(user);
				session.getTransaction().commit();
				result = true;
			} catch (Exception e) {
				e.printStackTrace();
				if (session.isOpen()) {
					session.getTransaction().rollback();
				}
			}
		}
		return result;
	}

	/**
	 * 拒绝代金券
	 * 
	 * @param userid
	 * @return
	 */
	public boolean denyVoucher(int userId) {
		boolean result = false;
		User user = this.getUser(userId);
		if (user != null && user.getUserLevel() == UserLevel.USER) {
			Session session = null;
			try {
				user.setUserVoucher(null);
				user.setUserLevel(UserLevel.NOTLEVEL);
				session = sessionHelper.getPlatformSession();
				session.beginTransaction();
				session.update(user);
				session.getTransaction().commit();
				result = true;
			} catch (Exception e) {
				e.printStackTrace();
				if (session.isOpen()) {
					session.getTransaction().rollback();
				}
			}
		}
		return result;
	}

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
	public boolean updateUser(Integer userid, String userName, String userMail,
			String userPhone, String userCompany, int userLevel,int roleUuid) {
		boolean result = false;
		Session session = null;
		try {
			session = sessionHelper.getPlatformSession();
			session.beginTransaction();
			Query query = session
					.createQuery("update User set userName= :name, userMail= :mail, "+ "userPhone = :phone, userCompany = :com, userLevel = :level, userRoleId = :userRoleId "+ "where userId = :id");
			query.setString("name", userName);
			query.setString("mail", userMail);
			query.setString("phone", userPhone);
			query.setString("com", userCompany);
			query.setInteger("level", userLevel);
			query.setInteger("userRoleId", roleUuid);
			query.setInteger("id", userid);
			query.executeUpdate();
			session.getTransaction().commit();
			result = true;
		} catch (Exception e) {
			loginfo("Update user infos failed.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public List<User> getUserList() {
		List<User> list = new ArrayList<User>();
		Session session = null;
		try {
			session = sessionHelper.getPlatformSession();
			session.beginTransaction();
			String queryStr = "from User where userLevel>0";
			Query query = session.createQuery(queryStr);
			list = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Get all user list failed.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return list;
	}

	
	public int getCountInRole(int roleid) {
		int count = 0;
		Session session = null;
		try {
			session = sessionHelper.getPlatformSession();
			session.beginTransaction();
			StringBuilder builder = new StringBuilder(
					"select count(*) from User where userRoleId = "+roleid);
			Query query = session.createQuery(builder.toString());
			count = ((Number) query.uniqueResult()).intValue();
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Count user numbers of same role failed.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return count;
	}
	
	private void loginfo(String message){
		LogConstant.loginfo(message);
	}

	@Override
	public boolean updateUser(User user) {
		boolean result = false;
		Session session = null;
		try {
			session = sessionHelper.getPlatformSession();
			session.beginTransaction();
			session.update(user);
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Count user numbers of same role failed.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return result;
	}

	@Override
	public boolean updateAreaInfo(Integer userId, String userActiveLocation) {
		boolean result = false;
		Session session = null;
		try {
			
			session = sessionHelper.getPlatformSession();
			session.beginTransaction();
			Query query = session.createQuery("update User set userActiveLocation= :userActiveLocation where userId = :userId");
			
			query.setInteger("userId", userId);
			query.setString("userActiveLocation", userActiveLocation);
			query.executeUpdate();
			session.getTransaction().commit();
			result = true;
		} catch (Exception e) {
			loginfo("update userActiveLocation failed.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return result;
	}
	
	/**
	 * 更新管理员账户
	 * 
	 * @param userid
	 * @param userName
	 * @param userMail
	 * @param userPhone
	 * @return
	 */
	public boolean updateAdmin(Integer userid, String userPass, String userMail,
			String userPhone) {
		boolean result = false;
		Session session = null;
		try {
			session = sessionHelper.getPlatformSession();
			session.beginTransaction();
			Query query = session
					.createQuery("update User set userPass= :pass, userMail= :mail, "+ "userPhone = :phone "+ "where userId = :id");
			query.setString("pass", hashHelper.md5Hash(userPass));
			query.setString("mail", userMail);
			query.setString("phone", userPhone);
			query.setInteger("id", userid);
			query.executeUpdate();
			session.getTransaction().commit();
			result = true;
		} catch (Exception e) {
			loginfo("Update user infos failed.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return result;
	}

	@Override
	public Integer getEnterpriseCount(Date endTime) {
		Integer count=0;
		Session session = null;
		try {
			session = sessionHelper.getPlatformSession();
			session.beginTransaction();
			StringBuilder builder = new StringBuilder(
					"select count(*) from User where userLevel = 1 and userDate<:endTime");
			Query query = session.createQuery(builder.toString());
			query.setDate("endTime", endTime);
			count = ((Number) query.uniqueResult()).intValue();
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Count Enterprise numbers of same role failed.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return count;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getEnterpriseList(int num) {
		List<User> list = new ArrayList<User>();
		Session session = null;
		try {
			session = sessionHelper.getPlatformSession();
			session.beginTransaction();
			String queryStr = "from User where userLevel = 1 order by userDate desc";
			Query query = session.createQuery(queryStr);
			query.setMaxResults(num);
			list = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Get all user list failed.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return list;
	}

	@Override
	public boolean userSetMap(String userid, String lng, String lat) {
		boolean result = false;
		Session session = null;
		try {
			session = sessionHelper.getPlatformSession();
			session.beginTransaction();
			Query query = session
					.createQuery("update User set lng= :lng, lat= :lat where userId = :id");
			query.setString("lng", "".equals(lng)?null:lng);
			query.setString("lat", "".equals(lat)?null:lat);
			query.setInteger("id", Integer.parseInt(userid));
			query.executeUpdate();
			session.getTransaction().commit();
			result = true;
		} catch (Exception e) {
			loginfo("Update user infos failed.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return result;
	}
	
	public boolean addUser(User user) {
		boolean result = false;
		if (user != null) {
			Session session = null;
			try {
				session = sessionHelper.getPlatformSession();
				session.beginTransaction();
				session.save(user);
				session.getTransaction().commit();
				result = true;
			} catch (Exception e) {
				e.printStackTrace();
				if (session.isOpen()) {
					session.getTransaction().rollback();
				}
			}
		}
		return result;
	}

	public boolean removeUser(User user) {
		boolean result = false;
		if (user != null) {
			Session session = null;
			try {
				session = sessionHelper.getPlatformSession();
				session.beginTransaction();
				session.delete(user);
				session.getTransaction().commit();
				result = true;
			} catch (Exception e) {
				e.printStackTrace();
				if (session.isOpen()) {
					session.getTransaction().rollback();
				}
			}
		}
		return result;
	}
}
