/**
 * @author hty
 * @time 下午3:18:25
 * @date 2014年12月5日
 */
package com.oncecloud.service;

import com.oncecloud.wrapper.MACServiceWrapper;

public interface AllocateMACFromRandom extends MACServiceWrapper {
	
	/**
	 * 生成mac地址
	 * 
	 */
	public abstract String getMac();

}
