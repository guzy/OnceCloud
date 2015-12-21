package com.oncecloud.core.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.Xen.API.Connection;
import org.Xen.API.Host;
import org.Xen.API.Pool;
import org.Xen.API.Types;
import org.Xen.API.VM;
import org.springframework.stereotype.Component;

import com.oncecloud.core.PoolCore;
import com.oncecloud.core.constant.Constant;
import com.oncecloud.entity.OCHost;
import com.oncecloud.entity.PoolRecord;

@Component("PoolCore")
public class PoolCoreImpl implements PoolCore {
	
	@Resource
	private Constant constant;
	
	public List<PoolRecord> getPoolRecordList(String poolUuid) {
		List<PoolRecord> prList = new ArrayList<PoolRecord>();
		try {
			Connection conn = constant.getConnectionFromPool(poolUuid);
			Map<VM, VM.Record> map = VM.getAllRecords(conn);
			for (VM thisVM : map.keySet()) {
				VM.Record vmRecord = map.get(thisVM);
				if (!vmRecord.isControlDomain && !vmRecord.isATemplate) {
					String name = vmRecord.nameLabel;
					if (name.contains("i-")) {
						prList.add(new PoolRecord(vmRecord.uuid,
								vmRecord.powerState.toString(),
								vmRecord.residentOn.toWireString(), "instance"));
					} else if (name.contains("rt-")) {
						prList.add(new PoolRecord(vmRecord.uuid,
								vmRecord.powerState.toString(),
								vmRecord.residentOn.toWireString(), "router"));
					} else if (name.contains("lb-")) {
						prList.add(new PoolRecord(vmRecord.uuid,
								vmRecord.powerState.toString(),
								vmRecord.residentOn.toWireString(),
								"loadbalance"));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			prList = null;
		}
		return prList;
	}

	public boolean create(String poolUuid) {
		boolean result = false;
		try {
			Connection conn = constant.getConnectionFromPool(poolUuid);
			Pool.create(conn, poolUuid);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public boolean ejectPoolConnection(String poolUuid, Host host) {
		boolean result = false;
		try {
			Connection conn = constant.getConnectionFromPool(poolUuid);
			Pool.eject(conn, host);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public boolean joinPool(Integer userId, OCHost master, OCHost host) {
		try {
			Connection poolConn = constant.getConnectionFromHost(master);
			Host phyHost = Types.toHost(host.getHostUuid());
			if (!Pool.isHostInPool(poolConn, phyHost)) {
				Connection conn = constant.getConnectionFromHost(host);
				Pool.join(conn, master.getHostIP(),
						com.oncecloud.constants.SystemConstant.DEFAULT_USER,
						master.getHostPwd());
			}
			return true;
			/*message.pushSuccess(userId, "服务器host-"
					+ host.getHostUuid().substring(0, 3)
					+ "恢复成功");*/
		} catch (Exception e) {
			/*message.pushError(userId, "服务器host-"
					+ host.getHostUuid().substring(0, 3)
					+ "恢复失败");*/
			e.printStackTrace();
			return false;
		}
	}

}

