
package com.oncecloud.controller.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oncecloud.entity.User;
import com.oncecloud.manager.ContainerManager;
import com.oncecloud.model.CreateVMModel;
import com.oncecloud.model.ListModel;
import com.oncecloud.query.QueryList;
import com.oncecloud.service.ContainerService;
import com.oncecloud.utils.Dispatch;

/**
 * @author luogan
 * 2015年5月19日
 * 下午1:59:37
 */
@RequestMapping("/ContainerAction")
@Controller
public class ContainerAction {

	@Resource
	private QueryList queryList;
	@Resource
	private Dispatch dispatch;
	@Resource
	private ContainerService containerService;
	@Resource
	private ContainerManager containerManager;

	@RequestMapping(value = "/createContainer", method = { RequestMethod.POST })
	@ResponseBody
	public void createContainer(HttpServletRequest request, CreateVMModel createvmModel) {
		
		User user = (User) request.getSession().getAttribute("user");
		JSONObject jo = new JSONObject();
		jo.put("Handler", "CreateContainerHandler");
		JSONArray ja = new JSONArray();
		JSONObject params = new JSONObject();
		params.put("uuid", createvmModel.getVmUuid());
		params.put("tplUuid", createvmModel.getImageUuid());
		params.put("userId", user.getUserId());
		params.put("cpu", createvmModel.getCpu());
		params.put("memory", createvmModel.getMemory());
		params.put("pwd", createvmModel.getPassword());
		params.put("name", createvmModel.getVmName());
		params.put("statistics", "0");
		params.put("startCommand", createvmModel.getStartCommand());
		//params.put("sysPort", createvmModel.getSysPort());
		//params.put("mapPort", createvmModel.getMapPort());
		params.put("link", createvmModel.getLink());
		params.put("dockerParams", createvmModel.getDockerParams());
		ja.put(0, params);
		jo.put("Params", ja);
		dispatch.post(jo);
	}
	
	@RequestMapping(value = "/createContainerWithCluster", method = { RequestMethod.POST })
	@ResponseBody
	public String createContainerWithCluster(HttpServletRequest request, CreateVMModel createvmModel) {
		
		User user = (User) request.getSession().getAttribute("user");
		JSONObject params = new JSONObject();
		params.put("uuid", createvmModel.getVmUuid());
		params.put("tplUuid", createvmModel.getImageUuid());
		params.put("userId", user.getUserId());
		params.put("cpu", createvmModel.getCpu());
		params.put("memory", createvmModel.getMemory());
		params.put("pwd", createvmModel.getPassword());
		params.put("name", createvmModel.getVmName());
		params.put("statistics", "0");
		params.put("startCommand", createvmModel.getStartCommand());
		//params.put("sysPort", createvmModel.getSysPort());
		//params.put("mapPort", createvmModel.getMapPort());
		params.put("link", createvmModel.getLink());
		params.put("dockerParams", createvmModel.getDockerParams());
		
		return containerManager.createContainer(params, createvmModel.getClusterUuid()).toString();
	}

	@RequestMapping(value = "/Delete", method = { RequestMethod.GET })
	@ResponseBody
	public void deleteContianer(HttpServletRequest request,@RequestParam String containerId) {
		
		User user = (User) request.getSession().getAttribute("user");
		JSONObject jo = new JSONObject();
		jo.put("Handler", "DeleteContainerHandler");
		JSONArray ja = new JSONArray();
		JSONObject params = new JSONObject();
		params.put("containerId", containerId);
		params.put("userId", user.getUserId());
		ja.put(0, params);
		jo.put("Params", ja);
		dispatch.post(jo);
	}
	
	@RequestMapping(value = "/Restart", method = { RequestMethod.GET })
	@ResponseBody
	public void restartContianer(HttpServletRequest request,@RequestParam String containerId) {
		
		User user = (User) request.getSession().getAttribute("user");
		JSONObject jo = new JSONObject();
		jo.put("Handler", "RestartContainerHandler");
		JSONArray ja = new JSONArray();
		JSONObject params = new JSONObject();
		params.put("containerId", containerId);
		params.put("userId", user.getUserId());
		ja.put(0, params);
		jo.put("Params", ja);
		dispatch.post(jo);
	}
	
	@RequestMapping(value = "/Stop", method = { RequestMethod.GET })
	@ResponseBody
	public void stopContianer(HttpServletRequest request,@RequestParam String containerId) {
		
		User user = (User) request.getSession().getAttribute("user");
		JSONObject jo = new JSONObject();
		jo.put("Handler", "StopContainerHandler");
		JSONArray ja = new JSONArray();
		JSONObject params = new JSONObject();
		params.put("containerId", containerId);
		params.put("userId", user.getUserId());
		ja.put(0, params);
		jo.put("Params", ja);
		dispatch.post(jo);
	}
	
	@RequestMapping(value = "/contianerMakeImage", method = { RequestMethod.POST })
	@ResponseBody
	public void contianerImage(HttpServletRequest request,@RequestParam String containerId,@RequestParam String imgaeAddress,@RequestParam String imageName,@RequestParam String imageVersion) {
		
		containerService.contianerMakeImage(containerId,imgaeAddress,imageName,imageVersion);
	}

	@RequestMapping(value = "/ContainerList", method = { RequestMethod.GET })
	@ResponseBody
	public String containerList(HttpServletRequest request, ListModel list) {
		User user = (User) request.getSession().getAttribute("user");
		int userId = user.getUserId();
		JSONArray ja = queryList.queryContainerList(list.getPage(),list.getLimit(), list.getSearch(), list.getGroupUuid(), userId);
		return ja.toString();
	}
	
	@RequestMapping(value = "/listAllContainer", method = { RequestMethod.GET })
	@ResponseBody
	public String listAllcontainer(HttpServletRequest request) {
		JSONArray ja = queryList.queryAllContainers();
		return ja.toString();
	}

}
