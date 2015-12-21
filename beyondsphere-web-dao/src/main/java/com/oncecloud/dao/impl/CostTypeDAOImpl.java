package com.oncecloud.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import com.oncecloud.constants.LogConstant;
import com.oncecloud.dao.CostTypeDAO;
import com.oncecloud.entity.CostType;
import com.oncecloud.helper.SessionHelper;


/**
 * @author zll
 * @Date 2015年5月11日
 */

@Component("CostTypeDAO")
public class CostTypeDAOImpl implements CostTypeDAO {

	@Resource
	private SessionHelper sessionHelper;

	public boolean add(CostType costType)  {
		boolean result = false;
		if (costType != null) {
			Session session = null;
			try {
				session = sessionHelper.getMainSession();
				session.beginTransaction();
				session.save(costType);
				session.getTransaction().commit();
				result = true;
			} catch (Exception e) {
				loginfo("Add costType failed.");
				e.printStackTrace();
				if (session.isOpen()) {
					session.getTransaction().rollback();
				}
			}
		}
		return result;
	}

	public boolean edit(CostType costType) {
		boolean result = false;
		if (costType != null) {
			Session session = null;
			try {
				session = sessionHelper.getMainSession();
				session.beginTransaction();
				session.saveOrUpdate(costType);
				session.getTransaction().commit();
				result = true;
			} catch (Exception e) {
				loginfo("Edit costType failed.");
				e.printStackTrace();
				if (session.isOpen()) {
					session.getTransaction().rollback();
				}
			}
		}
		return result;
	}

	public boolean remove(String id) {
		boolean result = false;
		Session session = null;
		session = sessionHelper.getMainSession();
		session.beginTransaction();
		Query query = session.createQuery("from CostType where costTypeId = :costTypeId");
		query.setString("costTypeId", id);
		CostType costType = (CostType) query.uniqueResult();
		if (costType != null) {
			try {
				session.delete(costType);
				session.getTransaction().commit();
				result = true;
			} catch (Exception e) {
				 loginfo("remove costType failed.");
				e.printStackTrace();
				if (session.isOpen()) {
					session.getTransaction().rollback();
				}
			}
		}
		return result;
	}

	public CostType getOneByID(String id) {
		CostType costType = null;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			Query query = session.createQuery("from CostType where costTypeId = :id");
			query.setString("id", id);
			costType = (CostType) query.uniqueResult();
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Get costType failed by actionId. ");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return costType;
	}

	@SuppressWarnings("unchecked")
	public List<CostType> getPagedList(int page, int limit, String search) {
		List<CostType> costTypeList = null;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			int startPos = (page - 1) * limit;
			String queryString = "from CostType where costTypeName like :search order by costTypeId desc";
			Query query = session.createQuery(queryString);
			query.setString("search", "%" + search + "%");
			query.setFirstResult(startPos);
			query.setMaxResults(limit);
			costTypeList = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Get action list of Page failed.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return costTypeList;
	}

	public int countAllList(String search) {
		int count = 0;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			StringBuilder builder = new StringBuilder(
					"select count(*) from CostType where costTypeName like :search ");
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
		}
		return count;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CostType> getAllList() {
		List<CostType> costTypeList = null;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String queryString = "from CostType";
			Query query = session.createQuery(queryString);
			costTypeList = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Get all CostType list of Page failed.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return costTypeList;
	}

	private void loginfo(String message){
		LogConstant.loginfo(message);
	}

}
