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

import com.oncecloud.manager.VMManager;
import com.oncecloud.entity.User;
import com.oncecloud.model.AdminListModel;
import com.oncecloud.model.SwitchType;

@RequestMapping("VMAction")
@Controller
public class VMAction {
	
	@Resource
	private VMManager vmManager;

	@RequestMapping(value = "/AdminList", method = { RequestMethod.GET })
	@ResponseBody
	public String adminList(HttpServletRequest request, AdminListModel alrModel) {
		JSONArray ja = vmManager.getAdminVMList(alrModel.getPage(),
				alrModel.getLimit(), alrModel.getHost(),
				alrModel.getImportance(), alrModel.getUserid(), alrModel.getType());
		return ja.toString();
	}

	@RequestMapping(value ="/RestartHostVm", method = { RequestMethod.GET })
	@ResponseBody
	public String RestartHostVm(HttpServletRequest request,
			@RequestParam String uuid, @RequestParam String force) {
		User user = (User) request.getSession().getAttribute("user");
		int userId = user.getUserId();
		vmManager.doRestartHostVm(userId, uuid, force);
		
		
		return "restartVM";
	}
	@RequestMapping(value ="/AdminStopHost", method = { RequestMethod.GET })
	@ResponseBody
	public String vmAdminStopHost(HttpServletRequest request,
			@RequestParam String uuid, @RequestParam String force) {
		User user = (User) request.getSession().getAttribute("user");
		int userId = user.getUserId();
		vmManager.doAdminStopHost(userId, uuid, force);
		
		return "pauseVM";
	}
	@RequestMapping(value = "/AdminStartUp", method = { RequestMethod.GET })
	@ResponseBody
	public String vmAdminStartUp(HttpServletRequest request,
			@RequestParam String uuid) {
		User user = (User) request.getSession().getAttribute("user");
		
		int userId = user.getUserId();
		boolean result=vmManager.doAdminStartVm(userId, uuid);
		
		return String.valueOf(result);
	}

	@RequestMapping(value = "/AdminShutDown", method = { RequestMethod.GET })
	@ResponseBody
	public String vmAdminShutDown(HttpServletRequest request,
			@RequestParam String uuid, @RequestParam String force) {
		User user = (User) request.getSession().getAttribute("user");
		int userId = user.getUserId();
		vmManager.doAdminShutDown(userId, uuid, force);
		
		return "shutdownVM";
	}

	@RequestMapping(value = "/AdminDestoryVM", method = { RequestMethod.GET })
	@ResponseBody
	public void vmAdminDestory(HttpServletRequest request,
			@RequestParam String uuid) {
		User user = (User) request.getSession().getAttribute("user");
		int userId = user.getUserId();
		vmManager.adminDestoryVM(userId, uuid);
	}

	@RequestMapping(value = "/ISOList", method = { RequestMethod.POST })
	@ResponseBody
	public String isoList(HttpServletRequest request,
			@RequestParam String poolUuid) {
		JSONArray ja = vmManager.getISOList(poolUuid);
		return ja.toString();
	}

	@RequestMapping(value = "/ISOCreate", method = { RequestMethod.POST })
	@ResponseBody
	public void createWithISO(HttpServletRequest request,
			@RequestParam String pooluuid, @RequestParam int cpu,
			@RequestParam int memory, @RequestParam String vmName,
			@RequestParam String isouuid, @RequestParam int isotype,
			@RequestParam String vmUuid, @RequestParam String diskuuid, 
			@RequestParam int volum) {
		User user = (User) request.getSession().getAttribute("user");
		vmManager.createVMByISO(vmUuid, isouuid,isotype, diskuuid, vmName,
				cpu, memory, volum, pooluuid, user.getUserId());
	}

	@RequestMapping(value = "/SaveToDataBase", method = { RequestMethod.POST })
	@ResponseBody
	public void saveToDataBase(HttpServletRequest request,
			@RequestParam String vmUuid, @RequestParam String vmPWD,
			@RequestParam int vmPlatform, @RequestParam String vmName,
			@RequestParam String vmIP, @RequestParam int vmUID) {
		vmManager.saveToDataBase(vmUuid, vmPWD, vmUID, vmPlatform,
				vmName, vmIP);
	}

	@RequestMapping(value = "/MacList", method = { RequestMethod.POST })
	@ResponseBody
	public String getMacList(HttpServletRequest request,
			@RequestParam String uuid, @RequestParam String type) {
		JSONArray ja = vmManager.getMacs(uuid, type);
		return ja.toString();
	}

