/**
 * @author hty
 * @time 上午11:05:48
 * @date 2014年12月9日
 */
package com.beyondsphere.manager;

import org.json.JSONArray;
import org.json.JSONObject;

public interface ImageManager {

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

	public abstract JSONArray shareImages(Integer userId, String sorpoolUuid,
			String despoolUuid, String[] imageUuids);

	public abstract boolean updateImage(String uuid, String pwd, String desc,
			int disk, int platform);
}
