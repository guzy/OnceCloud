package com.beyondsphere.manager.component;

import java.util.List;

import javax.annotation.Resource;

import org.Xen.API.Connection;
import org.Xen.API.Host;
import org.Xen.API.Pool;
import org.Xen.API.Types;
import org.springframework.stereotype.Component;

import com.oncecloud.core.HostCore;
import com.oncecloud.core.PoolCore;
import com.oncecloud.core.constant.Constant;
import com.oncecloud.entity.OCHost;
import com.oncecloud.entity.OCPool;
import com.oncecloud.service.HostService;
import com.oncecloud.service.PoolService;
import com.oncecloud.tools.SSH;


@Component("Compensator")
public class Compensator {
	
	@Resource
	private PoolService poolService;
	@Resource
	private HostService hostService;
	@Resource
	private PoolCore poolCore;
	@Resource
	private HostCore hostCore;
	@Resource
	private Constant constant;
	
	/**
	 * 为虚拟机的生命周期管理提供一种补偿机制
	 * @author lining
	 * @param poolUuid, hostUuid
	 * @return
	 */
	public void compensate(String poolUuid){
		boolean result = false;
		List<OCHost> hostList = hostService.getAllHostInPool(poolUuid);
		OCHost masterHost = hostService.getMasterOfPool(poolUuid);
		try {
			//修复资源池主节点
			if (!poolCore.create(poolUuid)) {
				SSH masterSsh = new SSH(masterHost.getHostIP(), masterHost.getHostName(), masterHost.getHostPwd());
				if (masterSsh.connect()) {
					if (masterSsh.execute("/etc/init.d/xend restart")) {
						result = poolCore.create(poolUuid);
					}
				}
			}else {
				result = true;
			}
			//修复主节点成功，修复从节点
			if (result) {
				Connection poolconn = constant.getConnectionFromPool(poolUuid);
				for (OCHost ochost : hostList) {
					String thisHostUuid = ochost.getHostUuid();
					if (thisHostUuid.equals(masterHost.getHostUuid())) {
						continue;
					} else {
						Host host = Types.toHost(thisHostUuid);
						if (!Pool.isHostInPool(poolconn, host)) {
							Connection hostconn = constant.getConnectionFromHost(ochost);
							if (hostconn != null) {
								if (!hostCore.joinToPool(masterHost, ochost)) {
									ochost.setPoolUuid(null);
								}
							}else {
								SSH ssh = new SSH(ochost.getHostIP(), ochost.getHostName(), ochost.getHostPwd());
								if (ssh.connect()) {
									if (ssh.execute("/etc/init.d/xend restart")) {
										if (!hostCore.joinToPool(masterHost, ochost)) {
											ochost.setPoolUuid(null);
										}
									}
									ssh.close();
								}
							}
						}
						
					}
					hostService.updateHost(ochost);
				}
			}else {
				//修复主节点失败，则修改数据库状态
				OCPool pool = poolService.getPoolById(poolUuid);
				pool.setPoolMaster(null);
				poolService.updatePool(pool);
				for(OCHost host : hostList){
					host.setPoolUuid(null);
					hostService.updateHost(host);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
