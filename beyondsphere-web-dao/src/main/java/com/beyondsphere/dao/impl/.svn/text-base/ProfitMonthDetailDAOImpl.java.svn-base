package com.beyondsphere.dao.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import com.beyondsphere.constants.LogConstant;
import com.beyondsphere.dao.ProfitMonthDetailDAO;
import com.beyondsphere.entity.ProfitMonthDetail;
import com.beyondsphere.helper.SessionHelper;


/**
 * @author zll
 * @Date 2015年5月27日
 */

@Component("ProfitMonthDetailDAO")
public class ProfitMonthDetailDAOImpl implements ProfitMonthDetailDAO {

	@Resource
	private SessionHelper sessionHelper;

	public boolean add(ProfitMonthDetail profitMonthDetail)  {
		boolean result = false;
		if (profitMonthDetail != null) {
			Session session = null;
			try {
				session = sessionHelper.getMainSession();
				session.beginTransaction();
				session.save(profitMonthDetail);
				session.getTransaction().commit();
				result = true;
			} catch (Exception e) {
				loginfo("Add profitMonthDetail failed.");
				e.printStackTrace();
				if (session.isOpen()) {
					session.getTransaction().rollback();
				}
			}
		}
		return result;
	}

	public boolean edit(ProfitMonthDetail profitMonthDetail) {
		boolean result = false;
		if (profitMonthDetail != null) {
			Session session = null;
			try {
				session = sessionHelper.getMainSession();
				session.beginTransaction();
				session.saveOrUpdate(profitMonthDetail);
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
	public List<ProfitMonthDetail> getAllList(Date start,Date end) {
		List<ProfitMonthDetail> list = null;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String queryString = "from ProfitMonthDetail where createTime between :start and :end order by createTime";
			Query query = session.createQuery(queryString);
			query.setDate("start", start);
			query.setDate("end", end);
			list = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Get all ProfitMonthDetail list of Page failed.");
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
		Query query = session.createQuery("from ProfitMonthDetail where id = :cid");
		query.setInteger("cid", cid);
		ProfitMonthDetail obj = (ProfitMonthDetail) query.uniqueResult();
		if (obj != null) {
			try {
				session.delete(obj);
				session.getTransaction().commit();
				result = true;
			} catch (Exception e) {
				 loginfo("remove ProfitMonthDetail failed.");
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
	public List<ProfitMonthDetail> getAllListByUserId(int userId) {
		List<ProfitMonthDetail> list = null;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String queryString = "from ProfitMonthDetail where userId=:userId order by id";
			Query query = session.createQuery(queryString);
			query.setInteger("userId", userId);
			list = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Get all ProfitMonthDetail list of Page failed.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProfitMonthDetail> getAllListByDetailId(String costDetailId) {
		List<ProfitMonthDetail> list = null;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String queryString = "from ProfitMonthDetail where costDetailId=:costDetailId order by id";
			Query query = session.createQuery(queryString);
			query.setString("costDetailId", costDetailId);
			list = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Get all ProfitMonthDetail list of Page failed.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProfitMonthDetail> getAllListByUserid(Date start, Date end, int userid) {
			List<ProfitMonthDetail> list = null;
			Session session = null;
			try {
				session = sessionHelper.getMainSession();
				session.beginTransaction();
				String queryString = "from ProfitMonthDetail where createTime between :start and :end and userId=:userid order by createTime desc";
				Query query = session.createQuery(queryString);
				query.setDate("start", start);
				query.setDate("end", end);
				query.setInteger("userid", userid);
				list = query.list();
				session.getTransaction().commit();
			} catch (Exception e) {
				loginfo("Get all ProfitMonthDetail list of Page failed.");
				e.printStackTrace();
				if (session.isOpen()) {
					session.getTransaction().rollback();
				}
			}
			return list;
	}
	
}
