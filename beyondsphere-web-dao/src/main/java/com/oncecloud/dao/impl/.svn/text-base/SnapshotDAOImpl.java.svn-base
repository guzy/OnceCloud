/**
 * @author hty
 * @time 下午12:30:38
 * @date 2014年12月11日
 */
package com.beyondsphere.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import com.beyondsphere.dao.SnapshotDAO;
import com.beyondsphere.entity.Snapshot;
import com.beyondsphere.helper.SessionHelper;
import com.beyondsphere.util.Utilities;

@Component("SnapshotDAO")
public class SnapshotDAOImpl implements SnapshotDAO {

	@Resource
	private SessionHelper sessionHelper;

	/**
	 * 获取快照
	 * 
	 * @param snapshotId
	 * @return
	 */
	public Snapshot getSnapshot(String snapshotId) {
		Snapshot ss = null;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String queryString = "from Snapshot where snapshotId = :snapshotId";
			Query query = session.createQuery(queryString);
			query.setString("snapshotId", snapshotId);
			ss = (Snapshot) query.uniqueResult();
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return ss;
	}

	/**
	 * 获取主机的备份链
	 * 
	 * @param vmUuid
	 * @return
	 */
	public Object getOneVmSnapshot(String vmUuid) {
		Object object = null;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String queryString = "select ss.snapshotVm, ov.vmName, count(*), sum(ss.snapshotSize), max(ss.backupDate), ov.vmStatus "
					+ "from Snapshot ss, OCVM ov "
					+ "where ss.snapshotVm = ov.vmUuid "
					+ "group by ss.snapshotVm, ov.vmName "
					+ "having ss.snapshotVm = :vmUuid ";
			Query query = session.createQuery(queryString);
			query.setString("vmUuid", vmUuid);
			object = query.list().get(0);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return object;
	}

	/**
	 * 获取硬盘的备份链
	 * 
	 * @param volumeUuid
	 * @return
	 */
	public Object getOneVolumeSnapshot(String volumeUuid) {
		Object object = null;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String queryString = "select ss.snapshotVolume, ol.volumeName, count(*), sum(ss.snapshotSize), max(ss.backupDate), ol.volumeStatus "
					+ "from Snapshot ss, Volume ol "
					+ "where ss.snapshotVolume=ol.volumeUuid "
					+ "group by ss.snapshotVolume, ol.volumeName "
					+ "having ss.snapshotVolume=:volumeUuid";
			Query query = session.createQuery(queryString);
			query.setString("volumeUuid", volumeUuid);
			object = query.list().get(0);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return object;
	}

	/**
	 * 获取一页主机备份链列表
	 * 
	 * @param page
	 * @param limit
	 * @param search
	 * @param uid
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Object> getOnePageVMSnapshotList(int userId, int page,
			int limit, String search) {
		List<Object> vmSnapshotList = null;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			int startPos = (page - 1) * limit;
			String queryString = "select ss.snapshotVm, ov.vmName, count(*), sum(ss.snapshotSize), max(ss.backupDate) "
					+ "from Snapshot ss, OCVM ov where ss.snapshotVm = ov.vmUuid "
					+ "group by ss.snapshotVm, ov.vmName having ss.snapshotVm in "
					+ "(select vm.vmUuid from OCVM vm where vm.vmUid = :userId "
					+ "and vm.vmName like :search) order by ov.vmCreateDate desc";
			Query query = session.createQuery(queryString);
			query.setInteger("userId", userId);
			query.setString("search", "%" + search + "%");
			query.setFirstResult(startPos);
			query.setMaxResults(limit);
			vmSnapshotList = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return vmSnapshotList;
	}

	/**
	 * 获取一页硬盘备份链列表
	 * 
	 * @param page
	 * @param limit
	 * @param search
	 * @param uid
	 * @param offside
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Object> getOnePageVolumeSnapshotList(int userId, int page,
			int limit, String search, int offside) {
		List<Object> volumeSnapshotList = null;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			int startPos = (page - 1) * limit;
			String queryString = "select ss.snapshotVolume, ol.volumeName, count(*), sum(ss.snapshotSize), max(ss.backupDate) "
					+ "from Snapshot ss, Volume ol where ss.snapshotVolume = ol.volumeUuid "
					+ "group by ss.snapshotVolume, ol.volumeName having ss.snapshotVolume in "
					+ "(select v.volumeUuid from Volume v where v.volumeUID = :userId "
					+ "and v.volumeName like :search) order by ol.createDate desc";
			Query query = session.createQuery(queryString);
			query.setInteger("userId", userId);
			query.setString("search", "%" + search + "%");
			query.setFirstResult(startPos);
			query.setMaxResults(limit);
			volumeSnapshotList = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return volumeSnapshotList;
	}

	/**
	 * 获取虚拟机快照列表
	 * 
	 * @param vmUuid
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Snapshot> getVmSnapshotList(String vmUuid) {
		List<Snapshot> snapshotList = null;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String queryString = "from Snapshot where snapshotVm = :vmUuid order by backupDate desc";
			Query query = session.createQuery(queryString);
			query.setString("vmUuid", vmUuid);
			snapshotList = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return snapshotList;
	}

	/**
	 * 获取硬盘快照列表
	 * 
	 * @param volumeUuid
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Snapshot> getVolumeSnapshotList(String volumeUuid) {
		List<Snapshot> snapshotList = null;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String queryString = "from Snapshot where snapshotVolume = :volumeUuid order by backupDate desc";
			Query query = session.createQuery(queryString);
			query.setString("volumeUuid", volumeUuid);
			snapshotList = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return snapshotList;
	}

	/**
	 * 获取最近的主机快照时间
	 * 
	 * @param vmUuid
	 * @return
	 */
	public Date getRecentVmSnapshotDate(String vmUuid) {
		Date date = null;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String queryString = "select max(ss.backupDate) "
					+ "from Snapshot ss group by ss.snapshotVm "
					+ "having ss.snapshotVm = :vmUuid";
			Query query = session.createQuery(queryString);
			query.setString("vmUuid", vmUuid);
			date = (Date) query.uniqueResult();
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return date;
	}

