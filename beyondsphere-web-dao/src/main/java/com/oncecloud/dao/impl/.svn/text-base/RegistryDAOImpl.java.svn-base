package com.beyondsphere.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import com.beyondsphere.dao.RegistryDAO;
import com.beyondsphere.entity.Registry;
import com.beyondsphere.helper.SessionHelper;

/**
 * @author luogan
 * 2015年7月15日
 * 下午12:12:09
 */
@Component("RegistryDAO")
public class RegistryDAOImpl implements RegistryDAO {

	@Resource
	private SessionHelper sessionHelper;

	@Override
	public boolean createRegistry(String regUuid, String name,String ip,String port, String status,Date createTime){
		Session session = null;
		boolean result = false;
		try {
			Registry reg = new Registry(regUuid,name,ip,port,status,createTime);
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			session.save(reg);
			session.getTransaction().commit();
			result = true;
		} catch (Exception e) {
			// loginfo("Pre create vm failed.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
			result = false;
		}
		return result;
	}

	@Override
	public boolean deleteRegistry(int userId, String containerId) {
		Session session = null;
		boolean result = false;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String string = "delete Registry where regId = :containerId ";
			Query query = session.createQuery(string);
			// query.setInteger("userId", userId);
			query.setString("containerId", containerId);
			query.executeUpdate();
			session.getTransaction().commit();
			result = true;
		} catch (Exception e) {
			// loginfo("Delete vm failed.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
			result = false;
		}
		return result;
	}

	@Override
	public int countByUserId(int userId, String search) {
		int count = 0;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String queryString = "select count(*) from Registry";
			Query query = session.createQuery(queryString);
			// query.setInteger("userId", userId);
			// query.setString("search", "%" + search + "%");
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

	@SuppressWarnings("unchecked")
	@Override
	public List<Registry> getOnePageByUserId(int userId, int page, int limit,
			String groupUuid, String search) {
		List<Registry> regList = new ArrayList<Registry>();
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			int startPos = (page - 1) * limit;
			StringBuilder queryString = new StringBuilder("from Registry");
			//if (!groupUuid.equals("all")) {
		    //queryString.append(" and vmGroup = :groupUuid ");
			//}
			// queryString.append("order by vmCreateDate desc");
			Query query = session.createQuery(queryString.toString());
			//query.setInteger("userId", userId);
			// if (!groupUuid.equals("all")) {
			// query.setString("groupUuid", groupUuid);
			// }
			// query.setString("search", "%" + search + "%");
			query.setFirstResult(startPos);
			query.setMaxResults(limit);
			regList = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return regList;
	}

	@Override
	public List<Registry> getOnePageByUserId(int userId, int page, int limit,
			String search) {
		// TODO Auto-generated method stub
		return null;
	}

}
