package com.oncecloud.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import com.oncecloud.constants.LogConstant;
import com.oncecloud.dao.RoleDAO;
import com.oncecloud.entity.OCRole;
import com.oncecloud.helper.SessionHelper;


/**
 * @author 玉和
 * 
 * @Date 2014年12月4日
 */
@Component("RoleDAO")
public class RoleDAOImpl implements RoleDAO {

	@Resource
	private SessionHelper sessionHelper;

	public boolean addOCRole(OCRole role) {
		boolean result = false;
		if (role != null) {
			Session session = null;
			try {
				session = sessionHelper.getPlatformSession();
				session.beginTransaction();
				session.save(role);
				session.getTransaction().commit();
				result = true;
			} catch (Exception e) {
				loginfo("Insert role failed");
				e.printStackTrace();
				if (session.isOpen()) {
					session.getTransaction().rollback();
				}
			}
		}
		return result;
	}

	public boolean editOCRole(OCRole role) {
		boolean result = false;
		if (role != null) {
			Session session = null;
			try {
				session = sessionHelper.getPlatformSession();
				session.beginTransaction();
				session.saveOrUpdate(role);
				session.getTransaction().commit();
				result = true;
			} catch (Exception e) {
				loginfo("Edit role failed");
				e.printStackTrace();
				if (session.isOpen()) {
					session.getTransaction().rollback();
				}
			}
		}
		return result;
	}

	public boolean removeOCRole(OCRole role) {
		boolean result = false;
		if (role != null) {
			Session session = null;
			try {
				session = sessionHelper.getPlatformSession();
				session.beginTransaction();
				session.delete(role);
				session.getTransaction().commit();
				result = true;
			} catch (Exception e) {
				loginfo("Remove role failed");
				e.printStackTrace();
				if (session.isOpen()) {
					session.getTransaction().rollback();
				}
			}
		}
		return result;
	}

	public OCRole getRoleByID(int id) {
		OCRole role = null;
		Session session = null;
		try {
			session = sessionHelper.getPlatformSession();
			session.beginTransaction();
			Query query = session.createQuery("from OCRole where roleId = "
					+ id + "");
			role = (OCRole) query.uniqueResult();
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Get role infos failed by id.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return role;
	}

	@SuppressWarnings("unchecked")
	public List<OCRole> getPagedRoleList(int page, int limit, String search) {
		List<OCRole> roleList = null;
		Session session = null;
		try {
			session = sessionHelper.getPlatformSession();
			session.beginTransaction();
			int startPos = (page - 1) * limit;
			String queryString = "from OCRole where roleName like :search order by roleId desc";
			Query query = session.createQuery(queryString);
			query.setString("search", "%" + search + "%");
			query.setFirstResult(startPos);
			query.setMaxResults(limit);
			roleList = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Get role list failed by page.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return roleList;
	}

	public int countAllRoleList(String search) {
		int count = 0;
		Session session = null;
		try {
			session = sessionHelper.getPlatformSession();
			session.beginTransaction();
			StringBuilder builder = new StringBuilder(
					"select count(*) from OCRole where roleName like :search ");
			Query query = session.createQuery(builder.toString());
			query.setString("search", "%" + search + "%");
			count = ((Number) query.uniqueResult()).intValue();
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Count role number failed.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return count;
	}

	public OCRole getRole(String roleName) {
		OCRole role = null;
		Session session = null;
		try {
			session = sessionHelper.getPlatformSession();
			session.beginTransaction();
			Query query = session.createQuery("from OCRole where roleName = '" + roleName + "'");
			role = (OCRole) query.uniqueResult();
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Get role infos failed.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return role;
	}
	
	private void loginfo(String message){
		LogConstant.loginfo(message);
	}

}
