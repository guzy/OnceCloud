package com.oncecloud.core.ha.thread;

import java.net.Inet4Address;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.Xen.API.Connection;

import com.oncecloud.core.ha.HAConstants;
import com.oncecloud.core.ha.ref.HostRef;
import com.oncecloud.core.ha.ref.PoolRef;
import com.oncecloud.core.ha.utils.DBUtils;
import com.oncecloud.core.ha.utils.IPUtils;
import com.oncecloud.core.ha.utils.VMUtils;
import com.oncecloud.tools.SSH;
import com.oncecloud.util.TimeUtils;

public class JoinbackPool implements Runnable {
	
	private List<HostRef> normalHosts = new ArrayList<HostRef>();
	private Set<HostRef> crashHosts = new HashSet<HostRef>();
	private PoolRef m_pool = new PoolRef();
	private DBUtils dbUtils = new DBUtils();
	
	public JoinbackPool(PoolRef m_pool, List<HostRef> normalHosts, Set<HostRef> crashHosts) {
		this.normalHosts = normalHosts;
		this.crashHosts = crashHosts;
		this.m_pool = m_pool;
	}
	
	@SuppressWarnings("deprecation")
	public void run() {
		List<HostRef> recoverHosts = new ArrayList<HostRef>();
		for (HostRef crashserver : crashHosts) {
			// 获取已经宕机的主机ip
			Inet4Address vmmIP = IPUtils.getInet4Address(crashserver
					.getVMHostIP());
			if (IPUtils.avaiable(vmmIP, 3000)) {
				// 初始化一个ssh信息
				SSH ssh = new SSH(crashserver.getVMHostIP(),
						HAConstants.SSH_USERNAME,
						dbUtils.getPassword(crashserver.getHostUUID()));
				if (ssh.connect()) {
					// 把配置文件迁移到其他地方
					String target = HAConstants.PATH_DELETED_DOMAINBS
							+ TimeUtils.formatTime(new Date());
					// 创建新的文件夹
					ssh.forceMakeDir(target);
					// 把原来文件移动到新的文件下面去
					ssh.forceMoveFolderTo(target);
					ssh.mountAll();
				}
				if (ssh != null) {
					ssh.close();
				}
				dbUtils.updateServerStatus(crashserver.getHostUUID(), null);
				Connection newConn = VMUtils.createXenConnection(
						createXendURL(crashserver.getVMHostIP(),
								HAConstants.XEND_PORT),
						HAConstants.XEND_USERNAME, dbUtils
								.getPassword(crashserver.getHostUUID()), null);
				// 新建的虚拟机加入资源池
				VMUtils.joinPool(newConn, m_pool.getMaster(), dbUtils
						.getPassword(crashserver.getHostUUID()));
				// 数据库更新主机所在资源池的id
				dbUtils.updateServerStatus(crashserver.getHostUUID(),
						m_pool.getPoolUUID());
				// 设置服务器可得
				markServerAvaiable(crashserver);
				// 把宕机的服务器加入到
				recoverHosts.add(crashserver);
			}
		}
		// 宕机的服务器中删除已经恢复的
		crashHosts.removeAll(recoverHosts);
		// 正常的服务器中添加已经恢复的
		normalHosts.addAll(recoverHosts);

	}

	private static URL createXendURL(String IP, int port) {
		String url = "http://" + IP + ":" + port;
		try {
			return new URL(url);
		} catch (MalformedURLException e) {
			return null;
		}
	}
	
	private void markServerAvaiable(HostRef nServer) {
		nServer.resetVmUnavaiableTimes();
	}
	
}
