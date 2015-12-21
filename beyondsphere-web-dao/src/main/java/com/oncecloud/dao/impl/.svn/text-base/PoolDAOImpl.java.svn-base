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
import org.hibernate.criterion.Example;
import org.springframework.stereotype.Component;

import com.beyondsphere.constants.LogConstant;
import com.beyondsphere.dao.PoolDAO;
import com.beyondsphere.entity.OCPool;
import com.beyondsphere.helper.SessionHelper;

@Component("PoolDAO")
public class PoolDAOImpl implements PoolDAO {
	
	@Resource
	private SessionHelper sessionHelper;

	/**
	 * 获取资源池
	 * 
	 * @param poolUuid
	 * @return
	 */
	public OCPool getPool(String poolUuid) {
		OCPool pool = null;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String builder = "from OCPool where poolUuid = :poolUuid";
			Query query = session.createQuery(builder);
			query.setString("poolUuid", poolUuid);
			pool = (OCPool) query.uniqueResult();
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Get pool infos failed.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return pool;
	}

	public OCPool getPoolNoTransactional(String poolUuid) {
		OCPool pool = null;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			String builder = "from OCPool where poolUuid = :poolUuid";
			Query query = session.createQuery(builder);
			query.setString("poolUuid", poolUuid);
			pool = (OCPool) query.uniqueResult();
		} catch (Exception e) {
			loginfo("Get pool infos failed by no transactional.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return pool;
	}

	/*	*//**
	 * 获取随机资源池
	 * 
	 * @return
	 */
	public String getRandomPool() {
		String pool = null;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String queryString = "from OCPool where poolStatus = 1 order by rand()";
			Query query = session.createQuery(queryString);
			query.setFirstResult(0);
			query.setMaxResults(1);
			pool = ((OCPool) query.list().get(0)).getPoolUuid();
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Get random pool infos failed.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return pool;
	}

	/**
	 * 获取一页资源池列表
	 * 
	 * @param page
	 * @param limit
	 * @param search
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<OCPool> getOnePagePoolList(int page, int limit, String search) {
		List<OCPool> poolList = null;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			int startPos = (page - 1) * limit;
			String queryString = "from OCPool where poolName like :search and poolStatus = 1 order by createDate desc";
			Query query = session.createQuery(queryString);
			query.setString("search", "%" + search + "%");
			query.setFirstResult(startPos);
			query.setMaxResults(limit);
			poolList = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Get all pool list failed by page.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return poolList;
	}
	
	@SuppressWarnings("unchecked")
	public List<OCPool> getOnePagePoolListOfHA(int page, int limit, String search) {
		List<OCPool> poolList = null;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			int startPos = (page - 1) * limit;
			String queryString = "from OCPool where poolName like :search and poolMaster is not null and poolStatus = 1 order by createDate desc";
			Query query = session.createQuery(queryString);
			query.setString("search", "%" + search + "%");
			query.setFirstResult(startPos);
			query.setMaxResults(limit);
			poolList = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Get all ha pool list failed by page.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return poolList;
	}

	/**
	 * 获取所有资源池列表
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<OCPool> getPoolList() {
		List<OCPool> poolList = null;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String queryString = "from OCPool where poolStatus = 1 order by createDate desc";
			Query query = session.createQuery(queryString);
			poolList = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Get all pool list failed.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return poolList;
	}

	/**
	 * 获取数据中心的资源池列表
	 * 
	 * @param dcUuid
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<OCPool> getPoolListOfDC(String dcUuid) {
		List<OCPool> pooList = null;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String queryString = "from OCPool where dcUuid = :dcUuid and poolStatus = 1";
			Query query = session.createQuery(queryString);
			query.setString("dcUuid", dcUuid);
			pooList = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Get pool list of datacenter failed.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return pooList;
	}

	public OCPool getPoolByMaster(String poolMaster) {
		OCPool pool = null;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String queryString = "from OCPool where poolMaster = :poolMaster and poolStatus = 1";
			Query query = session.createQuery(queryString);
			query.setString("poolMaster", poolMaster);
			pool = (OCPool) query.uniqueResult();
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Get pool infos failed by pool master.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return pool;
	}

	/**
	 * 获取资源池总数
	 * 
	 * @param search
	 * @return
	 */
	public int countAllPoolList(String search) {
		int count = 0;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			StringBuilder builder = new StringBuilder(
					"select count(*) from OCPool where ");
			builder.append("poolName like :search and poolStatus = 1");
			Query query = session.createQuery(builder.toString());
			query.setString("search", "%" + search + "%");
			count = ((Number) query.uniqueResult()).intValue();
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Count all pool number failed.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return count;
	}

	/**
	 * 创建资源池
	 * 
	 * @param poolName
	 * @param poolDesc
	 * @param dcuuid
	 * @return
	 */
	public OCPool createPool(String poolUuid,String poolName, String poolType, String poolDesc, String dcuuid) {
		OCPool pool = new OCPool();
		pool.setPoolUuid(poolUuid);
		pool.setPoolName(poolName);
		pool.setPoolType(poolType);
		pool.setPoolDesc(poolDesc);
		pool.setDcUuid(dcuuid);
		pool.setPoolStatus(1);
		pool.setCreateDate(new Date());
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			session.save(pool);
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Create pool failed.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
			pool = null;
		}
		return pool;
	}

