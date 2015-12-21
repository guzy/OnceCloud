package com.oncecloud.controller.forward;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.oncecloud.manager.DatacenterManager;

@Controller
public class DetailControllerAdmin {
	
	@Resource
	private DatacenterManager datacenterManager;
	
	@RequestMapping(value = "image/detail")
	@ResponseBody
	public ModelAndView imageDetail(HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("title", "映像");
		model.put("sideActive", 2);
		String imageUuid = request.getParameter("imageUuid");
		String imageType = request.getParameter("imageType");
		if (imageUuid != null) {
			request.getSession().setAttribute("imageUuid", imageUuid);
			request.getSession().setAttribute("imageType", imageType);
			return new ModelAndView("admin/detail/imagedetail", model);
		} else {
			if (request.getSession().getAttribute("imageUuid") != null) {
				return new ModelAndView("admin/detail/imagedetail", model);
			} else {
				return new ModelAndView(new RedirectView("/dashboard"));
			}
		}
	}
	
	@RequestMapping(value = "datacenter/detail")
	@ResponseBody
	public ModelAndView datacenterDetail(HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("title", "数据中心管理");
		model.put("sideActive", 12);
		String dcid = request.getParameter("dcid");
		
		if (dcid != null) {
			request.getSession().setAttribute("dcid", dcid);
			request.getSession().setAttribute("datacenterInfo", datacenterManager.getDatacenterInfos(dcid));
			return new ModelAndView("admin/detail/datacenterdetail", model);
		} else {
			if (request.getSession().getAttribute("dcid") != null) {
				request.getSession().setAttribute("datacenterInfo", datacenterManager.getDatacenterInfos(dcid));
				return new ModelAndView("admin/detail/datacenterdetail", model);
			} else {
				return new ModelAndView(new RedirectView("/dashboard"));
			}
		}
	}
	
	@RequestMapping(value = "overview/companydetail")
	@ResponseBody
	public ModelAndView companyDetail(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("title", "企业虚拟机信息");
		model.put("sideActive", 20);
		return new ModelAndView("admin/detail/companydetail", model);
	}
	
	@RequestMapping(value = "host/detail")
	@ResponseBody
	public ModelAndView hostDetail(HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("title", "服务器");
		model.put("sideActive", 15);
		String hostid = request.getParameter("hostid");
		if (hostid != null) {
			request.getSession().setAttribute("hostid", hostid);
			request.getSession().setAttribute("showId",
					"host-" + hostid.substring(0, 8));
			return new ModelAndView("admin/detail/hostdetail", model);
		} else {
			if (request.getSession().getAttribute("hostid") != null) {
				return new ModelAndView("admin/detail/hostdetail", model);
			} else {
				return new ModelAndView(new RedirectView("/dashboard"));
			}
		}
	}

	@RequestMapping(value = "user/detail")
	@ResponseBody
	public ModelAndView userDetail(HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("title", "用户");
		model.put("sideActive", 3);
		String userid = request.getParameter("userid");
		String username = request.getParameter("userid");
		if (userid != null) {
			request.getSession().setAttribute("userid", userid);
			request.getSession().setAttribute("username", username);
			return new ModelAndView("admin/detail/userdetail", model);
		} else {
			if (request.getSession().getAttribute("userid") != null) {
				return new ModelAndView("admin/detail/userdetail", model);
			} else {
				return new ModelAndView(new RedirectView("/dashboard"));
			}
		}
	}
	
	@RequestMapping(value = "service/detail")
	@ResponseBody
	public ModelAndView questionDetail(HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("title", "表单");
		model.put("sideActive", 11);
		String qaId = request.getParameter("qaId");
		if (qaId != null) {
			request.getSession().setAttribute("qaId", qaId);
			return new ModelAndView("admin/detail/servicedetail", model);
		} else {
			if (request.getSession().getAttribute("qaId") != null) {
				return new ModelAndView("admin/detail/servicedetail", model);
			} else {
				return new ModelAndView(new RedirectView("/dashboard"));
			}
		}
	}
	
	@RequestMapping(value = "costtype/detail")
	@ResponseBody
	public ModelAndView costTypeDetail(HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("title", "财务分析");
		model.put("sideActive", 666);
		String typeid = request.getParameter("costtypeid");
		if (typeid != null) {
			request.getSession().setAttribute("costtypeid", typeid);
			return new ModelAndView("admin/detail/costtypedetail", model);
		} else {
			if (request.getSession().getAttribute("costtypeid") != null) {
				return new ModelAndView("admin/detail/costtypedetail", model);
			} else {
				return new ModelAndView(new RedirectView("/dashboard"));
			}
		}
	}
	
	@RequestMapping(value = "cost/detail")
	@ResponseBody
	public ModelAndView costDetail(HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("title", "财务分析");
		model.put("sideActive", 666);
		String costId = request.getParameter("costid");
		if (costId != null) {
			request.getSession().setAttribute("costid", costId);
			return new ModelAndView("admin/detail/costdetail", model);
		} else {
			if (request.getSession().getAttribute("costid") != null) {
				return new ModelAndView("admin/detail/costdetail", model);
			} else {
				return new ModelAndView(new RedirectView("/dashboard"));
			}
		}
	}
	
	@RequestMapping(value = "profit/detail")
	@ResponseBody
	public ModelAndView profitDetail(HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("title", "财务分析");
		model.put("sideActive", 666);
		String userid = request.getParameter("userid");
		String username = request.getParameter("username");
		if (userid != null) {
			request.getSession().setAttribute("p_userid", userid);
			request.getSession().setAttribute("p_username", username);
			return new ModelAndView("admin/detail/profitdetail", model);
		} else {
			if (request.getSession().getAttribute("p_userid") != null) {
				return new ModelAndView("admin/detail/profitdetail", model);
			} else {
				return new ModelAndView(new RedirectView("/dashboard"));
			}
		}
	}
	
	@RequestMapping(value = "userprofit/detail")
	@ResponseBody
	public ModelAndView userprofitDetail(HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("title", "财务分析");
		model.put("sideActive", 666);
		String userid = request.getParameter("userid");
		String username = request.getParameter("username");
		if (userid != null) {
			request.getSession().setAttribute("p_userid", userid);
			request.getSession().setAttribute("p_username", username);
			return new ModelAndView("admin/detail/userprofitdetail", model);
		} else {
			if (request.getSession().getAttribute("p_userid") != null) {
				return new ModelAndView("admin/detail/userprofitdetail", model);
			} else {
				return new ModelAndView(new RedirectView("/dashboard"));
			}
		}
	}
	
	@RequestMapping(value = "router/detail")
	@ResponseBody
	public ModelAndView routerDetail(HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("title", "路由器");
		model.put("sideActive", 13);
		String uuid = request.getParameter("routerUuid");
		if (uuid != null) {
			request.getSession().setAttribute("routerUuid", uuid);
			request.getSession().setAttribute("showId",
					"rt-" + uuid.substring(0, 8));
			return new ModelAndView("admin/detail/routerdetail", model);
		} else {
			if (request.getSession().getAttribute("routerUuid") != null) {
				return new ModelAndView("admin/detail/routerdetail", model);
			} else {
				return new ModelAndView(new RedirectView("/dashboard"));
			}
		}
	}

}
