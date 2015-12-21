package com.beyondsphere.dao.impl;

import javax.annotation.Resource;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import com.beyondsphere.dao.QuotaDAO;
import com.beyondsphere.entity.Quota;
import com.beyondsphere.helper.SessionHelper;
@Component("QuotaDAO")
public class QuotaDAOImpl implements QuotaDAO {
	@Resource
	private SessionHelper sessionHelper;

	/**
	 * 获取已用的配额
	 * 
	 * @param userId
	 * @return
	 */
	public Quota getQuotaUsed(int userId) {
		Quota quota = null;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			Query query = session
					.createQuery("from Quota where quotaUID = :userId and quotaType = 1");
			query.setInteger("userId", userId);
			quota = (Quota) query.uniqueResult();
			session.getTransaction().commit();
			return quota;
		} catch (Exception e) {
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return quota;
	}

	/**
	 * 获取全部的配额
	 * 
	 * @param userId
	 * @return
	 */
	public Quota getQuotaTotal(int userId) {
		Quota quota = null;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			Query query = session
					.createQuery("from Quota where quotaUID = :userId and quotaType = 0");
			query.setInteger("userId", userId);
			quota = (Quota) query.uniqueResult();
			session.getTransaction().commit();
		} catch (Exception e) {
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return quota;
	}

	/**
	 * 获取全部的配额（不包含事务）
	 * 
	 * @param userId
	 * @return
	 */
	public Quota getQuotaTotalNoTransaction(int userId) {
		Session session = sessionHelper.getMainSession();
		Query query = session
				.createQuery("from Quota where quotaUID = :userId and quotaType = 0");
		query.setInteger("userId", userId);
		return (Quota) query.uniqueResult();
	}

	/**
	 * 更新配额（不包含事务）
	 * 
	 * @param userId
	 * @param filedName
	 * @param size
	 * @param isadd
	 */
	public synchronized void updateQuotaFieldNoTransaction(int userId,
			String filedName, int size, boolean isadd) {
		Session session = sessionHelper.getMainSession();
		String setString = filedName + "=" + filedName + "+" + size;
		if (isadd == false) {
			setString = filedName + "=" + filedName + "-" + size;
		}
		String queryString = String
				.format("update Quota set %s where quotaUID = :userId and quotaType = 1",
						setString);
		Query query = session.createQuery(queryString);
		query.setInteger("userId", userId);
		query.executeUpdate();
	}

	/**
	 * 初始化配额（不包含事务）
	 * 
	 * @param userId
	 * @throws Exception
	 */
	public void initQuotaNoTransaction(Integer userId){
		Session session = sessionHelper.getMainSession();
		if (userId == 1) {
			Quota quota = new Quota(1, 2, 5, 5, 5, 10, 100, 10, 10, 2, 5, 3,
					10, 20, 10, 0);
			session.save(quota);
		} else {
			Quota def = new Quota(1, 2, 5, 5, 5, 10, 100, 10, 10, 2, 5, 3,
					10, 20, 10, 0);
			def.setQuotaUID(userId);
			Quota current = new Quota(userId, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0,
					0, 0, 0, 1);
			session.save(def);
			session.save(current);
		}
	}

	/**
	 * 更新配额
	 * 
	 * @param newQuota
	 * @return
	 */
	public boolean updateQuota(Quota newQuota) {
		boolean result = false;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			session.update(newQuota);
			session.getTransaction().commit();
			result = true;
		} catch (Exception e) {
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return result;
	}
}
