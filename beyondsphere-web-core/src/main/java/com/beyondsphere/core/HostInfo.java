/**
 * 
 */
package com.beyondsphere.core;

import com.beyondsphere.tools.SSH;


/**
 * @author luogan
 * 2015年5月19日
 * 下午5:41:20
 */
public abstract class HostInfo {

	final static String DEFAULT_USER = "root";
	
	public SSH connectSsh(String hostIp, String hostPwd) {
		SSH ssh = new SSH(hostIp, DEFAULT_USER, hostPwd);
		if (!ssh.connect()) {
			return null;
		}
		return ssh;
	}
	
	public String UUID(String hostIp, String hostPwd, String hostType) {
		String hostUuid = "";
		SSH ssh = new SSH(hostIp, DEFAULT_USER, hostPwd);
		if (!ssh.connect()) {
			return null;
		}
		try {
			hostUuid = ssh.executeWithResult(command());
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			ssh.close();
		}
		//eliminates whitespace from the beginning and end of the uuid
		return hostUuid.trim();
	}

	protected abstract String command();
	
	protected abstract String valid();
	
}
