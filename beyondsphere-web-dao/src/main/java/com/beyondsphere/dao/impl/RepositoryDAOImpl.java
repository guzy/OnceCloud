package com.beyondsphere.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import com.beyondsphere.constants.LoggerConfig;
import com.beyondsphere.dao.RepositoryDAO;
import com.beyondsphere.entity.Repository;
import com.beyondsphere.helper.SessionHelper;

/**
 * @author luogan
 * 2015年5月19日
 * 下午2:19:14
 */
@Component("RepositoryDAO")
public class RepositoryDAOImpl implements RepositoryDAO {

	@Resource
	private SessionHelper sessionHelper;
	@Resource
	private LoggerConfig logger;
	
	//
	public int countByUserId(int userId, String search) {
		int count = 0;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String queryString = "select count(*) from Repository";
			Query query = session.createQuery(queryString);
			//query.setInteger("userId", userId);
			//query.setString("search", "%" + search + "%");
			count = ((Number) query.uniqueResult()).intValue();
			session.getTransaction().commit();
		} catch (Exception e) {
			logger.logError("Count repository number failed");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		} finally{
			if (session.isOpen()) {
				session.close();
			}
		}
		return count;
	}
	
	//
	@SuppressWarnings("unchecked")
	public List<Repository> getOnePageByUserId(int userId, int page, int limit,
			String search) {
		List<Repository> repoList = new ArrayList<Repository>();
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			int startPos = (page - 1) * limit;
			StringBuilder queryString = new StringBuilder("from Repository");
			Query query = session.createQuery(queryString.toString());
			//query.setInteger("userId", userId);
			//query.setString("search", "%" + search + "%");
			query.setFirstResult(startPos);
			query.setMaxResults(limit);
			repoList = query.list();
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
		return repoList;
	}
	
	//

	@Override
	public boolean insertImage(String repoId, String repoName,
			String repoVersion, String repoAddress) {
		Session session = null;
		try {
			Repository repository = new Repository(repoId, repoName, repoVersion, repoAddress, new Date());
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			session.save(repository);
			session.getTransaction().commit();
		} catch (Exception e) {
			logger.logError("Insert image infos failed.");
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
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Repository> getRepoList(int userId, int page, int limit,
			String groupUuid, String search) {
		List<Repository> repoList = new ArrayList<Repository>();
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			int startPos = (page - 1) * limit;
			//StringBuilder queryString = new StringBuilder("from Repository where repoName = 'registrator'");
			StringBuilder queryString = new StringBuilder("from Repository where repoName like '%regi%'");
			Query query = session.createQuery(queryString.toString());
			//query.setInteger("userId", userId);
			//query.setString("search", "%" + search + "%");
			query.setFirstResult(startPos);
			query.setMaxResults(limit);
			repoList = query.list();
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
		return repoList;
	}
	
	
}
