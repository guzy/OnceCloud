/**
 * @author hty
 * @time 上午11:05:48
 * @date 2014年12月9日
 */
package com.beyondsphere.controller.action;

import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beyondsphere.manager.PowerManager;
import com.oncecloud.entity.Power;
import com.oncecloud.entity.User;


@RequestMapping("PowerAction")
@Controller
public class PowerAction {
	
    @Resource
    private PowerManager powerManager;
   
	@RequestMapping(value = "/PowerHost", method = { RequestMethod.GET })
	@ResponseBody
	public Power PowerHost(HttpServletRequest request, String hostid) {
		Power power = powerManager.getPower(hostid);
		if(power==null){
			power =new Power();
			power.setHostUuid(hostid);
			power.setPowerPort(8088);
			power.setPowerUuid(UUID.randomUUID().toString());
			power.setPowerValid(-1);
		}
		return power;
	}
	
	@RequestMapping(value = "/CheckPowerStatus", method = { RequestMethod.POST})
	@ResponseBody
	public int checkPowerStatus(HttpServletRequest request, String hostmotherip, int hostport, String hostusername, String hostpsw) {
		int powerstatus = powerManager.getStatusOfPower(hostmotherip, hostport, hostusername, hostpsw);
		return powerstatus;
	}
	
	/*@RequestMapping(value = "/PowerStatus", method = { RequestMethod.POST })
	@ResponseBody
	public int PowerStatus(HttpServletRequest request, String powerid,String hostid,String hostip,int hostport,String hostusername,String hostpsw) {
		//int powerstatus
		int powerstatus = powerManager.getStatusOfPower(hostip, hostport, hostusername, hostpsw);
		User user = (User) request.getSession().getAttribute("user");
		powerManager.savePower(user.getUserId(), powerid, hostid, hostip, hostport, hostusername, hostpsw, powerstatus>0?1:0);
		return powerstatus;
	}*/
	
	@RequestMapping(value = "/savePowerStatus", method = { RequestMethod.POST })
	@ResponseBody
	public int savePowerStatus(HttpServletRequest request, String powerid,String hostid,String hostip,int hostport,String hostusername,String hostpsw, int powerstatus) {
		User user = (User) request.getSession().getAttribute("user");
		powerManager.savePower(user.getUserId(), powerid, hostid, hostip, hostport, hostusername, hostpsw, powerstatus>0?1:0);
		return powerstatus;
	}
	
	@RequestMapping(value = "/StartPower", method = { RequestMethod.POST })
	@ResponseBody
	public boolean StartPower(HttpServletRequest request, String hostip, int hostport,String hostusername,String hostpsw) {
		User user = (User) request.getSession().getAttribute("user");
		return powerManager.startPower(user.getUserId(), hostip, hostport, hostusername, hostpsw);
	}
	
	@RequestMapping(value = "/StartPowerByhostUuid", method = { RequestMethod.POST })
	@ResponseBody
	public boolean StartPower(HttpServletRequest request, String hostUuid) {
		User user = (User) request.getSession().getAttribute("user");
		return powerManager.startPower(user.getUserId(), hostUuid);
	}
	
	@RequestMapping(value = "/ShutDownPowerByhostUuid", method = { RequestMethod.POST })
	@ResponseBody
	public boolean ShutDownPower(HttpServletRequest request, String hostUuid) {
		User user = (User) request.getSession().getAttribute("user");
		return powerManager.stopPower(user.getUserId(), hostUuid);
	}
	
	@RequestMapping(value = "/ShutDownPower", method = { RequestMethod.POST })
	@ResponseBody
	public boolean ShutDownPower(HttpServletRequest request, String hostip, int hostport,String hostusername,String hostpsw) {
		User user = (User) request.getSession().getAttribute("user");
		return powerManager.stopPower(user.getUserId(), hostip, hostport, hostusername, hostpsw);
	}

}
