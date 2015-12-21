package com.beyondsphere.dao.impl;

import java.util.Date;
import java.util.UUID;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import com.beyondsphere.dao.AlarmLogDAO;
import com.beyondsphere.entity.AlarmLog;
import com.beyondsphere.helper.SessionHelper;

@Component("AlarmLogDAO")
public class AlarmLogDAOImpl implements AlarmLogDAO {

	@Resource
	private SessionHelper sessionHelper;

	/**
	 * 增加一条告警记录
	 * 
	 * @param logDesc
	 * @return
	 */
	public boolean addAlarmLog(String logDesc, String ruleId) {
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String logUuid = UUID.randomUUID().toString();
			AlarmLog alarmLog = new AlarmLog(logUuid, logDesc, new Date(), 1,
					ruleId);
			session.save(alarmLog);
			session.getTransaction().commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
			return false;
		} finally{
			if (session.isOpen()) {
				session.close();
			}
		}
	}

	/**
	 * 获取一条告警记录
	 * 
	 * @param logUuid
	 * @return
	 */
	public AlarmLog getAlarmLog(String logUuid) {
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			return (AlarmLog) session.get(AlarmLog.class, logUuid);
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
		return null;
	}

	/**
	 * 修改告警次数
	 * 
	 * @param logUuid
	 * @param logFlag
	 * @return
	 */
	public boolean updateLogFlag(String logUuid, int logFlag) {
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String queryString = "update AlarmLog set logFlag = :logFlag where logUuid = :logUuid";
			Query query = session.createQuery(queryString);
			query.setInteger("logFlag", logFlag);
			query.setString("logUuid", logUuid);
			query.executeUpdate();
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

	/**
	 * 获取告警记录的上一次记录时间
	 * 
	 * @param logUuid
	 * @return
	 */
	/*
	 * public String getLogTime(String logUuid) {
	 * 
	 * }
	 */

	/**
	 * 获取最新告警记录
	 * 
	 * @param ruleId
	 * @param logDesc
	 * @return
	 */
	public AlarmLog getAlarmLogByDesc(String ruleId, String logDesc) {
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String queryString = "from AlarmLog where ruleId = :ruleId and logDesc like :logDesc order by logTime desc";
			Query query = session.createQuery(queryString);
			query.setString("ruleId", ruleId);
			query.setString("logDesc", "%" + logDesc + "%");
			if (query.list().size() != 0) {
				return (AlarmLog) query.list().get(0);
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
		return null;
	}
}