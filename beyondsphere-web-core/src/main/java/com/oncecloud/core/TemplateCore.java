
package com.oncecloud.core;

import java.util.List;

import org.springframework.stereotype.Component;

import com.github.dockerjava.api.model.Image;

/**
 * @author luogan
 * 2015年5月19日
 * 下午1:56:51
 */
@Component("TemplateCore")
public interface TemplateCore {
	
	/**
	 * @param masterIp
	 * @param username
	 * @param password
	 * @return
	 */
	public abstract String makeImage(String masterIp, String password, String fileName, String templateName);
	
	/**
	 * @param masterIp
	 * @param username
	 * @param password
	 * @param imageId
	 * @param registryIp
	 * @return
	 */
	public abstract String tagImage(String masterIp, String password, String imageId, String registryIp, String imageName);
	
	/**
	 * @param masterIp
	 * @param username
	 * @param password
	 * @param imageTag
	 * @return
	 */
	public abstract boolean pushImage(String masterIp, String password, String imageTag);
	
	/**
	 * @param masterIp
	 * @param password
	 * @param imageTag
	 * @return
	 */
	public abstract boolean pullImage(String imageTag);
	
	/**
	 * @param userId
	 * @param page
	 * @param limit
	 * @param search
	 * @param type
	 * @return
	 */
	public abstract List<Image> listAllImages();
	
	/**
	 * @param imageId
	 * @return
	 */
	public abstract boolean deleteImage(String imageId);
	
	//public abstract boolean pushTemplate(String repoId,String imageId,String imageName,String imageVersion);
}