	/**
	 * 获取最近的硬盘快照时间
	 * 
	 * @param volumeUuid
	 * @return
	 */
	public Date getRecentVolumeSnapshotDate(String volumeUuid) {
		Date date = null;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String queryString = "select max(ss.backupDate) "
					+ "from Snapshot ss group by ss.snapshotVolume "
					+ "having ss.snapshotVolume = :volumeUuid";
			Query query = session.createQuery(queryString);
			query.setString("volumeUuid", volumeUuid);
			date = (Date) query.uniqueResult();
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return date;
	}

	/**
	 * 获取主机快照数目
	 * 
	 * @param vmUuid
	 * @return
	 */
	public int getVmSnapshotSize(String vmUuid) {
		int size = 0;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String queryString = "select sum(ss.snapshotSize) from Snapshot ss where ss.snapshotVm = :vmUuid";
			Query query = session.createQuery(queryString);
			query.setString("vmUuid", vmUuid);
			size = ((Number) query.uniqueResult()).intValue();
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return size;
	}

	/**
	 * 获取硬盘快照数目
	 * 
	 * @param volumeUuid
	 * @return
	 */
	public int getVolumeSnapshotSize(String volumeUuid) {
		int size = 0;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String queryString = "select sum(ss.snapshotSize) from Snapshot ss where ss.snapshotVolume = :volumeUuid";
			Query query = session.createQuery(queryString);
			query.setString("volumeUuid", volumeUuid);
			size = ((Number) query.uniqueResult()).intValue();
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return size;
	}

