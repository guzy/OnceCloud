package com.oncecloud.service;

import org.json.JSONArray;

/**
 * @author luogan
 * 2015年5月19日
 * 下午2:06:30
 */
public interface TemplateService {

	/**
	 * @param userId
	 * @param page
	 * @param limit
	 * @param search
	 * @param type
	 * @return
	 */
	public abstract JSONArray listTemplatesByPage(int userId, int page,
			int limit, String search, String type);
	
	/**
	 * @param imageId
	 * @return
	 */
	public abstract boolean deleteImage(String imageId);
	
	/**
	 * @param repoId
	 * @param imageId
	 * @param templateAddress
	 * @param templateName
	 * @return
	 */
	public abstract boolean pushTemplate(String repoId,String imageId,String templateAddress,String templateName);
	
	/**
	 * @param templateUuid
	 * @param templateName
	 * @param templateVersion
	 * @param registryIpandPort
	 * @return
	 */
	public abstract boolean pushImage(String templateUuid, String templateName, String templateVersion, String registryIpandPort);

}
