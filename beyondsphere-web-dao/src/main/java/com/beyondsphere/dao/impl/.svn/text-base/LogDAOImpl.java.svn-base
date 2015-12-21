/**
 * @author hty
 * @time 上午11:05:48
 * @date 2014年12月9日
 */
package com.beyondsphere.dao.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import com.beyondsphere.constants.LogConstant;
import com.beyondsphere.dao.LogDAO;
import com.beyondsphere.entity.OCLog;
import com.beyondsphere.helper.SessionHelper;

@Component("LogDAO")
public class LogDAOImpl implements LogDAO {

	@Resource
	private SessionHelper sessionHelper;

	@SuppressWarnings("unchecked")
	public List<OCLog> getLogList(int logUID, int logStatus, int start, int num,String search) {
		List<OCLog> logList = null;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(OCLog.class);
			if (logStatus != LogConstant.logStatus.All.ordinal()) {
				criteria = criteria
						.add(Restrictions.eq("logStatus", logStatus));
			}
			criteria.add(Restrictions.or(Restrictions.like("logInfo","%"+search+"%")));
			criteria = criteria.addOrder(Order.desc("logId"))
					.setFirstResult(start).setMaxResults(num);
			logList = criteria.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Get log list failed.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return logList;
	}
	
	public OCLog insertLog(OCLog log) {
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			session.save(log);
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Insert log infos failed");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return log;
	}
	
	public OCLog insertLog(int logUID, int logObject, int logAction,
			int logStatus, String logInfo, Date logTime, int logElapse) {
		OCLog log = null;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			log = new OCLog();
			log.setLogUID(logUID);
			log.setLogObject(logObject);
			log.setLogAction(logAction);
			log.setLogStatus(logStatus);
			log.setLogInfo(logInfo);
			log.setLogTime(logTime);
			log.setLogElapse(logElapse);
			session.save(log);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return log;
	}

	private void loginfo(String message){
		LogConstant.loginfo(message);
	}
	
	@Override
	public boolean deleteLog(int logId) {
		boolean result = false;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String str = "delete from OCLog where logId = :logId";
			Query query = session.createQuery(str);
			query.setInteger("logId", logId);
			query.executeUpdate();
			session.getTransaction().commit();
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
			result = false;
		}
		return result;
	}

	@Override
	public boolean deleteLogByTime(Date logTime) {
		boolean result = false;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String str = "delete from OCLog where logTime < :logTime";
			Query query = session.createQuery(str);
			query.setDate("logTime", logTime);
			query.executeUpdate();
			session.getTransaction().commit();
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
			result = false;
		}
		return result;
	}
}
