package com.oncecloud.service.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;

import org.Xen.API.Connection;
import org.Xen.API.VM;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.oncecloud.constants.SystemConstant;
import com.oncecloud.dao.HostDAO;
import com.oncecloud.dao.HostSRDAO;
import com.oncecloud.dao.ImageDAO;
import com.oncecloud.dao.PoolDAO;
import com.oncecloud.dao.UserDAO;
import com.oncecloud.dao.VMDAO;
import com.oncecloud.entity.Image;
import com.oncecloud.entity.OCLog;
import com.oncecloud.entity.OCPool;
import com.oncecloud.entity.OCVM;
import com.oncecloud.entity.User;
import com.oncecloud.log.LogAction;
import com.oncecloud.log.LogObject;
import com.oncecloud.log.LogRecord;
import com.oncecloud.log.LogRole;
import com.oncecloud.message.MessageUtil;
import com.oncecloud.model.ImagePlatform;
import com.oncecloud.service.ImageService;
import com.oncecloud.util.TimeUtils;
import com.oncecloud.util.Utilities;

@Component("ImageService")
public class ImageServiceImpl implements ImageService {
	
	@Resource
	private ImageDAO imageDAO;
	@Resource
	private UserDAO userDAO;
	@Resource
	private PoolDAO poolDAO;
	@Resource
	private HostSRDAO hostSRDAO;
	@Resource
	private LogRecord logRecord;
	@Resource
	private MessageUtil message;
	@Resource
	private VMDAO vmDAO;
	@Resource
	private HostDAO hostDAO;
	
	public JSONArray getImageList(int userId, int userLevel, int page,
			int limit, String search, String type) {
		JSONArray ja = new JSONArray();
		int totalNum = imageDAO.countAllImageList(userId, search, type);
		List<Image> imageList = imageDAO.getOnePageImageList(userId,
				page, limit, search, type);
		ja.put(totalNum);
		if (imageList != null) {
			for (Image image : imageList) {
				JSONObject jo = new JSONObject();
				jo.put("imageid", image.getImageUuid());
				jo.put("imagename", TimeUtils.encodeText(image.getImageName()));
				jo.put("imagesize", image.getImageDisk());
				jo.put("imageplatform", TimeUtils.encodeText(SystemConstant.Platform
						.values()[image.getImagePlatform().ordinal()].toString()));
				jo.put("imagestatus", image.getImageStatus());
				User imageUser = userDAO.getUser(image.getImageUID());
				jo.put("imageuser", imageUser.getUserName());
				jo.put("createDate",
						TimeUtils.formatTime(image.getCreateDate()));
				OCPool pool = poolDAO.getPool(image.getPoolUuid());
				jo.put("pooluuid", pool.getPoolUuid());
				jo.put("poolname", pool.getPoolName());
				jo.put("reference", image.getReferenceUuid());
				ja.put(jo);
			}
		}
		return ja;
	}

	public JSONArray createImage(int userId, int userLevel, String imageUuid,
			String imageName, String imageServer, int imageOs,
			String imageDesc, String hostUuid, String imagePwd) {
		JSONArray ja = new JSONArray();
		Date startTime = new Date();
		int imageUID = userId;
		if (userLevel == 0) {
			imageUID = 1;
		}
		Image image = imageDAO.createImage(imageUuid, imageName,
					imageUID, imageOs, imageServer, imageDesc, hostUuid, imagePwd);
		Date endTime = new Date();
		if (image != null) {
			JSONObject jo = new JSONObject();
			jo.put("imageid", image.getImageUuid());
			jo.put("imagename", TimeUtils.encodeText(image.getImageName()));
			jo.put("imagesize", image.getImageDisk());
			jo.put("imageplatform", TimeUtils.encodeText(SystemConstant.Platform
					.values()[image.getImagePlatform().ordinal()].toString()));
			jo.put("imagestatus", image.getImageStatus());
			User imageUser = userDAO.getUser(imageUID);
			jo.put("imageuser", imageUser.getUserName());
			jo.put("createDate", TimeUtils.formatTime(image.getCreateDate()));
			String pooluuid = image.getPoolUuid();
			OCPool pool = poolDAO.getPool(pooluuid);
			jo.put("poolname", pool.getPoolName());
			jo.put("pooluuid", pooluuid);
			jo.put("reference", image.getReferenceUuid());
			
			ja.put(jo);
		}
		// write log and push message
		JSONArray infoArray = logRecord.createLoginfos(LogRole.IMAGE, imageName);
		if (image != null) {
			OCLog log = logRecord.addSuccessLog(userId, LogObject.IMAGE, 
					LogAction.ADD, 
					infoArray.toString(), startTime, endTime); 
			message.pushSuccess(userId, log.toString());
		} else {
			OCLog log = logRecord.addFailedLog(userId, LogObject.IMAGE, 
					LogAction.ADD, 
					infoArray.toString(), startTime, endTime);
			message.pushError(userId, log.toString());
		}
		return ja;
	}

