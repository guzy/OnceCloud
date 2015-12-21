package com.oncecloud.manager.Impl;

import javax.annotation.Resource;

import org.json.JSONArray;
import org.springframework.stereotype.Service;

import com.oncecloud.core.TemplateCore;
import com.oncecloud.entity.OCHost;
import com.oncecloud.manager.TemplateManager;
import com.oncecloud.service.HostService;
import com.oncecloud.service.TemplateService;
import com.oncedocker.core.DockerConfig;
import com.oncedocker.core.DockerConstants;

/**
 * @author luogan
 * 2015年6月19日
 * 下午3:23:10
 */
@Service("TemplateManager")
public class TemplateManagerImpl implements TemplateManager {
	
	@Resource
	private TemplateCore templateCore;
	@Resource
	private TemplateService templateService;
	@Resource
	private HostService hostService;
	
	@Override
	public JSONArray listAllTemplates() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONArray listTemplatesByPage(int userId, int page, int limit,
			String search, String type) {
		return templateService.listTemplatesByPage(userId, page, limit, search, type);
	}

	@Override
	public String makeTemplate(String clusterUuid, String fileName,
			String templateName) {
		OCHost master = hostService.getMasterOfPool(clusterUuid);
		String tmpMessage = templateCore.makeImage(master.getHostIP(), master.getHostPwd(), fileName, templateName);
		System.out.println("-----"+tmpMessage);
		return tmpMessage.substring(tmpMessage.length()-13, tmpMessage.length()-1);
	}

	@Override
	public String tagTemplate(String clusterUuid,String templateUuid, String registryIp, String templateName) {
		OCHost master = hostService.getMasterOfPool(clusterUuid);
		return templateCore.tagImage(master.getHostIP(), master.getHostPwd(), templateUuid, master.getHostIP(), templateName);
	}

	@Override
	public boolean pushTemplate(String clusterUuid, String templateUuid, String templateName) {
		OCHost master = hostService.getMasterOfPool(clusterUuid);
		String templateTag = templateCore.tagImage(master.getHostIP(), master.getHostPwd(), templateUuid, master.getHostIP(), templateName);
		if (templateTag==null || "".equals(templateTag)) {
			templateTag = "localhost:"+DockerConfig.getValue(DockerConstants.REGISTRY_PORT)+"/"+templateName;
		}
		if(templateCore.pushImage(master.getHostIP(), master.getHostPwd(), templateTag)){
			String[] templateVersion = templateName.split(":");
			return templateService.pushImage(templateUuid, templateVersion[0], templateVersion[templateVersion.length-1], 
					DockerConfig.getValue(DockerConstants.REGISTRY_URL)+":"+DockerConfig.getValue(DockerConstants.REGISTRY_PORT));
		}else {
			return false;
		}
	}

	@Override
	public boolean deleteTemplate(String templateUuid) {
		return templateCore.deleteImage(templateUuid);
	}


}
