package com.beyondsphere.service;

import org.Xen.API.Connection;
import org.json.JSONArray;
import org.json.JSONObject;

import com.beyondsphere.entity.Image;

public interface ImageService {
	
	public abstract boolean checkImage(String imageUuid);
	
	public abstract boolean saveImage(Image image);
	
	public abstract JSONArray getImageList(int userId, int userLevel, int page,
			int limit, String search, String type);

	public abstract JSONArray createImage(int userId, int userLevel,
			String imageUuid, String imageName, String imageServer,
			int imageOs, String imageDesc, String hostUuid, String imagePwd);

	public abstract JSONObject deleteImage(int userId, String imageId,
			String imageName);

	public abstract JSONObject getBasciList(String imageUuid);

	public abstract JSONArray getShareImageList(String poolUuid,
			String[] imageUuids);

	public abstract JSONObject shareImage(Integer userId, String sorpoolUuid, 
			String imageUuid, String despoolUuid);

	public abstract boolean updateImage(String uuid, String pwd, String desc,
			int disk, int platform);
	
	public abstract boolean updateImage(Image image);
	
	public abstract Image getImage(String imageUuid);
	
	public abstract int countImageOfHost(String hostUuid);
	
	/**
	 * 获取模板列表信息
	 * 
	 * @param userId
	 * @param userLevel
	 * @param page
	 * @param limit
	 * @param search
	 * @param type
	 * @return
	 */
	public abstract JSONArray getImageList(int userId, int page,
			int limit, String search, String type);
	
	/**
	 * 获取模板详细信息
	 * @author lining
	 * @param imageUuid
	 * @return
	 */
	public abstract JSONObject getImagedetail(String imageUuid);

	/**
	 * 用户自定义模板
	 * 
	 * @param vmUuid
	 * @param userId
	 * @param imageName
	 * @param imageDesc
	 * @param c
	 * @return
	 */
	public abstract boolean makeImage(String vmUuid, int userId,
			String imageName, String imageDesc, Connection c);
	
	/**
	 * 用户删除自定义模板
	 * 
	 * @param imageUuid
	 * @param userId
	 * @return
	 */
	public abstract boolean deleteImage(Connection conn, String imageUuid, int userId);

}