	public JSONObject deleteImage(int userId, String imageId, String imageName) {
		JSONObject jo = new JSONObject();
		Date startTime = new Date();
		boolean result = imageDAO.deleteImage(imageId);
		Date endTime = new Date();
		JSONArray infoArray = logRecord.createLoginfos(LogRole.IMAGE, imageName);
		if (result) {
			OCLog log = logRecord.addSuccessLog(userId, LogObject.IMAGE, 
					LogAction.DELETE, 
					infoArray.toString(), startTime, endTime); 
			message.pushSuccess(userId, log.toString());
		} else {
			OCLog log = logRecord.addFailedLog(userId, LogObject.IMAGE, 
					LogAction.DELETE, 
					infoArray.toString(), startTime, endTime); 
			message.pushError(userId, log.toString());
		}
		jo.put("result", result);
		return jo;
	}

	public JSONObject getBasciList(String imageUuid) {
		JSONObject jo = new JSONObject();
		Image image = getImage(imageUuid);
		if (image != null) {
			jo.put("imageName", TimeUtils.encodeText(image.getImageName()));
			jo.put("imageUID", image.getImageUID());
			jo.put("imageDisk", image.getImageDisk());
			jo.put("imagePlatform", TimeUtils.encodeText(SystemConstant.Platform
					.values()[image.getImagePlatform().ordinal()].toString()));
			jo.put("imageStatus", image.getImageStatus());
			jo.put("poolUuid", image.getPoolUuid());
			jo.put("imageDesc", (image.getImageDesc() == null) ? "&nbsp;"
					: TimeUtils.encodeText(image.getImageDesc()));
			User imageUser = userDAO.getUser(image.getImageUID());
			jo.put("imageUser", TimeUtils.encodeText(imageUser.getUserName()));
			jo.put("createDate", TimeUtils.formatTime(image.getCreateDate()));
			String timeUsed = TimeUtils.encodeText(TimeUtils.dateToUsed(image
					.getCreateDate()));
			jo.put("useDate", timeUsed);
		}
		return jo;
	}

	public JSONArray getShareImageList(String poolUuid, String[] imageUuids) {
		JSONArray ja = new JSONArray();
		OCPool pool = poolDAO.getPool(poolUuid);
		String master = pool.getPoolMaster();
		Set<String> srListSet = new HashSet<String>();
		srListSet = hostSRDAO.getSRList(master);
		if (srListSet.size() > 0) {
			Set<String> hostListSet = new HashSet<String>();
			Iterator<String> iterator = srListSet.iterator();
			hostListSet = hostSRDAO.getHostList(iterator.next().toString());
			hostListSet.remove(master);
			if (hostListSet.size() > 0){
				while (iterator.hasNext()) {
					Set<String> hSet = new HashSet<String>();
					hSet = hostSRDAO.getHostList(iterator.next().toString());
					hostListSet.retainAll(hSet);
				}
			}
			if (hostListSet.size() > 0) {
				boolean result = true;
				for (String hostString : hostListSet) {
					OCPool pl = poolDAO.getPoolByMaster(hostString);
					if (pl != null) {
						for (String uuid : imageUuids) {
							result &= imageDAO.isShared( pl.getPoolUuid(), uuid); 
						}
						if (result) {
							JSONObject jo = new JSONObject();
							jo.put("pooluuid", pl.getPoolUuid());
							jo.put("poolname", pl.getPoolName());
							ja.put(jo);
						}
					}
				}
			}
		}
		return ja;
	}

	public JSONObject shareImage(Integer userId, String sorpoolUuid, String imageUuid, 
			String despoolUuid) {
		Date startTime = new Date();
		String uuid = UUID.randomUUID().toString();
		OCPool pool = poolDAO.getPool(despoolUuid);
		boolean result = imageDAO.shareImage(uuid, imageUuid, despoolUuid);
		// write log and push message
		Date endTime = new Date();
		Image image = imageDAO.getImage(imageUuid);
		JSONObject jo = new JSONObject();
		jo.put("imagename", TimeUtils.encodeText(image.getImageName()));
		jo.put("imageid", uuid);
		jo.put("imagesize", image.getImageDisk());
		jo.put("imageplatform", TimeUtils.encodeText(SystemConstant.Platform
				.values()[image.getImagePlatform().ordinal()].toString()));
		jo.put("createDate", TimeUtils.formatTime(image.getCreateDate()));
		jo.put("pooluuid", despoolUuid);
		jo.put("poolname", pool.getPoolName());
		jo.put("reference", imageUuid);
		
		JSONArray infoArray = new JSONArray();
		infoArray.put(TimeUtils.createLogInfo(
				LogRole.IMAGE, uuid));
		if (result) {
			OCLog log = logRecord.addSuccessLog(userId, LogObject.IMAGE, 
					LogAction.CREATE, 
					infoArray.toString(), startTime, endTime); 
			message.pushSuccess(userId, log.toString());
		} else {
			OCLog log = logRecord.addFailedLog(userId, LogObject.IMAGE, 
					LogAction.CREATE, 
					infoArray.toString(), startTime, endTime); 
			message.pushError(userId, log.toString());
		}
					
		return jo;
	}

