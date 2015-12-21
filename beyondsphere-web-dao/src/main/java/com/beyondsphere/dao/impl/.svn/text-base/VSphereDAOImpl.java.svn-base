package com.beyondsphere.dao.impl;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import com.beyondsphere.dao.VSphereDAO;
import com.beyondsphere.helper.SessionHelper;
import com.beyondsphere.model.VMPower;
@Component("VSphereDAO")
public class VSphereDAOImpl implements VSphereDAO {
	@Resource
	private SessionHelper sessionHelper;
	
	public boolean updateStatusOfPower(String vmUuid, VMPower vmPower) {
		Session session = null;
		boolean result = false;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String str = "update OCVM set vmPower = :vmPower where vmUuid = :vmUuid";
			Query query = session.createQuery(str);
			query.setParameter("vmPower", vmPower);
			query.setString("vmUuid", vmUuid);
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
	public boolean deleteVMByUuid(int userId, String vmUuid) {
		Session session = null;
		boolean result = false;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String str = "update OCVM set vmStatus = 0 where vmUuid = :vmUuid and vmUid = :userId";
			Query query = session.createQuery(str);
			query.setString("vmUuid", vmUuid);
			query.setInteger("userId", userId);
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
}
