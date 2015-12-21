/**
 * @author hty
 * @time 下午3:50:54
 * @date 2014年12月9日
 */
package com.beyondsphere.controller.forward;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CreateController {
	
	@RequestMapping(value = "cluster/create", method = { RequestMethod.GET })
	public ModelAndView createPool(HttpServletRequest request) {
		return new ModelAndView("user/create/createcluster");
	}
	
	@RequestMapping(value = "host/create", method = { RequestMethod.GET })
	public ModelAndView createHost(HttpServletRequest request) {
		return new ModelAndView("user/create/createhost");
	}
	
	@RequestMapping(value = "/instance/create", method = { RequestMethod.GET })
	public ModelAndView createVM(HttpServletRequest request) {
		return new ModelAndView("user/create/createinstance");
	}
	
	@RequestMapping(value = "/platform/create", method = { RequestMethod.GET })
	public ModelAndView createContainer(HttpServletRequest request) {
		return new ModelAndView("user/create/createcontainer");
	}
	
	@RequestMapping(value = "/alarm/create", method = { RequestMethod.GET })
	public ModelAndView createAlarm(HttpServletRequest request) {
		return new ModelAndView("user/create/createalarm");
	}
	
	@RequestMapping(value = "/group/create", method = { RequestMethod.GET })
	public ModelAndView createGroup(HttpServletRequest request) {
		return new ModelAndView("user/create/creategroup");
	}
	
	@RequestMapping(value = "/alarmrule/create", method = { RequestMethod.GET })
	public ModelAndView createAlarmRule(HttpServletRequest request) {
		return new ModelAndView("user/create/createalarmrule");
	}
	
	@RequestMapping(value = "/volume/create", method = { RequestMethod.GET })
	public ModelAndView createVolume(HttpServletRequest request) {
		return new ModelAndView("user/create/createvolume");
	}
	
	@RequestMapping(value = "/snapshot/create", method = { RequestMethod.GET })
	public ModelAndView createSnapshot(HttpServletRequest request,
			@RequestParam String rsid, @RequestParam String rstype,
			@RequestParam String rsname) {
		Map<String, String> model = new HashMap<String, String>();
		model.put("resourceId", rsid);
		model.put("resourceType", rstype);
		model.put("resourceName", rsname);
		return new ModelAndView("user/create/createsnapshot", model);
	}
	
	@RequestMapping(value = "/image/clone", method = { RequestMethod.GET })
	public ModelAndView cloneImage(HttpServletRequest request,
			@RequestParam String rsid) {
		Map<String, String> model = new HashMap<String, String>();
		model.put("rsid", rsid);
		return new ModelAndView("user/create/createimage", model);
	}
	
	@RequestMapping(value = "/platform/clone", method = { RequestMethod.GET })
	public ModelAndView cloneContainerImage(HttpServletRequest request,
			@RequestParam String rsid) {
		Map<String, String> model = new HashMap<String, String>();
		model.put("rsid", rsid);
		return new ModelAndView("user/create/createcontainerimage", model);
	}
	
	@RequestMapping(value = "/template/push", method = { RequestMethod.GET })
	public ModelAndView pushTemplate(HttpServletRequest request,
			@RequestParam String rsid) {
		Map<String, String> model = new HashMap<String, String>();
		model.put("rsid", rsid);
		return new ModelAndView("user/create/createcontainertemplate", model);
	}
	
	@RequestMapping(value = "/service/create", method = { RequestMethod.GET })
	public ModelAndView createService(HttpServletRequest request) {
		return new ModelAndView("user/create/createservice");
	}
	
	@RequestMapping(value = "/registry/create", method = { RequestMethod.GET })
	public ModelAndView createRegistr(HttpServletRequest request) {
		return new ModelAndView("user/create/createregistry");
	}
}
