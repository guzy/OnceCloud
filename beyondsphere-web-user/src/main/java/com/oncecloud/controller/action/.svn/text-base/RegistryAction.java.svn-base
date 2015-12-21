package com.beyondsphere.controller.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beyondsphere.entity.User;
import com.beyondsphere.model.CreateRegistryModel;
import com.beyondsphere.model.ListModel;
import com.beyondsphere.query.QueryList;
import com.beyondsphere.utils.Dispatch;

/**
 * @author luogan 
 * 2015年7月13日 
 * 上午11:52:33
 */
@RequestMapping("/RegistryAction")
@Controller
public class RegistryAction {

	@Resource
	private QueryList queryList;
	@Resource
	private Dispatch dispatch;
	
	@RequestMapping(value = "/registryRepoList", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public String imageList(HttpServletRequest request, ListModel list) {
		User user = (User) request.getSession().getAttribute("user");
		JSONArray ja = queryList.registryRepoList(user.getUserId(),list.getPage(), list.getLimit(), list.getSearch(),list.getType());
		return ja.toString();
	}
	
	@RequestMapping(value = "/registryList", method = { RequestMethod.GET})
	@ResponseBody
	public String registryList(HttpServletRequest request, ListModel list) {
		User user = (User) request.getSession().getAttribute("user");
		JSONArray ja = queryList.registryList(user.getUserId(),list.getPage(), list.getLimit(), list.getSearch(),list.getType());
		return ja.toString();
	}
	
	@RequestMapping(value = "/createRegistry", method = { RequestMethod.POST })
	@ResponseBody
	public void createRegistry(HttpServletRequest request, CreateRegistryModel createRegistryModel) {
		//User user = (User) request.getSession().getAttribute("user");
		JSONObject jo = new JSONObject();
		jo.put("Handler", "CreateRegistryHandler");
		JSONArray ja = new JSONArray();
		JSONObject params = new JSONObject();
		params.put("imageUuid", createRegistryModel.getImageUuid());
		params.put("name", createRegistryModel.getRegName());
		params.put("ip", createRegistryModel.getRegIP());
		params.put("port", createRegistryModel.getRegPort());
		ja.put(0, params);
		jo.put("Params", ja);
		dispatch.post(jo);
	}

	@RequestMapping(value = "/deleteRegistry", method = { RequestMethod.GET })
	@ResponseBody
	public void deleteRegistry(HttpServletRequest request, @RequestParam String uuid) {
		User user = (User) request.getSession().getAttribute("user");
		JSONObject jo = new JSONObject();
		jo.put("Handler", "DeleteRegistryHandler");
		JSONArray ja = new JSONArray();
		JSONObject params = new JSONObject();
		params.put("uuid", uuid);
		params.put("userId", user.getUserId());
		ja.put(0, params);
		jo.put("Params", ja);
		dispatch.post(jo);
	}
}
