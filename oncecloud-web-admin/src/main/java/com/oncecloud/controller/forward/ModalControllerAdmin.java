package com.oncecloud.controller.forward;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.oncecloud.entity.User;
import com.oncecloud.model.CommonModifyModel;


@RequestMapping("admin/modal")
@Controller
public class ModalControllerAdmin {

	@RequestMapping(value = "/modify")
	@ResponseBody
	public ModelAndView modify(HttpServletRequest request,CommonModifyModel commonModifyModel) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("modifyType", commonModifyModel.getModifyType());
		model.put("modifyUuid", commonModifyModel.getModifyUuid());
		model.put("modifyName", commonModifyModel.getModifyName());
		String desc = commonModifyModel.getModifyDesc();
		model.put("modifyDesc", desc.equals("&nbsp;") ? "" : desc);
		return new ModelAndView("admin/modal/modify", model);
	}
	
	@RequestMapping(value = "/addtopool")
	public ModelAndView addtopool(HttpServletRequest request,
			@RequestParam String uuidjsonstr, @RequestParam String hosttype) {
		request.setAttribute("uuidjsonstr", uuidjsonstr);
		request.setAttribute("hostype", hosttype);
		return new ModelAndView("admin/modal/addtopool");
	}

	@RequestMapping(value = "/addtohost")
	public ModelAndView addtohost(HttpServletRequest request) {
		return new ModelAndView("admin/modal/addtohost");
	}

	@RequestMapping(value = "/recover")
	public ModelAndView recover(HttpServletRequest request) {
		return new ModelAndView("admin/modal/recover");
	}
	
	@RequestMapping(value = "/register")
	public ModelAndView register(HttpServletRequest request) {
		return new ModelAndView("admin/modal/register");
	}
	
	@RequestMapping(value = "/joinvlan")
	public ModelAndView joinVlan(HttpServletRequest request) {
		return new ModelAndView("admin/modal/joinvlan");
	}

	@RequestMapping(value = "/migration")
	public ModelAndView migration(HttpServletRequest request, @RequestParam String type) {
		request.setAttribute("type", type);
		return new ModelAndView("admin/modal/migration");
	}
	
	@RequestMapping(value = "/limitdisk")
	public ModelAndView limitdisk(HttpServletRequest request) {
		return new ModelAndView("admin/modal/limitdisk");
	}
	
	@RequestMapping(value = "/vmtoimage")
	public ModelAndView vmToImage(HttpServletRequest request) {
		return new ModelAndView("admin/modal/vmtoimage");
	}
	
	@RequestMapping(value = "/storageofhost")
	public ModelAndView storageofhost(HttpServletRequest request,
			@RequestParam String hostuuid, @RequestParam String hostname) {
		request.setAttribute("hostuuid", hostuuid);
		request.setAttribute("hostname", hostname);
		return new ModelAndView("admin/modal/storageofhost");
	}

	@RequestMapping(value = "/modifynetwork")
	public ModelAndView modifynetwork(HttpServletRequest request,
			@RequestParam String uuid, @RequestParam String type,
			@RequestParam String isrun) {
		request.setAttribute("uuid", uuid);
		request.setAttribute("type", type);
		request.setAttribute("isrun", isrun);
		return new ModelAndView("admin/modal/modifynetwork");
	}
	@RequestMapping(value = "/distributeVm2User")
	public ModelAndView distributeVm2User(HttpServletRequest request,
			@RequestParam String vmuuid, @RequestParam String userName,String vmUid) {
		request.setAttribute("vmuuid", vmuuid);
		request.setAttribute("userName", userName);
		request.setAttribute("vmUid", vmUid);
		return new ModelAndView("admin/modal/distributeVm");
	}
	@RequestMapping(value = "/host/modify", method = { RequestMethod.GET })
	public ModelAndView createHost(HttpServletRequest request) {
		return new ModelAndView("admin/modal/modifyhost");
	}
	
	@RequestMapping(value = "/host/power")
	public ModelAndView power(HttpServletRequest request) {
		return new ModelAndView("admin/modal/power");
	}
	
	@RequestMapping(value = "/pool/hamanager")
	public ModelAndView hamanager(HttpServletRequest request) {
		return new ModelAndView("admin/modal/hamanager");
	}
	
	@RequestMapping(value = "/ha/monitor")
	public ModelAndView monitor(HttpServletRequest request){
		return new ModelAndView("admin/modal/monitor");
	}
	
	@RequestMapping(value = "/ha/access")
	public ModelAndView access(HttpServletRequest request){
		return new ModelAndView("admin/modal/access");
	}

	@RequestMapping(value = "/ha/migratehost")
	public ModelAndView migratehost(HttpServletRequest request){
		return new ModelAndView("admin/modal/migratehost");
	}
	
	@RequestMapping(value = "/pool/accordance")
	public ModelAndView accordance(HttpServletRequest request) {
		return new ModelAndView("admin/modal/accordance");
	}
	
	@RequestMapping(value = "/role/setauth")
	public ModelAndView setauth(HttpServletRequest request) {
		return new ModelAndView("admin/modal/setauth");
	}
	
	@RequestMapping(value = "/iso/upfile")
	public ModelAndView upfile(HttpServletRequest request) {
		return new ModelAndView("admin/modal/upfile");
	}
	
	@RequestMapping(value = "/limitnetwork")
	public ModelAndView limitnetwork(HttpServletRequest request) {
		return new ModelAndView("admin/modal/limitnetwork");
	}
	
	@RequestMapping(value = "/modifyuserinfo")
	public ModelAndView modifyUserinfo(HttpServletRequest request) {
		return new ModelAndView("admin/modal/modifyuserinfo");
	}
	
	//
	@RequestMapping(value = "/modifyArea")
	public ModelAndView modifyUpdate(HttpServletRequest request) {
		return new ModelAndView("admin/modal/modifyArea");
	}
	
	@RequestMapping(value = "/modifyprice")
	public ModelAndView modifyPrinceInfo(HttpServletRequest request) {
		return new ModelAndView("admin/modal/modifyprice");
	}
	
	@RequestMapping(value = "/modifyadmin")
	public ModelAndView modifyUserInfo(HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user");
		request.setAttribute("username", user.getUserName());
		request.setAttribute("usermail", user.getUserMail());
		request.setAttribute("userphone", user.getUserPhone());
		return new ModelAndView("admin/modal/modifyuserinfo");
	}
	
	@RequestMapping(value = "/modifycost")
	public ModelAndView modifyCost(HttpServletRequest request) {
		request.setAttribute("type", request.getParameter("type"));
		int type=Integer.valueOf((String)request.getParameter("type"));
		if(type==3){
			return new ModelAndView("admin/modal/modifycostdetail");
		}else{
			return new ModelAndView("admin/modal/modifycost");
		}
	}
	
	@RequestMapping(value = "/imageUpdate")
	@ResponseBody
	public ModelAndView updateImage(HttpServletRequest request) {
		return new ModelAndView("admin/modal/imageupdate");
	}
	
	@RequestMapping(value = "/imageShare")
	@ResponseBody
	public ModelAndView shareImage(HttpServletRequest request) {
		return new ModelAndView("admin/modal/imageshare");
	}
	@RequestMapping(value = "/createMap")
	@ResponseBody
	public ModelAndView createMap(HttpServletRequest request) {
		return new ModelAndView("admin/modal/createmap");
	}
	
	@RequestMapping(value = "/linkrouter", method = { RequestMethod.GET })
	public ModelAndView linkRouterToVnet(HttpServletRequest request) {
		return new ModelAndView("admin/modal/addtorouter");
	}
	
	@RequestMapping(value = "/instanceAdd2Router", method = { RequestMethod.GET })
	public ModelAndView instanceAdd2Router(HttpServletRequest request) {
		return new ModelAndView("admin/modal/instanceAdd2Router");
	}
	
	@RequestMapping(value = "/modifyUSB")
	public ModelAndView modifyUSB(HttpServletRequest request,
			@RequestParam String uuid, @RequestParam String type) {
		request.setAttribute("uuid", uuid);
		request.setAttribute("type", type);
		return new ModelAndView("admin/modal/modifyUSB");
	}
}
