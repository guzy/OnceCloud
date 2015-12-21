package com.beyondsphere.manager;

import org.json.JSONArray;

/**
 * @author luogan 
 * 2015年6月19日 
 * 下午3:22:33
 */
public interface TemplateManager {
	
	/**
	 * @author langzi
	 * @return
	 * 列出所有的镜像
	 */
	public abstract JSONArray listAllTemplates();
	
	/**
	 * @author langzi
	 * @param userId
	 * @param page
	 * @param limit
	 * @param search
	 * @param type
	 * @return
	 * 分页列出所有镜像
	 */
	public abstract JSONArray listTemplatesByPage(int userId, int page, int limit,
			String search, String type);
	
	/**
	 * @author langzi
	 * @param clusterUuid
	 * @param fileName
	 * @return
	 * 制作镜像
	 */
	public abstract String makeTemplate(String clusterUuid, String fileName, String templateName);
	
	/**
	 * @author langzi
	 * @param clusterUuid
	 * @param registryIp
	 * @param templateName
	 * @return
	 * 标记镜像
	 */
	public abstract String tagTemplate(String clusterUuid, String templateUuid, String registryIp, String templateName);
	
	/**
	 * @author langzi
	 * @param clusterUuid
	 * @param templateTag
	 * @return
	 * 发布镜像到仓库
	 */
	public abstract boolean pushTemplate(String clusterUuid, String templateUuid, String templateName);
	
	/**
	 * @author langzi
	 * @param templateUuid
	 * @return
	 * 删除镜像
	 */
	public abstract boolean deleteTemplate(String templateUuid);
}