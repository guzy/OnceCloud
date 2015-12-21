/**
 * @author hty
 * @time 下午1:54:24
 * @date 2014年12月12日
 */
package com.oncecloud.controller.forward;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.oncecloud.entity.User;

@Controller
public class DetailController {

	
	@RequestMapping(value = "/instance/detail")
	@ResponseBody
	public ModelAndView instanceDetail(HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("title", "主机");
		model.put("activeArea", "virtual");
		model.put("sideActive", 1);
		String uuid = request.getParameter("instanceUuid");
		if (uuid != null) {
			request.getSession().setAttribute("instanceUuid", uuid);
			request.getSession().setAttribute("showId",
					"i-" + uuid.substring(0, 8));
			return new ModelAndView("user/detail/instancedetail", model);
		} else {
			if (request.getSession().getAttribute("instanceUuid") != null) {
				return new ModelAndView("user/detail/instancedetail", model);
			} else {
				return new ModelAndView(new RedirectView("/dashboard"));
			}
		}
	}
	
	@RequestMapping(value = "/alarm/detail")
	@ResponseBody
	public ModelAndView alarmDetail(HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("title", "监控警告");
		model.put("activeArea", "system");
		model.put("sideActive", 5);
		String alarmUuid = request.getParameter("alarmUuid");
		if (alarmUuid != null) {
			request.getSession().setAttribute("alarmUuid", alarmUuid);
			request.getSession().setAttribute("showId",
					"al-" + alarmUuid.substring(0, 8));
			return new ModelAndView("user/detail/alarmdetail", model);
		} else {
			if (request.getSession().getAttribute("alarmUuid") != null) {
				return new ModelAndView("user/detail/alarmdetail", model);
			} else {
				return new ModelAndView(new RedirectView("/dashboard"));
			}
		}
	}
	
	@RequestMapping(value = "/group/detail")
	@ResponseBody
	public ModelAndView groupDetail(HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("title", "分组管理");
		model.put("activeArea", "system");
		model.put("sideActive", 6);
		String groupUuid = request.getParameter("groupUuid");
		if ( groupUuid!= null) {
			request.getSession().setAttribute("groupUuid", groupUuid);
			request.getSession().setAttribute("showId",
					"gl-" + groupUuid.substring(0, 8));
			return new ModelAndView("user/detail/groupdetail", model);
		} else {
			if (request.getSession().getAttribute("groupUuid") != null) {
				return new ModelAndView("user/detail/groupdetail", model);
			} else {
				return new ModelAndView(new RedirectView("/dashboard"));
			}
		}
	}
	
	@RequestMapping(value = "/volume/detail")
	@ResponseBody
	public ModelAndView volumeDetail(HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("title", "硬盘");
		model.put("activeArea", "virtual");
		model.put("sideActive", 3);
		String uuid = request.getParameter("volumeUuid");
		if (uuid != null) {
			request.getSession().setAttribute("volumeUuid", uuid);
			request.getSession().setAttribute("showId",
					"vol-" + uuid.substring(0, 8));
			return new ModelAndView("user/detail/volumedetail", model);
		} else {
			if (request.getSession().getAttribute("volumeUuid") != null) {
				return new ModelAndView("user/detail/volumedetail", model);
			} else {
				return new ModelAndView(new RedirectView("/dashboard"));
			}
		}
	}
	
	@RequestMapping(value = "/snapshot/detail")
	@ResponseBody
	public ModelAndView snapshotDetail(HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("title", "备份");
		model.put("activeArea", "virtual");
		model.put("sideActive", 4);
		String resourceUuid = request.getParameter("resourceUuid");
		String resourceType = request.getParameter("resourceType");
		String resourceName = request.getParameter("resourceName");
		if (resourceUuid != null) {
			request.getSession().setAttribute("resourceUuid", resourceUuid);
			request.getSession().setAttribute("resourceType", resourceType);
			request.getSession().setAttribute("resourceName", resourceName);
			request.getSession().setAttribute("showId", "bk-" + resourceUuid.substring(0, 8));
			return new ModelAndView("user/detail/snapshotdetail", model);
		} else {
			if (request.getSession().getAttribute("resourceUuid") != null) {
				return new ModelAndView("user/detail/snapshotdetail", model);
			} else {
				return new ModelAndView(new RedirectView("/dashboard"));
			}
		}
	}
	
	@RequestMapping(value = "/image/detail")
	@ResponseBody
	public ModelAndView imageDetail(HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("title", "映像");
		model.put("activeArea", "virtual");
		model.put("sideActive", 2);
		String imageUuid = request.getParameter("imageUuid");
		String imageType = request.getParameter("imageType");
		User user = (User) request.getSession().getAttribute("user");
		if (imageUuid != null) {
			request.getSession().setAttribute("imageUuid", imageUuid);
			request.getSession().setAttribute("imageType", imageType);
			request.getSession().setAttribute("userLevel", user.getUserLevel().ordinal());
			return new ModelAndView("user/detail/imagedetail", model);
		} else {
			if (request.getSession().getAttribute("imageUuid") != null) {
				return new ModelAndView("user/detail/imagedetail", model);
			} else {
				return new ModelAndView(new RedirectView("/dashboard"));
			}
		}
	}
	
	@RequestMapping(value = "/service/detail")
	@ResponseBody
	public ModelAndView questionDetail(HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("title", "表单");
		model.put("activeArea", "system");
		model.put("sideActive", 11);
		String qaId = request.getParameter("qaId");
		if (qaId != null) {
			request.getSession().setAttribute("qaId", qaId);
			return new ModelAndView("user/detail/servicedetail", model);
		} else {
			if (request.getSession().getAttribute("qaId") != null) {
				return new ModelAndView("user/detail/servicedetail", model);
			} else {
				return new ModelAndView(new RedirectView("/dashboard"));
			}
		}
	}
	
	@RequestMapping(value = "/container/detail")
	@ResponseBody
	public ModelAndView containerDetail(HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("title", "容器");
		model.put("activeArea", "container");
		model.put("sideActive", 12);
		String uuid = request.getParameter("conUuid");
		if (uuid != null) {
			request.getSession().setAttribute("conUuid", uuid);
			request.getSession().setAttribute("showId",
					"c-" + uuid.substring(0, 8));
			return new ModelAndView("user/detail/containerdetail", model);
		} else {
			if (request.getSession().getAttribute("conUuid") != null) {
				return new ModelAndView("user/detail/containerdetail", model);
			} else {
				return new ModelAndView(new RedirectView("/dashboard"));
			}
		}
	}
	
}
