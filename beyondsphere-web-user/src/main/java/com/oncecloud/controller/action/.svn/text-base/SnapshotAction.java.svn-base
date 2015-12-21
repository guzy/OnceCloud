/**
 * @author hty
 * @time 上午11:08:37
 * @date 2014年12月12日
 */
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
import com.beyondsphere.model.CreateSnapshotModel;
import com.beyondsphere.model.ListModel;
import com.beyondsphere.model.StatisticsType;

@RequestMapping("/SnapshotAction")
@Controller
public class SnapshotAction {
	
	@Resource
	private QueryList queryList;
	@Resource
	private Dispatch dispatch;
	
	@RequestMapping(value = "/SnapshotList", method = { RequestMethod.GET })
	@ResponseBody
	public String snapshotList(HttpServletRequest request, ListModel list) {
		User user = (User) request.getSession().getAttribute("user");
		JSONArray ja = queryList.querySnapshotList(user.getUserId(),
				list.getPage(), list.getLimit(), list.getSearch());
		return ja.toString();
	}

	@RequestMapping(value = "/CreateSnapshot", method = { RequestMethod.POST })
	@ResponseBody
	public String createSnapshot(HttpServletRequest request,
			CreateSnapshotModel createsnapshotModel) {
		User user = (User) request.getSession().getAttribute("user");
		JSONObject jo = new JSONObject();
		jo.put("Handler", "CreateSnapshotHandler");
		JSONArray ja = new JSONArray();
		JSONObject params = new JSONObject();
		params.put("userId", user.getUserId());
		String snapshotId = createsnapshotModel.getSnapshotId();
		params.put("uuid", snapshotId);
		params.put("name", createsnapshotModel.getSnapshotName());
		params.put("resourceUuid", createsnapshotModel.getResourceUuid());
		params.put("resourceType", createsnapshotModel.getResourceType());
		params.put("statistics", StatisticsType.SNAPSHOT.ordinal());
		ja.put(0, params);
		jo.put("Params", ja);
		dispatch.post(jo);
		return ((JSONObject)((JSONArray)jo.get("Params")).get(0)).toString();
	}

	@RequestMapping(value = "/DetailList", method = { RequestMethod.GET })
	@ResponseBody
	public String detailList(HttpServletRequest request,
			CreateSnapshotModel createsnapshotModel) {
		JSONArray ja = queryList.queryDetailSnapshotList(
				createsnapshotModel.getResourceUuid(),
				createsnapshotModel.getResourceType());
		return ja.toString();
	}

	@RequestMapping(value = "/BasicList", method = { RequestMethod.GET })
	@ResponseBody
	public String basicList(HttpServletRequest request,
			CreateSnapshotModel createsnapshotModel) {
		User user = (User) request.getSession().getAttribute("user");
		JSONObject jsonobect = queryList.querySnapshotBasicList(
				user.getUserId(), createsnapshotModel.getResourceUuid(),
				createsnapshotModel.getResourceType());
		return jsonobect.toString();
	}

	@RequestMapping(value = "/DeleteSnapshotSeries", method = { RequestMethod.GET })
	@ResponseBody
	public String deleteSnapshotSeries(HttpServletRequest request,
			@RequestParam String resourceUuid, @RequestParam String resourceType) {
		User user = (User) request.getSession().getAttribute("user");
		JSONObject jo = new JSONObject();
		jo.put("Handler", "DeleteSnapshotHandler");
		JSONArray ja = new JSONArray();
		JSONObject params = new JSONObject();
		params.put("userId", user.getUserId());
		params.put("resourceUuid", resourceUuid);
		params.put("resourceType", resourceType);
		ja.put(0, params);
		jo.put("Params", ja);
		dispatch.post(jo);
		return ((JSONObject)((JSONArray)jo.get("Params")).get(0)).toString();
	}

	@RequestMapping(value = "/DeleteSnapshot", method = { RequestMethod.POST })
	@ResponseBody
	public String deleteSnapshot(HttpServletRequest request,
			@RequestParam String snapshotId, @RequestParam String resourceUuid,
			@RequestParam String resourceType) {
		User user = (User) request.getSession().getAttribute("user");
		JSONObject jo = new JSONObject();
		jo.put("Handler", "DestorySnapshotHandler");
		JSONArray ja = new JSONArray();
		JSONObject params = new JSONObject();
		params.put("userId", user.getUserId());
		params.put("resourceUuid", resourceUuid);
		params.put("resourceType", resourceType);
		params.put("snapshotId", snapshotId);
		ja.put(0, params);
		jo.put("Params", ja);
		dispatch.post(jo);
		return ((JSONObject)((JSONArray)jo.get("Params")).get(0)).toString();
	}

	@RequestMapping(value = "/Rollback", method = { RequestMethod.POST })
	@ResponseBody
	public String rollbackSnapshot(HttpServletRequest request,
			@RequestParam String snapshotId, @RequestParam String resourceUuid,
			@RequestParam String resourceType) {
		User user = (User) request.getSession().getAttribute("user");
		JSONObject jo = new JSONObject();
		jo.put("Handler", "RollbackSnapshotHandler");
		JSONArray ja = new JSONArray();
		JSONObject params = new JSONObject();
		params.put("userId", user.getUserId());
		params.put("resourceUuid", resourceUuid);
		params.put("resourceType", resourceType);
		params.put("snapshotId", snapshotId);
		ja.put(0, params);
		jo.put("Params", ja);
		dispatch.post(jo);
		return ((JSONObject)((JSONArray)jo.get("Params")).get(0)).toString();
	}
}
