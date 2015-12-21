package com.oncecloud.core.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.Xen.API.Connection;
import org.Xen.API.Host;
import org.Xen.API.SR;
import org.Xen.API.SR.Record;
import org.springframework.stereotype.Component;

import com.oncecloud.core.SRCore;
import com.oncecloud.core.constant.Constant;
import com.oncecloud.entity.OCHost;
import com.oncecloud.entity.Storage;

@Component("SRCore")
public class SRCoreImpl implements SRCore {

	@Resource
	private Constant constant;
	
	public Map<SR, Record> getRealStorage(String poolUuid) {
		Connection conn = null;
		Map<SR, SR.Record> srMap = null;
		try {
			conn = constant.getConnectionFromPool(poolUuid);
			srMap = SR.getAllRecords(conn);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return srMap;
	}

	public boolean checkStorage(OCHost host, Storage storage) {
		boolean result = false;
		Connection conn; 
		try {
			conn = constant.getConnectionFromHost(host);
			result = Host.checkSR(conn, storage.getSrAddress(),
					storage.getSrdir(), storage.getSrtype());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public boolean activeStorage(OCHost host, Storage storage) {
		boolean result = false;
		Connection conn; 
		try {
			conn = constant.getConnectionFromHost(host);
			result = Host.activeSR(conn,
					storage.getDiskuuid(), storage.getIsouuid(),
					storage.getHauuid(), storage.getSrdir(),
					storage.getSrtype());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
