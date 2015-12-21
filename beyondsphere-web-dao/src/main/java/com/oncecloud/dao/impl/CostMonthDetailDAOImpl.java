package com.oncecloud.dao.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import com.oncecloud.constants.LogConstant;
import com.oncecloud.dao.CostMonthDetailDAO;
import com.oncecloud.entity.CostMonthDetail;
import com.oncecloud.helper.SessionHelper;


/**
 * @author zll
 * @Date 2015年5月27日
 */

@Component("CostMonthDetailDAO")
public class CostMonthDetailDAOImpl implements CostMonthDetailDAO {

	@Resource
	private SessionHelper sessionHelper;

	public boolean add(CostMonthDetail costMonthDetail)  {
		boolean result = false;
		if (costMonthDetail != null) {
			Session session = null;
			try {
				session = sessionHelper.getMainSession();
				session.beginTransaction();
				session.save(costMonthDetail);
				session.getTransaction().commit();
				result = true;
			} catch (Exception e) {
				loginfo("Add costMonthDetail failed.");
				e.printStackTrace();
				if (session.isOpen()) {
					session.getTransaction().rollback();
				}
			}
		}
		return result;
	}

	public boolean edit(CostMonthDetail costMonthDetail) {
		boolean result = false;
		if (costMonthDetail != null) {
			Session session = null;
			try {
				session = sessionHelper.getMainSession();
				session.beginTransaction();
				session.saveOrUpdate(costMonthDetail);
				session.getTransaction().commit();
				result = true;
			} catch (Exception e) {
				loginfo("Edit costMonthDetail failed.");
				e.printStackTrace();
				if (session.isOpen()) {
					session.getTransaction().rollback();
				}
			}
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CostMonthDetail> getAllList(Date start,Date end) {
		List<CostMonthDetail> list = null;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String queryString = "from CostMonthDetail where createTime between :start and :end order by createTime";
			Query query = session.createQuery(queryString);
			query.setDate("start", start);
			query.setDate("end", end);
			list = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Get all CostMonth list of Page failed.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return list;
	}
	
	private void loginfo(String message){
		LogConstant.loginfo(message);
	}


	public boolean remove(int cid) {
		boolean result = false;
		Session session = null;
		session = sessionHelper.getMainSession();
		session.beginTransaction();
		Query query = session.createQuery("from CostMonthDetail where id = :cid");
		query.setInteger("cid", cid);
		CostMonthDetail costMonthDetail = (CostMonthDetail) query.uniqueResult();
		if (costMonthDetail != null) {
			try {
				session.delete(costMonthDetail);
				session.getTransaction().commit();
				result = true;
			} catch (Exception e) {
				 loginfo("remove CostMonthDetail failed.");
				e.printStackTrace();
				if (session.isOpen()) {
					session.getTransaction().rollback();
				}
			}
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CostMonthDetail> getAllListByTypeId(String costTypeId) {
		List<CostMonthDetail> list = null;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String queryString = "from CostMonthDetail where costTypeId=:costTypeId order by id";
			Query query = session.createQuery(queryString);
			query.setString("costTypeId", costTypeId);
			list = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Get all CostMonthDetail list of Page failed.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CostMonthDetail> getAllListByDetailId(String costDetailId) {
		List<CostMonthDetail> list = null;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String queryString = "from CostMonthDetail where costDetailId=:costDetailId order by id";
			Query query = session.createQuery(queryString);
			query.setString("costDetailId", costDetailId);
			list = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Get all CostMonthDetail list of Page failed.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CostMonthDetail> getListByTypeId(Date start,Date end,String typeid) {
		List<CostMonthDetail> list = null;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String queryString = "from CostMonthDetail where createTime between :start and :end and costTypeId=:typeid order by createTime";
			Query query = session.createQuery(queryString);
			query.setDate("start", start);
			query.setDate("end", end);
			query.setString("typeid", typeid);
			list = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Get all CostMonth list of Page failed.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return list;
	}
}
