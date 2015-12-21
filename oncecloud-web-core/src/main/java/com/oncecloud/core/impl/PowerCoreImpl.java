package com.oncecloud.core.impl;

import java.net.InetAddress;

import org.springframework.stereotype.Component;

import com.oncecloud.core.PowerCore;
import com.veraxsystems.vxipmi.api.async.ConnectionHandle;
import com.veraxsystems.vxipmi.api.sync.IpmiConnector;
import com.veraxsystems.vxipmi.coding.commands.IpmiVersion;
import com.veraxsystems.vxipmi.coding.commands.PrivilegeLevel;
import com.veraxsystems.vxipmi.coding.commands.chassis.ChassisControl;
import com.veraxsystems.vxipmi.coding.commands.chassis.GetChassisStatus;
import com.veraxsystems.vxipmi.coding.commands.chassis.GetChassisStatusResponseData;
import com.veraxsystems.vxipmi.coding.commands.chassis.PowerCommand;
import com.veraxsystems.vxipmi.coding.commands.session.SetSessionPrivilegeLevel;
import com.veraxsystems.vxipmi.coding.protocol.AuthenticationType;
import com.veraxsystems.vxipmi.coding.security.CipherSuite;

@Component("PowerCore")
public class PowerCoreImpl implements PowerCore {

	public int getStatusOfPower(String powerIP, int powerPort, String userName,
			String passWord) {
		int power_status = 0;
		try{
			IpmiConnector connector = new IpmiConnector(powerPort);
			ConnectionHandle handle = connector.createConnection(InetAddress.getByName(powerIP));
			CipherSuite cs = connector.getAvailableCipherSuites(handle).get(0);
			connector.getChannelAuthenticationCapabilities(handle, cs, PrivilegeLevel.Operator);
			connector.openSession(handle, userName, passWord, null);
			GetChassisStatusResponseData rd = (GetChassisStatusResponseData) connector
					.sendMessage(handle, new GetChassisStatus(IpmiVersion.V20, cs,
							AuthenticationType.RMCPPlus));
			connector.closeSession(handle);
			connector.tearDown();
			boolean powerOn = rd.isPowerOn();
			if(powerOn){
				power_status =  1;
			}
		}
		catch (Exception e){
			power_status =  -1;
		}
		return power_status;
	}

	public boolean startup(int userId, String powerIP, int powerPort,
			String userName, String passWord) {
		boolean result = false;
		try{
			IpmiConnector connector = new IpmiConnector(powerPort);
			ConnectionHandle handle = connector.createConnection(InetAddress.getByName(powerIP));
			CipherSuite cs = connector.getAvailableCipherSuites(handle).get(0);
			connector.getChannelAuthenticationCapabilities(handle, cs, PrivilegeLevel.Operator);
			connector.openSession(handle, userName, passWord, null);
			connector.sendMessage(handle, new SetSessionPrivilegeLevel(
					IpmiVersion.V20, cs, AuthenticationType.RMCPPlus,
					PrivilegeLevel.Operator));
			ChassisControl chassisControl = new ChassisControl(IpmiVersion.V20, cs,
					AuthenticationType.RMCPPlus, PowerCommand.PowerUp);
			connector.sendMessage(handle, chassisControl);
			connector.closeSession(handle);
			connector.tearDown();
			result = true;
		}catch (Exception e){
			e.printStackTrace();
			result = false;
		}
		return result;
	}

	public boolean shutdown(int userId, String powerIP, int powerPort,
			String userName, String passWord) {
		boolean result = false;
		try{
			IpmiConnector connector = new IpmiConnector(powerPort);
			ConnectionHandle handle = connector.createConnection(InetAddress.getByName(powerIP));
			CipherSuite cs = connector.getAvailableCipherSuites(handle).get(0);
			connector.getChannelAuthenticationCapabilities(handle, cs, PrivilegeLevel.Operator);
			connector.openSession(handle, userName, passWord, null);
			connector.sendMessage(handle, new SetSessionPrivilegeLevel(
					IpmiVersion.V20, cs, AuthenticationType.RMCPPlus,
					PrivilegeLevel.Operator));
			ChassisControl chassisControl = new ChassisControl(IpmiVersion.V20, cs,
					AuthenticationType.RMCPPlus, PowerCommand.PowerDown);
			connector.sendMessage(handle, chassisControl);
			connector.closeSession(handle);
			connector.tearDown();
			result = true;
		}
		catch (Exception e){
			e.printStackTrace();
		}
		return result;
	}


}
