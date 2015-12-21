/**
 * @author hty
 * @time 上午11:17:28
 * @date 2014年12月5日
 */
package com.beyondsphere.utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.Xen.API.Connection;
import org.Xen.API.Host;

import com.beyondsphere.entity.OCHost;
import com.beyondsphere.dao.HaDAO;
import com.beyondsphere.dao.HostDAO;
import com.beyondsphere.dao.UserDAO;
import com.beyondsphere.dao.VMDAO;
import com.beyondsphere.entity.OCHa;
import com.beyondsphere.entity.User;
import com.beyondsphere.model.performance.HaAccessPolicy;

@Component
public class AllocateHost {
	
	private final static long MB = 1024 * 1024;
	
	@Resource
	private UserDAO userDAO;
	@Resource
	private HaDAO haDAO;
	@Resource
	private HostDAO hostDAO;
	@Resource
	private VMDAO vmDAO;
	
	/**
	 * 获取空闲资源最大的连接池
	 * @param conn
	 * @param memory
	 * @return
	 */
	public String getAllocateHost(Connection conn, int memory, int userId) {
		String host = null;
		
		Set<Host> hostSet = null;
		try {
			Map<Host, Host.Record> hostMap = Host.getAllRecords(conn);
			hostSet = getAviliableHost(hostMap, userId);
			long maxFree = 0;
			for (Host thisHost : hostSet) {
				Host.Record hostRecord = hostMap.get(thisHost);
				long memoryFree = hostRecord.memoryFree;
				if (memoryFree > maxFree) {
					maxFree = memoryFree;
					host = thisHost.toWireString();
				}
			}
			if ((int) (maxFree / MB) >= memory) {
				return host;
			} else {
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * @author lining
	 * @param map
	 * HA为指定迁移主机的时候，把指定的主机去掉，获取可用的host资源
	 */
	
	private Set<Host> getAviliableHost(Map<Host, Host.Record> hostMap, int userId){
		Set<Host> hostSet = null;
		OCHa ha = haDAO.getHa(getPoolUuid(userId));
		String[] migrateHostIds = null;
		hostSet = hostMap.keySet();
		if(ha != null){
			if (ha.getAccessControlPolicy() == HaAccessPolicy.APPOINT_HOST) {
				migrateHostIds = ha.getMigratoryHostUuid().split(",");
				for (int i = 0; i < migrateHostIds.length; i++) {
					for (Host leftHost : hostSet) {
						if (leftHost.toWireString().equals(migrateHostIds[i])) {
							hostSet.remove(leftHost);
						}
					}
				}
			}
		}
		return hostSet;
	}
	
	/**
	 * @author lining
	 * @param userid
	 * 高可用条件判断
	 * 
	 */
	public boolean isHA(int userId, int memory){
		OCHa ha = haDAO.getHa(getPoolUuid(userId));
		List<OCHost> hostList = hostDAO.getHostListOfPool(getPoolUuid(userId));
		if (ha == null) {
			return true;
		}else {
			Integer accessPolicy = ha.getAccessControlPolicy();
			if(accessPolicy == null){
				return true;
			}else {
				int totalMem = getTotalMem(hostList);
				int usedMem = memory;
				int unUsedMem = totalMem - usedMem;
				for (OCHost host : hostList) {
					usedMem += vmDAO.getUsedMemByHostUuid(host.getHostUuid());
				}
				switch (accessPolicy) {
				case HaAccessPolicy.LEFT_HOST_POLICY:
					int lefthost = ha.getLeftHost();
					int maxMem = getMaxMemOfHost(hostList, lefthost);
					if (unUsedMem > maxMem) {
						return true;
					}else {
						return false;
					}
				case HaAccessPolicy.PERCENT_POLICY:
					if (unUsedMem > (totalMem * ha.getMemoryPercent())) {
						return true;
					}else {
						return false;
					}
				case HaAccessPolicy.APPOINT_HOST:
					int leftMem = getLeftMem(ha);
					if (unUsedMem > leftMem) {
						return true;
					}else {
						return false;
					}
				default:
					return true;
				}
			}
			
		}
	}
	
	/**
	 * @author lining
	 * @param userId
	 * 获取poolUuid
	 * 
	 */
	private String getPoolUuid(int userId){
		User user = userDAO.getUser(userId);
		return user.getUserAllocate();
	}
	
	/**
	 * @author lining
	 * @param hostList
	 * 获取主机列表中的最大内存
	 * 
	 */
    private int getMaxMemOfHost(List<OCHost> hosts, int lefthost){
    	Integer[] memArray = new Integer[hosts.size()];
    	for (int i = 0; i < memArray.length; i++) {
    		for (OCHost host : hosts) {
    			memArray[i] = host.getHostMem();
    		}
		}
		Arrays.sort(memArray, Collections.reverseOrder());
		return maxMemOfHosts(memArray, lefthost);
    }
	
    /**
     * @author lining
     * @param lefthost
     * 预留主机策略，根据预留主机数量计算预留的内存大小（默认主机内存按照从大到小排序）
     * 
     */
    private int maxMemOfHosts(Integer[] memArray, int lefthost){
    	int maxMem = 0;
    	for (int j = 0; j < lefthost; j++) {
			maxMem += memArray[j];
		}
		return maxMem;
    }
    
	/**
	 * @author lining
	 * @param ha
	 * 获取指定主机方案时剩余内存的大小
	 * 
	 */
	private int getLeftMem(OCHa ha){
		String migrateHostId = ha.getMigratoryHostUuid();
		String[] migrateHostIds = migrateHostId.split(",");
		int leftMem = 0;
		for (int i = 0; i < migrateHostIds.length; i++) {
			if (migrateHostIds[i].length() > 0) {
				OCHost host = hostDAO.getHost(migrateHostIds[i]);
				leftMem += host.getHostMem();
			}
		}
		return leftMem;
	}
	
	/**
	 * @author lining
	 * @param hostlist
	 * 获取一个资源池内内存的大小
	 */
	private int getTotalMem(List<OCHost> hostLists){
		int totalMem = 0;
		if (hostLists != null) {
			for (OCHost host : hostLists) {
				totalMem += host.getHostMem();
			}
		}
		return totalMem;
	}
}


