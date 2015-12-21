/**
 * @author hty
 * @time 上午11:05:48
 * @date 2014年12月9日
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
import com.beyondsphere.model.ImageCloneModel;
import com.beyondsphere.model.ListModel;

@RequestMapping("/ImageAction")
@Controller
public class ImageAction {

	@Resource
	private QueryList queryList;
	@Resource
	private Dispatch dispatch;

	@RequestMapping(value = "/ImageList", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public String imageList(HttpServletRequest request, ListModel list) {
		User user = (User) request.getSession().getAttribute("user");
		JSONArray ja = queryList.queryImageList(user.getUserId(),
				list.getPage(), list.getLimit(), list.getSearch(),
				list.getType());
		return ja.toString();
	}

	@RequestMapping(value = "/Imagedetail", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public String basicList(HttpServletRequest request,
			@RequestParam String uuid) {
		JSONObject jo = queryList.queryImagedetail(uuid);
		return jo.toString();
	}
	
	@RequestMapping(value = "/Clone", method = { RequestMethod.POST })
	@ResponseBody
	public void clone(HttpServletRequest request,
			ImageCloneModel imagecloneModel) {
		User user = (User) request.getSession().getAttribute("user");
		JSONObject jo = new JSONObject();
		jo.put("Handler", "CreateImageHandler");
		JSONArray ja = new JSONArray();
		JSONObject params = new JSONObject();
		params.put("vmUuid", imagecloneModel.getVmUuid());
		params.put("imageName", imagecloneModel.getImageName());
		params.put("imageDesc", imagecloneModel.getImageDesc());
		params.put("userId", user.getUserId());
		ja.put(0, params);
		jo.put("Params", ja);
		dispatch.post(jo);
	}
	
	@RequestMapping(value = "/Delete", method = { RequestMethod.POST })
	@ResponseBody
	public String delete(HttpServletRequest request,
			@RequestParam String imageId, @RequestParam String imageName) {
		User user = (User) request.getSession().getAttribute("user");
		JSONObject jo = new JSONObject();
		jo.put("Handler", "DeleteImageHandler");
		JSONArray ja = new JSONArray();
		JSONObject params = new JSONObject();
		params.put("imageUuid", imageId);
		params.put("imageName", imageName);
		params.put("userId", user.getUserId());
		ja.put(0, params);
		jo.put("Params", ja);
		dispatch.post(jo);
		return ((JSONObject)((JSONArray)jo.get("Params")).get(0)).toString();
	}

}
