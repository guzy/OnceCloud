package com.beyondsphere.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import com.beyondsphere.constants.LogConstant;
import com.beyondsphere.dao.HostDAO;
import com.beyondsphere.entity.OCHost;
import com.beyondsphere.entity.Storage;
import com.beyondsphere.helper.HashHelper;
import com.beyondsphere.helper.SessionHelper;

@Component("HostDAO")
public class HostDAOImpl implements HostDAO {

	@Resource
	private SessionHelper sessionHelper;
	@Resource
	private HashHelper hashHelper;
	
	public int countAllHostList(String search) {
		int count = 0;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String queryString = "select count(*) from OCHost where hostName like '%"
					+ search + "%' and hostStatus = 1";
			Query query = session.createQuery(queryString);
			count = ((Number) query.iterate().next()).intValue();
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Count all hosts number failed.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return count;
	}

	@SuppressWarnings("unchecked")
	public List<OCHost> getAllHost() {
		List<OCHost> list = null;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String queryString = "from OCHost where hostStatus = 1";
			Query query = session.createQuery(queryString);
			list = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Get all host list failed.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return changePwdOfHost(list);
	}

	@SuppressWarnings("unchecked")
	public OCHost getHost(String hostUuid) {
		OCHost host = null;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			Query query = session.createQuery("from OCHost where hostUuid = '"
					+ hostUuid + "'");
			List<OCHost> hostList = query.list();
			if (hostList.size() == 1) {
				host = hostList.get(0);
			}
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Get host infos failed.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return changeHostPwd(host);
	}

