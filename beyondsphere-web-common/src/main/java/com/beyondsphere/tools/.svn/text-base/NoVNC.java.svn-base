package com.beyondsphere.tools;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import com.beyondsphere.constants.SystemConstant;

/**
 * @author hehai
 * @version 2014/04/23
 */
public class NoVNC {

	/**
	 * 创建NoVNC的密钥
	 * 
	 * @param token
	 * @param host
	 * @param port
	 * @return
	 */
	public static boolean createToken(String token, String host, int port) {
		try {
			StringBuffer urlbuffer = new StringBuffer();
			urlbuffer.append(SystemConstant.noVNCServer + "VncAction?action=create&host=");
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
				StringBuffer result = new StringBuffer(2);
				int a;
				while ((a = is.read()) != -1) {
					result.append((char) a);
				}
				return result.toString().equals("1");
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 删除NoVNC的密钥
	 * 
	 * @param token
	 * @return
	 */
	public static boolean deleteToken(String token) {
		try {
			URL url = new URL(SystemConstant.noVNCServer + "VncAction?action=delete&token=" + token);
			URLConnection connection = url.openConnection();
			int i = connection.getContentLength();
			if (i > 0) {
				StringBuffer result = new StringBuffer(2);
				InputStream is = connection.getInputStream();
				int a;
				while ((a = is.read()) != -1) {
					result.append((char) a);
				}
				return result.toString().equals("1");
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
