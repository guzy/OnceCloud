package com.beyondsphere.dao.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import com.beyondsphere.dao.LogBackupDAO;
import com.beyondsphere.helper.SessionHelper;

@Component
public class LogBackupDAOImpl implements LogBackupDAO {
	
	@Resource
	private SessionHelper sessionHelper;

	@Override
	public boolean insertLogBackup(Date savetime) {
		Session session = null;
		boolean result = false;
		try {
			session= sessionHelper.getMainSession();
			session.beginTransaction();
			String sqlStr = "insert into OCLogBackup (logId, logUID, logObject, logAction, logStatus, logInfo, logTime, logElapse) "
					+"select logId, logUID, logObject, logAction, logStatus, logInfo, logTime, logElapse from OCLog where logTime < :savetime";
			Query query = session.createQuery(sqlStr);
			query.setDate("savetime", savetime);
			query.executeUpdate();
			session.getTransaction().commit();
			result = true;
			
		} catch (Exception e) {
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().commit();
			}
			return result;
		}
		return result;
	}

}