	@RequestMapping(value = "/NetList", method = { RequestMethod.POST })
	@ResponseBody
	public String getNetList(HttpServletRequest request,
			@RequestParam String uuid, @RequestParam String type) {
		JSONArray ja = vmManager.getNets(uuid, type);
		return ja.toString();
	}

	@RequestMapping(value = "/AddMac", method = { RequestMethod.POST })
	@ResponseBody
	public String addMac(HttpServletRequest request, @RequestParam String uuid,
			@RequestParam String type, @RequestParam String vnetid) {
		JSONObject jo = new JSONObject();
		jo.put("result",
				vmManager.addMac(uuid, type, SwitchType.SWITCH, vnetid));
		return jo.toString();
	}

	@RequestMapping(value = "/DeleteMac", method = { RequestMethod.POST })
	@ResponseBody
	public String deleteMac(HttpServletRequest request,
			@RequestParam String uuid, @RequestParam String type,
			@RequestParam String vifUuid) {
		JSONObject jo = new JSONObject();
		jo.put("result", vmManager.deleteMac(uuid, type, vifUuid));
		return jo.toString();
	}

	/*@RequestMapping(value = "/ModifyVnet", method = { RequestMethod.POST })
	@ResponseBody
	public String modifyVnet(HttpServletRequest request,
			@RequestParam String uuid, @RequestParam String type,
			@RequestParam String vifUuid, @RequestParam String vnetid) {
		JSONObject jo = new JSONObject();
		jo.put("result",
				vmManager.modifyPhysical(uuid, type, SwitchType.SWITCH,
						vifUuid));
		return jo.toString();
	}*/

	@RequestMapping(value = "/ModifyPhysical", method = { RequestMethod.POST })
	@ResponseBody
	public String modifyPhysical(HttpServletRequest request,
			@RequestParam String uuid, @RequestParam String type,
			@RequestParam String vifUuid) {
		JSONObject jo = new JSONObject();
		jo.put("result",
				vmManager.modifyPhysical(uuid, type, SwitchType.SWITCH,
						vifUuid));
		return jo.toString();
	}

	@RequestMapping(value = "/TemplateToVM", method = { RequestMethod.POST })
	@ResponseBody
	public String templateToVM(HttpServletRequest request,
			@RequestParam String uuid) {
		User user = (User) request.getSession().getAttribute("user");
		JSONObject jo = new JSONObject();
		jo.put("result",
				vmManager.templateToVM(uuid, user.getUserId()));
		return jo.toString();
	}

	@RequestMapping(value = "/VMToTemplate", method = { RequestMethod.POST })
	@ResponseBody
	public String vmToTemplate(HttpServletRequest request,
			@RequestParam String uuid) {
		User user = (User) request.getSession().getAttribute("user");
		JSONObject jo = new JSONObject();
		jo.put("result",
				vmManager.vmToTemplate(uuid, user.getUserId()));
		return jo.toString();
	}

	@RequestMapping(value = "/Migration", method = { RequestMethod.POST })
	@ResponseBody
	public String migration(HttpServletRequest request,
			@RequestParam String uuid, @RequestParam String tarHost,
			@RequestParam String content, @RequestParam String conid) {
		User user = (User) request.getSession().getAttribute("user");
		JSONObject jo = new JSONObject();
		jo.put("result",
				vmManager.migrate(user.getUserId(), uuid, tarHost,
						content, conid));
		return jo.toString();

	}
	
	@RequestMapping(value = "/OffMigration", method = { RequestMethod.POST })
	@ResponseBody
	public String offMigration(HttpServletRequest request,
			@RequestParam String uuid, @RequestParam String tarHost,
			@RequestParam String content, @RequestParam String conid) {
		User user = (User) request.getSession().getAttribute("user");
		JSONObject jo = new JSONObject();
		jo.put("result",
				vmManager.offmigrate(user.getUserId(), uuid, tarHost,
						content, conid));
		return jo.toString();

	}

	@RequestMapping(value = "/GetDiskLimit", method = { RequestMethod.POST })
	@ResponseBody
	public String getDiskLimit(HttpServletRequest request, @RequestParam String vmUuid) {
		JSONObject jo = new JSONObject();
		jo.put("readM", vmManager.getDiskLimit(vmUuid, "read", "MBps"));
		jo.put("readi", vmManager.getDiskLimit(vmUuid, "read", "iops"));
		jo.put("writeM", vmManager.getDiskLimit(vmUuid, "write", "MBps"));
		jo.put("writei", vmManager.getDiskLimit(vmUuid, "write", "iops"));
		return jo.toString();
	}
	
