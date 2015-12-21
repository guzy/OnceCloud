/**
 * @author hty
 * @time 上午11:05:48
 * @date 2014年12月9日
 */
package com.oncecloud.manager.Impl;


import javax.annotation.Resource;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.oncecloud.manager.ImageManager;
import com.oncecloud.core.ImageCore;
import com.oncecloud.entity.Image;
import com.oncecloud.entity.OCHost;
import com.oncecloud.model.ImageStatus;
import com.oncecloud.service.HostService;
import com.oncecloud.service.ImageService;

@Service("ImageManager")
public class ImageManagerImpl implements ImageManager {

	@Resource
	private HostService hostService;
	@Resource
	private ImageService imageService;
	@Resource
	private ImageCore imageCore;
	
	private boolean imageExist(String imageUuid, String poolUuid) {
		boolean result = false;
		try {
			OCHost host = getPoolMaster(poolUuid);
			result = imageCore.imageExist(host, imageUuid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	private OCHost getPoolMaster(String poolUuid) {
		OCHost master = null;
		if (poolUuid != null) {
			master = hostService.getMasterOfPool(poolUuid);
		}
		return master;
	}
	
	public JSONArray getImageList(int userId, int userLevel, int page,
			int limit, String search, String type) {
		return imageService.getImageList(userId, userLevel, page, limit, search, type);
	}

	public JSONArray createImage(int userId, int userLevel, String imageUuid,
			String imageName, String imageServer, int imageOs,
			String imageDesc, String hostUuid, String imagePwd) {
		JSONArray ja = new JSONArray();
		Image imageExist = imageService.getImage(imageUuid);
		boolean isCheck = true;
		if (imageExist != null && imageExist.getImageStatus() == ImageStatus.EXIST) {
			// image exist in database
			isCheck = false;
		}
		if (!imageExist(imageUuid, imageServer)) {
			// image not exist
			isCheck = false;
		}
		if (isCheck) {
			ja = imageService.createImage(userId, userLevel, imageUuid, imageName, 
					imageServer, imageOs, imageDesc, hostUuid, imagePwd);
		}
		return ja;
	}

	public JSONObject deleteImage(int userId, String imageId, String imageName) {
		return imageService.deleteImage(userId, imageId, imageName);
	}

	public JSONObject getBasciList(String imageUuid) {
		return imageService.getBasciList(imageUuid);
	}

	public JSONArray getShareImageList(String poolUuid, String[] imageUuids) {
		return imageService.getShareImageList(poolUuid, imageUuids);
	}
	
	public JSONArray shareImages(Integer userId, String sorpoolUuid, String despoolUuid, String[] imageUuids) {
		JSONArray ja = new JSONArray();
		for (String imageString : imageUuids) {
			try {
				OCHost host = hostService.getMasterOfPool(despoolUuid);
				String des_ip = host.getHostIP();
				imageCore.migrateImage(sorpoolUuid, imageString, des_ip);
				JSONObject jo = imageService.shareImage(1, sorpoolUuid, imageString, despoolUuid);
				ja.put(jo);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return ja;
	}
	
	public boolean updateImage(String uuid, String pwd, String desc, int disk, int platform) {
		return imageService.updateImage(uuid, pwd, desc, disk, platform);
	}

}
