/**
 * @author hty
 * @time 上午11:05:48
 * @date 2014年12月9日
 */
package com.beyondsphere.controller.action;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beyondsphere.model.CreateHostModel;
import com.beyondsphere.model.ListModel;
import com.beyondsphere.entity.OCHost;
import com.beyondsphere.entity.User;
import com.beyondsphere.manager.HostManager;
import com.beyondsphere.manager.PowerManager;

/**
 * @author luogan
 * 
 */
@RequestMapping("HostAction")
@Controller
public class HostAction {
	
	@Resource
	private HostManager hostManager;
	@Resource 
	private PowerManager powerManager;

	@RequestMapping(value = "/ALLList", method = { RequestMethod.GET })
	@ResponseBody
	public String allList(HttpServletRequest request) {
		JSONArray ja = hostManager.getAllList();
		return ja.toString();
	}

	@RequestMapping(value = "/LoadList", method = { RequestMethod.GET })
	@ResponseBody
	public String loadList(HttpServletRequest request, ListModel list,
			@RequestParam String sruuid) {
		JSONArray ja = hostManager.getHostLoadList(list.getPage(),
				list.getLimit(), list.getSearch(), sruuid);
		return ja.toString();
	}

	@RequestMapping(value = "/HostList", method = { RequestMethod.GET })
	@ResponseBody
	public String hostList(HttpServletRequest request, ListModel list) {
		JSONArray ja = hostManager.getHostList(list.getPage(),
				list.getLimit(), list.getSearch());
		return ja.toString();
	}

	@RequestMapping(value = "/Delete", method = { RequestMethod.POST })
	@ResponseBody
	public String delete(HttpServletRequest request,
			@RequestParam("hostid") String hostId,
			@RequestParam("hostname") String hostName) {
		User user = (User) request.getSession().getAttribute("user");
		powerManager.deletePower(hostId);
		JSONArray ja = hostManager.deleteHost(hostId, hostName,
				user.getUserId());
		
		return ja.toString();
	}

	@RequestMapping(value = "/Issamesr", method = { RequestMethod.GET })
	@ResponseBody
	public String isSameSR(HttpServletRequest request,
			@RequestParam("uuidjsonstr") String uuidJsonStr) {
		JSONArray ja = hostManager.isSameSr(uuidJsonStr);
		return ja.toString();
	}

	@RequestMapping(value = "/RemoveFromPool", method = { RequestMethod.POST })
	@ResponseBody
	public String removeFromPool(HttpServletRequest request,
			@RequestParam String hostuuid) {
		User user = (User) request.getSession().getAttribute("user");
		JSONArray ja = hostManager.r4Pool(hostuuid, user.getUserId());
		return ja.toString();
	}

	@RequestMapping(value = "/OneHost", method = { RequestMethod.GET })
	@ResponseBody
	public String oneHost(HttpServletRequest request,
			@RequestParam String hostUuid) {
		JSONArray ja = hostManager.getOneHost(hostUuid);
		return ja.toString();
	}

	@RequestMapping(value = "/QueryAddress", method = { RequestMethod.GET })
	@ResponseBody
	public String queryAddress(HttpServletRequest request,
			@RequestParam String address) {
		JSONArray ja = hostManager.queryAddress(address);
		return ja.toString();
	}

	@RequestMapping(value = "/Create", method = { RequestMethod.POST })
	@ResponseBody
	public String create(HttpServletRequest request, CreateHostModel hostModel) {
		User user = (User) request.getSession().getAttribute("user");
		JSONArray ja = hostManager.createHost(hostModel.getHostname(), hostModel.getHostpwd(),
				hostModel.getHostdesc(), hostModel.getHostip(), hostModel.getHosttype(), user.getUserId());
		if (hostModel.getPowerStatus()!=null&&!(hostModel.getPowerStatus().equals("-1")) && ja.length() > 0) {
			powerManager.savePower(user.getUserId(), hostModel.getPowerid(), (String) ja.getJSONObject(0).get("hostid"), 
					hostModel.getHostmotherip(), Integer.parseInt(hostModel.getHostport()),
					hostModel.getPowername(), hostModel.getPowerpwd(), 
					Integer.parseInt(hostModel.getPowerStatus())>0?1:0);
		}
		return ja.toString();
	}
	
