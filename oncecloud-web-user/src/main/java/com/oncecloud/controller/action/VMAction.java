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

import com.oncecloud.entity.OCVM;
import com.oncecloud.entity.OCVMip;
import com.oncecloud.entity.User;
import com.oncecloud.model.CreateVMModel;
import com.oncecloud.model.ListModel;
import com.oncecloud.model.StatisticsType;
import com.oncecloud.query.QueryList;
import com.oncecloud.service.VMService;
import com.oncecloud.service.VMipService;
import com.oncecloud.utils.Dispatch;

@RequestMapping("/VMAction")
@Controller
public class VMAction {

	@Resource
	private QueryList queryList;
	@Resource
	private Dispatch dispatch;
	@Resource
	private VMService vmService;
	@Resource
	private VMipService vmipService;

	@RequestMapping(value = "/VMList", method = { RequestMethod.GET })
	@ResponseBody
	public String vmList(HttpServletRequest request, ListModel list) {
		User user = (User) request.getSession().getAttribute("user");
		int userId = user.getUserId();
		JSONArray ja = queryList.queryVMList(list.getPage(),
				list.getLimit(), list.getSearch(), list.getGroupUuid(), userId);
		return ja.toString();
	}

	@RequestMapping(value = "/VMListOfVolume", method = { RequestMethod.GET })
	@ResponseBody
	public String vmListOfVolume(HttpServletRequest request, ListModel list) {
		User user = (User) request.getSession().getAttribute("user");
		JSONArray ja = queryList.queryVMListOfVolume(user.getUserId(), list.getPage(),
				list.getLimit(), list.getSearch());
		return ja.toString();
	}

	@RequestMapping(value = "/CreateVM", method = { RequestMethod.POST })
	@ResponseBody
	public void createVM(HttpServletRequest request, CreateVMModel createvmModel) {
		User user = (User) request.getSession().getAttribute("user");
		JSONObject jo = new JSONObject();
		jo.put("Handler", "CreateVMHandler");
		JSONArray ja = new JSONArray();
		JSONObject params = new JSONObject();
		params.put("uuid", createvmModel.getVmUuid());
		params.put("tplUuid", createvmModel.getImageUuid());
		params.put("userId", user.getUserId());
		params.put("cpu", createvmModel.getCpu());
		params.put("memory", createvmModel.getMemory());
		params.put("pwd", createvmModel.getPassword());
		params.put("name", createvmModel.getVmName());
		params.put("statistics", StatisticsType.INSTANCE.ordinal());
		ja.put(0, params);
		jo.put("Params", ja);
		dispatch.post(jo);
	}

	@RequestMapping(value = "/ShutdownVM", method = { RequestMethod.GET })
	@ResponseBody
	public void shutdownVM(HttpServletRequest request,
			@RequestParam String uuid, @RequestParam String force) {
		User user = (User) request.getSession().getAttribute("user");
		JSONObject jo = new JSONObject();
		jo.put("Handler", "ShutdownVMHandler");
		JSONArray ja = new JSONArray();
		JSONObject params = new JSONObject();
		params.put("uuid", uuid);
		params.put("force", force);
		params.put("userId", user.getUserId());
		ja.put(0, params);
		jo.put("Params", ja);
		dispatch.post(jo);
	}
	
	@RequestMapping(value = "/StartVM", method = { RequestMethod.GET })
	@ResponseBody
	public void startVM(HttpServletRequest request, @RequestParam String uuid) {
		User user = (User) request.getSession().getAttribute("user");
		JSONObject jo = new JSONObject();
		jo.put("Handler", "StartVMHandler");
		JSONArray ja = new JSONArray();
		JSONObject params = new JSONObject();
		params.put("uuid", uuid);
		params.put("userId", user.getUserId());
		ja.put(0, params);
		jo.put("Params", ja);
		dispatch.post(jo);
	}
	
	@RequestMapping(value = "/RestartVM", method = { RequestMethod.GET })
	@ResponseBody
	public void restartVM(HttpServletRequest request, @RequestParam String uuid) {
		User user = (User) request.getSession().getAttribute("user");
		JSONObject jo = new JSONObject();
		jo.put("Handler", "RestartVMHandler");
		JSONArray ja = new JSONArray();
		JSONObject params = new JSONObject();
		params.put("uuid", uuid);
		params.put("userId", user.getUserId());
		ja.put(0, params);
		jo.put("Params", ja);
		dispatch.post(jo);
	}