	/**
	 * 获取主机备份链总数
	 * 
	 * @param userId
	 * @param search
	 * @return
	 */
	public int countVMSnapshotList(int userId, String search) {
		int count = 0;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String queryString1 = "select count(distinct ss.snapshotVm) "
					+ "from Snapshot ss where ss.snapshotVm in "
					+ "(select vm.vmUuid from OCVM vm where vm.vmUid= :userId "
					+ "and vm.vmName like :search)";
			Query query = session.createQuery(queryString1);
			query.setInteger("userId", userId);
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
	 * 获取备份链总数
	 * 
	 * @param search
	 * @param uid
	 * @return
	 */
	public int countAllSnapshotList(int userId, String search) {
		int count = 0;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String queryString1 = "select count(distinct ss.snapshotVm) "
					+ "from Snapshot ss where ss.snapshotVm in "
					+ "(select vm.vmUuid from OCVM vm where vm.vmUid= :userId "
					+ "and vm.vmName like :search)";
			Query query1 = session.createQuery(queryString1);
			query1.setInteger("userId", userId);
			query1.setString("search", "%" + search + "%");
			int vmCount = ((Number) query1.uniqueResult()).intValue();
			String queryString2 = "select count(distinct ss.snapshotVolume) "
					+ "from Snapshot ss where ss.snapshotVolume in "
					+ "(select v.volumeUuid from Volume v where v.volumeUID = :userId "
					+ "and v.volumeName like :search)";
			Query query2 = session.createQuery(queryString2);
			query2.setInteger("userId", userId);
			query2.setString("search", "%" + search + "%");
			int volumeCount = ((Number) query2.uniqueResult()).intValue();
			count = vmCount + volumeCount;
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
	 * 判断是否是新的备份链
	 * 
	 * @param uuid
	 * @return
	 */
	public boolean ifNewChain(String uuid) {
		boolean result = false;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String queryString = "from Snapshot where snapshotVm = :uuid or snapshotVolume = :uuid";
			Query query = session.createQuery(queryString);
			query.setString("uuid", uuid);
			int size = query.list().size();
			if (size == 0) {
				result = true;
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
	 * 创建新的快照
	 * 
	 * @param snapshotId
	 * @param snapshotName
	 * @param snapshotSize
	 * @param backupDate
	 * @param snapshotVm
	 * @param snapshotVolume
	 * @param newChain
	 * @param userId
	 */
	public boolean insertSnapshot(String snapshotId, String snapshotName,
			int snapshotSize, Date backupDate, String snapshotVm,
			String snapshotVolume, int userId) {
		Session session = null;
		try {
			Snapshot snapshot = new Snapshot(snapshotId, snapshotName,
					snapshotSize, backupDate, snapshotVm, snapshotVolume, userId);
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			session.save(snapshot);
			session.getTransaction().commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
			return false;
		}
	}

	/**
	 * 删除快照
	 * 
	 * @param userId
	 * @param snapshotId
	 */
	public boolean deleteOneSnapshot(Snapshot ss) {
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			if (ss != null) {
				Query query__ = session.createQuery("update OCStatistics set deleteDate = :deletedate where uuid = :uuid");
				query__.setString("uuid", ss.getSnapshotId());
				query__.setDate("deletedate", new Date());
				query__.executeUpdate();
				session.delete(ss);
			}
			session.getTransaction().commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
			return false;
		}
	}

	/**
	 * 删除主机的全部快照
	 * 
	 * @param vmUuid
	 * @param userId
	 */
	@SuppressWarnings("unchecked")
	public boolean deleteVmSnapshot(String vmUuid, int userId) {
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			Query query_ = session.createQuery("from Snapshot where snapshotVm = :vmUuid");
			query_.setString("vmUuid", vmUuid);
			ArrayList<Snapshot> list = ((ArrayList<Snapshot>) query_.list());
			for (Snapshot snapshot : list) {
				Query query__ = session.createQuery("update OCStatistics set deleteDate = :deletedate where uuid = :uuid");
				query__.setString("uuid", snapshot.getSnapshotId());
				query__.setString("deletedate", Utilities.formatTime(new Date()));
				query__.executeUpdate();
			}
			String queryString = "delete from Snapshot where snapshotVm = :vmUuid";
			Query query = session.createQuery(queryString);
			query.setString("vmUuid", vmUuid);
			query.executeUpdate();
			session.getTransaction().commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
			return false;
		}
	}

	/**
	 * 删除硬盘的全部快照
	 * 
	 * @param volumeUuid
	 * @param userId
	 */
	@SuppressWarnings("unchecked")
	public boolean deleteVolumeSnapshot(String volumeUuid, int userId) {
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			Query query_ = session.createQuery("from Snapshot where snapshotVolume = :volumeUuid");
			query_.setString("volumeUuid", volumeUuid);
			ArrayList<Snapshot> list = ((ArrayList<Snapshot>) query_.list());
			for (Snapshot snapshot : list) {
				Query query__ = session.createQuery("update OCStatistics set deleteDate = :deletedate where uuid = :uuid");
				query__.setString("uuid", snapshot.getSnapshotId());
				query__.setString("deletedate", Utilities.formatTime(new Date()));
				query__.executeUpdate();
			}
			String queryString = "delete from Snapshot where snapshotVolume = :volumeUuid";
			Query query = session.createQuery(queryString);
			query.setString("volumeUuid", volumeUuid);
			query.executeUpdate();
			session.getTransaction().commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
			return false;
		}
	}

	@Override
	public int countByUserId(int userId, String search) {
		int count = 0;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String queryString = "select count(*) from Snapshot where userId = :userId and snapshotName like :search";
			Query query = session.createQuery(queryString);
			query.setInteger("userId", userId);
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

	@SuppressWarnings("unchecked")
	@Override
	public List<Snapshot> getOnePageByUserId(int userId, int page, int limit,
			String search) {
		List<Snapshot> list = new ArrayList<Snapshot>();
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			int startPos = (page - 1) * limit;
			StringBuilder queryString = new StringBuilder("from Snapshot where userId = :userId and snapshotName like :search ");
			queryString.append("order by backupDate desc");
			Query query = session.createQuery(queryString.toString());
			query.setInteger("userId", userId);
			query.setString("search", "%" + search + "%");
			query.setFirstResult(startPos);
			query.setMaxResults(limit);
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
}
