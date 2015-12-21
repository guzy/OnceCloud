package com.beyondsphere.core.impl;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.Xen.API.Connection;
import org.Xen.API.Host;
import org.Xen.API.Host.Record;
import org.Xen.API.Pool;
import org.Xen.API.SR;
import org.Xen.API.Session;
import org.Xen.API.Types;
import org.springframework.stereotype.Component;

import com.beyondsphere.core.HostCore;
import com.beyondsphere.core.HostInfo;
import com.beyondsphere.core.constant.Constant;
import com.beyondsphere.core.util.TypeUtils;
import com.beyondsphere.entity.OCHost;
import com.beyondsphere.entity.Storage;
import com.beyondsphere.helper.HashHelper;
import com.beyondsphere.tools.SSH;


@Component("HostCore")
public class HostCoreImpl implements HostCore {
	
	private final static String DEFAULT_USER = "root";
	
	@Resource
	private Constant constant;
	@Resource
	private HashHelper hashHelper;

	public String connect(String hostIp, String hostPwd, String type) {
		String classname = TypeUtils.getClassname(type);
		try {
			return ((HostInfo)Class.forName(classname).newInstance()).UUID(hostIp, hostPwd, type);
		} catch (Exception e) {
			return null;
		}
	}
	
	public SSH getSshConnection(String hostIp, String hostPwd, String type) {
		String classname = TypeUtils.getClassname(type);
		try {
			return ((HostInfo)Class.forName(classname).newInstance()).connectSsh(hostIp, hostPwd);
		} catch (Exception e) {
			return null;
		}
	}

	public boolean createHostConnection(String poolUuid, OCHost host) {
		boolean result = false;
		try {
			Connection conn = constant.getConnectionFromHost(host);
			Pool.create(conn, poolUuid);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public boolean joinToPool(OCHost master, OCHost host) {
		boolean result = false;
		Connection conn =  constant.getConnectionFromHost(host);
		try {
			if (conn != null) {
				Pool.join(conn, master.getHostIP(), DEFAULT_USER,
						master.getHostPwd());
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return result;
	}
	
	public Host getHost(String hostUuid) {
		return Types.toHost(hostUuid);
	}

	public Map<Host, Record> getHostMap(String key, String ip, String username,
			String password) {
		Map<Host, Host.Record> hostMap = null;
		SSH ssh = new SSH(ip, username, password);
		if (!ssh.connect()) {
			return hostMap;
		}
		try {
			if (ssh.execute("python /beyondsphere/activate.py " + key)) {
				URL url = new URL("HTTP", ip, 9363, "/");
				Connection conn = new Connection(url);
				Session.loginWithPassword(conn, username, password, null);
				hostMap = Host.getAllRecords(conn);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			ssh.close();
		}
		return hostMap;
	}

	public String getHAPath(OCHost host) {
		String haPath = "";
		Connection conn = constant.getConnectionFromHost(host);
		try {
			for (SR sr : SR.getAll(conn)) {
				if (sr.getType(conn).endsWith("ha")){
					haPath = sr.getRecord(conn).otherConfig.get("location") + "/" + sr.toWireString();
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return haPath;
	}

	@Override
	public Map<Host, Record> isHostActive(String hostUuid, String ip,
			String username, String password) {
		return null;
	}

	@Override
	public boolean unBindStorage(OCHost host, Storage storage) {
		boolean result = false;
		Connection conn = constant.getConnectionFromHost(host);
		SR diskSr = Types.toSR(storage.getDiskuuid());
		SR isoSr = Types.toSR(storage.getIsouuid());
		SR haSr = Types.toSR(storage.getHauuid());
		try {
			diskSr.destroy(conn);
			isoSr.destroy(conn);
			haSr.destroy(conn);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public Map<String,Long> getDiskInfo(String poolUuid) {
		
		long sumTotalDisk = 0l;
		long sumUsedDisk= 0l;
		
		try {
			Connection conn = constant.getConnectionFromPool(poolUuid);
			Set<SR> srs = SR.getAll(conn);
			for (SR sr : srs) {
				Long totalDisk = sr.getRecord(conn).physicalSize;
				Long usedDisk = sr.getRecord(conn).physicalUtilisation;
				sumTotalDisk+=totalDisk;
				sumUsedDisk+=usedDisk;
//				System.out.println(totalDisk+"------------");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Map<String,Long> map=new HashMap<String, Long>();
		map.put("sumTotalDisk", sumTotalDisk);
		map.put("sumUsedDisk", sumUsedDisk);
		return map;
	}

}
