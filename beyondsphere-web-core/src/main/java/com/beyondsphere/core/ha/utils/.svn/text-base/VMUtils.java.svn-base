/**
 * Copyright (2014, ) Institute of Software, Chinese Academy of Sciences
 */
package com.beyondsphere.core.ha.utils;

import java.net.URL;

import org.Xen.API.Connection;
import org.Xen.API.Host;
import org.Xen.API.Pool;
import org.Xen.API.Session;
import org.Xen.API.Types;
import org.Xen.API.VM;
import org.apache.log4j.Logger;


/**
 * @author henry
 * @date 2014年9月24日
 *
 * 虚拟机操作相关
 */
public class VMUtils {

	private final static Logger m_logger = Logger.getLogger(VMUtils.class);
	

	public static Connection createXenConnection(URL url, String usr,
			String pwd, String version) {
		Connection conn = new Connection(url);
		try {
			Session.loginWithPassword(conn, usr, pwd, version);
		} catch (Exception e) {
			return null;
		}
		return conn;
	}

	/**
	 * @param conn
	 * @param uuid
	 * @return
	 */
	public synchronized static boolean startVM(Connection conn, String hostUuid, String uuid) {
		try {
			VM vm = VM.getByUuid(conn, uuid);
			Host host = Types.toHost(hostUuid);
			vm.startOn(conn, host, false, true);
		} catch (Exception e) {
			e.printStackTrace();
			m_logger.error(e.getMessage());
			return false;
		}
		return true;
	}

	/**
	 * @param conn
	 * @param poolUudi
	 * @return
	 */
	public static boolean createPool(Connection conn, String poolUudi) {
		try {
			Pool.create(conn, poolUudi);
		} catch (Exception e) {
			m_logger.error(e.getMessage());
			return false;
		}
		m_logger.info("Recover pool " + poolUudi + " sucessful.");
		return true;
	}

	/**
	 * @param conn
	 * @param IP
	 * @return
	 */
	public static boolean joinPool(Connection conn, String IP, String pwd) {
		try {
			Pool.join(conn, IP, "root", pwd);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
}
