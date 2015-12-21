/**
 * @author hty
 * @time 上午11:05:48
 * @date 2014年12月9日
 */
package com.beyondsphere.dao.impl;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import com.beyondsphere.constants.LogConstant;
import com.beyondsphere.dao.PowerDAO;
import com.beyondsphere.entity.Power;
import com.beyondsphere.helper.SessionHelper;

@Component("powerDAO")
public class PowerDAOImpl implements PowerDAO {

	@Resource
    private SessionHelper sessionHelper;
	
	public boolean addPower(Power power) {
		boolean result = false;
		if (power != null) {
			Session session = null;
			try {
				session = sessionHelper.getMainSession();
				session.beginTransaction();
				session.save(power);
				session.getTransaction().commit();
				result = true;
			} catch (Exception e) {
				loginfo("Save power infos failed.");
				e.printStackTrace();
				if (session.isOpen()) {
					session.getTransaction().rollback();
				}
			}
		}
		return result;
	}

	public boolean editPower(Power power) {
		boolean result = false;
		if (power != null) {
			Session session = null;
			try {
				session = sessionHelper.getMainSession();
				session.beginTransaction();
				session.saveOrUpdate(power);
				session.getTransaction().commit();
				result = true;
			} catch (Exception e) {
				loginfo("Edit power infos failed.");
				e.printStackTrace();
				if (session.isOpen()) {
					session.getTransaction().rollback();
				}
			}
		}
		return result;
	}

	public boolean removePower(Power power) {
		boolean result = false;
		if (power != null) {
			Session session = null;
			try {
				session = sessionHelper.getMainSession();
				session.beginTransaction();
				session.delete(power);
				session.getTransaction().commit();
				result = true;
			} catch (Exception e) {
				loginfo("Remove power failed.");
				e.printStackTrace();
				if (session.isOpen()) {
					session.getTransaction().rollback();
				}
			}
		}
		return result;
	}

	public Power getPowerByHostID(String hostUuid) {
		Power power = null;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			Query query = session.createQuery("from Power where hostUuid = :hostUuid and powerValid != :powerValid");
			query.setString("hostUuid", hostUuid);
			query.setInteger("powerValid", -1);
			power =(Power) query.uniqueResult();
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Get power infos failed by hostId.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return power;
	}

	private void loginfo(String message){
		LogConstant.loginfo(message);
	}
}