	@RequestMapping(value = "/DeleteVM", method = { RequestMethod.GET })
	@ResponseBody
	public void deleteVM(HttpServletRequest request, @RequestParam String uuid) {
		User user = (User) request.getSession().getAttribute("user");
		JSONObject jo = new JSONObject();
		jo.put("Handler", "DeleteVMHandler");
		JSONArray ja = new JSONArray();
		JSONObject params = new JSONObject();
		params.put("uuid", uuid);
		params.put("userId", user.getUserId());
		ja.put(0, params);
		jo.put("Params", ja);
		dispatch.post(jo);
	}
	
	@RequestMapping(value = "/VMAdjust", method = { RequestMethod.POST })
	@ResponseBody
	public void adjustVM(HttpServletRequest request,
			@RequestParam String uuid, @RequestParam int mem,
			@RequestParam int cpu, @RequestParam String content,
			@RequestParam String conid) {
		User user = (User) request.getSession().getAttribute("user");
		JSONObject jo = new JSONObject();
		jo.put("Handler", "AdjustVMHandler");
		JSONArray ja = new JSONArray();
		JSONObject params = new JSONObject();
		params.put("uuid", uuid);
		params.put("userId", user.getUserId());
		params.put("cpu", cpu);
		params.put("mem", mem);
		params.put("content", content);
		params.put("conid", conid);
		ja.put(0, params);
		jo.put("Params", ja);
		dispatch.post(jo);
	}
	
	@RequestMapping(value = "/CheckVMStatus", method = { RequestMethod.POST })
	@ResponseBody
	public void checkVMStatus(HttpServletRequest request, String vmUuid){
		User user = (User) request.getSession().getAttribute("user");
		vmService.checkVMStatus(user.getUserId(), vmUuid);
	}
	
	@RequestMapping(value = "/VMDetail", method = { RequestMethod.GET })
	@ResponseBody
	public String vmDetail(HttpServletRequest request, @RequestParam String uuid) {
		JSONObject jo = vmService.getVMDetail(uuid);
		return jo.toString();
	}
	
	@RequestMapping(value = "/MountVmip", method = { RequestMethod.GET})
	@ResponseBody
	public String mounVmip(HttpServletRequest request, @RequestParam String uuid,
					@RequestParam String vmip,@RequestParam String vmnetmask,@RequestParam String vmgateway,
					@RequestParam String vmdns){
		OCVM vm = vmService.getOCVM(uuid);
		User user = (User)request.getSession().getAttribute("user");
		JSONObject jo = new JSONObject();
		if (vm != null) {
			OCVMip vMip = new OCVMip();
			vMip.setVmIp(vmip);
			vMip.setVmMac(vm.getVmMac());
			vMip.setVmVlan(Integer.parseInt(vm.getVmVlan().split(",")[0]));
			vMip.setVmNetmask(vmnetmask);
			vMip.setVmGateway(vmgateway);
			vMip.setVmDns(vmdns);
			jo = vmipService.changeVMip(user.getUserId(), vMip,uuid);
		}
		return jo.toString();
	}
	
	@RequestMapping(value = "/updateVmips", method = { RequestMethod.POST})
	@ResponseBody
	public String updateVmips(HttpServletRequest request, @RequestParam String uuids,
					@RequestParam String vmip,@RequestParam String vmnetmask,@RequestParam String vmgateway,
					@RequestParam String vmdns,@RequestParam int ipstart,@RequestParam int ipend){
		boolean flag=true;
		
		User user = (User)request.getSession().getAttribute("user");
		String[] uuidstr=uuids.split(",");
		OCVM vm=new OCVM();
		JSONObject jo = new JSONObject();
		for (String uuid : uuidstr) {
			vm= vmService.getOCVM(uuid);
			if (vm != null) {
				OCVMip vMip = new OCVMip();
				vMip.setVmIp(vmip+"."+ipstart);
				vMip.setVmMac(vm.getVmMac());
				vMip.setVmVlan(Integer.parseInt(vm.getVmVlan().split(",")[0]));
				vMip.setVmNetmask(vmnetmask);
				vMip.setVmGateway(vmgateway);
				vMip.setVmDns(vmdns);
				jo = vmipService.changeVMip(user.getUserId(), vMip,uuid);
				
				ipstart++;
				boolean bo=jo.getBoolean("result");
				if(!bo){
					flag=false;
				}
			}
		}
		 
		return String.valueOf(flag);
	}
}