	@RequestMapping(value = "/AddToPool", method = { RequestMethod.POST })
	@ResponseBody
	public String addToPool(HttpServletRequest request,
			@RequestParam String uuidjsonstr, @RequestParam String hasmaster,
			@RequestParam String pooluuid) {
		User user = (User) request.getSession().getAttribute("user");
		JSONArray ja = hostManager.add2Pool(uuidjsonstr, hasmaster,
				pooluuid, user.getUserId());
		return ja.toString();
	}

	@RequestMapping(value = "/UnbindSR", method = { RequestMethod.GET })
	@ResponseBody
	public String unbindSR(HttpServletRequest request,
			@RequestParam String hostuuid, @RequestParam String sruuid) {
		User user = (User) request.getSession().getAttribute("user");
		return hostManager.unbindSr(hostuuid, sruuid, user.getUserId()).toString();
	}

	@RequestMapping(value = "/TablePool", method = { RequestMethod.POST })
	@ResponseBody
	public String tablePool(HttpServletRequest request,
			@RequestParam String uuidjsonstr) {
		JSONArray ja = hostManager.getTablePool(uuidjsonstr);
		return ja.toString();
	}

	@RequestMapping(value = "/Update", method = { RequestMethod.POST })
	@ResponseBody
	public void update(HttpServletRequest request, @RequestParam String hostid,
			@RequestParam String hostname, @RequestParam String hostdesc,
			@RequestParam String hostType) {
		hostManager.updateHost(hostid, hostname, hostdesc, hostType);
	}

	@RequestMapping(value = "/SRofhost", method = { RequestMethod.GET })
	@ResponseBody
	public String srOfHost(HttpServletRequest request,
			@RequestParam String hostUuid) {
		JSONArray ja = hostManager.getSrOfHost(hostUuid);
		return ja.toString();
	}

	@RequestMapping(value = "/MigrationTar", method = { RequestMethod.GET })
	@ResponseBody
	public String migrationTar(HttpServletRequest request,
			@RequestParam String vmuuid) {
		JSONArray ja = hostManager.getHostListForMigration(vmuuid);
		return ja.toString();
	}

	@RequestMapping(value = "/Recover", method = { RequestMethod.POST })
	@ResponseBody
	public String recover(HttpServletRequest request, @RequestParam String ip,
			@RequestParam String username, @RequestParam String password,
			@RequestParam String content, @RequestParam String conid,
			@RequestParam String hostUuid) {
		User user = (User) request.getSession().getAttribute("user");
		JSONObject jo = new JSONObject();
		jo.put("result",
				hostManager.recover(user.getUserId(), ip, username,
						password, content, conid, hostUuid));
		return jo.toString();
	}
	
	@RequestMapping(value = "/Register", method = { RequestMethod.POST })
	@ResponseBody
	public String register(HttpServletRequest request, @RequestParam String key,
			@RequestParam String ip,@RequestParam String hostUuid) {
		User user = (User) request.getSession().getAttribute("user");
		JSONObject jo = new JSONObject();
		jo.put("result",
				hostManager.activeHost(key, ip, hostUuid, user.getUserId()));
		return jo.toString();
	}

	@RequestMapping(value = "/HostListNotInPool", method = { RequestMethod.GET })
	@ResponseBody
	public List<OCHost> hostListNotInPool(HttpServletRequest request) {
		return hostManager.getAllHostNotInPool();
	}
	
	@RequestMapping(value = "/HostListInPool", method = { RequestMethod.GET })
	@ResponseBody
	public String hostListInPool(HttpServletRequest request, String poolUuid) {
		return hostManager.getAllHostOfPool(poolUuid).toString();
	}
	
	@RequestMapping(value = "/HostNumofPool", method = {RequestMethod.POST })
	@ResponseBody
	public int getHostNumOfPool(HttpServletRequest request, @RequestParam String poolUuid){
		int result = 0;
		result = hostManager.getAllHostInPool(poolUuid).size();
		return result;
	}
	
	@RequestMapping(value = "/OffMigrationTar", method = { RequestMethod.GET })
	@ResponseBody
	public String OffMigrationTar(HttpServletRequest request,
			@RequestParam String vmuuid) {
		JSONArray ja = hostManager.getAllList();
		return ja.toString();
	}

}
