package com.beyondsphere.core.impl;

import javax.annotation.Resource;

import org.Xen.API.Connection;
import org.Xen.API.VIF;
import org.Xen.API.VM;
import org.springframework.stereotype.Component;

import com.beyondsphere.core.VMCore;
import com.beyondsphere.core.constant.Constant;
import com.beyondsphere.model.SwitchType;
import com.beyondsphere.util.TimeUtils;

@Component("VMCore")
public class VMCoreImpl implements VMCore {
	
	@Resource
	private Constant constant;

	public String getPowerStatus(String poolUuid, String vmUuid) {
		Connection conn = null;
		String powerState = "";
		try {
			conn = constant.getConnectionFromPool(poolUuid);
			VM thisVM = VM.getByUuid(conn, vmUuid);
			powerState = thisVM.getPowerState(conn).toString();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return powerState;
	}
	
	public VM getVm(String poolUuid, String vmUuid) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean destory(String poolUuid, String vmUuid) {
		boolean result = false;
		Connection c = null;
		try {
			c = constant.getConnectionFromPool(poolUuid);
			VM vm = VM.getByUuid(c, vmUuid);
			vm.hardShutdown(c);
			vm.destroy(c, true);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public String addMAC(String poolUuid, String vmUuid, String physical,
			String vnetid) {
		Connection conn = null;
		String mac = "";
		try {
			conn = constant.getConnectionFromPool(poolUuid);
			VM vm = VM.getByUuid(conn, vmUuid);
			mac = TimeUtils.randomMac();
			VIF vif = VIF.createBindToPhysicalNetwork(conn, vm, physical,
					mac);
			vm.setTag(conn, vif, vnetid, SwitchType.SWITCH);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return mac;
	}
	
}