	@RequestMapping(value = "/LimitDisk", method = { RequestMethod.POST })
	@ResponseBody
	public void limitDisk(HttpServletRequest request, @RequestParam int speed,
			@RequestParam String type, @RequestParam String ioType, @RequestParam String vmUuid) {
		User user = (User) request.getSession().getAttribute("user");
		vmManager.limitDisk(vmUuid, speed, type, ioType, user.getUserId());
	}
	
	@RequestMapping(value = "/ClearLimit", method = { RequestMethod.POST })
	@ResponseBody
	public void clearLimit(HttpServletRequest request, @RequestParam String vmUuid) {
		User user = (User) request.getSession().getAttribute("user");
		int userId = user.getUserId();
		vmManager.clearLimit(vmUuid, userId);
	}

	@RequestMapping(value = "/LimitNetwork", method = { RequestMethod.POST })
	@ResponseBody
	public void LimitNetwork(HttpServletRequest request,
			@RequestParam int netSpeed, @RequestParam String netchoice,
			@RequestParam String vmUuid, @RequestParam String vifUuid) {
		User user = (User) request.getSession().getAttribute("user");
		vmManager.limitNetwork(vmUuid, vifUuid, netSpeed * 1024,
				netchoice, user.getUserId());
	}
	
	@RequestMapping(value = "/switchList", method = { RequestMethod.POST })
	@ResponseBody
	public String listSwitch(HttpServletRequest request, String vmid, int type){
		JSONArray ja = vmManager.getSwitchListByvm(vmid, type);
		return ja.toString();
	}
	
	@RequestMapping(value = "/joinVlan", method = { RequestMethod.POST })
	@ResponseBody
	public String joinVlan(HttpServletRequest request, String vmid, String vifid, int switchid, int vlanid, int type){
		JSONObject jo = new JSONObject();
		User user = (User) request.getSession().getAttribute("user");
		boolean result = vmManager.joinVlan(user.getUserId(), vmid, vifid, switchid, vlanid, type);
		if (result) {
			jo.put("result", "success");
		}else {
			jo.put("result", "false");
		}
		return jo.toString();
	}
	
	@RequestMapping(value = "/CheckVMStatus", method = { RequestMethod.POST })
	@ResponseBody
	public void checkVMStatus(HttpServletRequest request, String vmUuid){
		User user = (User) request.getSession().getAttribute("user");
		vmManager.checkVMStatus(user.getUserId(), vmUuid);
	}
	
	@RequestMapping(value="/Export", method = {RequestMethod.POST})
	@ResponseBody
	public String export(HttpServletRequest request){
		JSONObject jo =  vmManager.exportVM();
		return jo.toString();
	}
	
	@RequestMapping(value="/distributeVM2User", method = {RequestMethod.POST})
	@ResponseBody
	public String distributeVM2User(HttpServletRequest request,String vmuuid,int userid){
		JSONObject jo =  vmManager.distributeVM2User(vmuuid,userid);
		return jo.toString();
	}
	
	@RequestMapping(value="/add2Router", method = {RequestMethod.POST})
	@ResponseBody
	public String add2Router(HttpServletRequest request,String vmuuid,int netid,
			String routerid,String ip,String netmask,String gateway){
		boolean jo =  vmManager.add2Router(vmuuid,netid,routerid,ip,netmask,gateway);
		return String.valueOf(jo);
	}
	
	
	@RequestMapping(value = "/UsbList", method = { RequestMethod.POST })
	@ResponseBody
	public String getUsbList(HttpServletRequest request,
			@RequestParam String uuid, @RequestParam String type) {
		JSONArray ja = vmManager.getUsbList(uuid, type);
		return ja.toString();
	}
	
	
	@RequestMapping(value = "/createUSB", method = { RequestMethod.POST })
	@ResponseBody
	public String createUSB(HttpServletRequest request, @RequestParam String address,
			@RequestParam String type, @RequestParam String vmuuid) {
		JSONObject jo = new JSONObject();
		jo.put("result",vmManager.createUSB(vmuuid, type,address));
		return jo.toString();
	}
	
	@RequestMapping(value = "/hostUSBlist", method = { RequestMethod.GET })
	@ResponseBody
	public String hostUSBlist(HttpServletRequest request,@RequestParam String vmuuid,
			@RequestParam String type) {
		JSONArray jo = new JSONArray();
		jo=vmManager.hostUSBlist(vmuuid,type);
		return jo.toString();
	}
	
	@RequestMapping(value = "/DeleteUSB", method = { RequestMethod.POST })
	@ResponseBody
	public String DeleteUSB(HttpServletRequest request, @RequestParam String usbuuid,
			@RequestParam String type, @RequestParam String vmuuid) {
		JSONObject jo = new JSONObject();
		jo.put("result",vmManager.deleteUSB(vmuuid, type, usbuuid));
		return jo.toString();
	}
}