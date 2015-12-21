package com.oncecloud.dao.impl;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import com.oncecloud.dao.VMipDAO;
import com.oncecloud.entity.OCVMip;
import com.oncecloud.helper.SessionHelper;

@Component("VMipDAO")
public class VMipDAOImpl implements VMipDAO {

	@Resource
	private SessionHelper sessionHelper;
	
	@Override
	public boolean insertVMip(OCVMip vMip) {
		Session session = null;
		boolean result = false;
		try {
			session = sessionHelper.getMainSession();
			session.getTransaction().begin();
			session.save(vMip);
			session.getTransaction().commit();
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return result;
	}

	@Override
	public boolean updateVMip(OCVMip vMip) {
		Session session = null;
		boolean result = false;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String str = "update OCVMip set vmIp = :vmIp where vmMac = :vmMac";
			Query query = session.createQuery(str);
			query.setString("vmIp", vMip.getVmIp());
			query.setString("vmMac", vMip.getVmMac());
			query.executeUpdate();
			session.getTransaction().commit();
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return result;
	}

	@Override
	public OCVMip getVMip(String vmMC) {
		OCVMip vmip = null;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String queryString = "from OCVMip where vmMac = :vmMC";
			Query query = session.createQuery(queryString);
			query.setString("vmMC", vmMC);
			vmip = (OCVMip) query.uniqueResult();
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return vmip;
	}

	@Override
	public int countVMipByIpAndVlan(String vmIP, int vlan) {
		int count = 0;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String queryString = "select count(*) from OCVMip where vmIp = :vmIP and vmVlan = :vlan";
			Query query = session.createQuery(queryString);
			query.setString("vmIP", vmIP);
			query.setInteger("vlan", vlan);
			count = ((Number) query.uniqueResult()).intValue();
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return count;
	}

}
