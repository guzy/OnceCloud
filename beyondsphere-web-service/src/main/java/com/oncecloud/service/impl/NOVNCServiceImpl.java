/**
 * @author hty
 * @time 上午9:39:37
 * @date 2014年12月5日
 */
package com.oncecloud.service.impl;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.annotation.Resource;

import org.Xen.API.Connection;
import org.Xen.API.Types;
import org.Xen.API.VM;

import com.oncecloud.dao.HostDAO;
import com.oncecloud.entity.OCHost;
import com.oncecloud.service.NOVNCService;
import com.oncecloud.util.OnceProperties;

public class NOVNCServiceImpl implements NOVNCService {
	public static String noVNCServer = "";
	public static String noVNCServerPublic = "";

	@Resource
	private HostDAO hostDAO;

	public boolean allocation(String vmUuid, String hostUuid, Connection c) {
		String hostAddress = getHostAddress(hostUuid);
		int port = getVNCPort(vmUuid, c);
		return NoVNC.createToken(vmUuid.substring(0, 8), hostAddress, port);
	}

	private String getHostAddress(String hostUuid) {
		String hostIP = null;
		OCHost host = hostDAO.getHost(hostUuid);
		if (host != null) {
			hostIP = host.getHostIP();
		}
		return hostIP;
	}

	private int getVNCPort(String uuid, Connection c) {
		int port = 0;
		try {
			VM vm = Types.toVM(uuid);
			String location = vm.getVNCLocation(c);
			port = 5900;
			int len = location.length();
			if (len > 5 && location.charAt(len - 5) == ':') {
				port = Integer.parseInt(location.substring(len - 4));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return port;
	}

	public boolean deleteToken(String vmUuid) {
		return NoVNC.deleteToken(vmUuid.substring(0, 8));
	}

	static class NoVNC {

		/**
		 * 创建NoVNC的密钥
		 * 
		 * @param token
		 * @param host
		 * @param port
		 * @return
		 */
		public static boolean createToken(String token, String host, int port) {
			boolean result = false;
			try {
				StringBuffer urlbuffer = new StringBuffer();
				noVNCServer = OnceProperties.loadXML(
						"./com/beyondsphere/config/config.xml").getProperty(
						"no_vnc");
				urlbuffer.append(noVNCServer + "VncAction?action=create&host=");
				urlbuffer.append(host);
				urlbuffer.append("&port=");
				urlbuffer.append(port);
				urlbuffer.append("&token=");
				urlbuffer.append(token);
				URL url = new URL(urlbuffer.toString());
				URLConnection connection = url.openConnection();
				int i = connection.getContentLength();
				if (i > 0) {
					InputStream is = connection.getInputStream();
					StringBuffer _result = new StringBuffer(2);
					int a;
					while ((a = is.read()) != -1) {
						_result.append((char) a);
					}
					result = _result.toString().equals("1");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return result;
		}

		/**
		 * 删除NoVNC的密钥
		 * 
		 * @param token
		 * @return
		 */
		public static boolean deleteToken(String token) {
			boolean result = false;
			try {
				noVNCServer = OnceProperties.loadXML(
						"./com/beyondsphere/config/config.xml").getProperty(
						"no_vnc");
				URL url = new URL(noVNCServer
						+ "VncAction?action=delete&token=" + token);
				URLConnection connection = url.openConnection();
				int i = connection.getContentLength();
				if (i > 0) {
					StringBuffer _result = new StringBuffer(2);
					InputStream is = connection.getInputStream();
					int a;
					while ((a = is.read()) != -1) {
						_result.append((char) a);
					}
					result = _result.toString().equals("1");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return result;
		}
	}

	public String getPublicVNC() {
		try {
			noVNCServerPublic = OnceProperties.loadXML(
					"./com/beyondsphere/config/config.xml").getProperty(
					"no_vnc_public");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return noVNCServerPublic;
	}
}
