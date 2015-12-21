/**
 * @author hty
 * @time 上午10:51:27
 * @date 2014年12月10日
 */
package com.beyondsphere.controller.forward;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.beyondsphere.model.CommonModifyModel;
import com.beyondsphere.model.GroupModel;


@Controller
public class ModalController {

	@RequestMapping(value = "/user/modal/adjust")
	public ModelAndView adjust(HttpServletRequest request) {
		return new ModelAndView("user/modal/adjustVM");
	}
	
	@RequestMapping(value = "/user/modal/adjustc")
	public ModelAndView adjustc(HttpServletRequest request) {
		return new ModelAndView("user/modal/adjustContainer");
	}
	
	@RequestMapping(value = "/user/modal/addreg")
	public ModelAndView addreg(HttpServletRequest request) {
		return new ModelAndView("user/modal/addregistry");
	}
	
	@RequestMapping(value = "/user/modal/bindalarm")
	public ModelAndView bindAlarm(HttpServletRequest request) {
		String uuid = request.getParameter("uuid");
		request.setAttribute("showid", "al-" + uuid.substring(0, 8));
		return new ModelAndView("user/modal/bindalarm");
	}
	
	@RequestMapping(value = "/user/modal/bindgroup")
	public ModelAndView bindGroup(HttpServletRequest request) {
		String uuid = request.getParameter("uuid");
		request.setAttribute("showid", "gl-" + uuid.substring(0, 8));
		request.setAttribute("groupUuid", uuid);
		return new ModelAndView("user/modal/bindgroup");
	}
	
	@RequestMapping(value = "/user/modal/modifyalarm")
	public ModelAndView modifyAlarm(HttpServletRequest request,@RequestParam String modifyUuid,
			@RequestParam String modifyName, @RequestParam String modifyDesc) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("modifyUuid", modifyUuid);
		model.put("modifyName", modifyName);
		model.put("modifyDesc", modifyDesc.equals("&nbsp;") ? "" : modifyDesc);
		return new ModelAndView("user/modal/modifyalarm",model);
	}
	
	@RequestMapping(value = "user/modal/modifyalarmrule")
	public ModelAndView modifyAlarmRule(HttpServletRequest request,
		    @RequestParam String ruleId,
			@RequestParam String ruleName, @RequestParam String ruleterm,
			@RequestParam String rulethre,@RequestParam String unit) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("ruleId", ruleId);
		model.put("ruleName", ruleName);
		model.put("ruleterm", ruleterm);
		model.put("rulethre", rulethre);
		model.put("unit", unit);
		return new ModelAndView("user/modal/modifyalarmrule", model);
	}
	
	@RequestMapping(value = "/volume/bind", method = { RequestMethod.GET })
	public ModelAndView bindVolume(HttpServletRequest request) {
		return new ModelAndView("user/modal/bindvolume");
	}
	
	@RequestMapping(value = "user/modal/mountvolume")
	public ModelAndView mountVolume(HttpServletRequest request){
		return new ModelAndView("user/modal/mountvolume");
	}
	
	@RequestMapping(value = "user/modal/mountVmip")
	public ModelAndView mountVmip(HttpServletRequest request, CommonModifyModel commonModifyModel ){
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("modifyUuid", commonModifyModel.getModifyUuid());
		return new ModelAndView("user/modal/mountVmip", model);
	}
	
	@RequestMapping(value = "user/modal/logConfig")
	public ModelAndView logConfig(HttpServletRequest request){
		return new ModelAndView("user/modal/logConfig");
	}
	
	@RequestMapping(value = "user/modal/modifyUserinfo")
	public ModelAndView modifyUserinfo(HttpServletRequest request){
		return new ModelAndView("user/modal/modifyUserinfo");
	}
	
	@RequestMapping(value = "user/modal/modify")
	@ResponseBody
	public ModelAndView modify(HttpServletRequest request,CommonModifyModel commonModifyModel) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("modifyType", commonModifyModel.getModifyType());
		model.put("modifyUuid", commonModifyModel.getModifyUuid());
		model.put("modifyName", commonModifyModel.getModifyName());
		String desc = commonModifyModel.getModifyDesc();
		model.put("modifyDesc", desc.equals("&nbsp;") ? "" : desc);
		return new ModelAndView("user/modal/modify", model);
	}
	
	@RequestMapping(value = "user/modal/modifygroup")
	@ResponseBody
	public ModelAndView modifyGroup(HttpServletRequest request,GroupModel groupModel) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("groupUuid", groupModel.getGroupUuid());
		return new ModelAndView("user/modal/modifygroup", model);
	}
	
	@RequestMapping(value = "user/modal/addtocluster")
	public ModelAndView addtopool(HttpServletRequest request,
			@RequestParam String uuidjsonstr, @RequestParam String hosttype) {
		request.setAttribute("uuidjsonstr", uuidjsonstr);
		request.setAttribute("hostype", hosttype);
		return new ModelAndView("user/modal/addtocluster");
	}
	
	@RequestMapping(value = "user/modal/createtemplate")
	public ModelAndView upfile(HttpServletRequest request) {
		return new ModelAndView("user/modal/createtemplate");
	}
	
	@RequestMapping(value = "deploy/skipDeploy")
	public ModelAndView deploy(HttpServletRequest request){
		return new ModelAndView("user/deployArrange");
	}
	@RequestMapping(value = "deploy/modelParams")
	public ModelAndView deployModel(HttpServletRequest request){
		return new ModelAndView("user/deployModel");
	}
	
	@RequestMapping(value = "user/modal/updateVMsip")
	public ModelAndView updateVMsip(HttpServletRequest request,String vmids){
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("modifyUuids",vmids);
		return new ModelAndView("user/modal/updateVMsip", model);
	}
	
}