	/**
	 * 删除资源池
	 * 
	 * @param poolId
	 * @return
	 */
	public boolean deletePool(String poolId) {
		OCPool delPool = getPool(poolId);
		boolean result = false;
		if (delPool != null) {
			delPool.setPoolStatus(0);
			Session session = null;
			try {
				session = sessionHelper.getMainSession();
				session.beginTransaction();
				session.update(delPool);
				session.getTransaction().commit();
				result = true;
			} catch (Exception e) {
				loginfo("Delete pool infos failed.");
				e.printStackTrace();
				if (session.isOpen()) {
					session.getTransaction().rollback();
				}
			}
		}
		return result;
	}

	/**
	 * 更新资源池
	 * 
	 * @param poolId
	 * @param poolName
	 * @param poolDesc
	 * @param dcuuid
	 * @return
	 */
	public boolean updatePool(String poolId, String poolName, String poolType, String poolDesc,
			String dcUuid) {
		boolean result = false;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String queryString = "update OCPool set poolName = :poolName, poolType = :poolType, poolDesc = :poolDesc, "
					+ "dcUuid = :dcUuid where poolUuid = :poolId";
			Query query = session.createQuery(queryString);
			query.setString("poolName", poolName);
			query.setString("poolType", poolType);
			query.setString("poolDesc", poolDesc);
			query.setString("dcUuid", dcUuid);
			query.setString("poolId", poolId);
			query.executeUpdate();
			session.getTransaction().commit();
			result = true;
		} catch (Exception e) {
			loginfo("Update pool detail infos failed.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return result;
	}

	
	
	
	public boolean checkoutpool(String poolname) {
		
		int count = 0;
		Session session = null;
		boolean flag=false;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			StringBuilder builder = new StringBuilder(
					"select count(*) from OCPool where poolName =:poolname");
			Query query = session.createQuery(builder.toString());
			query.setString("poolname", poolname);
			count = ((Number) query.uniqueResult()).intValue();
			session.getTransaction().commit();
			if(count>0){
				
			       flag=false;
				}else{
					flag=true;
				}
		} catch (Exception e) {
			loginfo("Count all pool number failed.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return flag;
	}
	public boolean update(OCPool pool) {
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			session.update(pool);
			session.getTransaction().commit();
			return true;
		} catch (Exception e) {
			loginfo("Update pool infos failed.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
			return false;
		}
	}
	
	private void loginfo(String message){
		LogConstant.loginfo(message);
	}
}
