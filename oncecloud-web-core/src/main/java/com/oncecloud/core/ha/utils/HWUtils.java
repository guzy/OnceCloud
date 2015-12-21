/**
 * Copyright (2014, ) Institute of Software, Chinese Academy of Sciences
 */
package com.oncecloud.core.ha.utils;

import java.net.InetAddress;

import org.apache.log4j.Logger;

import com.oncecloud.core.ha.ref.IPMIRef;
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

/**
 * @author henry
 * @date 2014年9月19日
 * 
 *      硬件相关的操作
 */
public class HWUtils {

	private final static Logger m_logger = Logger.getLogger(HWUtils.class);
	
	/****
	 * 
	 *  判定物理服务器是否处于开机状态
	 *  
	 *  不判断任何参数是否传入正常，如果不正常，返回false
	 *  
	 * @param Ip        IP地址
	 * @param UdpPort   任意udp端口 
	 * @param user      用户名
	 * @param pwd       密码
	 * @return
	 * @throws Exception
	 */
	
	/**
	 * @author lining
	 * @param ipmi
	 * 对外提供的判断电源是否开启的借口
	 */
	public static boolean isPoweron(IPMIRef ipmi){
		try {
			return isPowerOn(ipmi.getUrl(), ipmi.getPort(),ipmi.getUser(), ipmi.getPwd());
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * @author lining
	 * @param ipmi
	 * 开启主机服务器
	 */
	public static void startUp(IPMIRef ipmi){
		try {
			startUp(ipmi.getUrl(), ipmi.getPort(),ipmi.getUser(), ipmi.getPwd());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @author lining
	 * @param ipmi
	 * 关闭主机服务器
	 */
	public static void shutDown(IPMIRef ipmi){
		try {
			shutDown(ipmi.getUrl(), ipmi.getPort(),ipmi.getUser(), ipmi.getPwd());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @author lining
	 * @param ipmi
	 * 判断电源是否开启
	 */
	private synchronized static boolean isPowerOn(String Ip, int UdpPort,
			String user, String pwd) throws Exception {
		IpmiConnector connector = new IpmiConnector(UdpPort);

		ConnectionHandle handle = connector.createConnection(InetAddress.getByName(Ip));

		CipherSuite cs = connector.getAvailableCipherSuites(handle).get(0);

		connector.getChannelAuthenticationCapabilities(handle, cs,
				PrivilegeLevel.Operator);

		connector.openSession(handle, user, pwd, null);

		GetChassisStatusResponseData rd = (GetChassisStatusResponseData) connector
				.sendMessage(handle, new GetChassisStatus(IpmiVersion.V20, cs,
						AuthenticationType.RMCPPlus));

		connector.closeSession(handle);

		connector.tearDown();

		boolean powerOn = rd.isPowerOn();
		
		m_logger.info("Server with IP " + Ip + " is " + (powerOn ? "power on" : "power off"));
		
		return powerOn;
	}

	/****
	 * 
	 *  对指定物理服务器执行开机操作
	 *  不判断任何参数是否传入正常，如果不正常，抛出异常
	 *  
	 * @param Ip        IP地址
	 * @param UdpPort   任意udp端口 
	 * @param user      用户名
	 * @param pwd       密码
	 * @return
	 * @throws Exception
	 */
	public synchronized static void shutDown(String Ip, int UdpPort,
			String user, String pwd) throws Exception {
		
		m_logger.info("Shut down server with IP " + Ip);
		
		IpmiConnector connector = new IpmiConnector(UdpPort);

		ConnectionHandle handle = connector.createConnection(InetAddress
				.getByName(Ip));

		CipherSuite cs = connector.getAvailableCipherSuites(handle).get(0);
		connector.getChannelAuthenticationCapabilities(handle, cs,
				PrivilegeLevel.Operator);

		connector.openSession(handle, user, pwd, null);

		connector.sendMessage(handle, new SetSessionPrivilegeLevel(
				IpmiVersion.V20, cs, AuthenticationType.RMCPPlus,
				PrivilegeLevel.Operator));

		ChassisControl chassisControl = new ChassisControl(IpmiVersion.V20, cs,
				AuthenticationType.RMCPPlus, PowerCommand.PowerDown);

		connector.sendMessage(handle, chassisControl);

		connector.closeSession(handle);
		connector.tearDown();
	}

	/****
	 * 
	 *  对指定物理服务器执行关机操作
	 *  不判断任何参数是否传入正常，如果不正常，抛出异常
	 *  
	 * @param Ip        IP地址
	 * @param UdpPort   任意udp端口 
	 * @param user      用户名
	 * @param pwd       密码
	 * @return
	 * @throws Exception
	 */
	public synchronized static void startUp(String Ip, int UdpPort, String user,
			String pwd) throws Exception {
		
		m_logger.info("Start server with IP " + Ip);
		
		IpmiConnector connector = new IpmiConnector(UdpPort);
		ConnectionHandle handle = connector.createConnection(InetAddress
				.getByName(Ip));

		CipherSuite cs = connector.getAvailableCipherSuites(handle).get(0);
		connector.getChannelAuthenticationCapabilities(handle, cs,
				PrivilegeLevel.Operator);
		connector.openSession(handle, user, pwd, null);

		connector.sendMessage(handle, new SetSessionPrivilegeLevel(
				IpmiVersion.V20, cs, AuthenticationType.RMCPPlus,
				PrivilegeLevel.Operator));

		ChassisControl chassisControl = new ChassisControl(IpmiVersion.V20, cs,
				AuthenticationType.RMCPPlus, PowerCommand.PowerUp);

		connector.sendMessage(handle, chassisControl);

		connector.closeSession(handle);
		connector.tearDown();
	}
}
