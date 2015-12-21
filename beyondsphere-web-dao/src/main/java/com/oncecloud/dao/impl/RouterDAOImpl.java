package com.oncecloud.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import com.oncecloud.dao.RouterDAO;
import com.oncecloud.entity.Router;
import com.oncecloud.helper.SessionHelper;
@Component("RouterDAO")
public class RouterDAOImpl implements RouterDAO {
	@Resource
	private SessionHelper sessionHelper;

	/**
	 * 获取路由器
	 * 
	 * @param routerUuid
	 * @return
	 */
	public Router getRouter(String routerUuid) {
		Router router = null;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String queryString = "from Router where routerUuid = :routerUuid";
			Query query = session.createQuery(queryString);
			query.setString("routerUuid", routerUuid);
			router = (Router) query.uniqueResult();
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return router;
	}

	public void updateRouter(Router router) {
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			session.update(router);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
	}
	
	/**
	 * 获取路由器名称
	 * 
	 * @param routerUuid
	 * @return
	 */
	public String getRouterName(String routerUuid) {
		String name = "";
		Router router = this.getRouter(routerUuid);
		if (router != null) {
			name = router.getRouterName();
		}
		return name;
	}

	/**
	 * 获取使用中的路由器
	 * 
	 * @param routerUuid
	 * @return
	 */
	public Router getAliveRouter(String routerUuid) {
		Router router = null;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String queryString = "from Router where routerUuid = :routerUuid and routerStatus = 1";
			Query query = session.createQuery(queryString);
			query.setString("routerUuid", routerUuid);
			router = (Router) query.uniqueResult();
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return router;
	}

