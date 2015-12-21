package com.oncecloud.core;

import java.util.Map;

import org.Xen.API.Host;

import com.oncecloud.entity.OCHost;
import com.oncecloud.entity.Storage;
import com.oncecloud.tools.SSH;


/**
 * @author wuheng09@otcaix.iscas.ac.cn
 * @date   2015年5月27日
 *
 */
public interface HostCore {

	
	/**
	 * 连接服务器
	 * @author lining
	 * @param hostIp, hostpwd
	 * @return hostUuid
	 */
	public String connect(String hostIp, String hostPwd, String type);
	
	/**
	 * 连接服务器
	 * @author lining
	 * @param hostIp, hostpwd
	 * @return hostUuid
	 */
	public SSH getSshConnection(String hostIp, String hostPwd, String type);
	
	
	/**
	 * 服务器加入到资源池
	 * @author lining
	 * @param master, host
	 * @return
	 */
	public boolean joinToPool(OCHost master, OCHost host);
	
	/**
	 * 获取host
	 * @author lining
	 * @param master hostUuid
	 * @return
	 */
	public Host getHost(String hostUuid);
	
	/**
	 * 获取服务器信息
	 * @author lining
	 * @param key, ip, username, password
	 * @return map
	 */
	public  Map<Host, Host.Record> getHostMap(String key, String ip, String username, String password);
	
	/**
	 * 获取高可用路径
	 * @author lining
	 * @param host
	 * @return 
	 */
	public String getHAPath(OCHost host);
	
	/**
	 * 判断服务器是否激活
	 * @author lining
	 * @param hostUuid
	 * @return
	 */
	public Map<Host, Host.Record> isHostActive(String hostUuid, String ip, String username, String password);
	
	/**
	 * 卸载存储
	 * @author lining
	 * @param
	 * @return 
	 */
	public boolean unBindStorage(OCHost host, Storage storage);
	/**
	 * 获取硬盘信息
	 * @return
	 */
	public Map<String,Long> getDiskInfo(String poolUuid);
}
