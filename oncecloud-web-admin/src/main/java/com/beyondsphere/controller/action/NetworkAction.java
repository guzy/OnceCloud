package com.beyondsphere.controller.action;


import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beyondsphere.manager.NetworkManager;
import com.oncecloud.entity.OCNetwork;
import com.oncecloud.entity.User;
import com.oncecloud.message.MessagePush;
import com.oncecloud.model.ListModel;
import com.oncecloud.model.SwitchType;
import com.oncecloud.util.TimeUtils;


@RequestMapping(value = "NetworkAction")
@Controller
public class NetworkAction {

	@Resource
	private NetworkManager networkManager;
	@Resource
	private MessagePush messagePush;
	/**
	 * @author lining
	 * @param page, limit search 
	 * 获取switch的列表
	 * @return
	 */
	@RequestMapping(value = "/NetworkList", method = { RequestMethod.GET })
	@ResponseBody
	public String networkList(HttpServletRequest request, ListModel list) {
		JSONArray ja = null;
		int type = SwitchType.GENERAL_SWITCH;
		if (Integer.parseInt(list.getType()) == 2) {
			type = SwitchType.DISTRIBUTED_SWITCH;
		}
		try {
			ja = networkManager.getOnePageListOfNetwork(list.getPage(),
					list.getLimit(), type);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ja.toString();
	}

	/**
	 * @author lining 获取资源池列表
	 * @return
	 */
	@RequestMapping(value = "/poolList", method = { RequestMethod.POST })
	@ResponseBody
	public String poolList(HttpServletRequest request) {
		JSONArray ja = null;
		try {
			ja = networkManager.getPool();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ja.toString();
	}

	/**
	 * @author lining
	 * @param 新增一个交换机
	 * @return
	 */
	@RequestMapping(value = "/create", method = { RequestMethod.POST })
	@ResponseBody
	public String createSwitch(HttpServletRequest request, String switchtype,String switchid) {
		String result = "false";
		boolean flag = false;
		User user = (User) request.getSession().getAttribute("user");
		OCNetwork network = null;
		try {
			network = networkManager.getSwitchByTypeAndId(switchtype, switchid);
			if (network == null) {
				network = new OCNetwork();
				network.setvlanId(Integer.parseInt(switchid));
				network.setvlanType(Integer.parseInt(switchtype));
				network.setvlanStatus(1);
				network.setCreateTime(new Date());
			}else {
				messagePush.pushMessage(user.getUserId(),
						TimeUtils.stickyToError("vlan号已存在！"));
				return result;
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		try {
			flag = networkManager.createSwitch(network, user.getUserId());
			if (flag) {
				result = "success";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * @author lining
	 * @param 删除一个交换机
	 * @return
	 * 
	 */
	@RequestMapping(value = "/destory", method = { RequestMethod.POST })
	@ResponseBody
	public String destorySwitch(HttpServletRequest request, String netid) {
		String result = "false";
		boolean flag = false;
		User user = (User) request.getSession().getAttribute("user");
		try {
			flag = networkManager.destorySwitchByNetid(netid, user.getUserId());
			if (flag) {
				result = "success";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@RequestMapping(value = "/LinkRouter", method = { RequestMethod.POST })
	@ResponseBody
	public String linkRouter(HttpServletRequest request,
			@RequestParam String vnetuuid, @RequestParam String routerid,
			@RequestParam int net, @RequestParam int gate,
			@RequestParam int start, @RequestParam int end,
			@RequestParam int dhcpState, @RequestParam String content
			, @RequestParam String conid) {
		User user = (User) request.getSession().getAttribute("user");

		JSONObject jo = networkManager.linkRouter(user.getUserId(),vnetuuid, routerid, net, gate, start, end, dhcpState, content, conid);
		return jo.toString();
	}

	@RequestMapping(value = "/UnlinkRouter", method = { RequestMethod.POST })
	@ResponseBody
	public String unlinkRouter(HttpServletRequest request,
			@RequestParam String vnetId) {
		User user = (User) request.getSession().getAttribute("user");
		JSONObject jo =networkManager.unlinkRouter(vnetId,
				user.getUserId());
		return jo.toString();
	}
	
	/**
	 * @author zll
	 * 获取switch的列表
	 * @return
	 */
	@RequestMapping(value = "/NetworkVlanList", method = { RequestMethod.GET })
	@ResponseBody
	public String networkVlanList(HttpServletRequest request) {
		JSONArray ja = null;
		try {
			ja = networkManager.getVlansOfNetwork();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ja.toString();
	}
	
	@RequestMapping(value = "/Vxlanlist", method = { RequestMethod.GET })
	@ResponseBody
	public String Vxlanlist(HttpServletRequest request, @RequestParam String routerid) {
		JSONArray ja = null;
		try {
			ja = networkManager.getVxlansByRouterid(routerid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ja.toString();
	}
	
	@RequestMapping(value = "/checkRouter", method = { RequestMethod.GET })
	@ResponseBody
	public String checkRouter(HttpServletRequest request, @RequestParam String routerid) {
		JSONObject ja = null;
		try {
			ja = networkManager.checkRouter(routerid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ja.toString();
	}
	
	@RequestMapping(value = "/clearRouter", method = { RequestMethod.POST })
	@ResponseBody
	public String clearRouter(HttpServletRequest request, @RequestParam String routerid) {
		JSONObject ja = new JSONObject();
		try {
			ja = networkManager.clearRouter(routerid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ja.toString();
	}
}