	/**
	 * 获取一页用户路由器列表
	 * 
	 * @param userId
	 * @param page
	 * @param limit
	 * @param search
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Router> getOnePageRouters(int page, int limit,
			String search) {
		List<Router> routerList = null;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			int startPos = (page - 1) * limit;
			String queryString = "from Router where routerName like :search and routerStatus > 0 order by createDate desc";
			Query query = session.createQuery(queryString);
			query.setString("search", "%" + search + "%");
			query.setFirstResult(startPos);
			query.setMaxResults(limit);
			routerList = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return routerList;
	}

	/**
	 * 获取一页管理员路由器列表
	 * 
	 * @param page
	 * @param limit
	 * @param host
	 * @param importance
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Router> getOnePageRoutersOfAdmin(int page, int limit,
			String host, int importance) {
		List<Router> rtList = null;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			int startPos = (page - 1) * limit;
			Criteria criteria = session.createCriteria(Router.class);
			criteria.add(Restrictions.ne("routerStatus", 0));
			criteria.setFirstResult(startPos);
			criteria.setMaxResults(limit);
			if (!host.equals("all")) {
				criteria.add(Restrictions.eq("hostUuid", host));
			}
			if (importance != 6) {
				criteria.add(Restrictions.eq("routerImportance", importance));
			}
			rtList = criteria.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return rtList;
	}

	/**
	 * 获取一页未设置监控警告的路由器列表
	 * 
	 * @param page
	 * @param limit
	 * @param search
	 * @param userId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Router> getOnePageRoutersWithoutAlarm(int page, int limit,
			String search, int userId) {
		List<Router> routerList = null;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			int startPos = (page - 1) * limit;
			String queryString = "from Router where routerUID = :userId and routerName like :search "
					+ "and routerStatus > 0 and alarmUuid is null order by createDate desc";
			Query query = session.createQuery(queryString);
			query.setString("search", "%" + search + "%");
			query.setInteger("userId", userId);
			query.setFirstResult(startPos);
			query.setMaxResults(limit);
			routerList = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return routerList;
	}

	/**
	 * 获取一页没有绑定公网IP的路由器列表
	 * 
	 * @param page
	 * @param limit
	 * @param search
	 * @param userId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Router> getOnePageRoutersWithoutEip(int page, int limit,
			String search, int userId) {
		List<Router> routerList = null;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			int startPos = (page - 1) * limit;
			String queryString = "from Router where routerUID = :userId and routerName like :search "
					+ "and routerStatus <>0 and routerUuid not in "
					+ "(select eip.eipDependency from EIP eip where eip.eipUID = :uid "
					+ "and eip.eipDependency is not null)";
			Query query = session.createQuery(queryString);
			query.setInteger("userId", userId);
			query.setString("search", "%" + search + "%");
			query.setInteger("uid", userId);
			query.setFirstResult(startPos);
			query.setMaxResults(limit);
			routerList = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return routerList;
	}

	/**
	 * 获取对应监控警告的路由器列表
	 * 
	 * @param routerUID
	 * @param alarmUuid
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Router> getRoutersOfAlarm(int routerUID, String alarmUuid) {
		List<Router> list = null;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String queryString = "from Router where routerUID = :routerUID "
					+ "and routerStatus > 0 and alarmUuid = :alarmUuid order by createDate desc";
			Query query = session.createQuery(queryString);
			query.setInteger("routerUID", routerUID);
			query.setString("alarmUuid", alarmUuid);
			list = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return list;
	}
	@SuppressWarnings("unchecked")
	public List<Router> getAllRouters() {
		List<Router> list = null;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String queryString = "from Router where routerStatus > 0 order by createDate desc";
			Query query = session.createQuery(queryString);
			list = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return list;
	}
	/**
	 * 获取用户路由器列表
	 * 
	 * @param userId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Router> getRoutersOfUser() {
		Session session = null;
		List<Router> resultList=new ArrayList<Router>();
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String queryString = "from Router where routerStatus <> 0";
			Query query = session.createQuery(queryString);
			resultList = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return resultList;
	}

	/**
	 * 获取用户路由器总数
	 * 
	 * @param userId
	 * @param search
	 * @return
	 */
	public int countRouters(String search) {
		int count = 0;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String queryString = "select count(*) from Router where routerName like :search and routerStatus > 0";
			Query query = session.createQuery(queryString);
			query.setString("search", "%" + search + "%");
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

	/**
	 * 获取管理员路由器总数
	 * 
	 * @param host
	 * @param importance
	 * @return
	 */
	public int countRoutersOfAdmin(String host, int importance) {
		int count = 0;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(Router.class);
			criteria.add(Restrictions.ne("routerStatus", 0));
			if (!host.equals("all")) {
				criteria.add(Restrictions.eq("hostUuid", host));
			}
			if (importance != 6) {
				criteria.add(Restrictions.eq("routerImportance", importance));
			}
			criteria.setProjection(Projections.rowCount());
			count = ((Number) criteria.uniqueResult()).intValue();
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return count;
	}

	/**
	 * 获取未设置监控警告的路由器总数
	 * 
	 * @param search
	 * @param routerUID
	 * @return
	 */
	public int countRoutersWithoutAlarm(String search, int uuid) {
		int count = 0;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(Router.class)
					.add(Restrictions.eq("routerUID", uuid))
					.add(Restrictions.ne("routerStatus", 0))
					.add(Restrictions.isNull("alarmUuid"))
					.setProjection(Projections.rowCount());
			count = ((Number) criteria.uniqueResult()).intValue();
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return count;
	}

	/**
	 * 获取未绑定公网IP的路由器总数
	 * 
	 * @param search
	 * @param userId
	 * @return
	 */
	public int countRoutersWithoutEIP(String search, int userId) {
		int count = 0;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String queryString = "select count(*) from Router where routerUID = :userId "
					+ "and routerName like :search and routerStatus <> 0 and routerUuid not in "
					+ "(select eip.eipDependency from EIP eip where eip.eipUID = :uid "
					+ "and eip.eipDependency is not null)";
			Query query = session.createQuery(queryString);
			query.setInteger("userId", userId);
			query.setString("search", "%" + search + "%");
			query.setInteger("uid", userId);
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

	/**
	 * 预创建路由器
	 * 
	 * @param uuid
	 * @param pwd
	 * @param userId
	 * @param name
	 * @param mac
	 * @param capacity
	 * @param fwuuid
	 * @param power
	 * @param status
	 * @param createDate
	 * @return
	 */
	public boolean preCreateRouter(String uuid, String pwd, int userId,
			String name, String mac, int power,
			int status, Date createDate) {
		boolean result = false;
		Session session = null;
		try {
			Router router = new Router(uuid, pwd, userId, name, mac, power, status, createDate);
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			session.save(router);
			session.getTransaction().commit();
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return result;
	}

	/**
	 * 删除路由器
	 * 
	 * @param userId
	 * @param uuid
	 */
	public void removeRouter(int userId, String uuid) {
		Router toDelete = this.getRouter(uuid);
		if (toDelete != null) {
			Session session = null;
			try {
				toDelete.setRouterStatus(0);
				session = sessionHelper.getMainSession();
				session.beginTransaction();
				session.update(toDelete);
				session.getTransaction().commit();
			} catch (Exception e) {
				e.printStackTrace();
				if (session.isOpen()) {
					session.getTransaction().rollback();
				}
			}
		}
	}

	/**
	 * 更新路由器
	 * 
	 * @param userId
	 * @param uuid
	 * @param pwd
	 * @param power
	 * @param firewallId
	 * @param hostUuid
	 * @param ip
	 */
	public void updateRouter(int userId, String uuid, String pwd, int power,
			String hostUuid, String ip) {
		Router router = this.getRouter(uuid);
		if (router != null) {
			Session session = null;
			try {
				router.setRouterPWD(pwd);
				router.setRouterPower(power);
				router.setHostUuid(hostUuid);
				router.setRouterIP(ip);
				session = sessionHelper.getMainSession();
				session.beginTransaction();
				session.update(router);
				session.getTransaction().commit();
			} catch (Exception e) {
				e.printStackTrace();
				if (session.isOpen()) {
					session.getTransaction().rollback();
				}
			}
		}
	}

	/**
	 * 更新路由器状态
	 * 
	 * @param routerUuid
	 * @param status
	 * @return
	 */
	public boolean updateStatus(String routerUuid, int status) {
		boolean result = false;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String queryString = "update Router set routerStatus = :status "
					+ "where routerUuid = :routerUuid";
			Query query = session.createQuery(queryString);
			query.setString("routerUuid", routerUuid);
			query.setInteger("status", status);
			query.executeUpdate();
			session.getTransaction().commit();
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return result;
	}

	/**
	 * 更新路由器电源状态
	 * 
	 * @param uuid
	 * @param powerStatus
	 * @return
	 */
	public boolean updatePowerStatus(String uuid, int powerStatus) {
		boolean result = false;
		Router router = this.getRouter(uuid);
		if (router != null) {
			Session session = null;
			try {
				router.setRouterPower(powerStatus);
				session = sessionHelper.getMainSession();
				session.beginTransaction();
				session.update(router);
				session.getTransaction().commit();
				result = true;
			} catch (Exception e) {
				e.printStackTrace();
				if (session.isOpen()) {
					session.getTransaction().rollback();
				}
			}
		}
		return result;
	}

	public boolean updateImportance(String uuid, int routerImportance) {
		boolean result = false;
		Router router = this.getRouter(uuid);
		if (router != null) {
			Session session = null;
			try {
				session = sessionHelper.getMainSession();
				session.beginTransaction();
				session.update(router);
				session.getTransaction().commit();
				result = true;
			} catch (Exception e) {
				e.printStackTrace();
				if (session.isOpen()) {
					session.getTransaction().rollback();
				}
			}
		}
		return result;
	}

	/**
	 * 更新路由器防火墙
	 * 
	 * @param routerUuid
	 * @param firewallId
	 */
	public void updateFirewall(String routerUuid, String firewallId) {
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String queryString = "update Router set firewallUuid= :firewallId "
					+ "where routerUuid = :routerUuid";
			Query query = session.createQuery(queryString);
			query.setString("firewallId", firewallId);
			query.setString("routerUuid", routerUuid);
			query.executeUpdate();
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
	}
	
	/**
	 * 更新路由器防火墙
	 * 
	 * @param routerUuid
	 * @param innerfirewallId
	 */
	public void updateInnerFirewall(String routerUuid, String innerfirewallId) {
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String queryString = "update Router set innerFirewallUuid= :firewallId "
					+ "where routerUuid = :routerUuid";
			Query query = session.createQuery(queryString);
			query.setString("firewallId", innerfirewallId);
			query.setString("routerUuid", routerUuid);
			query.executeUpdate();
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
	}

	/**
	 * 更新路由器名称和描述
	 * 
	 * @param routeruuid
	 * @param routerName
	 * @param routerDesc
	 * @return
	 */
	public boolean updateName(String routeruuid, String routerName,
			String routerDesc) {
		boolean result = false;
		Session session = null;
		try {
			Router router = this.getRouter(routeruuid);
			router.setRouterName(routerName);
			router.setRouterDesc(routerDesc);
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			session.update(router);
			session.getTransaction().commit();
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return result;
	}

	/**
	 * 更新路由器所在服务器
	 * 
	 * @param uuid
	 * @param hostUuid
	 * @return
	 */
	public boolean updateHostUuid(String uuid, String hostUuid) {
		boolean result = false;
		Router rt = getRouter(uuid);
		if (rt != null) {
			Session session = null;
			try {
				rt.setHostUuid(hostUuid);
				session = sessionHelper.getMainSession();
				session.beginTransaction();
				session.update(rt);
				session.getTransaction().commit();
				result = true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 更新路由器监控警告
	 * 
	 * @param routerUuid
	 * @param alarmUuid
	 */
	public void updateAlarm(String routerUuid, String alarmUuid) {
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String queryString = "update Router set alarmUuid = :alarmUuid where routerUuid = :routerUuid";
			Query query = session.createQuery(queryString);
			query.setString("alarmUuid", alarmUuid);
			query.setString("routerUuid", routerUuid);
			query.executeUpdate();
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
	}

	/**
	 * 是否有路由器具有该监控警告
	 * 
	 * @param alarmUuid
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public boolean isNotExistAlarm(String alarmUuid) {
		boolean result = true;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String queryString = "from Router where alarmUuid = :alarmUuid and routerStatus > 0";
			Query query = session.createQuery(queryString);
			query.setString("alarmUuid", alarmUuid);
			List<Router> routerList = query.list();
			if (routerList.size() > 0) {
				result = false;
			}
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return result;
	}

	/**
	 * 更新路由器电源状态和所在服务器
	 * 
	 * @param session
	 * @param uuid
	 * @param power
	 * @param hostUuid
	 */
	public void updatePowerAndHostNoTransaction(String uuid, int power,
			String hostUuid) {
		Session session = sessionHelper.getMainSession();
		String queryString = "update Router set routerPower = :power, hostUuid = :hostUuid where routerUuid = :uuid";
		Query query = session.createQuery(queryString);
		query.setInteger("power", power);
		query.setString("hostUuid", hostUuid);
		query.setString("uuid", uuid);
		query.executeUpdate();
	}

}
