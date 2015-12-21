/**
 * @author hty
 * @time 下午3:05:54
 * @date 2014年12月9日
 */
package com.oncecloud.controller.forward;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.oncecloud.factory.ServiceFactory;
import com.oncecloud.helper.AuthOperationHelper;

@Controller
public class UserController {

	@Resource
	private ServiceFactory serviceFactory;
	
	@RequestMapping(value = "/overview", method = { RequestMethod.GET })
	@ResponseBody
	public ModelAndView overview(HttpServletRequest request) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("sideActive", 0);
		model.put("activeArea", "overview");
		model.put("vncServer", serviceFactory.getVNCFactory().getPublicVNC());
		model.put("title", "概览");

		model.put("operationAuth", AuthOperationHelper.getOperationOfPageAuthString("/overview", request.getSession().getAttribute("authoritylists")));
		return new ModelAndView("user/overview", model);
	}
	
	@RequestMapping(value = "cluster", method = { RequestMethod.GET })
	@ResponseBody
	public ModelAndView pool(HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("sideActive", 20);
		model.put("activeArea", "resource");
		model.put("title", "集群管理");
		model.put("operationAuth", AuthOperationHelper.getOperationOfPageAuthString("/cluster", request.getSession().getAttribute("authoritylists")));
		return new ModelAndView("user/cluster", model);
	}
	
	@RequestMapping(value = "host", method = { RequestMethod.GET })
	@ResponseBody
	public ModelAndView host(HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("sideActive", 21);
		model.put("activeArea", "resource");
		model.put("title", "服务器管理");
		return new ModelAndView("user/host", model);
	}

	@RequestMapping(value = "/instance", method = { RequestMethod.GET })
	@ResponseBody
	public ModelAndView instance(HttpServletRequest request) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("sideActive", 1);
		model.put("activeArea", "virtual");
		model.put("vncServer", serviceFactory.getVNCFactory().getPublicVNC());
		model.put("title", "主机");

		model.put("operationAuth", AuthOperationHelper.getOperationOfPageAuthString("/instance", request.getSession().getAttribute("authoritylists")));
		return new ModelAndView("user/instance", model);
	}
	
	@RequestMapping(value = "/image", method = { RequestMethod.GET })
	@ResponseBody
	public ModelAndView image(HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("sideActive", 2);
		model.put("activeArea", "virtual");
		model.put("title", "映像");
		model.put("operationAuth", AuthOperationHelper.getOperationOfPageAuthString("/image", request.getSession().getAttribute("authoritylists")));
		return new ModelAndView("user/image", model);
	}
	
	@RequestMapping(value = "/volume", method = { RequestMethod.GET })
	@ResponseBody
	public ModelAndView volume(HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("sideActive", 3);
		model.put("activeArea", "virtual");
		model.put("title", "硬盘");
		
		model.put("operationAuth", AuthOperationHelper.getOperationOfPageAuthString("/volume", request.getSession().getAttribute("authoritylists")));
		return new ModelAndView("user/volume", model);
	}
	
	@RequestMapping(value = "/snapshot", method = { RequestMethod.GET })
	@ResponseBody
	public ModelAndView snapshot(HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("sideActive", 4);
		model.put("activeArea", "virtual");
		model.put("title", "备份");
		
		model.put("operationAuth", AuthOperationHelper.getOperationOfPageAuthString("/snapshot", request.getSession().getAttribute("authoritylists")));
		return new ModelAndView("user/snapshot", model);
	}
	
	@RequestMapping(value = "/alarm", method = { RequestMethod.GET })
	@ResponseBody
	public ModelAndView alarm(HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("sideActive", 5);
		model.put("activeArea", "system");
		model.put("title", "监控警告");

		model.put("operationAuth", AuthOperationHelper.getOperationOfPageAuthString("/alarm", request.getSession().getAttribute("authoritylists")));
		return new ModelAndView("user/alarm", model);
	}
	
	@RequestMapping(value = "/group", method = {RequestMethod.GET})
	@ResponseBody
	public ModelAndView group(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("sideActive", 6);
		model.put("activeArea", "system");
		model.put("title", "分组管理");
		
		model.put("operationAuth", AuthOperationHelper.getOperationOfPageAuthString("/group", request.getSession().getAttribute("authoritylists")));
		return new ModelAndView("user/group", model);
	}
	
	@RequestMapping(value = "/log", method = { RequestMethod.GET })
	@ResponseBody
	public ModelAndView log(HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("sideActive", 9);
		model.put("activeArea", "system");
		model.put("title", "操作日志");
		
		model.put("operationAuth", AuthOperationHelper.getOperationOfPageAuthString("/log", request.getSession().getAttribute("authoritylists")));
		return new ModelAndView("user/log", model);
	}
	
	@RequestMapping(value = "service", method = { RequestMethod.GET })
	@ResponseBody
	public ModelAndView service(HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("sideActive", 11);
		model.put("activeArea", "system");
		model.put("title", "表单");
		
		model.put("operationAuth", AuthOperationHelper.getOperationOfPageAuthString("/service", request.getSession().getAttribute("authoritylists")));
		return new ModelAndView("user/service", model);
	}
	
	@RequestMapping(value = "/statistics", method = { RequestMethod.GET })
	@ResponseBody
	public ModelAndView expenseSummary(HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("sideActive", 10);
		model.put("activeArea", "system");
		model.put("title", "使用统计");
		return new ModelAndView("user/statistics", model);
	}
	
	@RequestMapping(value = "/container", method = { RequestMethod.GET })
	@ResponseBody
	public ModelAndView platform(HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("sideActive", 12);
		model.put("activeArea", "container");
		model.put("title", "容器管理");
		
		model.put("operationAuth", AuthOperationHelper.getOperationOfPageAuthString("/container", request.getSession().getAttribute("authoritylists")));
		return new ModelAndView("user/container", model);
	}
	
	@RequestMapping(value = "/template", method = { RequestMethod.GET })
	@ResponseBody
	public ModelAndView template(HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("sideActive", 13);
		model.put("activeArea", "container");
		model.put("title", "模板管理");
		
		model.put("operationAuth", AuthOperationHelper.getOperationOfPageAuthString("/template", request.getSession().getAttribute("authoritylists")));
		return new ModelAndView("user/template", model);
	}
	
	@RequestMapping(value = "/repository", method = { RequestMethod.GET })
	@ResponseBody
	public ModelAndView repository(HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("sideActive", 14);
		model.put("activeArea", "container");
		model.put("title", "仓库管理");
		
		model.put("operationAuth", AuthOperationHelper.getOperationOfPageAuthString("/repository", request.getSession().getAttribute("authoritylists")));
		return new ModelAndView("user/repository", model);
	}
	
	@RequestMapping(value = "/deploy", method = { RequestMethod.GET })
	@ResponseBody
	public ModelAndView deploy(HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("sideActive", 30);
		model.put("activeArea", "container");
		model.put("title", "编排部署");
		model.put("operationAuth", AuthOperationHelper.getOperationOfPageAuthString("/deploy", request.getSession().getAttribute("authoritylists")));
		return new ModelAndView("user/deploy", model);
	}
	
	@RequestMapping(value = "/distribute", method = { RequestMethod.GET })
	@ResponseBody
	public ModelAndView distribute(HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("sideActive", 31);
		model.put("activeArea", "container");
		model.put("title", "编排部署");
		model.put("operationAuth", AuthOperationHelper.getOperationOfPageAuthString("/deploy", request.getSession().getAttribute("authoritylists")));
		return new ModelAndView("user/distribute", model);
	}
	
	@RequestMapping(value = "/registry", method = { RequestMethod.GET })
	@ResponseBody
	public ModelAndView registry(HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("sideActive", 32);
		model.put("activeArea", "container");
		model.put("title", "仓库列表");
		
		model.put("operationAuth", AuthOperationHelper.getOperationOfPageAuthString("/registry", request.getSession().getAttribute("authoritylists")));
		return new ModelAndView("user/registry", model);
	}
	
	@RequestMapping(value = "/consumption", method = { RequestMethod.GET })
	@ResponseBody
	public ModelAndView consumption(HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("sideActive", 15);
		model.put("activeArea", "system");
		model.put("title", "实时消费");
		return new ModelAndView("user/consumption", model);
	}
	@RequestMapping(value = "/application", method = { RequestMethod.GET })
	@ResponseBody
	public ModelAndView application(HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("sideActive", 16);
		model.put("activeArea", "application");
		model.put("title", "应用管理");
		return new ModelAndView("user/application", model);
	}
	
}
