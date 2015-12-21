
package com.oncecloud.controller.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oncecloud.entity.User;
import com.oncecloud.manager.TemplateManager;
import com.oncecloud.model.ListModel;
import com.oncecloud.query.QueryList;
import com.oncecloud.service.ContainerService;
import com.oncecloud.service.TemplateService;
import com.oncecloud.utils.Dispatch;

/**
 * @author luogan
 * 2015年5月19日
 * 下午1:59:47
 */
@RequestMapping("/TemplateAction")
@Controller
public class TemplateAction {

	@Resource
	private QueryList queryList;
	@Resource
	private Dispatch dispatch;
    //
	@Resource
	private ContainerService containerService;
	@Resource
	private TemplateService templateService;
	@Resource
	private TemplateManager templateManager;

	@RequestMapping(value = "/TemplateList", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public String imageList(HttpServletRequest request, ListModel list) {
		User user = (User) request.getSession().getAttribute("user");
		JSONArray ja = queryList.ContainerTemplateList(user.getUserId(),
				list.getPage(), list.getLimit(), list.getSearch(),
				list.getType());
		return ja.toString();
	}
	
	@RequestMapping(value = "/Delete", method = { RequestMethod.GET })
	@ResponseBody
	public void deleteTemplate(HttpServletRequest request,@RequestParam String imageId) {
		templateService.deleteImage(imageId);
	}
	
	@RequestMapping(value = "/PushTemplate", method = { RequestMethod.POST })
	@ResponseBody
	public void pushTemplate(HttpServletRequest request,@RequestParam String repoId,@RequestParam String imageId,@RequestParam String imageName,@RequestParam String imageVersion) {
		  templateService.pushTemplate(repoId,imageId, imageName, imageVersion);
	}
	
	@RequestMapping(value = "/makeTemplate", method = { RequestMethod.POST })
	@ResponseBody
	public String makeTemplate(HttpServletRequest request, String clusterUuid, String fileName, String templateName){
		return templateManager.makeTemplate(clusterUuid, fileName, templateName);
	}
	
	@RequestMapping(value = "/tagTemplate", method = { RequestMethod.POST })
	@ResponseBody
	public void tagTemplate(HttpServletRequest request, String clusterUuid, String templateUuid, String templateName){
		String tagResult = templateManager.tagTemplate(clusterUuid, templateUuid, "", templateName);
		System.out.println(tagResult);
	}
	
	@RequestMapping(value = "/template", method = { RequestMethod.POST })
	@ResponseBody
	public boolean pushTemplate(HttpServletRequest request, String clusterUuid, String templateUuid, String templateName){
		return templateManager.pushTemplate(clusterUuid, templateUuid, templateName);
	}

}
