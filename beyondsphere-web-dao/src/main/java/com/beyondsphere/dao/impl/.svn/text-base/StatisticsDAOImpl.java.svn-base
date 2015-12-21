package com.beyondsphere.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import com.beyondsphere.constants.LogConstant;
import com.beyondsphere.model.StatisticsType;
import com.beyondsphere.dao.StatisticsDAO;
import com.beyondsphere.entity.OCStatistics;
import com.beyondsphere.helper.SessionHelper;
import com.beyondsphere.util.TimeUtils;

/**
 * @author hty
 * 
 */
@Component("StatisticsDAO")
public class StatisticsDAOImpl implements StatisticsDAO {
	@Resource
	private SessionHelper sessionHelper;

	public boolean save(OCStatistics ocStatistics) {
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			session.save(ocStatistics);
			session.getTransaction().commit();
			return true;
		} catch (Exception e) {
			loginfo("Save statistics infos failed.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
			return false;
		}
	}

	public boolean update(OCStatistics ocStatistics) {
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			session.update(ocStatistics);
			session.getTransaction().commit();
			return true;
		} catch (Exception e) {
			loginfo("Update statistics infos failed.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
			return false;
		}
	}

	public OCStatistics get(String uuid) {
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			OCStatistics ocStatistics = (OCStatistics) session.get(OCStatistics.class, uuid);
			session.getTransaction().commit();
			return ocStatistics;
		} catch (Exception e) {
			loginfo("Get statistics infos failed.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
			return null;
		}
	}

	public boolean delete(OCStatistics ocStatistics) {
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			session.delete(ocStatistics);
			session.getTransaction().commit();
			return true;
		} catch (Exception e) {
			loginfo("Delete statistics infos failed.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	public List<OCStatistics> getListByUserId(int userId, Date date, StatisticsType type) {
		List<OCStatistics> list = new ArrayList<OCStatistics>();
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String str = "from OCStatistics where userId = :userId and createDate >= :date and type = :type";
			Query query = session.createQuery(str);
			query.setInteger("userId", userId);
			query.setDate("date", date);
			query.setInteger("type", type.ordinal());
			list = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Get statistics list failed by user and type");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<OCStatistics> getOnePageByUserId(Map<String, Object> params,
			int limit, int page) {
		List<OCStatistics> statisticsList = new ArrayList<OCStatistics>();
		Date start_time = TimeUtils.stringToDate((String) params.get("start_date"));
		Date end_time = TimeUtils.stringToDate((String) params.get("end_date"));
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			int startPos = (page - 1) * limit;
			StringBuilder queryString = new StringBuilder("from OCStatistics where type = :type ");
			if ((Integer)params.get("userId") != 0) {
				queryString.append(" and userId = :userId");
			}
			if (start_time != null) {
				queryString.append(" and createDate >= :start_date");
			}
			if (end_time != null) {
				queryString.append(" and (deleteDate <= :end_date or deleteDate is null)");
			}
			Query query = session.createQuery(queryString.toString());
			if (start_time != null) {
				query.setDate("start_date", start_time);
			}
			if (end_time != null) {
				query.setDate("end_date", end_time);
			}
			query.setInteger("type", (StatisticsType.valueOf(params.get("type").toString().toUpperCase())).ordinal());
			if ((Integer)params.get("userId") != 0) {
				query.setInteger("userId", (Integer)params.get("userId"));
			}
			query.setFirstResult(startPos);
			query.setMaxResults(limit);
			statisticsList = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Get statistics list failed by user and page");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return statisticsList;
	}

	public int getCount(Map<String, Object> params, String search) {
		int count = 0;
		Date start_time = TimeUtils.stringToDate((String) params.get("start_date"));
		Date end_time = TimeUtils.stringToDate((String) params.get("end_date"));
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			StringBuilder queryString = new StringBuilder("select count(*) from OCStatistics where userId = :userId and type = :type");
			if (start_time != null) {
				queryString.append(" and createDate >= :start_date");
			}
			if (end_time != null) {
				queryString.append(" and (deleteDate <= :end_date or deleteDate is null)");
			}
			Query query = session.createQuery(queryString.toString());
			query.setInteger("userId", (Integer)params.get("userId"));
			if (start_time != null) {
				query.setDate("start_date", start_time);
			}
			if (end_time != null) {
				query.setDate("end_date", end_time);
			}
			query.setInteger("type", (StatisticsType.valueOf(params.get("type").toString().toUpperCase())).ordinal());
			count = ((Number) query.uniqueResult()).intValue();
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Count statistics number failed.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return count;
	}
	
	private void loginfo(String message){
		LogConstant.loginfo(message);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OCStatistics> getListForDate(Date startTime) {
		List<OCStatistics> list = new ArrayList<OCStatistics>();
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String str = "from OCStatistics where createDate >= :date";
			Query query = session.createQuery(str);
			query.setDate("date", startTime);
			list = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Get statistics list failed by user and type");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OCStatistics> getAllListByUserId(int userid) {
		List<OCStatistics> list = new ArrayList<OCStatistics>();
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			if(userid==0){
				String str = "from OCStatistics order by type";
				Query query = session.createQuery(str);
				list = query.list();
			}else{
				String str = "from OCStatistics where userId = :userId order by type";
				Query query = session.createQuery(str);
				query.setInteger("userId", userid);
				list = query.list();
			}
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Get statistics list failed by user and type");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return list;
	}

}
