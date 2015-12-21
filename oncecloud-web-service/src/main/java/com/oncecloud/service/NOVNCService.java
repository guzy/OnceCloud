/**
 * @author hty
 * @time 上午9:39:37
 * @date 2014年12月5日
 */
package com.oncecloud.service;

import org.Xen.API.Connection;

import com.oncecloud.wrapper.VNCServiceWrapper;

public interface NOVNCService extends VNCServiceWrapper {

	public abstract boolean allocation(String vmUuid, String hostUuid,
			Connection c);

	public abstract boolean deleteToken(String vmUuid);
	
	/**
	 * 获取public vnc串
	 * @return
	 */
	public abstract String getPublicVNC();

}
