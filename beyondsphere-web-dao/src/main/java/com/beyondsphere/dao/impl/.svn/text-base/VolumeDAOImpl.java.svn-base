package com.beyondsphere.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import com.beyondsphere.constants.LogConstant;
import com.beyondsphere.dao.VolumeDAO;
import com.beyondsphere.entity.Volume;
import com.beyondsphere.helper.SessionHelper;
import com.beyondsphere.model.VolumeStatus;


/**
 * @author zll
 * @Date 2015年5月11日
 */

@Component("VolumeDAO")
public class VolumeDAOImpl implements VolumeDAO {

	@Resource
	private SessionHelper sessionHelper;



	public Volume getOneByID(String id) {
		Volume vol = null;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			Query query = session.createQuery("from Volume where volumeUuid = :id");
			query.setString("id", id);
			vol = (Volume) query.uniqueResult();
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Get Volume failed by actionId. ");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return vol;
	}

	public int countAllList(String search) {
		int count = 0;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			StringBuilder builder = new StringBuilder(
					"select count(*) from Volume where volumeName like :search ");
			Query query = session.createQuery(builder.toString());
			query.setString("search", "%" + search + "%");
			count = ((Number) query.uniqueResult()).intValue();
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Count action list failed.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return count;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Volume> getAllList() {
		List<Volume> volumeList = null;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String queryString = "from Volume";
			Query query = session.createQuery(queryString);
			volumeList = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Get all Volume list of Page failed.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return volumeList;
	}

	private void loginfo(String message){
		LogConstant.loginfo(message);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Volume> getListbyUserid(int userid) {
		List<Volume> volumeList = null;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String queryString = "from Volume where volumeUID=:userid";
			Query query = session.createQuery(queryString);
			query.setInteger("userid", userid);
			volumeList = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Get all Volume list of Page failed.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return volumeList;
	}
	
	@SuppressWarnings("unchecked")
	public List<String> getVolumeUuidListOfVM(String vmUuid) {
		List<String> volumeList = new ArrayList<String>();		
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String queryString = "select volumeUuid from Volume where volumeDependency = :vmUuid and volumeStatus = 4 order by createDate desc";
			Query query = session.createQuery(queryString);
			query.setString("vmUuid", vmUuid);
			volumeList = query.list();			
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
			e.printStackTrace();
		}
		return volumeList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Volume> getVolumeListOfVM(String vmUuid) {
		List<Volume> volumeList = null;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String queryString = "from Volume where volumeDependency=:vmUuid and volumeStatus=4";
			Query query = session.createQuery(queryString);
			query.setString("vmUuid", vmUuid);
			volumeList = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return volumeList;
	}

	public boolean emptyDependency(String volumeUuid) {
		boolean result = false;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String str = "update Volume set volumeDependency = NULL, volumeStatus = 1 where volumeUuid = :volumeUuid";
			Query query = session.createQuery(str);
			query.setString("volumeUuid", volumeUuid);
			query.executeUpdate();
			session.getTransaction().commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return result;
	}

	public boolean updateVolumeBackdate(String volumeUuid, Date backdate) {
		
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String queryString = "update Volume set backupDate = :date where volumeUuid = :volumeUuid";
			Query query = session.createQuery(queryString);
			query.setString("volumeUuid", volumeUuid);
			query.setTimestamp("date", backdate);
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

	public boolean isActive(String volumeUuid) {
		Session session = null;
		try {
			int count = 0;
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			Query query = session
					.createQuery("select count(1) from Volume where volumeUuid = :volumeUuid and volumeStatus = 1");
			query.setString("volumeUuid", volumeUuid);
			count = ((Number) query.uniqueResult()).intValue();
			session.getTransaction().commit();
			return count > 0;
		} catch (Exception e) {
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
			return false;
		}
	}

	public Volume getVolumeByUuid(String volumeUuid) {
		Volume volume = null;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			volume = (Volume) session.get(Volume.class, volumeUuid);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return volume;
	}

	public int countByUserId(int userId, String search) {
		int count = 0;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			StringBuilder builder = new StringBuilder(
					"select count(*) from Volume where volumeUID = :userId and ");
			builder.append("volumeName like :search and volumeStatus != 0");
			Query query = session.createQuery(builder.toString());
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
	public List<Volume> getOnePageByUserId(int userId, int page,
			int limit, String search) {
		List<Volume> volumeList = new ArrayList<Volume>();
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			int startPos = (page - 1) * limit;
			StringBuilder builder = new StringBuilder(
					"from Volume where volumeUID = :userId and ");
			builder.append("volumeName like :search and volumeStatus != 0 ");
			builder.append("order by createDate desc");
			Query query = session.createQuery(builder.toString());
			query.setInteger("userId", userId);
			query.setString("search", "%" + search + "%");
			query.setFirstResult(startPos);
			query.setMaxResults(limit);
			volumeList = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return volumeList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Volume> getAbledVolumes(int userId) {
		List<Volume> volumeList = new ArrayList<Volume>();
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			StringBuilder builder = new StringBuilder(
					"from Volume where volumeUID = :userId and volumeDependency=null and volumeStatus=1 ");
			builder.append("order by createDate desc");
			Query query = session.createQuery(builder.toString());
			query.setInteger("userId", userId);
			volumeList = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return volumeList;
	}

	public boolean preCreateVolume(String volumeUuid, String volumeName,
			Integer volumeUID, Integer volumeSize, Date createDate,
			VolumeStatus status) {
		Session session = null;
		try {
			Volume volume = new Volume(volumeUuid, volumeName, volumeUID,
					volumeSize, createDate, status);
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			session.save(volume);
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

	public boolean updateVolumeStatus(String volumeUuid, VolumeStatus status) {
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String str = "update Volume set volumeStatus = :volumeStatus where volumeUuid = :volumeUuid";
			Query query = session.createQuery(str);
			query.setParameter("volumeStatus", status);
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

	public boolean deleteVolume(int userId, String volumeUuid) {
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String str = "update Volume set volumeStatus = 0 where volumeUuid = :volumeUuid and volumeUID = :userId";
			Query query = session.createQuery(str);
			query.setInteger("userId", userId);
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

	public boolean addDependency(String volumeUuid, String volumeDependency) {
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String str = "update Volume set volumeStatus = 4, volumeDependency = :volumeDependency where volumeUuid = :volumeUuid";
			Query query = session.createQuery(str);
			query.setString("volumeDependency", volumeDependency);
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
	public void updateName(String volumeUuid, String newName, String description) {
		Volume volume = getVolumeByUuid(volumeUuid);
		if (volume != null) {
			Session session = null;
			try {
				volume.setVolumeName(newName);
				volume.setVolumeDescription(description);
				session = sessionHelper.getMainSession();
				session.beginTransaction();
				session.update(volume);
				session.getTransaction().commit();
			} catch (Exception e) {
				if (session.isOpen()) {
					session.getTransaction().rollback();
				}
			}
		}
	}

}
