package com.beyondsphere.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import com.beyondsphere.constants.LogConstant;
import com.beyondsphere.dao.HostInformationDAO;
import com.beyondsphere.entity.HostInformation;
import com.beyondsphere.helper.HashHelper;
import com.beyondsphere.helper.SessionHelper;

@Component("HostInformationDAO")
public class HostInformationDAOImpl implements HostInformationDAO {

	@Resource
	private SessionHelper sessionHelper;
	@Resource
	private HashHelper hashHelper;
	
	public int countAllHostList() {
		int count = 0;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String queryString = "select count(*) from HostInformation";
			Query query = session.createQuery(queryString);
			count = ((Number) query.iterate().next()).intValue();
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Count all hosts number failed.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return count;
	}


	@SuppressWarnings("unchecked")
	public List<HostInformation> getOnePageList(int page, int limit) {
		List<HostInformation> hostList = null;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			int startPos = (page - 1) * limit;
			String queryString = "from HostInformation order by createDate desc";
			Query query = session.createQuery(queryString);
			query.setFirstResult(startPos);
			query.setMaxResults(limit);
			hostList = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Get host list failed by page.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return hostList;
	}
	
	@SuppressWarnings("unchecked")
	public List<HostInformation> getAllList() {
		List<HostInformation> hostList = null;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String queryString = "from HostInformation";
			Query query = session.createQuery(queryString);
			hostList = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Get all host list failed.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return hostList;
	}

	private void loginfo(String message){
		LogConstant.loginfo(message);
	}


	public boolean saveInformation(HostInformation info)  {
		boolean result = false;
		if (info != null) {
			Session session = null;
			try {
				session = sessionHelper.getMainSession();
				session.beginTransaction();
				session.save(info);
				session.getTransaction().commit();
				result = true;
			} catch (Exception e) {
				loginfo("Add HostInformation failed.");
				e.printStackTrace();
				if (session.isOpen()) {
					session.getTransaction().rollback();
				}
			}
		}
		return result;
	}
	
	public boolean doDeleteInfo(int infoid) {
		boolean result = false;
		HostInformation info = getInformation(infoid);
		if (info != null) {
			Session session = null;
			try {
				session = sessionHelper.getMainSession();
				session.beginTransaction();
				session.delete(info);
				session.getTransaction().commit();
				result = true;
			} catch (Exception e) {
				loginfo("delete hostInformation failed.");
				e.printStackTrace();
				if (session.isOpen()) {
					session.getTransaction().rollback();
				}
			}
		}
		return result;
	}
	
	public HostInformation getInformation(int infoid) {
		HostInformation info = null;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			Query query = session
					.createQuery("from HostInformation where id = :infoid");
			query.setInteger("infoid", infoid);
			info = (HostInformation) query.uniqueResult();
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Get HostInformation infos failed by infoid.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return info;
	}


	@Override
	public HostInformation getInformationByUserid(int userid) {
		HostInformation info = null;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			Query query = session
					.createQuery("from HostInformation where userId = :userid");
			query.setInteger("userid", userid);
			info = (HostInformation) query.uniqueResult();
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Get HostInformation info failed by userid.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return info;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<HostInformation> getAllListbyNetid(int netid) {
		List<HostInformation> hostList = null;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String queryString = "from HostInformation where netId=:netid";
			Query query = session.createQuery(queryString);
			query.setInteger("netid", netid);
			hostList = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Get all hostInformation list by netid failed.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return hostList;
	}
	
}
