
package com.oncecloud.controller.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oncecloud.entity.User;
import com.oncecloud.model.CreateVMModel;
import com.oncecloud.model.ListModel;
import com.oncecloud.model.StatisticsType;
import com.oncecloud.query.QueryList;
import com.oncecloud.service.ContainerService;
import com.oncecloud.service.RepositoryService;
import com.oncecloud.utils.Dispatch;

/**
 * @author luogan
 * 2015年5月19日
 * 下午1:59:59
 */
@RequestMapping("/RepositoryAction")
@Controller
public class RepositoryAction {

	@Resource
	private QueryList queryList;
	@Resource
	private Dispatch dispatch;
    //
	@Resource
	private ContainerService containerService;
	@Resource
	private RepositoryService repositoryService;

	@RequestMapping(value = "/ContainerRepoList", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public String imageList(HttpServletRequest request, ListModel list) {
		User user = (User) request.getSession().getAttribute("user");
		JSONArray ja = queryList.ContainerRepoList(user.getUserId(),
				list.getPage(), list.getLimit(), list.getSearch(),
				list.getType());
		return ja.toString();
	}
	
	@RequestMapping(value = "/createContainer", method = { RequestMethod.POST })
	@ResponseBody
	public void createVM(HttpServletRequest request, CreateVMModel createvmModel) {
		User user = (User) request.getSession().getAttribute("user");
		JSONObject jo = new JSONObject();
		jo.put("Handler", "CreateVMHandler");
		JSONArray ja = new JSONArray();
		JSONObject params = new JSONObject();
		params.put("uuid", createvmModel.getVmUuid());
		params.put("tplUuid", createvmModel.getImageUuid());
		params.put("userId", user.getUserId());
		params.put("cpu", createvmModel.getCpu());
		params.put("memory", createvmModel.getMemory());
		params.put("pwd", createvmModel.getPassword());
		params.put("name", createvmModel.getVmName());
		params.put("statistics", StatisticsType.INSTANCE.ordinal());
		ja.put(0, params);
		jo.put("Params", ja);
		dispatch.post(jo);
	}

}
