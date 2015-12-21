/**
 * @author hty
 * @time 上午11:05:48
 * @date 2014年12月9日
 */
package com.oncecloud.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import com.oncecloud.constants.LogConstant;
import com.oncecloud.dao.ImageDAO;
import com.oncecloud.entity.Image;
import com.oncecloud.helper.SessionHelper;
import com.oncecloud.model.ImagePlatform;
import com.oncecloud.model.ImageStatus;

@Component("ImageDAO")
public class ImageDAOImpl implements ImageDAO {
	
	@Resource
	private SessionHelper sessionHelper;
	
	public Image getImage(String imageUuid) {
		Image image = null;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			Query query = session
					.createQuery("from Image where imageUuid = :imageUuid");
			query.setString("imageUuid", imageUuid);
			image = (Image) query.uniqueResult();
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Get image infos failed by id");
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return image;
	}

	@SuppressWarnings("unchecked")
	public List<Image> getOnePageImageList(int userId, int page,
			int limit, String search, String type) {
		List<Image> imageList = null;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			int startPos = (page - 1) * limit;
			String queryString = "";
			if (type.equals("system")) {
				queryString = "from Image where imageUID = 1 and imageName like '%"
						+ search
						+ "%' and imageStatus = 1 order by createDate desc";
			} else {
				queryString = "from Image where imageUID != 1 and imageName like '%"
						+ search
						+ "%' and imageStatus = 1 order by createDate desc";
			}
			Query query = session.createQuery(queryString);
			query.setFirstResult(startPos);
			query.setMaxResults(limit);
			imageList = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Count all image list infos failed by page and user.");
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return imageList;
	}

	public int countByHost(String hostUuid) {
		int count = 0;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			StringBuilder builder = new StringBuilder(
					"select count(*) from Image where ");
			builder.append("imageStatus != 0 and hostUuid = :hostUuid");
			Query query = session.createQuery(builder.toString());
			query.setString("hostUuid", hostUuid);
			count = ((Number) query.iterate().next()).intValue();
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Count all image list infos failed by host .");
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return count;
	}

	public int countAllImageList(int userId, String search,
			String type) {
		int total = 0;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String queryString = "";
			if (type.equals("system")) {
				queryString = "select count(*) from Image where imageUID = 1 and imageName like '%"
						+ search + "%' and imageStatus = 1";
			} else {
				queryString = "select count(*) from Image where imageUID != 1 and imageName like '%"
						+ search + "%' and imageStatus = 1";
			}
			Query query = session.createQuery(queryString);
			total = ((Number) query.iterate().next()).intValue();
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Count all image list infos failed. by user ");
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return total;
	}
	
	public int countAllImageList() {
		int total = 0;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String queryString = "select count(*) from Image where imageStatus = 1";
			Query query = session.createQuery(queryString);
			total = ((Number) query.iterate().next()).intValue();
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Count all image list infos failed.");
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return total;
	}

	public boolean deleteImage(String imageId) {
		boolean result = false;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String queryString = "update Image set imageStatus = 0 where imageUuid='"
					+ imageId + "'";
			Query query = session.createQuery(queryString);
			query.executeUpdate();
			result = true;
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Delete image infos failed.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return result;
	}

	public Image createImage(String imageUuid, String imageName, int imageUID,
			int imagePlatform, String imageServer, String imageDesc, String hostUuid,
			String imagePwd) {
		Image image = null;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			image = new Image(imageUuid, imageName, imagePwd, imageUID, 20,
					ImagePlatform.values()[imagePlatform], ImageStatus.EXIST, imageServer, imageDesc, hostUuid, new Date(), null);
			session.saveOrUpdate(image);
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Create image infos failed.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return image;
	}

	public boolean isShared(String poolUuid, String referenceUuid) {
		boolean result = false;
		Session session = null;
		Integer count = 0;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String queryString = "select count(*) from Image where poolUuid=:poolUuid and referenceUuid=:referenceUuid and imageStatus = 1";
			Query query = session.createQuery(queryString);
			query.setString("poolUuid", poolUuid);
			query.setString("referenceUuid", referenceUuid);
			count = ((Number)query.uniqueResult()).intValue();
			result = count > 0? false:true;
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Check current image isShared failed.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return result;
	}

