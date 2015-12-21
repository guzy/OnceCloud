package com.beyondsphere.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import com.beyondsphere.constants.LogConstant;
import com.beyondsphere.dao.RoleActionDAO;
import com.beyondsphere.entity.RoleAction;
import com.beyondsphere.helper.SessionHelper;


/**
 * @author 玉和
 * 
 * @Date 2014年12月4日
 */
@Component("RoleActionDAO")
public class RoleActionDAOImpl implements RoleActionDAO {

	@Resource
	private SessionHelper sessionHelper;

	public boolean addRoleAction(RoleAction roleAction) {
		boolean result = false;
		if (roleAction != null) {
			Session session = null;
			try {
				session = sessionHelper.getPlatformSession();
				session.beginTransaction();
				session.save(roleAction);
				session.getTransaction().commit();
				result = true;
			} catch (Exception e) {
				loginfo("Insert role action infos failed.");
				e.printStackTrace();
				if (session.isOpen()) {
					session.getTransaction().rollback();
				}
			}
		}
		return result;
	}

	public boolean editRoleAction(RoleAction roleAction) {
		boolean result = false;
		if (roleAction != null) {
			Session session = null;
			try {
				session = sessionHelper.getPlatformSession();
				session.beginTransaction();
				session.saveOrUpdate(roleAction);
				session.getTransaction().commit();
				result = true;
			} catch (Exception e) {
				loginfo("Edit role action infos failed.");
				e.printStackTrace();
				if (session.isOpen()) {
					session.getTransaction().rollback();
				}
			}
		}
		return result;
	}

	public boolean removeRoleAction(RoleAction roleAction) {
		boolean result = false;
		if (roleAction != null) {
			Session session = null;
			try {
				session = sessionHelper.getPlatformSession();
				session.beginTransaction();
				session.delete(roleAction);
				session.getTransaction().commit();
				result = true;
			} catch (Exception e) {
				loginfo("Remove role action failed.");
				e.printStackTrace();
				if (session.isOpen()) {
					session.getTransaction().rollback();
				}
			}
		}
		return result;
	}

	public RoleAction getRoleActionByID(int id) {
		RoleAction roleAction = null;
		Session session = null;
		try {
			session = sessionHelper.getPlatformSession();
			session.beginTransaction();
			Query query = session.createQuery("from RoleAction where id = "
					+ id + "");
			roleAction = (RoleAction) query.uniqueResult();
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Get role action failed by id.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return roleAction;
	}

	@SuppressWarnings("unchecked")
	public List<RoleAction> getPagedRoleActionList(int page, int limit,
			int roleId) {
		List<RoleAction> roleActionList = null;
		Session session = null;
		try {
			session = sessionHelper.getPlatformSession();
			session.beginTransaction();
			int startPos = (page - 1) * limit;
			String queryString = "from RoleAction where roleId = :roleId order by roleId desc";
			Query query = session.createQuery(queryString);
			query.setInteger("roleId", roleId);
			query.setFirstResult(startPos);
			query.setMaxResults(limit);
			roleActionList = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Get role action list failed by page");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return roleActionList;
	}

	public int countAllRoleActionList(int roleId) {
		int count = 0;
		Session session = null;
		try {
			session = sessionHelper.getPlatformSession();
			session.beginTransaction();
			StringBuilder builder = new StringBuilder(
					"select count(*) from OCRole where roleId = :roleId ");
			Query query = session.createQuery(builder.toString());
			query.setInteger("roleId", roleId);
			count = ((Number) query.uniqueResult()).intValue();
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Count role action numbers failed.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return count;
	}

	public boolean deleteByActionId(int actionId) {
		boolean result = false;
		Session session = null;
		try {
			session = sessionHelper.getPlatformSession();
			session.beginTransaction();
			StringBuilder builder = new StringBuilder(
					"delete from RoleAction where actionId = :actionId ");
			Query query = session.createQuery(builder.toString());
			query.setInteger("actionId", actionId);
			query.executeUpdate();
			session.getTransaction().commit();
			result = true;
		} catch (Exception e) {
			loginfo("Delete role action failed by id.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return result;
	}

	public boolean deleteByRoleId(int roleid) {
		boolean result = false;
		Session session = null;
		try {
			session = sessionHelper.getPlatformSession();
			session.beginTransaction();
			StringBuilder builder = new StringBuilder(
					"delete from RoleAction where roleId = :roleId ");
			Query query = session.createQuery(builder.toString());
			query.setInteger("roleId", roleid);
			query.executeUpdate();
			session.getTransaction().commit();
			result = true;
		} catch (Exception e) {
			loginfo("Delete user role infos failed.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return result;
	}

	private void loginfo(String message){
		LogConstant.loginfo(message);
	}
}
