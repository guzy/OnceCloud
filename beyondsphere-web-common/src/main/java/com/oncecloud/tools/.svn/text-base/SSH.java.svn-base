package com.beyondsphere.tools;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;

import ch.ethz.ssh2.ChannelCondition;
import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.SCPClient;
import ch.ethz.ssh2.SFTPv3Client;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

import com.beyondsphere.constants.HAConstants;
import com.beyondsphere.util.IOUtils;
import com.beyondsphere.util.ObjectUtils;

/**
 * @author henry(wuheng09@otcaix.iscas.ac.cn)
 * @date 2015-5-22
 * 
 */
public class SSH {

	private final static Logger m_logger = Logger.getLogger(SSH.class);

	private final static int DEFAULT_PORT = 22;

	/**
	 * The host name of the agent, in form of IP address
	 */
	private String hostname;

	/**
	 * The user name of the agent
	 */
	private String username;

	/**
	 * The password of the agent, corresponds to the user-name
	 */
	private String password;

	/**
	 * The port
	 */
	private int port;
	/**
	 * SSH connection between the console and agent
	 */
	private Connection conn;

	private Session session;

	public SSH(String hostname, String username, String password) {
		this(hostname, username, password, DEFAULT_PORT);
	}

	public SSH(String hostname, String username, String password, int port) {
		this.hostname = hostname;
		this.username = username;
		this.password = password;
		this.port = port;
	}

	public boolean connect() {
		boolean isAuthenticated = false;
		try {
			conn = new Connection(hostname, port);
			conn.connect();
			isAuthenticated = conn.authenticateWithPassword(username, password);
			session = conn.openSession();
		} catch (Exception e) {
			m_logger.error(e);
		}
		return isAuthenticated;
	}

	public void close() {

		if (session != null) {
			session.close();
		}

		if (conn != null) {
			conn.close();
		}
	}

	public String toString() {
		return hostname + File.pathSeparator + username + File.pathSeparator
				+ password;
	}

	/********************************************************************************************
	 * 
	 * Other Commands
	 * 
	 ********************************************************************************************/

	/**
	 * 直到指令全部执行完，方能返回结果。
	 * 如果执行大文件拷贝指令，则需要等待很久
	 * 
	 * @param commandLine
	 * @return
	 * @throws Exception
	 */
	public boolean execute(String commandLine) throws Exception {
		return execute(commandLine, 0);
	}

	/**
	 * 如果timeout，则返回false
	 * 
	 * 
	 * @param commandLine
	 * @param timeout
	 * @return
	 * @throws Exception
	 */
	public boolean execute(String commandLine, long timeout) throws Exception {
		if (ObjectUtils.isNull(session) || ObjectUtils.isNull(commandLine)) {
			return false;
		}

		session.execCommand(commandLine);
		session.waitForCondition(ChannelCondition.EXIT_STATUS, timeout);
		//当超时发生时，session.getExitStatus()为null
		return (ObjectUtils.isNull(session.getExitStatus())) ? false
				: ((session.getExitStatus() == 0) ? true : false);
	}

	/**
	 * 直到指令全部执行完，方能返回结果。
	 * 如果执行大文件拷贝指令，则需要等待很久
	 * 
	 * @param commandLine
	 * @return
	 * @throws Exception
	 */
	public String executeWithResult(String commandLine) throws Exception {
		return executeWithResult(commandLine, 0);
	}

	/**
	 * 如果timeout，则返回""
	 * 
	 * @param commandLine
	 * @param timeout
	 * @return
	 * @throws Exception
	 */
	public String executeWithResult(String commandLine, long timeout)
			throws Exception {
		StringBuffer result = new StringBuffer();
		if (ObjectUtils.isNull(session) || ObjectUtils.isNull(commandLine)) {
			return null;
		}
		session.execCommand(commandLine);
		int condition = session.waitForCondition(ChannelCondition.EXIT_STATUS,
				timeout);
		if (timeout(condition)) {
			m_logger.error("Command [" + commandLine + "] is timeout");
		} else {
			StreamGobbler is = new StreamGobbler(session
					.getStderr());
			result.append(
					IOUtils.toString(new StreamGobbler(session.getStdout())))
					.append(IOUtils.toString(is));
			IOUtils.close(is);
		}
		return result.toString();
	}

	private boolean timeout(int condition) {
		return ((condition & ChannelCondition.TIMEOUT) == 1) ? true : false;
	}

	/********************************************************************************************
	 * 
	 * Other Commands
	 * 
	 ********************************************************************************************/

	@Deprecated
	public boolean SCPFile(String localFile, String remoteDir) {
		SCPClient cp = new SCPClient(conn);
		try {
			cp.put(localFile, remoteDir);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	
	@Deprecated
	public boolean rmFile(String toDelFileName) {
		try {
			SFTPv3Client sftpClient = new SFTPv3Client(conn);
			sftpClient.rm(toDelFileName);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Deprecated
	public boolean GetFile(String remoteFile, String localDir) {
		SCPClient sc = new SCPClient(conn);
		try {
			sc.get(remoteFile, localDir);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 执行挂载本地磁盘操作 不检查参数是否有效，如果不有效，则返回false
	 * 
	 * @return
	 */
	@Deprecated
	public boolean mountAll() {
		try {
			this.execute(HAConstants.COMMAND_MOUNTALL);
			return true;
		} catch (Exception e) {
			m_logger.error(e);
			return false;
		}
	}

	/**
	 * 
	 * 不检查参数是否有效，如果不有效，则返回false
	 * 
	 * @return
	 */
	@Deprecated
	public boolean forceRestartXend() {
		try {
			this.execute(HAConstants.COMMAND_RESTARTXEND);
			return true;
		} catch (Exception e) {
			m_logger.error(e);
			return false;
		}
	}

	/**
	 * 不检查参数是否有效，如果不有效，则返回false
	 * 
	 * @param dir
	 * @return
	 */
	@Deprecated
	public boolean forceMakeDir(String dir) {
		try {
			this.execute(HAConstants.COMMAND_MAKEDIR + dir);
			return true;
		} catch (Exception e) {
			m_logger.error(e);
			return false;
		}
	}

	/**
	 * 不检查参数是否有效，如果不有效，则返回false
	 * 
	 * @param dir
	 * @return
	 */
	@Deprecated
	public boolean forceMoveFolderTo(String target) {
		try {
			this.execute(HAConstants.COMMAND_MOVE
					+ HAConstants.PATH_LOCAL_DOMAINS + " " + target);
			return true;
		} catch (Exception e) {
			m_logger.error(e);
			return false;
		}
	}

	/**
	 * 不检查参数是否有效，如果不有效，则返回false
	 * 
	 * @param src
	 * @param target
	 * @return
	 */
	@Deprecated
	public boolean forceCopyFolderTo(String src, String target) {
		try {
			execute(HAConstants.COMMAND_COPY + src + " " + target);
			return true;
		} catch (Exception e) {
			m_logger.error(e);
			return false;
		}
	}

}