	public boolean shareImage(String imageUuid, String referenceUuid, String poolUuid) {
		boolean result = false;
		Session session = null;
		try {
			Image oldImage = this.getImage(referenceUuid);
			Image image = new Image(imageUuid, oldImage.getImageName(), oldImage.getImagePwd(), 
					oldImage.getImageUID(), oldImage.getImageDisk(), oldImage.getImagePlatform(), 
					oldImage.getImageStatus(), poolUuid, oldImage.getImageDesc(), oldImage.getHostUuid(),
					oldImage.getCreateDate(), referenceUuid);
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			session.save(image);
			session.getTransaction().commit();
			result = true;
		} catch (Exception e) {
			loginfo("Share image infos with other pool failed.");
			e.printStackTrace();
		}
		return result;
	}
	
	public boolean updateImage(Image image) {
		boolean result = false;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			session.update(image);
			session.getTransaction().commit();
			result = true;
		} catch (Exception e) {
			loginfo("Update image infos failed.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return result;
	}
	
	public boolean save(Image image) {
		boolean result = false;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			session.save(image);
			session.getTransaction().commit();
			result = true;
		} catch (Exception e) {
			loginfo("Save image infos failed.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return result;
	}

	public boolean checkImage(String uuid) {
		boolean result = false;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			if (session.get(Image.class, uuid) != null) {
				result = true;
			}
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Check image infos failed");
			e.printStackTrace();
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public List<Image> getSystemImage() {
		List<Image> imageList = new ArrayList<Image>();
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			Query query = session
					.createQuery("from Image where preAllocate > 0");
			imageList = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Get system images failed");
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return imageList;
	}

	private void loginfo(String message){
		LogConstant.loginfo(message);
	}
	
	public boolean deleteImage(String imageUuid, int userId) {
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String queryString = "update Image set imageStatus = 0 where imageUuid= :imageUuid and imageUID = :imageUID";
			Query query = session.createQuery(queryString);
			query.setString("imageUuid", imageUuid);
			query.setInteger("imageUID", userId);
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
	
	public int countImageByUserId(int userId, String search, String type) {
		int total = 0;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String queryString = "";
			if (type.equals("system")) {
				queryString = "select count(*) from Image where imageUID = 1 and imageName like '%"
						+ search
						+ "%' and imageStatus = 1 and imagePlatform < 2";
			} else {
				queryString = "select count(*) from Image where imageUID = "
						+ userId + " and imageName like '%" + search
						+ "%' and imageStatus = 1";
			}
			Query query = session.createQuery(queryString);
			total = ((Number) query.uniqueResult()).intValue();
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return total;
	}
	
	@SuppressWarnings("unchecked")
	public List<Image> getOnePageImageListByUserId(int userId, int page,
			int limit, String search, String type, String poolUuid) {
		List<Image> imageList = new ArrayList<Image>();
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			int startPos = (page - 1) * limit;
			String queryString = "";
			if (type.equals("system")) {
				queryString = "from Image where imageUID = 1 and imageName like '%"
						+ search
						+ "%' and imageStatus = 1 and poolUuid = '"
						+ poolUuid
						+ "' and imagePlatform < 2 order by createDate desc";
			} else {
				queryString = "from Image where imageUID = " + userId
						+ " and imageName like '%" + search
						+ "%' and imageStatus = 1 order by createDate desc";
			}
			Query query = session.createQuery(queryString);
			query.setFirstResult(startPos);
			query.setMaxResults(limit);
			imageList = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return imageList;
	}
	
	@Override
	public boolean updateName(String imageuuid, String newName,
			String description) {
		boolean result = false;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String queryString = "update Image set imageName=:name, imageDesc=:desc where imageUuid=:uuid";
			Query query = session.createQuery(queryString);
			query.setString("name", newName);
			query.setString("uuid", imageuuid);
			query.setString("desc", description);
			query.executeUpdate();
			result = true;
			session.getTransaction().commit();
		} catch (Exception e) {
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return result;
	}

	public Image getRTImage(String poolUuid) {
		Image image = null;
		if (poolUuid != null) {
			Session session = null;
			try {
				session = sessionHelper.getMainSession();
				session.beginTransaction();
//				Query query = session.createQuery("from Image where imagePlatform = 2 and imageStatus = 1 and poolUuid = :poolUuid");
				Query query = session.createQuery("from Image where imagePlatform = 2 and imageStatus = 1");
//				query.setString("poolUuid", poolUuid);
				image = (Image) query.uniqueResult();
				session.getTransaction().commit();
			} catch (Exception e) {
				if (session.isOpen()) {
					session.getTransaction().rollback();
				}
			}
		}
		return image;
	}
}
