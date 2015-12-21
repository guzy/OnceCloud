/**
 * Copyright (2014, ) Institute of Software, Chinese Academy of Sciences
 */
package com.oncecloud.core.ha;

/**
 * @author henry
 * @email  wuheng09@gmail.com
 * @date   2014年8月21日
 * @date   2014年12月15日
 */
public interface HAConstants {

	public final static String PATH_LOCAL_DOMAINS =  "/var/lib/xend/domains/";
	
	public final static String PATH_DELETED_DOMAINBS = "/etc/beyondsphere/";
	
	public final static String PATH_SEPARATOR = "/";
	
	public final static int PING_TIMEOUT = 3000;
	
	// SSH
	public final static String SSH_USERNAME = "root";
	
	public final static int SSH_PORT = 22;
	
	public final static int SSH_TIMEOUT = 3000;
	
	// Xend
	public final static String XEND_USERNAME = "root";
	
	public final static int XEND_PORT = 9363;

	//Command
	public final static String COMMAND_MOUNTALL = "/bin/mount -a";
	
	public final static String COMMAND_RESTARTXEND = "/etc/init.d/xend restart";
	
	public final static String COMMAND_MAKEDIR = "mkdir -p ";
	
	public final static String COMMAND_MOVE = "mv -f ";
	
	public final static String COMMAND_COPY = "\\cp -r ";
	
	
}