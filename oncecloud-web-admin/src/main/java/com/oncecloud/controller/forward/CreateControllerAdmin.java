package com.oncecloud.controller.forward;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CreateControllerAdmin {

	@RequestMapping(value = "user/create", method = { RequestMethod.GET })
	public ModelAndView createUser(HttpServletRequest request) {
		return new ModelAndView("admin/create/createuser");
	}

	@RequestMapping(value = "area/create", method = { RequestMethod.GET })
	public ModelAndView createArea(HttpServletRequest request) {
		return new ModelAndView("admin/create/createarea");
	}
	
	@RequestMapping(value = "storage/create", method = { RequestMethod.GET })
	public ModelAndView createStorage(HttpServletRequest request) {
		return new ModelAndView("admin/create/createstorage");
	}

	@RequestMapping(value = "pool/create", method = { RequestMethod.GET })
	public ModelAndView createPool(HttpServletRequest request) {
		return new ModelAndView("admin/create/createpool");
	}

	@RequestMapping(value = "host/create", method = { RequestMethod.GET })
	public ModelAndView createHost(HttpServletRequest request) {
		return new ModelAndView("admin/create/createhost");
	}

	@RequestMapping(value = "datacenter/create", method = { RequestMethod.GET })
	public ModelAndView createDatecenter(HttpServletRequest request) {
		return new ModelAndView("admin/create/createdatacenter");
	}

	@RequestMapping(value = "address/create", method = { RequestMethod.GET })
	public ModelAndView createAddress(HttpServletRequest request) {
		return new ModelAndView("admin/create/createaddress");
	}

	@RequestMapping(value = "image/create", method = { RequestMethod.GET })
	public ModelAndView createImage(HttpServletRequest request) {
		return new ModelAndView("admin/create/createimage");
	}

	@RequestMapping(value = "instanceiso/create", method = { RequestMethod.GET })
	public ModelAndView createInstanceWithISO(HttpServletRequest request) {
		return new ModelAndView("admin/create/createinstancewithiso");
	}

	@RequestMapping(value = "mac/create", method = { RequestMethod.GET })
	public ModelAndView createmac(HttpServletRequest request) {
		return new ModelAndView("admin/create/createmac");
	}
	
	@RequestMapping(value = "role/create", method = { RequestMethod.GET })
	public ModelAndView createRole(HttpServletRequest request) {
		return new ModelAndView("admin/create/createrole");
	}
	
	@RequestMapping(value = "network/create", method = { RequestMethod.GET })
	public ModelAndView createswitch(HttpServletRequest request) {
		return new ModelAndView("admin/create/createswitch");
	}
	
	@RequestMapping(value = "account/create", method = { RequestMethod.GET })
	public ModelAndView createCost(HttpServletRequest request) {
		return new ModelAndView("admin/create/createcost");
	}
	
	@RequestMapping(value = "/router/create", method = { RequestMethod.GET })
	public ModelAndView createrouter(HttpServletRequest request) {
		return new ModelAndView("admin/create/createrouter");
	}
	
	@RequestMapping(value = "/information/create", method = { RequestMethod.GET })
	public ModelAndView createinformation(HttpServletRequest request) {
		return new ModelAndView("admin/create/createhostinformation");
	}
	
	@RequestMapping(value = "usb/create", method = { RequestMethod.POST })
	public ModelAndView createUsb(HttpServletRequest request, @RequestParam String uuid,
			@RequestParam String type) {
		request.setAttribute("uuid", uuid);
		request.setAttribute("type", type);
		return new ModelAndView("admin/create/createusb");
	}
}
