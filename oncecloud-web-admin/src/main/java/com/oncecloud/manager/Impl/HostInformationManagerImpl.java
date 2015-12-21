package com.oncecloud.manager.Impl;

import java.util.List;

import javax.annotation.Resource;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.oncecloud.manager.HostInformationManager;
import com.oncecloud.dao.HostInformationDAO;
import com.oncecloud.dao.NetworkDAO;
import com.oncecloud.dao.RouterDAO;
import com.oncecloud.dao.UserDAO;
import com.oncecloud.entity.HostInformation;
import com.oncecloud.entity.OCNetwork;
import com.oncecloud.entity.Router;
import com.oncecloud.entity.User;
import com.oncecloud.util.TimeUtils;

@Component("HostInformationManager")
public class HostInformationManagerImpl implements HostInformationManager {

	@Resource
	private HostInformationDAO hostInformationDAO;
	@Resource
	private UserDAO userDAO;
	@Resource
	private NetworkDAO networkDAO;
	@Resource
	private RouterDAO routerDAO;

	public JSONArray getOnePageList(int page, int limit) {
		JSONArray ja = new JSONArray();
		int totalNum = hostInformationDAO.countAllHostList();
		ja.put(totalNum);
		List<HostInformation> infolist= hostInformationDAO.getOnePageList(page, limit);
		if (infolist != null) {
			for (HostInformation info : infolist) {
				JSONObject jo = new JSONObject();
				User user=userDAO.getUser(info.getUserId());
				jo.put("infoid", info.getId());
				jo.put("username", TimeUtils.encodeText(user.getUserName()));
				
				OCNetwork net=networkDAO.getSwitch(String.valueOf(info.getNetId()));
				jo.put("vlanid", net.getvlanId());
				jo.put("ipsegment", info.getIpSegment());
				jo.put("routerip", info.getRouterIp());
				jo.put("netmask", info.getRouterNetmask());
				
				ja.put(jo);
			}
		}
		return ja;
	}

	@Override
	public boolean saveInformation(HostInformation info) {
		return hostInformationDAO.saveInformation(info);
	}

	@Override
	public boolean doDeleteInfo(int infoid) {
		return hostInformationDAO.doDeleteInfo(infoid);
	}

	@Override
	public JSONArray getUnBindUsers() {
		JSONArray ja = new JSONArray();
		List<User> list = userDAO.getUserList();
		List<HostInformation> infolist=hostInformationDAO.getAllList();
		for (User user : list) {
			boolean flag=true;
			for (HostInformation hostInformation : infolist) {
				if(user.getUserId()==hostInformation.getUserId()){
					flag=false;
				}
			}
			
			if(flag){
				JSONObject jo = new JSONObject();
				jo.put("userid", user.getUserId());
				jo.put("username", user.getUserName());
				ja.put(jo);
			}
		}
		return ja;
	}
	
	@Override
	public JSONArray getBindUsers() {
		JSONArray ja = new JSONArray();
		List<HostInformation> infolist=hostInformationDAO.getAllList();
		for (HostInformation hostInformation : infolist) {
			User user=userDAO.getUser(hostInformation.getUserId());
			JSONObject jo = new JSONObject();
			jo.put("userid", user.getUserId());
			jo.put("username", user.getUserName());
			ja.put(jo);
		}
		return ja;
	}
	

	@Override
	public JSONObject checkSwitch(int netid) {
		JSONObject obj=new JSONObject();
		List<HostInformation> infolist=hostInformationDAO.getAllListbyNetid(netid);
		if(infolist.size()>0){
			obj.put("result", false);
			for (HostInformation hostInformation : infolist) {
				User user=userDAO.getUser(hostInformation.getUserId());
				obj.put("username", user.getUserName());
			}
		}else{
			obj.put("result", true);
		}
		
		return obj;
	}
	
	@Override
	public JSONArray getunBindRouterUsers() {
		JSONArray ja = new JSONArray();
		List<Router> list = routerDAO.getAllRouters();
		List<HostInformation> infolist=hostInformationDAO.getAllList();
		for (HostInformation hostInformation : infolist) {
			boolean flag=true;
			for (Router router : list) {
				if(router.getRouterUID()==hostInformation.getUserId()){
					flag=false;
				}
			}
			
			if(flag){
				User user=userDAO.getUser(hostInformation.getUserId());
				JSONObject jo = new JSONObject();
				jo.put("userid", hostInformation.getUserId());
				jo.put("username",user.getUserName());
				ja.put(jo);
			}
		}
		return ja;
	}
}
