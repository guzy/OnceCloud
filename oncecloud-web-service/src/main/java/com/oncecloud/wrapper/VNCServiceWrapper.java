/**
 * @author hty
 * @time 上午9:39:37
 * @date 2014年12月5日
 */
package com.oncecloud.wrapper;

import org.Xen.API.Connection;


public interface VNCServiceWrapper {

	/**
	 * 分配nvc访问链接串
	 * @param vmUuid
	 * @param hostUuid
	 * @param c
	 * @return
	 * @throws Exception
	 */
	public abstract boolean allocation(String vmUuid, String hostUuid,
			Connection c) throws Exception;

	/**
	 * 删除vnc访问链接串
	 * @param vmUuid
	 * @return
	 * @throws Exception
	 */
	public abstract boolean deleteToken(String vmUuid) throws Exception;

	/**
	 * 获取public vnc串
	 * @return
	 */
	public abstract String getPublicVNC();
}
