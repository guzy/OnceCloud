package com.beyondsphere.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import com.beyondsphere.constants.LogConstant;
import com.beyondsphere.dao.ISODAO;
import com.beyondsphere.entity.ISO;
import com.beyondsphere.helper.SessionHelper;


/**
 * @author 玉和
 * 
 * @Date 2014年12月13日
 */
@Component("ISODAO")
public class ISODAOImpl implements ISODAO{

	@Resource
	private SessionHelper sessionHelper;

	public boolean addISO(ISO iso) {
		boolean result = false;
		if (iso != null) {
			Session session = null;
			try {
				session = sessionHelper.getMainSession();
				session.beginTransaction();
				session.save(iso);
				session.getTransaction().commit();
				result = true;
			} catch (Exception e) {
				loginfo("Add ISO infos failed.");
				e.printStackTrace();
				if (session.isOpen()) {
					session.getTransaction().rollback();
				}
			}
		}
		return result;
	}

	public boolean editISO(ISO iso) {
		boolean result = false;
		if (iso != null) {
			Session session = null;
			try {
				session = sessionHelper.getMainSession();
				session.beginTransaction();
				session.saveOrUpdate(iso);
				session.getTransaction().commit();
				result = true;
			} catch (Exception e) {
				loginfo("Edit ISO infos failed.");
				e.printStackTrace();
				if (session.isOpen()) {
					session.getTransaction().rollback();
				}
			}
		}
		return result;
	}

	public boolean removeISO(String uuid) {
		ISO iso = this.getISOByID(uuid);
		iso.setIsoStatus(1);
		return this.editISO(iso);
	}

	public ISO getISOByID(String id) {
		ISO iso = null;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			Query query = session.createQuery("from ISO where isoUuid = '"
					+ id + "'");
			iso = (ISO) query.uniqueResult();
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Get ISO failed by isoid.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return iso;
	}

	@SuppressWarnings("unchecked")
	public List<ISO> getPagedISOList(int page, int limit, String search) {
		List<ISO> isoList = null;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			int startPos = (page - 1) * limit;
			String queryString = "from ISO where isoName like :search order by isoUuid desc";
			Query query = session.createQuery(queryString);
			query.setString("search", "%" + search + "%");
			query.setFirstResult(startPos);
			query.setMaxResults(limit);
			isoList = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Get ISO list failed by page.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return isoList;
	}

	public int countAllISOList(String search) {
		int count = 0;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			StringBuilder builder = new StringBuilder(
					"select count(*) from ISO where isoName like :search ");
			Query query = session.createQuery(builder.toString());
			query.setString("search", "%" + search + "%");
			count = ((Number) query.uniqueResult()).intValue();
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Count ISO numbers failed.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return count;
	}

	private void loginfo(String message){
		LogConstant.loginfo(message);
	}
}
