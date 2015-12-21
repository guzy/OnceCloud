package com.beyondsphere.controller.forward;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.beyondsphere.helper.AccordanceHelper;
import com.beyondsphere.model.UserLevel;
import com.beyondsphere.constants.SystemConstant;
import com.beyondsphere.entity.User;

@Controller
public class AdminController {

	@Resource
	private AccordanceHelper accordanceHelper;
	
	@RequestMapping(value = "infrastructure", method = { RequestMethod.GET })
	@ResponseBody
	public ModelAndView Infrastructure(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("sideActive", 0);
		model.put("title", "基础架构");
		return new ModelAndView("admin/infrastructure", model);
	}
	
	@RequestMapping(value = "overview", method = { RequestMethod.GET })
	@ResponseBody
	public ModelAndView statisticalChart(HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("sideActive", 22);
		model.put("title", "概览");
		return new ModelAndView("admin/overview", model);
	}
	@RequestMapping(value = "maplocation", method = { RequestMethod.GET })
	@ResponseBody
	public ModelAndView maplocation(HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("sideActive", 23);
		model.put("title", "概览");
		return new ModelAndView("admin/maplocation", model);
	}
	
	@RequestMapping(value = "instance", method = { RequestMethod.GET })
	@ResponseBody
	public ModelAndView instance(HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		User user = (User) request.getSession().getAttribute("user");
		model.put("sideActive", 1);
		model.put("vncServer", SystemConstant.noVNCServerPublic);
		int userid = 0;
		if (request.getParameter("userid")!=null) {
			userid = Integer.parseInt(request.getParameter("userid"));
		}
		model.put("userid", userid);
		if (user.getUserLevel() != UserLevel.ADMIN) {
			model.put("title", "主机");
			return new ModelAndView("user/instance", model);
		} else {
			model.put("title", "虚拟机管理");
			return new ModelAndView("admin/instance", model);
		}
	}

	@RequestMapping(value = "image", method = { RequestMethod.GET })
	@ResponseBody
	public ModelAndView image(HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("sideActive", 2);
		model.put("title", "映像");
		return new ModelAndView("admin/image", model);
	}
	
	@RequestMapping(value = "iso", method = { RequestMethod.GET })
	@ResponseBody
	public ModelAndView iso(HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		User user = (User) request.getSession().getAttribute("user");
		model.put("sideActive", 222);
		model.put("title", "镜像管理");
		if (user.getUserLevel() == UserLevel.ADMIN) {
			return new ModelAndView("admin/iso", model);
		} else {
			return new ModelAndView(new RedirectView("/dashboard"));
		}
	}
	
	@RequestMapping(value = "datacenter", method = { RequestMethod.GET })
	@ResponseBody
	public ModelAndView datacenter(HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		User user = (User) request.getSession().getAttribute("user");
		model.put("sideActive", 12);
		model.put("title", "数据中心管理");
		if (user.getUserLevel() == UserLevel.ADMIN) {
			return new ModelAndView("admin/datacenter", model);
		} else {
			return new ModelAndView(new RedirectView("/dashboard"));
		}
	}
	
	@RequestMapping(value = "pool", method = { RequestMethod.GET })
	@ResponseBody
	public ModelAndView pool(HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		User user = (User) request.getSession().getAttribute("user");
		model.put("sideActive", 14);
		model.put("title", "资源池管理");
		if (user.getUserLevel() == UserLevel.ADMIN) {
			request.getSession().setAttribute("isac", accordanceHelper.isFlag());
			return new ModelAndView("admin/pool", model);
		} else {
			return new ModelAndView(new RedirectView("/dashboard"));
		}
	}
	
	@RequestMapping(value = "ha", method = { RequestMethod.GET })
	@ResponseBody
	public ModelAndView ha(HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		User user = (User) request.getSession().getAttribute("user");
		model.put("sideActive", 15);
		model.put("title", "高可用管理");
		if (user.getUserLevel() == UserLevel.ADMIN) {
			return new ModelAndView("admin/ha", model);
		} else {
			return new ModelAndView(new RedirectView("/dashboard"));
		}
	}
	
	@RequestMapping(value = "host", method = { RequestMethod.GET })
	@ResponseBody
	public ModelAndView host(HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		User user = (User) request.getSession().getAttribute("user");
		model.put("sideActive", 16);
		model.put("title", "服务器管理");
		if (user.getUserLevel() == UserLevel.ADMIN) {
			return new ModelAndView("admin/host", model);
		} else {
			return new ModelAndView(new RedirectView("/dashboard"));
		}
	}
	
