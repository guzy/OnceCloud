/**
 * @author hty
 * @time 上午11:05:48
 * @date 2014年12月9日
 */
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

import com.oncecloud.manager.ImageManager;
import com.oncecloud.entity.User;
import com.oncecloud.model.ListModel;

@RequestMapping("ImageAction")
@Controller
public class ImageAction {

	@Resource
	private ImageManager imageManager;

	@RequestMapping(value = "/ImageList", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public String imageList(HttpServletRequest request, ListModel list) {
		User user = (User) request.getSession().getAttribute("user");
		int userId = user.getUserId();
		int userLevel = user.getUserLevel().ordinal();
		JSONArray ja = imageManager.getImageList(userId, userLevel,
				list.getPage(), list.getLimit(), list.getSearch(),
				list.getType());
		return ja.toString();
	}

	@RequestMapping(value = "/Delete", method = { RequestMethod.POST })
	@ResponseBody
	public String delete(HttpServletRequest request,
			@RequestParam String imageId, @RequestParam String imageName) {
		User user = (User) request.getSession().getAttribute("user");
		JSONObject jo = imageManager.deleteImage(user.getUserId(),
				imageId, imageName);
		return jo.toString();
	}

	@RequestMapping(value = "/Create", method = { RequestMethod.POST })
	@ResponseBody
	public String create(HttpServletRequest request,
			@RequestParam String imageUUId, @RequestParam String imageName,
			@RequestParam String imageServer, @RequestParam int imageOs,
			@RequestParam String imageDesc,@RequestParam String hostUuid,
			@RequestParam String imagePwd) {
		User user = (User) request.getSession().getAttribute("user");
		JSONArray ja = imageManager.createImage(user.getUserId(),
				user.getUserLevel().ordinal(), imageUUId, imageName, imageServer,
				imageOs, imageDesc, hostUuid, imagePwd);
		return ja.toString();
	}

	@RequestMapping(value = "/BasicList", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public String basicList(HttpServletRequest request,
			@RequestParam String uuid) {
		JSONObject jo = imageManager.getBasciList(uuid);
		return jo.toString();
	}

	@RequestMapping(value = "/ShareImageList", method = { RequestMethod.POST })
	@ResponseBody
	public String shareImageList(HttpServletRequest request,
			@RequestParam String pooluuid, @RequestParam String images) {
		String[] imageArray = images.split(",");
		JSONArray ja = imageManager.getShareImageList(pooluuid,
				imageArray);
		return ja.toString();
	}

	@RequestMapping(value = "/ImageShare", method = { RequestMethod.POST })
	@ResponseBody
	public String imageShare(HttpServletRequest request,
			@RequestParam String sorpooluuid, @RequestParam String images,
			@RequestParam String despooluuid) {
		String[] imageArray = images.split(",");
		User user = (User) request.getSession().getAttribute("user");
		JSONArray ja = imageManager.shareImages(user.getUserId(), sorpooluuid,
				despooluuid, imageArray);
		return ja.toString();
	}

	@RequestMapping(value = "/ImageUpdate", method = { RequestMethod.POST })
	@ResponseBody
	public String updateImage(HttpServletRequest request,
			@RequestParam String uuid, @RequestParam String pwd,
			@RequestParam String desc, @RequestParam int disk,
			@RequestParam int platform) {
		JSONObject jo = new JSONObject();
		jo.put("result", imageManager.updateImage(uuid, pwd, desc, disk, platform));
		return jo.toString();
	}
}
