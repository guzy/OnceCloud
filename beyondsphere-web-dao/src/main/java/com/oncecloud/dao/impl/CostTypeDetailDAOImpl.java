package com.oncecloud.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import com.oncecloud.constants.LogConstant;
import com.oncecloud.dao.CostTypeDetailDAO;
import com.oncecloud.entity.CostTypeDetail;
import com.oncecloud.helper.SessionHelper;


/**
 * @author zll
 * @Date 2015年5月11日
 */

@Component("CostTypeDetailDAO")
public class CostTypeDetailDAOImpl implements CostTypeDetailDAO {

	@Resource
	private SessionHelper sessionHelper;

	public boolean add(CostTypeDetail costTypeDetail)  {
		boolean result = false;
		if (costTypeDetail != null) {
			Session session = null;
			try {
				session = sessionHelper.getMainSession();
				session.beginTransaction();
				session.save(costTypeDetail);
				session.getTransaction().commit();
				result = true;
			} catch (Exception e) {
				loginfo("Add costTypeDetail failed.");
				e.printStackTrace();
				if (session.isOpen()) {
					session.getTransaction().rollback();
				}
			}
		}
		return result;
	}

	public boolean edit(CostTypeDetail costTypeDetail) {
		boolean result = false;
		if (costTypeDetail != null) {
			Session session = null;
			try {
				session = sessionHelper.getMainSession();
				session.beginTransaction();
				session.saveOrUpdate(costTypeDetail);
				session.getTransaction().commit();
				result = true;
			} catch (Exception e) {
				loginfo("Edit costTypeDetail failed.");
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
		Query query = session.createQuery("from CostTypeDetail where costTypeDetailId = :costTypeDetailId");
		query.setString("costTypeDetailId", id);
		CostTypeDetail costTypeDetail = (CostTypeDetail) query.uniqueResult();
		if (costTypeDetail != null) {
			try {
				session.delete(costTypeDetail);
				session.getTransaction().commit();
				result = true;
			} catch (Exception e) {
				 loginfo("remove CostTypeDetail failed.");
				e.printStackTrace();
				if (session.isOpen()) {
					session.getTransaction().rollback();
				}
			}
		}
		return result;
	}

	public CostTypeDetail getOneByID(String id) {
		CostTypeDetail costTypeDetail = null;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			Query query = session.createQuery("from CostTypeDetail where costTypeDetailId = "
					+ id + "");
			costTypeDetail = (CostTypeDetail) query.uniqueResult();
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Get CostTypeDetail failed by costTypeDetailId. ");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return costTypeDetail;
	}

	@SuppressWarnings("unchecked")
	public List<CostTypeDetail> getPagedList(int page, int limit, String search) {
		List<CostTypeDetail> costTypeDetailList = null;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			int startPos = (page - 1) * limit;
			String queryString = "from CostTypeDetail where costTypeDetailName like :search order by costTypeDetailId desc";
			Query query = session.createQuery(queryString);
			query.setString("search", "%" + search + "%");
			query.setFirstResult(startPos);
			query.setMaxResults(limit);
			costTypeDetailList = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Get CostTypeDetail list of Page failed.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return costTypeDetailList;
	}

	public int countAllList(String search) {
		int count = 0;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			StringBuilder builder = new StringBuilder(
					"select count(*) from CostTypeDetail where costTypeDetailName like :search ");
			Query query = session.createQuery(builder.toString());
			query.setString("search", "%" + search + "%");
			count = ((Number) query.uniqueResult()).intValue();
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Count CostTypeDetail list failed.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return count;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CostTypeDetail> getAllList() {
		List<CostTypeDetail> costTypeDetailList = null;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String queryString = "from CostTypeDetail";
			Query query = session.createQuery(queryString);
			costTypeDetailList = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Get all CostTypeDetail list of Page failed.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return costTypeDetailList;
	}

	private void loginfo(String message){
		LogConstant.loginfo(message);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CostTypeDetail> getListByTypeId(String typeId) {
		List<CostTypeDetail> costTypeDetailList = null;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String queryString = "from CostTypeDetail where costTypeId=:typeId";
			Query query = session.createQuery(queryString);
			query.setString("typeId", typeId);
			costTypeDetailList = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Get all CostTypeDetail list of Page failed.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return costTypeDetailList;
	}

}
