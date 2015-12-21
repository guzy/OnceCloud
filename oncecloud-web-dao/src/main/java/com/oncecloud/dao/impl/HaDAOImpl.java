package com.oncecloud.dao.impl;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import com.oncecloud.constants.LogConstant;
import com.oncecloud.dao.HaDAO;
import com.oncecloud.entity.OCHa;
import com.oncecloud.helper.SessionHelper;

@Component("HaDAO")
public class HaDAOImpl implements HaDAO {
	
	@Resource
	private SessionHelper sessionHelper;

	public OCHa getHa(String poolUuid) {
		OCHa ha = null;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			StringBuilder builder = new StringBuilder("from OCHa where poolUuid = :poolUuid");
			Query query = session.createQuery(builder.toString());
			query.setString("poolUuid", poolUuid);
			ha = (OCHa) query.uniqueResult();
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Get HA policy failed");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return ha;
	}

	public int countHaByPoolUuid(String poolUuid) {
		int count = 0;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			StringBuilder builder = new StringBuilder(
					"select count(*) from OCHa where poolUuid = :poolUuid");
			Query query = session.createQuery(builder.toString());
			query.setString("poolUuid", poolUuid);
			count = ((Number) query.uniqueResult()).intValue();
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Count HA failed");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return count;
	}

	public boolean insertHa(OCHa ha) {
		Session session = null;
		boolean result = false;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			session.save(ha);
			session.getTransaction().commit();
			result = true;
		} catch (Exception e) {
			loginfo("insert HA failed.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return result;
	}

	public boolean updateHa(OCHa ha) {
		boolean result = false;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			StringBuilder builder = new StringBuilder("update OCHa set accessControlPolicy = :access, leftHost = :leftHost, slotPolicy =:slotPolicy, "
					+ "slotCpu = :slotCpu, slotMemory = :slotMemory, cpuPercent = :cpuPercent, memoryPercent = :memoryPercent, "
					+ "migratoryHostUuid = :migratoryHostUuid ");
			builder.append(" where poolUuid = :poolUuid");
			Query query = session.createQuery(builder.toString());
			query.setInteger("access", ha.getAccessControlPolicy());
			query.setInteger("leftHost", ha.getLeftHost());
			query.setInteger("slotPolicy", ha.getSlotPolicy());
			query.setInteger("slotCpu", ha.getSlotCpu());
			query.setInteger("slotMemory", ha.getSlotMemory());
			query.setInteger("cpuPercent", ha.getCpuPercent());
			query.setInteger("memoryPercent", ha.getMemoryPercent());
			query.setString("migratoryHostUuid", ha.getMigratoryHostUuid());
			query.setString("poolUuid", ha.getPoolUuid());
			query.executeUpdate();
			session.getTransaction().commit();
			result = true;
		} catch (Exception e) {
			loginfo("Update HA policy failed");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return result;
	}

	public boolean deleteHa(String poolUuid) {
		Session session = null;
		boolean result = false;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String string = "delete OCHa where poolUuid = :poolUuid";
			Query query = session.createQuery(string);
			query.setString("poolUuid", poolUuid);
			query.executeUpdate();
			session.getTransaction().commit();
			result = true;
		} catch (Exception e) {
			loginfo("Delete ha failed");
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
