package com.beyondsphere.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import com.beyondsphere.constants.LogConstant;
import com.beyondsphere.dao.ActionDAO;
import com.beyondsphere.entity.OCAction;
import com.beyondsphere.helper.SessionHelper;


/**
 * @author 玉和
 * 
 * @Date 2014年12月4日
 */
@Component("ActionDAO")
public class ActionDAOImpl implements ActionDAO {

	@Resource
	private SessionHelper sessionHelper;

	public boolean addOCAction(OCAction action)  {
		Session session = null;
		if (action != null) {
			try {
				session = sessionHelper.getPlatformSession();
				session.beginTransaction();
				session.save(action);
				session.getTransaction().commit();
				return true;
			} catch (Exception e) {
				loginfo("Add oction failed.");
				e.printStackTrace();
				if (session.isOpen()) {
					session.getTransaction().rollback();
				}
			} finally{
				if (session.isOpen()) {
					session.close();
				}
			}
		}
		return false;
	}

	public boolean editOCAction(OCAction action) {
		Session session = null;
		if (action != null) {
			try {
				session = sessionHelper.getPlatformSession();
				session.beginTransaction();
				session.saveOrUpdate(action);
				session.getTransaction().commit();
				return true;
			} catch (Exception e) {
				loginfo("Edit action failed.");
				e.printStackTrace();
				if (session.isOpen()) {
					session.getTransaction().rollback();
				}
			} finally{
				if (session.isOpen()) {
					session.close();
				}
			}
		}
		return false;
	}

	public boolean removeOCAction(OCAction action) {
		if (action != null) {
			Session session = null;
			try {
				session = sessionHelper.getPlatformSession();
				session.beginTransaction();
				session.delete(action);
				session.getTransaction().commit();
				return true;
			} catch (Exception e) {
				loginfo("Delete action of user failed.");
				e.printStackTrace();
				if (session.isOpen()) {
					session.getTransaction().rollback();
				}
			} finally{
				if (session.isOpen()) {
					session.close();
				}
			}
		}
		return false;
	}

	public OCAction getActionByID(int id) {
		OCAction action = null;
		Session session = null;
		try {
			session = sessionHelper.getPlatformSession();
			session.beginTransaction();
			Query query = session.createQuery("from OCAction where actionId = "
					+ id + "");
			action = (OCAction) query.uniqueResult();
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Get action failed by actionId. ");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		} finally{
			if (session.isOpen()) {
				session.close();
			}
		}
		return action;
	}

	@SuppressWarnings("unchecked")
	public List<OCAction> getPagedActionList(int page, int limit, String search) {
		List<OCAction> actionList = null;
		Session session = null;
		try {
			session = sessionHelper.getPlatformSession();
			session.beginTransaction();
			int startPos = (page - 1) * limit;
			String queryString = "from OCAction where actionName like :search order by actionId desc";
			Query query = session.createQuery(queryString);
			query.setString("search", "%" + search + "%");
			query.setFirstResult(startPos);
			query.setMaxResults(limit);
			actionList = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Get action list of Page failed.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		} finally{
			if (session.isOpen()) {
				session.close();
			}
		}
		return actionList;
	}

	public int countAllActionList(String search) {
		int count = 0;
		Session session = null;
		try {
			session = sessionHelper.getPlatformSession();
			session.beginTransaction();
			StringBuilder builder = new StringBuilder(
					"select count(*) from OCAction where actionName like :search ");
			Query query = session.createQuery(builder.toString());
			query.setString("search", "%" + search + "%");
			count = ((Number) query.uniqueResult()).intValue();
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Count action list failed.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		} finally{
			if (session.isOpen()) {
				session.close();
			}
		}
		return count;
	}
	
	private void loginfo(String message){
		LogConstant.loginfo(message);
	}
}
