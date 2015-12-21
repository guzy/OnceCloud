/**
 * @author hty
 * @time 上午9:39:37
 * @date 2014年12月5日
 */
package com.oncecloud.service;

import org.Xen.API.Connection;


public interface ConnectionService {

	/**
	 * 从用户id获取资源池链接
	 * 
	 * @param userId
	 * @return
	 */
	public abstract Connection getConnectionFromUser(int userId);

	/**
	 * 从资源池uuid获取资源池链接
	 * 
	 * @param poolUuid
	 * @return
	 */
	public abstract Connection getConnectionFromPool(String poolUuid);

}
