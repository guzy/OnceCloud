package com.oncecloud.core;

import java.util.Map;

import org.Xen.API.SR;

import com.oncecloud.entity.OCHost;
import com.oncecloud.entity.Storage;


public interface SRCore {
	
	/**
	 * 从服务器获取存储信息
	 * @author lining
	 * @param 
	 * @return
	 */
	public abstract Map<SR, SR.Record> getRealStorage(String poolUuid);
	
	/**
	 * 检查存储
	 * @author lining
	 * @param host, storage
	 * @return
	 */
	public abstract boolean checkStorage(OCHost host, Storage storage);
	
	/**
	 * 激活存储
	 * @author lining
	 * @param host, Storage
	 * @return
	 */
	public abstract boolean activeStorage(OCHost host, Storage storage);
}
