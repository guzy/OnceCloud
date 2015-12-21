package com.oncecloud.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import com.oncecloud.dao.AlarmDAO;
import com.oncecloud.entity.Alarm;
import com.oncecloud.helper.SessionHelper;

@Component("AlarmDAO")
public class AlarmDAOImpl implements AlarmDAO{
	
	@Resource
	private SessionHelper sessionHelper;

	public boolean addAlarm(String alarmUuid,String alarmName,int userId) {
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			Alarm alarm = new Alarm(alarmUuid, alarmName, new Date(), userId);
			session.save(alarm);
			session.getTransaction().commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		} finally{
			if (session.isOpen()) {
				session.close();
			}
		}
		return false;
	}
	
	public int countAlarmList(int userId, String alarmName) {
		Session session = null;
		int alarmNum = 0;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String queryString = "select count(*) from Alarm where alarmUid = :userId and alarmName like :alarmName";
			Query query = session.createQuery(queryString);
			query.setInteger("userId", userId);
			query.setString("alarmName", "%"+alarmName+"%");
			alarmNum = ((Number)query.uniqueResult()).intValue();
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		} finally{
			if (session.isOpen()) {
				session.close();
			}
		}
		return alarmNum;
	}
	
	public Alarm getAlarm(String alarmUuid) {
		Session session = null;
		Alarm alarm = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			alarm = (Alarm) session.get(Alarm.class, alarmUuid);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		} finally{
			if (session.isOpen()) {
				session.close();
			}
		}
		return alarm;
	}

	@SuppressWarnings("unchecked")
	public List<Alarm> getAlarmList(int userId, String alarmName,
			int page,int limit) {
		List<Alarm> alarmList = new ArrayList<Alarm>();
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			int startPos = (page - 1) * limit;
			String queryString = "from Alarm where alarmUid = :userId and alarmName like :alarmName order by alarmTime desc";
			Query query = session.createQuery(queryString);
			query.setInteger("userId", userId);
			query.setString("alarmName", "%" + alarmName + "%");
			query.setFirstResult(startPos);
			query.setMaxResults(limit);
			alarmList = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		} finally{
			if (session.isOpen()) {
				session.close();
			}
		}
		return alarmList;
	}

	public boolean destoryAlarm(Alarm alarm) {
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			if (alarm != null){
				session.delete(alarm);	
			}
			session.getTransaction().commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		} finally{
			if (session.isOpen()) {
				session.close();
			}
		}
		return false;
	}

	public boolean updateAlarm(Alarm alarm) {
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			if (alarm != null){
				session.update(alarm);
			}
			session.getTransaction().commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		} finally{
			if (session.isOpen()) {
				session.close();
			}
		}
		return false;
	}

	@Override
	public boolean updateName(String alarmUuid, String newName,
			String description) {
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			Alarm alarm = getAlarm(alarmUuid);
			if (alarm != null) {
				alarm.setAlarmName(newName);
				alarm.setAlarmDesc(description);
				session.update(alarm);
				return true;
			}
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		} finally{
			if (session.isOpen()) {
				session.close();
			}
		}
		return false;
	}
}