	@RequestMapping(value = "network", method = { RequestMethod.GET })
	@ResponseBody
	public ModelAndView network(HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		User user = (User) request.getSession().getAttribute("user");
		model.put("sideActive", 18);
		model.put("title", "网络管理");
		if (user.getUserLevel() == UserLevel.ADMIN) {
			return new ModelAndView("admin/network", model);
		} else {
			return new ModelAndView(new RedirectView("/dashboard"));
		}
	}

	@RequestMapping(value = "storage", method = { RequestMethod.GET })
	@ResponseBody
	public ModelAndView storage(HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		User user = (User) request.getSession().getAttribute("user");
		model.put("sideActive", 17);
		model.put("title", "存储管理");
		if (user.getUserLevel() == UserLevel.ADMIN) {
			return new ModelAndView("admin/storage", model);
		} else {
			return new ModelAndView(new RedirectView("/dashboard"));
		}
	}
	
	@RequestMapping(value = "information", method = { RequestMethod.GET })
	@ResponseBody
	public ModelAndView information(HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		User user = (User) request.getSession().getAttribute("user");
		model.put("sideActive", 181);
		model.put("title", "主机信息");
		if (user.getUserLevel() == UserLevel.ADMIN) {
			return new ModelAndView("admin/information", model);
		} else {
			return new ModelAndView(new RedirectView("/dashboard"));
		}
	}

	@RequestMapping(value = "user", method = { RequestMethod.GET })
	@ResponseBody
	public ModelAndView user(HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		User user = (User) request.getSession().getAttribute("user");
		model.put("sideActive", 3);
		model.put("title", "用户管理");
		if (user.getUserLevel() == UserLevel.ADMIN) {
			request.getSession().setAttribute("userid",
					request.getParameter("userid"));
			return new ModelAndView("admin/user", model);
		} else {
			return new ModelAndView(new RedirectView("/dashboard"));
		}
	}

	@RequestMapping(value = "role", method = { RequestMethod.GET })
	@ResponseBody
	public ModelAndView role(HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		User user = (User) request.getSession().getAttribute("user");
		model.put("sideActive", 333);
		model.put("title", "角色管理");
		if (user.getUserLevel() == UserLevel.ADMIN) {
			return new ModelAndView("admin/role", model);
		} else {
			return new ModelAndView(new RedirectView("/dashboard"));
		}
	}

	@RequestMapping(value = "area", method = { RequestMethod.GET })
	@ResponseBody
	public ModelAndView area(HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		User user = (User) request.getSession().getAttribute("user");
		model.put("sideActive", 21);
		model.put("title", "区域管理");
		if (user.getUserLevel() == UserLevel.ADMIN) {
			return new ModelAndView("admin/area", model);
		} else {
			return new ModelAndView(new RedirectView("/dashboard"));
		}
	}
	
	@RequestMapping(value = "account", method = { RequestMethod.GET })
	@ResponseBody
	public ModelAndView account(HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		User user = (User) request.getSession().getAttribute("user");
		model.put("sideActive", 666);
		model.put("title", "财务分析");
		if (user.getUserLevel() == UserLevel.ADMIN) {
			return new ModelAndView("admin/account", model);
		} else {
			return new ModelAndView(new RedirectView("/dashboard"));
		}
	}
	
	@RequestMapping(value = "upxen", method = { RequestMethod.GET })
	@ResponseBody
	public ModelAndView upxen(HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		User user = (User) request.getSession().getAttribute("user");
		model.put("sideActive", 99);
		model.put("title", "上传Xen");
		if (user.getUserLevel() == UserLevel.ADMIN) {
			return new ModelAndView("admin/upxen", model);
		} else {
			return new ModelAndView(new RedirectView("/dashboard"));
		}
	}
	
	@RequestMapping(value = "log", method = { RequestMethod.GET })
	@ResponseBody
	public ModelAndView log(HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("sideActive", 9);
		model.put("title", "操作日志");
		return new ModelAndView("admin/log", model);
	}

	@RequestMapping(value = "service", method = { RequestMethod.GET })
	@ResponseBody
	public ModelAndView service(HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("sideActive", 11);
		model.put("title", "表单");
		return new ModelAndView("admin/service", model);
	}
	
	@RequestMapping(value = "statistics", method = { RequestMethod.GET })
	@ResponseBody
	public ModelAndView expenseSummary(HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("sideActive", 20);
		model.put("title", "使用统计");
		return new ModelAndView("admin/statistics", model);
	}
	
}
