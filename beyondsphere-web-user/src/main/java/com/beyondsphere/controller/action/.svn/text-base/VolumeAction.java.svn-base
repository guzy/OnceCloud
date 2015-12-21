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
import com.beyondsphere.query.QueryList;
import com.beyondsphere.utils.Dispatch;
import com.beyondsphere.model.CreateVolumeModel;
import com.beyondsphere.model.ListModel;
import com.beyondsphere.model.StatisticsType;

@RequestMapping("/VolumeAction")
@Controller
public class VolumeAction {

	@Resource
	private QueryList queryList;
	@Resource
	private Dispatch dispatch;

	@RequestMapping(value = "/VolumeList", method = { RequestMethod.GET })
	@ResponseBody
	public String volumeList(HttpServletRequest request, ListModel list) {
		User user = (User) request.getSession().getAttribute("user");
		return queryList.queryVolumeList(user.getUserId(), list.getPage(),
						list.getLimit(), list.getSearch()).toString();
	}

	@RequestMapping(value = "/CreateVolume", method = { RequestMethod.POST })
	@ResponseBody
	public void createVolume(HttpServletRequest request,
			CreateVolumeModel cvModel) {
		User user = (User) request.getSession().getAttribute("user");
		JSONObject jo = new JSONObject();
		jo.put("Handler", "CreateVolumeHandler");
		JSONArray ja = new JSONArray();
		JSONObject params = new JSONObject();
		params.put("userId", user.getUserId());
		params.put("uuid", cvModel.getVolumeUuid());
		params.put("name", cvModel.getVolumeName());
		params.put("volumeSize", cvModel.getVolumeSize());
		params.put("statistics", StatisticsType.VOLUME.ordinal());
		ja.put(0, params);
		jo.put("Params", ja);
		dispatch.post(jo);
	}

	@RequestMapping(value = "/Bind", method = { RequestMethod.POST })
	@ResponseBody
	public void bind(HttpServletRequest request,
			@RequestParam String volumeUuid, @RequestParam String vmUuid) {
		User user = (User) request.getSession().getAttribute("user");
		JSONObject jo = new JSONObject();
		jo.put("Handler", "BindVolumeHandler");
		JSONArray ja = new JSONArray();
		JSONObject params = new JSONObject();
		params.put("userId", user.getUserId());
		params.put("volumeUuid", volumeUuid);
		params.put("vmUuid", vmUuid);
		ja.put(0, params);
		jo.put("Params", ja);
		dispatch.post(jo);
	}

	@RequestMapping(value = "/Unbind", method = { RequestMethod.POST })
	@ResponseBody
	public void unbind(HttpServletRequest request,
			@RequestParam String volumeUuid) {
		User user = (User) request.getSession().getAttribute("user");
		JSONObject jo = new JSONObject();
		jo.put("Handler", "UnbindVolumeHandler");
		JSONArray ja = new JSONArray();
		JSONObject params = new JSONObject();
		params.put("userId", user.getUserId());
		params.put("volumeUuid", volumeUuid);
		ja.put(0, params);
		jo.put("Params", ja);
		dispatch.post(jo);
	}

	@RequestMapping(value = "/DeleteVolume", method = { RequestMethod.POST })
	@ResponseBody
	public void deleteVolume(HttpServletRequest request,
			@RequestParam String volumeUuid) {
		User user = (User) request.getSession().getAttribute("user");
		JSONObject jo = new JSONObject();
		jo.put("Handler", "DeleteVolumeHandler");
		JSONArray ja = new JSONArray();
		JSONObject params = new JSONObject();
		params.put("userId", user.getUserId());
		params.put("uuid", volumeUuid);
		ja.put(0, params);
		jo.put("Params", ja);
		dispatch.post(jo);
	}
	
	@RequestMapping(value = "/VolumesOfVM", method = { RequestMethod.GET })
	@ResponseBody
	public String volumeOfVm(HttpServletRequest request,
			@RequestParam String vmUuid) {
		JSONArray ja = queryList.queryVolumeListByVm(vmUuid);
		return ja.toString();
	}
	
	@RequestMapping(value = "/AvailableVolumes", method = { RequestMethod.POST })
	@ResponseBody
	public String getAvailableVolumes(HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user");
		JSONArray ja = queryList.queryAvaliableVolumes(user.getUserId());
		return ja.toString();
	}
	
	@RequestMapping(value = "/VolumeDetail", method = { RequestMethod.GET })
	@ResponseBody
	public String volumeDetail(HttpServletRequest request,
			@RequestParam String uuid) {
		JSONObject jo = queryList.queryVolumeDetail(uuid);
		return jo.toString();
	}
	
}
