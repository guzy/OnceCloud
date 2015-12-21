/**
 * @author hty
 * @time 上午11:05:48
 * @date 2014年12月9日
 */
package com.beyondsphere.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import com.beyondsphere.dao.AreaDAO;
import com.beyondsphere.entity.Area;
import com.beyondsphere.helper.HashHelper;
import com.beyondsphere.helper.SessionHelper;

@Component("AreaDAO")
public class AreaDAOImpl implements AreaDAO {

	@Resource
	private SessionHelper sessionHelper;
	@Resource
	private HashHelper hashHelper;

	@Override
	public boolean insertArea(String areaId, String areaName,
			String areaDomain, String areaDesc, Date areaCreateTime) {
		Session session = null;
		try {
			Area area = new Area(areaId, areaName, areaDomain, areaDesc, areaCreateTime);
			session = sessionHelper.getPlatformSession();
			session.beginTransaction();
			session.save(area);
			// areaId = area.getAreaId();
			session.getTransaction().commit();
			return true;
		} catch (Exception e) {
			// loginfo("Insert area infos failed.");
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
	public boolean deleteArea(String areaId) {
		Session session = null;
		session = sessionHelper.getPlatformSession();
		session.beginTransaction();
		Query query = session.createQuery("from Area where areaId = :areaId");
		query.setString("areaId", areaId);
		Area delArea = (Area) query.uniqueResult();
		if (delArea != null) {
			try {
				session.delete(delArea);
				session.getTransaction().commit();
				return true;
			} catch (Exception e) {
				// loginfo("Disabled user failed.");
				e.printStackTrace();
				if (session.isOpen()) {
					session.getTransaction().rollback();
				}
			} finally{
				if (session.isOpen()) {
					session.close();
				}
			}
		} 
		return false;

	}

	@Override
	public boolean updateArea(String areaId, String areaName,
			String areaDomain, String areaDesc) {
		Session session = null;
		try {
			session = sessionHelper.getPlatformSession();
			session.beginTransaction();
			Query query = session
					.createQuery("update Area set areaName= :areaName, areaDomain= :areaDomain,areaDesc = :areaDesc where areaId = :areaId");
			query.setString("areaId", areaId);
			query.setString("areaName", areaName);
			query.setString("areaDomain", areaDomain);
			query.setString("areaDesc", areaDesc);
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

	@SuppressWarnings("unchecked")
	@Override
	public List<Area> getOnePageAreaList(int page, int limit, String search) {
		List<Area> areaList = new ArrayList<Area>();
		Session session = null;
		try {
			session = sessionHelper.getPlatformSession();
			session.beginTransaction();
			int startPos = (page - 1) * limit;
			String queryString = "from Area where areaName like :search order by areaCreateTime desc";
			Query query = session.createQuery(queryString);
			query.setString("search", "%" + search + "%");
			query.setFirstResult(startPos);
			query.setMaxResults(limit);
			areaList = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			// loginfo("Get user list failed by page.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		} finally{
			if (session.isOpen()) {
				session.close();
			}
		}
		return areaList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Area> getCompanyAreaList(String search) {
		List<Area> areaList = new ArrayList<Area>();
		Session session = null;
		try {
			session = sessionHelper.getPlatformSession();
			session.beginTransaction();
			String queryString = "from Area where areaName like :search order by userDate desc";
			Query query = session.createQuery(queryString);
			query.setString("search", "%" + search + "%");
			areaList = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			// loginfo("Get user infos failed by user level.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		} finally{
			if (session.isOpen()) {
				session.close();
			}
		}
		return areaList;

	}

	@Override
	public int countAllAreaList(String search) {
		int areaNum = 0;
		Session session = null;
		try {
			session = sessionHelper.getPlatformSession();
			session.beginTransaction();
			String queryString = "select count(*) from Area where areaName like :search";
			Query query = session.createQuery(queryString);
			query.setString("search", "%" + search + "%");
			areaNum = ((Number) query.uniqueResult()).intValue();
			session.getTransaction().commit();
		} catch (Exception e) {
			// loginfo("Count area list failed.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		
		} finally{
			if (session.isOpen()) {
				session.close();
			}
		}
		return areaNum;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Area> getAreaList() {
		List<Area> areaList = new ArrayList<Area>();
		Session session = null;
		try {
			session = sessionHelper.getPlatformSession();
			session.beginTransaction();
			String queryStr = "from Area";
			Query query = session.createQuery(queryStr);
			areaList = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			// loginfo("Get all area list failed.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		} finally{
			if (session.isOpen()) {
				session.close();
			}
		}
		return areaList;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Area> getAreaAllList() {
		List<Area> areaList = new ArrayList<Area>();
		Session session = null;
		try {
			session = sessionHelper.getPlatformSession();
			session.beginTransaction();
			String queryString = "from Area";
			Query query = session.createQuery(queryString);
			areaList = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			// loginfo("Get user list failed by page.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		} finally{
			if (session.isOpen()) {
				session.close();
			}
		}
		return areaList;
	}

	@Override
	public Area getArea(String areaId) {
		Session session = null;
		Area area = null;
		try {
			session = sessionHelper.getPlatformSession();
			session.beginTransaction();
			String queryString = "from Area where areaId = :areaId";
			Query query = session.createQuery(queryString);
			query.setString("areaId", areaId);
			area = (Area) query.uniqueResult();
			session.getTransaction().commit();
		} catch (Exception e) {
			// loginfo("Get user list failed by page.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		} finally{
			if (session.isOpen()) {
				session.close();
			}
		}
		return area;
	}

	@Override
	public Area queryArea(String areaDomain) {
		Session session = null;
		Area area = null;
		try {
			session = sessionHelper.getPlatformSession();
			session.beginTransaction();
			String queryString = "from Area where areaDomain = :areaDomain";
			Query query = session.createQuery(queryString);
			query.setString("areaDomain", areaDomain);
			area = (Area) query.uniqueResult();
			session.getTransaction().commit();
		} catch (Exception e) {
			// loginfo("Get user list failed by page.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		} finally{
			if (session.isOpen()) {
				session.close();
			}
		}
		return area;
	}

}
