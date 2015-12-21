/**
 * @author lining
 * */
package com.oncecloud.controller.action;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oncecloud.manager.HaManager;
import com.oncecloud.manager.PoolManager;
import com.oncecloud.entity.OCHa;
import com.oncecloud.entity.User;
import com.oncecloud.model.ListModel;

@RequestMapping(value="HaAction")
@Controller
public class HaAction {
	
	@Resource
	private HaManager haManager;
	
	@Resource
	private PoolManager poolManager;
	
	@RequestMapping(value = "/PoolList", method = { RequestMethod.GET })
	@ResponseBody
	public String poolList(HttpServletRequest request, ListModel list) {
		JSONArray ja = null;
		try {
			ja = haManager.getPoolList(list.getPage(),
					list.getLimit(), list.getSearch());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ja.toString();
	}
	
	@RequestMapping(value = "/HostList", method = { RequestMethod.GET })
	@ResponseBody
	public String hostList(HttpServletRequest request, @RequestParam String poolUuid) {
		JSONArray ja = null;
		try {
			ja = haManager.getMigrateHostList(poolUuid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ja.toString();
	}
	
	@RequestMapping(value = "/PoolHa", method = {RequestMethod.POST })
	@ResponseBody
	public String PoolHa(HttpServletRequest request, @RequestParam String poolUuid) {
		try {
			return haManager.getPoolHa(poolUuid).toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	@RequestMapping(value = "/PoolPower", method = {RequestMethod.POST })
	@ResponseBody
	public String poolPower(HttpServletRequest request, @RequestParam String poolUuid) {
		User user = (User) request.getSession().getAttribute("user");
		try {
			return haManager.getPowerInfosOfPool(user.getUserId(), poolUuid).toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}
	
	
	@RequestMapping(value = "/StartHa", method = {RequestMethod.POST })
	@ResponseBody
	public String StartHa(HttpServletRequest request, @RequestParam String poolUuid,@RequestParam String masterIP,@RequestParam String haPath) {
		String result ="失败";
		User user = (User) request.getSession().getAttribute("user");
		try {
			boolean flag = haManager.startHaService(user.getUserId(), poolUuid, haPath, masterIP);
			if (flag) {
				result = "成功";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result ;
		 
	}
	
	@RequestMapping(value = "/UpdateHa", method = {RequestMethod.POST})
	@ResponseBody
	public String updateHa(HttpServletRequest request, String pool_uuid, String access_control_flag,String left_host,String slot_policy,
							String slot_cpu, String slot_memory, String cpu_percent, String memory_percent, String migratory_host_uuid){
		String result = "失败";
		OCHa ha = new OCHa();
		ha.setPoolUuid(pool_uuid);
		ha.setStartDate(new Date());
		if (access_control_flag != null && !"".equals(access_control_flag)) {
			ha.setAccessControlPolicy(Integer.parseInt(access_control_flag));
		}else {
			ha.setAccessControlPolicy(0);
		}
		if (left_host != null && !"".equals(left_host)) {
			ha.setLeftHost(Integer.parseInt(left_host));
		}else {
			ha.setLeftHost(0);
		}
		if (slot_policy != null && !"".equals(slot_policy)) {
			ha.setSlotPolicy(Integer.parseInt(slot_policy));
		}else {
			ha.setSlotPolicy(0);
		}
		if (slot_cpu != null && !"".equals(slot_cpu)) {
			ha.setSlotCpu(Integer.parseInt(slot_cpu));
		}else {
			ha.setSlotCpu(0);
		}
		if (slot_memory != null && !"".equals(slot_memory)) {
			ha.setSlotMemory(Integer.parseInt(slot_memory));
		}else {
			ha.setSlotMemory(0);
		}
		if (cpu_percent != null && !"".equals(cpu_percent)) {
			ha.setCpuPercent(Integer.parseInt(cpu_percent));
		}else {
			ha.setCpuPercent(0);
		}
		if (memory_percent != null && !"".equals(memory_percent)) {
			ha.setMemoryPercent(Integer.parseInt(memory_percent));
		}else {
			ha.setMemoryPercent(0);
		}
		if (migratory_host_uuid != null && !"".equals(migratory_host_uuid)){
			ha.setMigratoryHostUuid(migratory_host_uuid);
		}else {
			ha.setMigratoryHostUuid(null);
		}
		try {
			boolean flag = haManager.updateAccessPolicy(ha);
			if (flag) {
				result = "成功";
			}
		} catch (Exception e) {
			result = "失败";
			e.printStackTrace();
		}
		return result;
	}
	
	@RequestMapping(value = "/StopHa", method = {RequestMethod.POST })
	@ResponseBody
	public String StopHa(HttpServletRequest request, @RequestParam String poolUuid,@RequestParam String masterIP,@RequestParam String haPath) {
		String result = "失败";
		User user = (User) request.getSession().getAttribute("user");
		try {
			boolean flag = haManager.stopHAService(user.getUserId(), poolUuid, masterIP, haPath);
			if (flag) {
				result = "成功";
			}
		} catch (Exception e) {
			result = "失败";
			e.printStackTrace();
		}
		return result;
		 
	}
}