	@SuppressWarnings("unchecked")
	public OCHost getHostNoTransactional(String hostUuid) {
		OCHost host = null;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			Query query = session.createQuery("from OCHost where hostUuid = '"
					+ hostUuid + "'");
			List<OCHost> hostList = query.list();
			if (hostList.size() == 1) {
				host = hostList.get(0);
			}
		} catch (Exception e) {
			loginfo("Get host infos of no transactional failed.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return changeHostPwd(host);
	}

	public OCHost getHostFromIp(String hostIp) {
		OCHost host = null;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			Query query = session
					.createQuery("from OCHost where hostIP = :hostIp and hostStatus = 1");
			query.setString("hostIp", hostIp);
			host = (OCHost) query.uniqueResult();
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Get host infos from is failed.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return changeHostPwd(host);
	}

	@SuppressWarnings("unchecked")
	public List<OCHost> getHostListOfPool(String poolUuid) {
		List<OCHost> hostList = null;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String queryString = "from OCHost where poolUuid = :poolUuid and hostStatus = 1";
			Query query = session.createQuery(queryString);
			query.setString("poolUuid", poolUuid);
			hostList = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Get host list of same pool failed by page.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return changePwdOfHost(hostList);
	}

	@SuppressWarnings("unchecked")
	public List<OCHost> getOnePageHostList(int page, int limit, String search) {
		List<OCHost> hostList = null;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			int startPos = (page - 1) * limit;
			String queryString = "from OCHost where hostName like '%" + search
					+ "%' and hostStatus = 1 order by createDate desc";
			Query query = session.createQuery(queryString);
			query.setFirstResult(startPos);
			query.setMaxResults(limit);
			hostList = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Get host list failed by page.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return changePwdOfHost(hostList);
	}

	@SuppressWarnings("unchecked")
	public List<OCHost> getOnePageLoadHostList(int page, int limit,
			String search, String sruuid) {
		List<OCHost> hostList = null;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			int startPos = (page - 1) * limit;
			String queryString = "from OCHost where hostName like '%"
					+ search
					+ "%' and hostStatus = 1 and hostMem != 0 and hostCpu != 0 and hostUuid not in (select hs.hostUuid from HostSR hs where hs.srUuid = '"
					+ sruuid + "') order by createDate desc";
			Query query = session.createQuery(queryString);
			query.setFirstResult(startPos);
			query.setMaxResults(limit);
			hostList = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Get host list of same storage failed by page.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return changePwdOfHost(hostList);
	}

	@SuppressWarnings("unchecked")
	public List<Storage> getSROfHost(String hostUuid) {
		List<Storage> storageList = null;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String queryString = "select sr from Storage sr where sr.srUuid in (select hs.srUuid from HostSR hs where hs.hostUuid='"
					+ hostUuid + "')";
			Query query = session.createQuery(queryString);
			storageList = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Get storages of host failed.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return storageList;
	}

	public boolean setPool(String hostUuid, String poolUuid) {
		boolean result = false;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String queryString = "update OCHost set poolUuid = :poolUuid where hostUuid = :hostUuid";
			Query query = session.createQuery(queryString);
			query.setString("hostUuid", hostUuid);
			query.setString("poolUuid", poolUuid);
			query.executeUpdate();
			result = true;
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Add host to pool failed.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return result;
	}

	public boolean unbindSr(String hostUuid, String srUuid) {
		boolean result = false;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String queryString = "delete from HostSR where hostUuid=:hid and srUuid=:sid";
			Query query = session.createQuery(queryString);
			query.setString("hid", hostUuid);
			query.setString("sid", srUuid);
			query.executeUpdate();
			result = true;
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Unbind storage to host failed.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return result;
	}

	public boolean saveHost(OCHost host) {
		boolean result = false;
		if (host != null) {
			//服务器密码生成密文
			host.setHostPwd(hashHelper.DiyHash(host.getHostPwd()));
			Session session = null;
			try {
				session = sessionHelper.getMainSession();
				session.beginTransaction();
				session.save(host);
				session.getTransaction().commit();
				result = true;
			} catch (Exception e) {
				loginfo("Save host infos failed.");
				e.printStackTrace();
				if (session.isOpen()) {
					session.getTransaction().rollback();
				}
			}
		}
		return result;
	}

	public boolean updateHost(String hostId, String hostName, String hostDesc,
			String hostType) {
		boolean result = false;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String querysString = "update OCHost set hostName=:name,"
					+ "hostDesc=:desc,hostType=:hostType where hostUuid =:hostid";
			Query query = session.createQuery(querysString);
			query.setString("name", hostName);
			query.setString("desc", hostDesc);
			query.setString("hostType", hostType);
			query.setString("hostid", hostId);
			query.executeUpdate();
			result = true;
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Update host infos failed.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return result;
	}

	public boolean deleteHost(String hostId) {
		boolean result = false;
		OCHost host = this.getHost(hostId);
		if (host != null) {
			Session session = null;
			try {
				session = sessionHelper.getMainSession();
				session.beginTransaction();
				this.deleteHostSR(hostId);
				session.delete(host);
				session.getTransaction().commit();
				result = true;
			} catch (Exception e) {
				loginfo("Delete host infos failed.");
				e.printStackTrace();
				if (session.isOpen()) {
					session.getTransaction().rollback();
				}
			}
		}
		return result;
	}

	private void deleteHostSR(String hostId) {
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			Query query = session
				.createQuery("delete from HostSR where hostUuid = :hostId");
			query.setString("hostId", hostId);
			query.executeUpdate();
		} catch (Exception e) {
			loginfo("Delete storage of host failed.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
	}

	public boolean update(OCHost host) {
		boolean result = false;
		Session session = null;
		//服务器密码生成密文
		host.setHostPwd(hashHelper.DiyHash(host.getHostPwd()));
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			session.update(host);
			session.getTransaction().commit();
			result = true;
		} catch (Exception e) {
			loginfo("Update host infos failed.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public List<OCHost> getAllHostNotInPool() {
		List<OCHost> hostList = null;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String queryString = "from OCHost where poolUuid is null order by createDate desc";
			Query query = session.createQuery(queryString);
			hostList = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Get host list of not in pool failed.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return changePwdOfHost(hostList);
	}
	
	private List<OCHost> changePwdOfHost(List<OCHost> hosts){
		if (hosts.size() > 0) {
			for(OCHost ocHost:hosts){
				//解码服务器密码
				ocHost.setHostPwd(hashHelper.hashDIY(ocHost.getHostPwd()));
			}
		}
		return hosts;
	}
	
	private OCHost changeHostPwd(OCHost host) {
		if (host != null) {
			//解码服务器密码
			host.setHostPwd(hashHelper.hashDIY(host.getHostPwd()));
		}
		return host;
	}
	
	private void loginfo(String message){
		LogConstant.loginfo(message);
	}

}
