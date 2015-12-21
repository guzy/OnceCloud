/**
 * @author hty
 * @time 上午11:05:48
 * @date 2014年12月9日
 */
package com.oncecloud.dao;

import java.util.List;

import com.oncecloud.entity.Image;

public interface ImageDAO {
	
	/**
	 * 根据imageUuid获取映像
	 * @param imageUuid
	 * @return image
	 */
	public abstract Image getImage(String imageUuid);

	/**
	 * 根据用户id，和image类型分页获取image列表
	 * @param userId, page, limit, serch, type
	 * @return list
	 */
	public abstract List<Image> getOnePageImageList(int userId, int page,
			int limit, String search, String type);

	/**
	 * 计算host上image的个数
	 * @param hostUuid
	 * @return int
	 */
	public abstract int countByHost(String hostUuid);

	/**
	 * 根据用户id和类型计算image的个数
	 * @param userid, search, type
	 * @return int
	 */
	public abstract int countAllImageList(int userId, String search, String type);
	
	/**
	 * 计算image的个数
	 * @return int
	 */
	public abstract int countAllImageList();

	/**
	 * 根据imageId删除image
	 * @param imageId
	 * @return boolean
	 */
	public abstract boolean deleteImage(String imageId);

	/**
	 * 删除模板
	 * 
	 * @param imageId
	 * @return
	 */
	public abstract boolean deleteImage(String imageUuid, int userId);

	/**
	 * 创建image
	 * @param 
	 * @return Image
	 */
	public abstract Image createImage(String imageUuid, String imageName,
			int imageUID, int imagePlatform, String imageServer,
			String imageDesc, String hostUuid, String imagePwd);
	
	/**
	 * 判断映像是否资源池之间共享
	 * @param poolUuid, referenceUuid
	 * @return boolean
	 */
	public abstract boolean isShared(String poolUuid, String referenceUuid);

	/**
	 * 资源池之间共享映像
	 * @param imageUuid, referenceUuid, poolUuid
	 * @return boolean
	 */
	public abstract boolean shareImage(String imageUuid, String referenceUuid,
			String poolUuid);

	/**
	 * 更新image
	 * @param image
	 * @return boolean
	 */
	public abstract boolean updateImage(Image image);

	/**
	 * 保存image
	 * @param image
	 * @return boolean
	 */
	public abstract boolean save(Image image);

	/**
	 * 检查image是否存在
	 * @param uuid
	 * @return boolean
	 */
	public abstract boolean checkImage(String uuid);
	
	/**
	 * 获取系统image
	 * @return list
	 */
	public abstract List<Image> getSystemImage();
	
	/**
	 * 根据用户id，获取该用户拥有的模板数量
	 * 
	 * @param userId
	 * @param search
	 * @param userLevel
	 * @param type
	 * @return
	 */
	public abstract int countImageByUserId(int userId, String search,
			String type);

	/**
	 * 根据用户id,获取该用户一定量的模板信息
	 * 
	 * @param userId
	 * @param userLevel
	 * @param page
	 * @param limit
	 * @param search
	 * @param type
	 * @param poolUuid
	 * @return
	 */
	public abstract List<Image> getOnePageImageListByUserId(int userId,
			int page, int limit, String search, String type, String poolUuid);

	/**
	 * 更新模板的基本信息
	 * 
	 * @author lining
	 * @return
	 */
	public boolean updateName(String imageuuid, String newName,
			String description);

	public Image getRTImage(String usepoolUuid);
}