	public boolean updateImage(String uuid, String pwd, String desc, int disk,
			int platform) {
		if((!pwd.equals(""))&&(!desc.equals(""))&& (disk != 0) && (platform != 0)) {
			Image image = getImage(uuid);
			image.setImagePwd(pwd);
			image.setImageDisk(disk);
			image.setImagePlatform(ImagePlatform.values()[platform]);
			image.setImageDesc(desc);
			return imageDAO.updateImage(image);
		} else {
			return true;
		}
	}
	

	public JSONArray getImageList(int userId, int page, int limit,
			String search, String type) {
		JSONArray ja = new JSONArray();
		int totalNum = imageDAO.countImageByUserId(userId, search,
				type);
		User imageUser = userDAO.getUser(userId);
		List<Image> imageList = imageDAO.getOnePageImageListByUserId(
				userId, page, limit, search, type, imageUser.getUserAllocate());
		ja.put(totalNum);
		if (imageList != null) {
			for (Image image : imageList) {
				JSONObject jo = new JSONObject();
				jo.put("imageid", image.getImageUuid());
				jo.put("imagename", Utilities.encodeText(image.getImageName()));
				jo.put("imagesize", image.getImageDisk());
				jo.put("imageplatform", Utilities.encodeText(image
						.getImagePlatform().toString()));
				jo.put("imagestatus", image.getImageStatus().ordinal());
				jo.put("imageuser", imageUser.getUserName());
				jo.put("createDate",
						Utilities.formatTime(image.getCreateDate()));
				ja.put(jo);
			}
		}
		return ja;
	}

	public boolean makeImage(String vmUuid, int userId, String imageName,
			String imageDesc, Connection c) {
		boolean result = false;
		OCVM fromVM = vmDAO.getVMByUuid(vmUuid);
		if (fromVM != null) {
			try {
				String poolUuid = hostDAO.getHost(fromVM.getVmHostUuid()).getPoolUuid();
				VM thisVM = VM.getByUuid(c, vmUuid);
				String imageUuid = UUID.randomUUID().toString();
				if (thisVM.createImage(c, imageUuid)) {
					Image image = imageDAO.createImage(
							imageUuid,
							imageName,
							userId,
							fromVM.getVmPlatform().ordinal(), 
							poolUuid, 
							imageDesc,
							fromVM.getVmHostUuid(),
							fromVM.getVmPwd());
					if(image!=null){
						result=true;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public boolean deleteImage(Connection conn, String imageUuid, int userId) {
		boolean result = false;
		if (imageDAO.deleteImage(imageUuid, userId)) {
			try {
				VM thisVM = VM.getByUuid(conn, imageUuid);
				thisVM.hardShutdown(conn);
				thisVM.destroy(conn, true);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				result = vmDAO.deleteVMByUuid(userId, imageUuid);
			}
		}
		return result;
	}

	@Override
	public JSONObject getImagedetail(String imageUuid) {
		JSONObject jo = new JSONObject();
		Image image = imageDAO.getImage(imageUuid);
		if (image != null) {
			jo.put("imageName", Utilities.encodeText(image.getImageName()));
			jo.put("imageUID", image.getImageUID());
			jo.put("imageDisk", image.getImageDisk());
			jo.put("imagePlatform", Utilities.encodeText(image.getImagePlatform().toString()));
			jo.put("imageStatus", image.getImageStatus());
			jo.put("poolUuid", image.getPoolUuid());
			jo.put("imageDesc", (image.getImageDesc() == null) ? "&nbsp;"
					: Utilities.encodeText(image.getImageDesc()));
			User imageUser = userDAO.getUser(image.getImageUID());
			jo.put("imageUser", Utilities.encodeText(imageUser.getUserName()));
			jo.put("createDate", Utilities.formatTime(image.getCreateDate()));
			String timeUsed = Utilities.encodeText(Utilities.dateToUsed(image
					.getCreateDate()));
			jo.put("useDate", timeUsed);
		}
		return jo;
	}

	public Image getImage(String imageUuid) {
		return imageDAO.getImage(imageUuid);
	}

	public int countImageOfHost(String hostUuid) {
		return imageDAO.countByHost(hostUuid);
	}

	public boolean updateImage(Image image) {
		return imageDAO.updateImage(image);
	}

	public boolean checkImage(String imageUuid) {
		return imageDAO.checkImage(imageUuid);
	}

	public boolean saveImage(Image image) {
		return imageDAO.save(image);
	}

}
