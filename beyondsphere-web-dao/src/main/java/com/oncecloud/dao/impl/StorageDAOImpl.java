/**
 * @author hty
 * @time 上午11:05:48
 * @date 2014年12月9日
 */
package com.oncecloud.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import com.oncecloud.constants.LogConstant;
import com.oncecloud.dao.StorageDAO;
import com.oncecloud.entity.Storage;
import com.oncecloud.helper.SessionHelper;

@Component("StorageDAO")
public class StorageDAOImpl implements StorageDAO {
	
	@Resource
	private SessionHelper sessionHelper;

	/**
	 * 获取存储
	 * 
	 * @param srUuid
	 * @return
	 */
	public Storage getStorage(String srUuid) {
		Storage sr = null;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			Query query = session
					.createQuery("from Storage where srUuid = :srUuid");
			query.setString("srUuid", srUuid);
			sr = (Storage) query.uniqueResult();
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Get storage infos failed by id.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return sr;
	}

	/**
	 * 获取存储大小
	 * 
	 * @param hostUuid
	 * @return
	 */
	public int getStorageSize(String hostUuid) {
		int total = 0;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String queryString = "select count(*) from HostSR where hostUuid = :hostUuid";
			Query query = session.createQuery(queryString);
			query.setString("hostUuid", hostUuid);
			total = ((Number) query.iterate().next()).intValue();
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Get storage size failed by hostId.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return total;
	}

	/**
	 * 获取一页存储列表
	 * 
	 * @param page
	 * @param limit
	 * @param search
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Storage> getOnePageStorageList(int page, int limit,
			String search) {
		List<Storage> srList = null;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			int startPos = (page - 1) * limit;
			String queryString = "from Storage where srName like :search and srstatus = 1 order by createDate desc";
			Query query = session.createQuery(queryString);
			query.setString("search", "%" + search + "%");
			query.setFirstResult(startPos);
			query.setMaxResults(limit);
			srList = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Get storage list failed by page.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return srList;
	}

	/**
	 * 获取主机存储列表
	 * 
	 * @param hostUuid
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Storage> getStorageListOfHost(String hostUuid) {
		List<Storage> srList = null;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			Query query = session
					.createQuery("from Storage where srUuid in (select s.srUuid from HostSR s where s.hostUuid = :hostUuid) and srstatus = 1");
			query.setString("hostUuid", hostUuid);
			srList = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Get storage list failed by host id.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return srList;
	}

	/**
	 * 获取对应目标IP的存储
	 * 
	 * @param address
	 * @return
	 */
	public Storage getStorageOfAddress(String address) {
		Session session = null;
		Storage sr = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			Query query = session
					.createQuery("from Storage where srAddress = :address "
							+ "and srstatus = 1");
			query.setString("address", address);
			sr = (Storage) query.uniqueResult();
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Get storage infos failed by address.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return sr;
	}

	/**
	 * 获取存储总数
	 * 
	 * @param search
	 * @return
	 */
	public int countAllStorageList(String search) {
		int count = 0;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String queryString = "select count(*) from Storage where srName like :search and srstatus = 1 ";
			Query query = session.createQuery(queryString);
			query.setString("search", "%" + search + "%");
			count = ((Number) query.uniqueResult()).intValue();
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Count storage numbers failed.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return count;
	}

	/**
	 * 获取挂载该存储的服务器数目
	 * 
	 * @param srUuid
	 * @return
	 */
	public int countHostsOfStorage(String srUuid) {
		int total = 0;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String queryString = "select count(*) from HostSR where srUuid = :srUuid";
			Query query = session.createQuery(queryString);
			query.setString("srUuid", srUuid);
			total = ((Number) query.uniqueResult()).intValue();
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Count storage numbers of host failed.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return total;
	}

	/**
	 * 创建存储
	 * 
	 * @param srname
	 * @param srAddress
	 * @param srDesc
	 * @param srType
	 * @param srDir
	 * @param rackid
	 * @return
	 */
	public Storage createStorage(String srname, String srAddress,
			String srDesc, String srType, String srDir) {
		Storage storage = new Storage();
		storage.setSrUuid(UUID.randomUUID().toString());
		storage.setSrName(srname);
		storage.setSrDesc(srDesc);
		storage.setSrAddress(srAddress.trim());
		storage.setSrtype(srType);
		storage.setSrdir(srDir);
		storage.setSrstatus(1);
		storage.setCreateDate(new Date());
		if("local_ocfs2".equals(srType)){
			storage.setDiskuuid("00000000-0000-0000-0000-000000000002");
			storage.setIsouuid("00000000-0000-0000-0000-000000000001");
		}else{
			storage.setDiskuuid(UUID.randomUUID().toString());
			storage.setIsouuid(UUID.randomUUID().toString());
			storage.setHauuid(UUID.randomUUID().toString());
		}
		
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			session.save(storage);
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Create storage infos failed.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return storage;
	}

	/**
	 * 删除存储
	 * 
	 * @param storageId
	 * @return
	 */
	public boolean removeStorage(String storageId) {
		boolean result = false;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String queryString = "update Storage set srstatus = 0 where srUuid = :storageId";
			Query query = session.createQuery(queryString);
			query.setString("storageId", storageId);
			query.executeUpdate();
			session.getTransaction().commit();
			result = true;
		} catch (Exception e) {
			loginfo("Delete storage infos failed.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return result;
	}

	/**
	 * 更新存储
	 * 
	 * @param srId
	 * @param srName
	 * @param srDesc
	 * @param rackId
	 * @return
	 */
	public boolean updateSR(String srId, String srName, String srDesc) {
		boolean result = false;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			Query query = session
					.createQuery("update Storage set srName= :srName, "
							+ "srDesc= :srDesc where srUuid = :srId");
			query.setString("srName", srName);
			query.setString("srDesc", srDesc);
			query.setString("srId", srId);
			query.executeUpdate();
			session.getTransaction().commit();
			result = true;
		} catch (Exception e) {
			loginfo("Update storage infos failed.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return result;
	}
	
	private void loginfo(String message){
		LogConstant.loginfo(message);
	}
}
