package com.oncecloud.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.github.dockerjava.api.model.Image;
import com.oncecloud.core.TemplateCore;
import com.oncecloud.dao.RepositoryDAO;
import com.oncecloud.message.MessagePush;
import com.oncecloud.service.TemplateService;

/**
 * @author luogan
 * 2015年5月19日
 * 下午2:06:59
 */
@Component("TemplateService")
public class TemplateServiceImpl implements TemplateService {
	
	//private final static Logger logger = Logger.getLogger(TemplateServiceImpl.class);
	
	@Resource
	TemplateCore templateCore;
	@Resource
	RepositoryDAO repositoryDAO;
	@Resource
	private MessagePush messagePush;
	
	@Override
	public JSONArray listTemplatesByPage(int userId, int page, int limit,
			String search, String type) {
		//查找docker容器中的image
		List<Image> images = templateCore.listAllImages();
		JSONArray ja = new JSONArray();
		if (images != null) {
			for (Image image : images) {
				JSONObject jo = new JSONObject();
				jo.put("imageid", image.getId());
				jo.put("imagename", image.getRepoTags());
				ja.put(jo);
			}
		}
		System.out.println(ja);
		return ja;
		
	}
	
	@Override
	public boolean deleteImage(String imageId) {
		return templateCore.deleteImage(imageId);
	}

	@Override
	public boolean pushTemplate(String repoId,String imageId, String imageName,String imageVersion) {
		//Response pushImage = client.pushImageCmd(templateAddress + "/" + templateName).exec();
		//Response pushImage = client.pushImageCmd(imageName).exec();
		// 发布镜像，保存到本地仓库
		String str=imageName;
		int i=str.lastIndexOf("/");
		String templateName = str.substring(0,i);
		String templateAddress = str.substring(i+1,str.length());
		return repositoryDAO.insertImage(repoId, templateAddress,imageVersion, templateName);
	}

	@Override
	public boolean pushImage(String templateUuid, String templateName,
			String templateVersion, String registryIpandPort) {
		return repositoryDAO.insertImage(templateUuid, templateName, templateVersion, registryIpandPort);
	}
	
	

}
