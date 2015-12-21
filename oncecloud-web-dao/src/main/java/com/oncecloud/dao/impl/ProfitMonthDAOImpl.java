package com.oncecloud.dao.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import com.oncecloud.constants.LogConstant;
import com.oncecloud.dao.ProfitMonthDAO;
import com.oncecloud.entity.ProfitMonth;
import com.oncecloud.helper.SessionHelper;


/**
 * @author zll
 * @Date 2015年5月27日
 */

@Component("ProfitMonthDAO")
public class ProfitMonthDAOImpl implements ProfitMonthDAO {

	@Resource
	private SessionHelper sessionHelper;

	public boolean add(ProfitMonth profitMonth)  {
		boolean result = false;
		if (profitMonth != null) {
			Session session = null;
			try {
				session = sessionHelper.getMainSession();
				session.beginTransaction();
				session.save(profitMonth);
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

	public boolean edit(ProfitMonth profitMonth) {
		boolean result = false;
		if (profitMonth != null) {
			Session session = null;
			try {
				session = sessionHelper.getMainSession();
				session.beginTransaction();
				session.saveOrUpdate(profitMonth);
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
	public List<ProfitMonth> getAllList(Date start,Date end) {
		List<ProfitMonth> list = null;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String queryString = "from ProfitMonth where createTime between :start and :end order by profitTotal desc";
			Query query = session.createQuery(queryString);
			query.setDate("start", start);
			query.setDate("end", end);
			list = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Get all ProfitMonth list of Page failed.");
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


	public boolean remove(Date startOfMonth,Date endOfMonth) {
		boolean result = false;
		Session session = null;
		session = sessionHelper.getMainSession();
		session.beginTransaction();
		Query query = session.createQuery("from ProfitMonth where createTime between :start and :end order by profitTotal desc");
		query.setDate("start", startOfMonth);
		query.setDate("end", endOfMonth);
		ProfitMonth profitMonth = (ProfitMonth) query.uniqueResult();
		if (profitMonth != null) {
			try {
				session.delete(profitMonth);
				session.getTransaction().commit();
				result = true;
			} catch (Exception e) {
				 loginfo("remove ProfitMonth failed.");
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
	public List<ProfitMonth> getAllListbyUserid(Date start, Date end, int userid) {
		List<ProfitMonth> list = null;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String queryString = "from ProfitMonth where createTime between :start and :end and userId=:userid order by profitTotal desc";
			Query query = session.createQuery(queryString);
			query.setDate("start", start);
			query.setDate("end", end);
			query.setInteger("userid", userid);
			list = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Get all ProfitMonth list of Page failed.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return list;
	}
}
