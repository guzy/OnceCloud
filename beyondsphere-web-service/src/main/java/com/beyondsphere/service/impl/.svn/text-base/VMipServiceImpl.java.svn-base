package com.beyondsphere.service.impl;

import javax.annotation.Resource;

import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.Xen.API.Connection;
import org.Xen.API.Types;
import org.Xen.API.VM;

import com.beyondsphere.core.constant.Constant;
import com.beyondsphere.dao.UserDAO;
import com.beyondsphere.dao.VMipDAO;
import com.beyondsphere.entity.OCVMip;
import com.beyondsphere.entity.User;
import com.beyondsphere.service.ConnectionService;
import com.beyondsphere.service.VMipService;

@Service
public class VMipServiceImpl implements VMipService {
	
	@Resource
	private VMipDAO vmipDAO;
	@Resource
	private UserDAO userDAO;
	@Resource
	private ConnectionService connService;
	@Resource
	private Constant constant;

	@Override
	public JSONObject changeVMip(int userId, OCVMip vMip,String uuid) {
		//添加日志
		OCVMip vmip = getVMip(vMip.getVmMac());
		JSONObject jo = new JSONObject();
		boolean result = false;
		if (countVMipByIpAndVlan(vMip.getVmIp(), vMip.getVmVlan())) {
			//更新虚拟机操作
			boolean vmresult=false;
			JSONObject obj=new JSONObject();
			obj.put("requestType", "Network.EnableStatic");
			obj.put("mac", vMip.getVmMac().trim());
			obj.put("ipAddress", vMip.getVmIp());
			obj.put("netmask", vMip.getVmNetmask());
			if(!"".equals(vMip.getVmGateway())){
				obj.put("gateway", vMip.getVmGateway());
			}
			if(!"".equals(vMip.getVmDns())){
				obj.put("dns", vMip.getVmDns());
			}
			//获取连接
			User user = userDAO.getUser(userId);
			Connection conn=null;
			try {
				conn = constant.getConnectionFromPool(user.getUserAllocate());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			VM vm = Types.toVM(uuid);
			try {
				vmresult=vm.sendRequestViaSerial(conn, obj.toString(), false);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
			if(vmresult){
				if(restarnetwork(userId,uuid)){
					if (vmip != null) {
						//判断ip是否冲突，根据vlan号判断
						result = vmipDAO.updateVMip(vMip);
					}else {
						result =  vmipDAO.insertVMip(vMip);
					}
				}
			}
			
			if (result) {
				jo.put("result", result);
				jo.put("message", "绑定ip地址成功!");
			}else {
				jo.put("result", result);
				jo.put("message", "绑定ip地址失败!");
			}
		}else {
			jo.put("result", result);
			jo.put("message", "ip地址在vlan"+vMip.getVmVlan()+"段已存在，请重新设定");
		}
		return jo;
		
	}

	private OCVMip getVMip(String vmMC) {
		return vmipDAO.getVMip(vmMC);
	}
	
	private boolean countVMipByIpAndVlan(String vmip, int vlan){
		int ipNum = vmipDAO.countVMipByIpAndVlan(vmip, vlan);
		if (ipNum != 0) {
			return false;
		}else {
			return true;
		}
	}

	private boolean restarnetwork(int userId, String uuid) {
		boolean vmresult=false;
		JSONObject obj=new JSONObject();
		obj.put("requestType", "Network.RestartNetwork");
		User user = userDAO.getUser(userId);
		
		Connection conn=null;
		try {
			conn = constant.getConnectionFromPool(user.getUserAllocate());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		if(conn!=null){
			VM vm = Types.toVM(uuid);
			try {
				vmresult=vm.sendRequestViaSerial(conn, obj.toString(), false);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
			
		return vmresult;
		
	}

}
