package com.oncecloud.core.impl;

import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.Xen.API.Connection;
import org.Xen.API.Host;
import org.Xen.API.Types;
import org.Xen.API.Types.BadServerResponse;
import org.Xen.API.Types.XenAPIException;
import org.Xen.API.VM;
import org.apache.xmlrpc.XmlRpcException;
import org.springframework.stereotype.Component;

import com.oncecloud.core.ImageCore;
import com.oncecloud.core.constant.Constant;
import com.oncecloud.entity.OCHost;

@Component("ImageCore")
public class ImageCoreImpl implements ImageCore {
	
	@Resource
	private Constant constant;
	/*@Resource
	private LogRecord logRecord;*/
	
	public boolean imageExist(OCHost host, String imageUuid) {
		boolean result = false;
		Connection conn = constant.getConnectionFromHost(host);
		/*logRecord.logInfo("Check Image Exist: Image [image-"
				+ imageUuid.substring(0, 8) + "] URL [http://"
				+ host.getHostIP() + ":9363");*/
		Map<VM, VM.Record> map = null;
		try {
			map = VM.getAllRecords(conn);
			for (VM thisVM : map.keySet()) {
				VM.Record vmRecord = map.get(thisVM);
				if (vmRecord.isATemplate && vmRecord.uuid.equals(imageUuid)) {
					result = true;
					break;
				}
			}
		} catch (BadServerResponse e) {
			e.printStackTrace();
		} catch (XenAPIException e) {
			e.printStackTrace();
		} catch (XmlRpcException e) {
			e.printStackTrace();
		}
		//logRecord.logInfo("Check Image Exist Result: " + result);
		return result;
	}

	public void migrateImage(String poolUuid, String imageUuid, String hostIp) {
		try {
			Connection conn = constant.getConnectionFromPool(poolUuid);
			if (conn != null) {
				Host.migrateTemplate(conn, Types.toVM(imageUuid), 
						UUID.randomUUID().toString(), hostIp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
