/**
 * Copyright (2014, ) Institute of Software, Chinese Academy of Sciences
 */
package com.beyondsphere.core.ha.utils;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.beyondsphere.core.ha.HAConstants;

/**
 * @author henry
 * @date   2014年9月24日
 *
 */
public class IPUtils {

	private final static Logger m_logger = Logger.getLogger(IPUtils.class);
	
	/**
	 * 合法的IPv4是分为4段的，范围从1.0.0.0-255.255.255.255
	 */
	final static String IP_RULE = "([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}";

	final static Pattern IP_PATTERN = Pattern.compile(IP_RULE);

	final static Inet4Address UNKNOWN_HOST = null;

	/**
	 * 如果IP输入为null，或者IP输入不满足范围1.0.0.0-255.255.255.255， 则返回结果为true，否则返回false。
	 * 注意：IP输入时，请不要带空格。
	 * 
	 * @param ipaddr
	 * @returnrestartXend
	 */
	public final static boolean invalidIP(String ipaddr) {
		return invalid(ipaddr) ? true : IP_PATTERN.matcher(ipaddr).matches();
	}
	
	
	/**
	 * 如果输入域名或者IP不合法，返回NULL
	 * 
	 * @param hostname
	 * @return
	 * @throws UnknownHostException
	 */
	public final static Inet4Address getInet4Address(String hostname) {
		try {
			Inet4Address ipaddr = (Inet4Address) (Inet4Address.getByName(hostname));
			return invalidIP(ipaddr.getHostAddress()) ? ipaddr : UNKNOWN_HOST;
		} catch (UnknownHostException e) {
			m_logger.error(e.getMessage());
			return UNKNOWN_HOST;
		}
	}

	public final static boolean avaiable(Inet4Address addr, int timeout) {
		try {
			return addr.isReachable(timeout);
		} catch (IOException e) {
			return false;
		}
	}
	
	public final static boolean avaiable(Inet4Address addr) {
		return avaiable(addr, HAConstants.PING_TIMEOUT);
	}
	
	/**
	 * 判断对象是否有效
	 * 
	 * @param obj
	 * @return
	 */
	private static boolean invalid(Object obj) {
		return (obj == null) ? true : false;
	}

}
