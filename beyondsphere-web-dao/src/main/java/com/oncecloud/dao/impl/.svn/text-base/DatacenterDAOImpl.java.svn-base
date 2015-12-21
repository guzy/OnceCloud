/**
 * @author hty
 * @time 上午11:05:48
 * @date 2014年12月9日
 */
package com.beyondsphere.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import com.beyondsphere.constants.LogConstant;
import com.beyondsphere.dao.DatacenterDAO;
import com.beyondsphere.entity.Datacenter;
import com.beyondsphere.entity.OCPool;
import com.beyondsphere.helper.SessionHelper;

@Component("DatacenterDAO")
public class DatacenterDAOImpl implements DatacenterDAO {

	@Resource
	private SessionHelper sessionHelper;

	public int countAllDatacenter(String search) {
		int count = 0;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			Criteria criteria = session
					.createCriteria(Datacenter.class)
					.add(Restrictions
							.like("dcName", search, MatchMode.ANYWHERE))
					.add(Restrictions.eq("dcStatus", 1))
					.setProjection(Projections.rowCount());
			count = ((Number) criteria.uniqueResult()).intValue();
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Count datacenter failed.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return count;
	}

	public Datacenter createDatacenter(String dcName, String dcLocation,
			String dcDesc) {
		Datacenter datacenter = null;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			datacenter = new Datacenter();
			datacenter.setDcUuid(UUID.randomUUID().toString());
			datacenter.setDcName(dcName);
			datacenter.setDcLocation(dcLocation);
			datacenter.setDcDesc(dcDesc);
			datacenter.setDcStatus(1);
			datacenter.setCreateDate(new Date());
			session.save(datacenter);
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Create datacenter failed");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return datacenter;
	}

	@SuppressWarnings("unchecked")
	public boolean deleteDatacenter(String dcUuid) {
		boolean result = false;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			Datacenter datacenter = this.doGetDatacenter(session, dcUuid);
			if (datacenter != null) {
				datacenter.setDcStatus(0);
				session.update(datacenter);
				List<OCPool> pools = session.createCriteria(OCPool.class)
						.add(Restrictions.eq("dcUuid", dcUuid)).list();
				for (OCPool pool : pools) {
					pool.setDcUuid(null);
					session.update(pool);
				}
				session.getTransaction().commit();
				result = true;
			}
		} catch (Exception e) {
			loginfo("Delete datacenter failed");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return result;
	}

	private Datacenter doGetDatacenter(Session session, String dcUuid) {
		Criteria criteria = session.createCriteria(Datacenter.class).add(
				Restrictions.eq("dcUuid", dcUuid));
		return (Datacenter) criteria.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<Datacenter> getAllPageDCList() {
		List<Datacenter> datacenterList = null;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(Datacenter.class)
					.add(Restrictions.eq("dcStatus", 1))
					.addOrder(Order.desc("createDate"));
			datacenterList = criteria.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Get datacenter list failed");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return datacenterList;
	}

	public Datacenter getDatacenter(String dcUuid) {
		Datacenter datacenter = null;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			datacenter = this.doGetDatacenter(session, dcUuid);
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Get datacenter failed by dcId.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return datacenter;
	}

	@SuppressWarnings("unchecked")
	public List<Datacenter> getOnePageDCList(int page, int limit, String search) {
		List<Datacenter> datacenterList = null;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			int startPos = (page - 1) * limit;
			Criteria criteria = session
					.createCriteria(Datacenter.class)
					.add(Restrictions
							.like("dcName", search, MatchMode.ANYWHERE))
					.add(Restrictions.eq("dcStatus", 1))
					.addOrder(Order.desc("createDate"))
					.setFirstResult(startPos).setMaxResults(limit);
			datacenterList = criteria.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Get datacenter list failed by page.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return datacenterList;
	}

	public boolean updateDatacenter(String dcUuid, String dcName,
			String dcLocation, String dcDesc) {
		boolean result = false;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(Datacenter.class).add(
					Restrictions.eq("dcUuid", dcUuid));
			Datacenter datacenter = (Datacenter) criteria.uniqueResult();
			if (datacenter != null) {
				datacenter.setDcName(dcName);
				datacenter.setDcLocation(dcLocation);
				datacenter.setDcDesc(dcDesc);
				session.update(datacenter);
				result = true;
			}
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Update datacenter failed");
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
