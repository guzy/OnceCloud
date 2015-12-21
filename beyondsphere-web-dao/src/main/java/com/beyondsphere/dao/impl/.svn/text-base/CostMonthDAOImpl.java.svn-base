package com.beyondsphere.dao.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import com.beyondsphere.constants.LogConstant;
import com.beyondsphere.dao.CostMonthDAO;
import com.beyondsphere.entity.CostMonth;
import com.beyondsphere.helper.SessionHelper;


/**
 * @author zll
 * @Date 2015年5月27日
 */

@Component("CostMonthDAO")
public class CostMonthDAOImpl implements CostMonthDAO {

	@Resource
	private SessionHelper sessionHelper;

	public boolean add(CostMonth costMonth)  {
		boolean result = false;
		if (costMonth != null) {
			Session session = null;
			try {
				session = sessionHelper.getMainSession();
				session.beginTransaction();
				session.save(costMonth);
				session.getTransaction().commit();
				result = true;
			} catch (Exception e) {
				loginfo("Add costMonth failed.");
				e.printStackTrace();
				if (session.isOpen()) {
					session.getTransaction().rollback();
				}
			}
		}
		return result;
	}

	public boolean edit(CostMonth costMonth) {
		boolean result = false;
		if (costMonth != null) {
			Session session = null;
			try {
				session = sessionHelper.getMainSession();
				session.beginTransaction();
				session.saveOrUpdate(costMonth);
				session.getTransaction().commit();
				result = true;
			} catch (Exception e) {
				loginfo("Edit CostMonth failed.");
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
	public List<CostMonth> getAllList(Date start,Date end) {
		List<CostMonth> list = null;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String queryString = "from CostMonth where createTime between :start and :end order by totalCost desc";
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
		Query query = session.createQuery("from CostMonth where id = :cid");
		query.setInteger("cid", cid);
		CostMonth costMonth = (CostMonth) query.uniqueResult();
		if (costMonth != null) {
			try {
				session.delete(costMonth);
				session.getTransaction().commit();
				result = true;
			} catch (Exception e) {
				 loginfo("remove CostMonth failed.");
				e.printStackTrace();
				if (session.isOpen()) {
					session.getTransaction().rollback();
				}
			}
		}
		return result;
	}
}
