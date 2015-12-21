/**
 * Copyright (2014, ) Institute of Software, Chinese Academy of Sciences
 */
package com.oncecloud.core.ha;

import java.net.Inet4Address;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.Xen.API.Connection;
import org.Xen.API.Types;
import org.Xen.API.VM;
import org.apache.log4j.Logger;

import com.oncecloud.constants.SystemConstant;
import com.oncecloud.core.ha.ref.HostRef;
import com.oncecloud.core.ha.ref.IPMIRef;
import com.oncecloud.core.ha.ref.PoolRef;
import com.oncecloud.core.ha.ref.VMRef;
import com.oncecloud.core.ha.utils.DBUtils;
import com.oncecloud.core.ha.utils.HWUtils;
import com.oncecloud.core.ha.utils.IPUtils;
import com.oncecloud.core.ha.utils.VMUtils;
import com.oncecloud.entity.Image;
import com.oncecloud.entity.OCHa;
import com.oncecloud.tools.NoVNC;
import com.oncecloud.tools.SSH;

/**
 * @author henry
 * @email wuheng09@gmail.com
 * @date 2014年8月20日 一个资源池对应一个Worker，负责其故障恢复
 */
public class HAPoolWorker implements Runnable {

	private DBUtils dbUtils = new DBUtils();

	private final static Logger m_logger = Logger.getLogger(HAPoolWorker.class);

	private PoolRef m_pool = new PoolRef();
	
	private List<VMRef> recoverVmRefs = new ArrayList<VMRef>();

	private boolean m_started = true;

	/***************************************************************************************************
	 * 
	 * 初始化相关操作
	 * 
	 ****************************************************************************************************/
	public HAPoolWorker(String poolUUID, String haPath, String masterIP) {
		super();
		initPoolRef(poolUUID, haPath, masterIP);
	}

	/* 初始化资源池关键信息 */
	private void initPoolRef(String poolUUID, String haPath, String masterIP) {
		m_pool.setPoolUUID(poolUUID);
		m_pool.setHAPath(haPath);
		m_pool.setMaster(masterIP);
		m_pool.setXenConn(VMUtils.createXenConnection(
				createXendURL(masterIP, HAConstants.XEND_PORT),
				HAConstants.XEND_USERNAME, dbUtils.getPassword(dbUtils.getHostUUID(masterIP)), null));
	}

	/***************************************************************************************************
	 * 
	 * 具体执行操作
	 * 
	 ****************************************************************************************************/
	public void run() {
		
		// Get all normal hosts from Database by poolUuid
		List<HostRef> normalHosts = dbUtils.getAllServersOnPool(m_pool
				.getPoolUUID());
		if (!checkHostValid(normalHosts)) {
			return;
		}
		
		// The set of crashed hosts
		Set<HostRef> crashHosts = new HashSet<HostRef>();
		// The set of suspect crashed hosts
		Set<HostRef> suspectedHosts = new HashSet<HostRef>();

		// Begin check the hosts
		while (m_started) {
			
			// Get the suspect crashed host from the normal hosts
			for (HostRef nServer : normalHosts) {

				// Get VM host ip address
				Inet4Address vmmIP = IPUtils.getInet4Address(nServer
						.getVMHostIP());

				// 可ping通，说明服务器处于正常状态，此时查看是否有迁移过来的虚拟机需要启动
				if (IPUtils.avaiable(vmmIP)) {
					m_logger.info("Server with IP (" + vmmIP + ") is avaiable.");
					// Mark the server is avaiable
					markServerAvaiable(nServer);
					// failoverVMsOnThisHostIfHad(nServer);
				} else {
					// logging
					m_logger.info("Server with IP (" + vmmIP
							+ ") is unavaiable.");
					// Mark the server is unavaiable
					markServerUnavaiable(nServer);
					// Add the server to the suspected Sets
					suspectedHosts.add(nServer);
				}
			}

			// 三次不可达，说明服务器宕机
			// 强制关机****!!!
			for (HostRef nServer : suspectedHosts) {
				
				if (!checkHostCrash(nServer)) {
					continue;
				} 
				//crashHosts.add(nServer);
				// One host crashed, then reboot the host.
				if(forceServerReboot(nServer)) { // Add the crashed host to crash sets. 
					crashHosts.add(nServer); 
				} else { 
				//如果无法强制重启，则HA进程失效 
				//由于crashHosts为null，则后续操作均不会执行，进入下一个循环，导致HA结束
				m_logger.error("Motherboard for Host " +
						nServer.getVMHostIP() + " is null, and stop HA for Pool " +
						m_pool.getPoolUUID()); 
				this.m_started = false; break; }
				 
			}
			// Get the expected numbers of normal hosts
			int expectedNormalSize = normalHosts.size();
			// Remove the crashed host from normal host sets
			normalHosts.removeAll(crashHosts);
			// Get the actual numbers of normal hosts.
			int actualNormalSize = normalHosts.size();

			if (expectedNormalSize != actualNormalSize) {
				suspectedHosts.removeAll(crashHosts);
				// Migrate the failed vms to normal servers
				if (migrateFailedVMsConfigToNormalServersByHAPolicy(
						normalHosts, crashHosts)) {
					// Update the information of resource pools
					updatePoolInfo(normalHosts);
					//恢复虚拟机的状态
					if(failoverVMsOnThisHostIfHad(recoverVmRefs)){
						recoverVmRefs.clear();
					};
				}
			}
			// 已经宕机的机器加入恢复的资源池。
			if (crashHosts.size() > 0) {
				crashServersJoinBackPoolIfRecover(normalHosts, crashHosts);
			}
			// 等待5秒钟，继续执行监听
			waitForNextPeriod();
		}

	}

	/**
	 * 已经宕机的主机，加入到恢复的资源池中
	 */
	@SuppressWarnings("deprecation")
	private void crashServersJoinBackPoolIfRecover(List<HostRef> normalHosts,
			Set<HostRef> crashHosts) {
		//启动另外一个线程加入资源池
		List<HostRef> recoverHosts = new ArrayList<HostRef>();
		for (HostRef crashserver : crashHosts) {
			// 获取已经宕机的主机ip
			Inet4Address vmmIP = IPUtils.getInet4Address(crashserver
					.getVMHostIP());
			if (IPUtils.avaiable(vmmIP, 5000)) {
				// 初始化一个ssh信息
				SSH ssh = new SSH(crashserver.getVMHostIP(),
						HAConstants.SSH_USERNAME,
						dbUtils.getPassword(crashserver.getHostUUID()));
				boolean connFlag = ssh.connect();
				if (connFlag) {
					// 把配置文件迁移到其他地方
					String target = HAConstants.PATH_DELETED_DOMAINBS
							+ getDate();
					// 创建新的文件夹
					ssh.forceMakeDir(target);
					// 把原来文件移动到新的文件下面去
					ssh.forceMoveFolderTo(target);
					ssh.mountAll();
					//重新启动xen
					ssh.forceRestartXend();
					Connection newConn = VMUtils.createXenConnection(
							createXendURL(crashserver.getVMHostIP(),
									HAConstants.XEND_PORT),
							HAConstants.XEND_USERNAME, dbUtils
									.getPassword(crashserver.getHostUUID()), null);
					
					// 新建的虚拟机加入资源池
					boolean result = VMUtils.joinPool(newConn, m_pool.getMaster(), dbUtils
							.getPassword(crashserver.getHostUUID()));
					int count = 0;
					while(!result){
						try {
							//确保xen完全启动
							Thread.sleep(10000);
							result = VMUtils.joinPool(newConn, m_pool.getMaster(), dbUtils
									.getPassword(crashserver.getHostUUID()));
							System.out.println(count);
							count++;
							if (count == 3) {
								result = true;
							}
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					if (result && count != 3) {
						dbUtils.updateServerStatus(crashserver.getHostUUID(), null);
						m_logger.info("join pool success");
						// 数据库更新主机所在资源池的id
						dbUtils.updateServerStatus(crashserver.getHostUUID(),
								m_pool.getPoolUUID());
						// 设置服务器可得
						markServerAvaiable(crashserver);
						// 把宕机的服务器加入到
						recoverHosts.add(crashserver);
					}
				}
				if (ssh != null) {
					ssh.close();
				}
			}
		}
		if (recoverHosts.size() > 0) {
			// 宕机的服务器中删除已经恢复的
			crashHosts.removeAll(recoverHosts);
			// 正常的服务器中添加已经恢复的
			normalHosts.addAll(recoverHosts);
		}
		
	}

	/**
	 * 等待一段时间，进入下一次监听
	 */
	private void waitForNextPeriod() {
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			m_logger.error(e);
		}
	}

	private void updatePoolInfo(List<HostRef> normalHosts) {
		boolean isMaster = true;
		for (HostRef server : normalHosts) {
			Connection newConn = VMUtils.createXenConnection(
					createXendURL(server.getVMHostIP(), HAConstants.XEND_PORT),
					HAConstants.XEND_USERNAME,
					dbUtils.getPassword(server.getHostUUID()), null);
			if (isMaster) {
				dbUtils.updatePoolStatus(server.getHostUUID(),
						m_pool.getPoolUUID());
				VMUtils.createPool(newConn, m_pool.getPoolUUID());
				isMaster = false;
				m_pool.setMaster(server.getVMHostIP());
				m_pool.setXenConn(VMUtils.createXenConnection(
						createXendURL(server.getVMHostIP(),
								HAConstants.XEND_PORT),
						HAConstants.XEND_USERNAME, dbUtils.getPassword(server.getHostUUID()), null));
				m_logger.info("Recover pool with UUID " + m_pool.getPoolUUID()
						+ " and new master is " + server.getVMHostIP());
			} else {
				m_logger.error("Server " + server.getVMHostIP()
						+ " join to pool " + m_pool.getPoolUUID());
				VMUtils.joinPool(newConn, m_pool.getMaster(), dbUtils.getPassword(server.getHostUUID()));
			}
		}
		
	}

	private boolean migrateFailedVMsConfigToNormalServersByHAPolicy(
			List<HostRef> normalHosts, Set<HostRef> crashHosts) {

		// Get access controller infos from HA service
		OCHa ha = getHaServiceHa(m_pool.getPoolUUID());
		boolean migrateFlag = false;
		if (ha == null) {
			return false;
		}
		Integer accessControllPolicy = ha.getAccessControlPolicy();
		if (accessControllPolicy == null) {
			accessControllPolicy = 0;
		}
		if (accessControllPolicy == 3) {
			// 对于指定的多个主机，进行拆分操作
			String migrateHostId = ha.getMigratoryHostUuid();
			String[] migrateHostIds = migrateHostId.split(",");
			List<HostRef> migrateHostLists = new ArrayList<HostRef>();
			for (int i = 0; i < migrateHostIds.length; i++) {
				if (migrateHostIds[i].length() > 0) {
					HostRef appointServer = dbUtils
							.getAppointedHost(migrateHostIds[i]);
					migrateHostLists.add(appointServer);
					normalHosts.remove(appointServer);
				}
			}
			migrateFlag = migrateFailedVMsConfigToNormalServers(
					migrateHostLists, crashHosts);
			if (!migrateFlag) {
				return migrateFailedVMsConfigToNormalServers(normalHosts,
						crashHosts);
			} else {
				return migrateFlag;
			}
		} else {
			return migrateFailedVMsConfigToNormalServers(normalHosts,
					crashHosts);
		}
	}

	/**
	 * @author lining
	 * @param normalHosts
	 *            crashHosts Migrate by memory and cpu percent
	 */
	@SuppressWarnings("deprecation")
	private boolean migrateFailedVMsConfigToNormalServers(
			List<HostRef> normalHosts, Set<HostRef> crashHosts) {
		// Get the normal hosts size.
		int normalHostNum = normalHosts.size();
		if (normalHostNum == 0) {
			return false;
		}
		try {
			for (HostRef nServer : crashHosts) {
				// Get the vms from crashed hosts
				List<VMRef> crashVMs = dbUtils.getAllVMsOnHost(nServer.getHostUUID());
				if (crashVMs == null) {
					return false;
				}
				int failedVMNum = crashVMs.size();
				int failedVMPerNormalHost = (failedVMNum % normalHostNum == 0) ? failedVMNum
						/ normalHostNum
						: failedVMNum / normalHostNum + 1;
				for (int i = 0; i < crashVMs.size(); i++) {
					HostRef newServer = normalHosts.get(i
							/ failedVMPerNormalHost);
					VMRef vm = crashVMs.get(i);
					vm.setHostUUID(newServer.getHostUUID());
					recoverVmRefs.add(vm);
					//newServer.registerFailoverVM(vm);
					boolean sucessful = false;
					SSH ssh = new SSH(newServer.getVMHostIP(),
							HAConstants.SSH_USERNAME,
							dbUtils.getPassword(newServer.getHostUUID()));
					if (ssh.connect()) {
						sucessful = ssh.forceCopyFolderTo(m_pool.getHAPath()
								+ HAConstants.PATH_SEPARATOR + vm.getVmUUID(),
								HAConstants.PATH_LOCAL_DOMAINS);
					} else {
						continue;
					}
					if (ssh != null) {
						ssh.close();
					}
					if (sucessful) {
						dbUtils.updateVMOnHostInfo(vm.getHostUUID(),
								vm.getVmUUID());
						m_logger.info("VM configuration with UUID "
								+ vm.getVmUUID() + " is sucessful");
						m_logger.info("VM with UUID " + vm.getVmUUID()
								+ " migrate to Server "
								+ newServer.getVMHostIP() + " sucessful");
					} else {
						m_logger.info("VM configuration with UUID "
								+ vm.getVmUUID() + " is failed");
						m_logger.info("VM with UUID " + vm.getVmUUID()
								+ " migrate to Server "
								+ newServer.getVMHostIP() + " failed");
					}
				}
				//迁移映像
				migrateFailedImagesConfigToNormalServers(normalHosts, crashHosts);
				dbUtils.updateServerStatus(nServer.getHostUUID(), null);
			}
			//重新启动xen
			for(HostRef normalServer:normalHosts){
				SSH ssh = new SSH(normalServer.getVMHostIP(),
						HAConstants.SSH_USERNAME,
						dbUtils.getPassword(normalServer.getHostUUID()));
				if(ssh.connect()){
					ssh.forceRestartXend();
				}else {
					continue;
				}
				if (ssh != null) {
					ssh.close();
				}
			}
			/*if(failoverVMsOnThisHostIfHad(crashVMs)){
				nServer.markAllVMsFailoverSucessful();
			};*/
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@SuppressWarnings("deprecation")
	private boolean migrateFailedImagesConfigToNormalServers(
			List<HostRef> normalHosts, Set<HostRef> crashHosts) {
		// Get the normal hosts size.
		int normalHostNum = normalHosts.size();
		if (normalHostNum == 0) {
			return false;
		}
		try {
			for (HostRef nServer : crashHosts) {
				// Get the vms from crashed hosts
				List<Image> crashImages = dbUtils.getCrashedImagesByHostUuid(nServer.getHostUUID());
				if (crashImages == null) {
					return false;
				}
				int failedImageNum = crashImages.size();
				int failedVMPerNormalHost = (failedImageNum % normalHostNum == 0) ? failedImageNum
						/ normalHostNum
						: failedImageNum / normalHostNum + 1;
				for (int i = 0; i < crashImages.size(); i++) {
					HostRef newServer = normalHosts.get(i
							/ failedVMPerNormalHost);
					Image image = crashImages.get(i);
					image.setHostUuid(newServer.getHostUUID());
					boolean sucessful = false;
					SSH ssh = new SSH(newServer.getVMHostIP(),
							HAConstants.SSH_USERNAME,
							dbUtils.getPassword(newServer.getHostUUID()));
					if (ssh.connect()) {
						sucessful = ssh.forceCopyFolderTo(m_pool.getHAPath()
								+ HAConstants.PATH_SEPARATOR + image.getImageUuid(),
								HAConstants.PATH_LOCAL_DOMAINS);
					} else {
						continue;
					}
					if (ssh != null) {
						ssh.close();
					}
					if (sucessful) {
						// 更新vm在数据表中的信息
						dbUtils.updateImageOnImageInfo(image.getHostUuid(),
								image.getImageUuid());
						m_logger.info("Image configuration with UUID "
								+ image.getImageUuid() + " is sucessful");
						m_logger.info("Image with UUID " + image.getImageUuid()
								+ " migrate to Server "
								+ newServer.getVMHostIP() + " sucessful");
					} else {
						m_logger.info("Image configuration with UUID "
								+ image.getImageUuid() + " is failed");
						m_logger.info("Image with UUID " + image.getImageUuid()
								+ " migrate to Server "
								+ newServer.getVMHostIP() + " failed");
					}
				}
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * @author lining
	 * @param HostRef
	 *            强行重新启动已经宕机的服务器
	 * @return
	 */
	private boolean forceServerReboot(HostRef nServer) {
		m_logger.info("Server with IP " + nServer.getVMHostIP()
				+ " is rebooting");
		System.out.println(nServer.getHostUUID());
		IPMIRef ipmi = dbUtils.getIPMI(nServer.getHostUUID());
		try {
			if (!invalid(ipmi)) {
				if (HWUtils.isPoweron(ipmi)) {
					HWUtils.shutDown(ipmi);
					while (HWUtils.isPoweron(ipmi)) {
						Thread.sleep(10000);
					}
					//start up the host
					HWUtils.startUp(ipmi);
				} else {
					HWUtils.startUp(ipmi);
				}
			} else {
				return false;
			}
		} catch (Exception e) {
			m_logger.error(e.getMessage());
			m_logger.error("No motherborad IP for server " + nServer.getVMHostIP());
			return false;
		}
		return true;
	}

	/***********************************************************************************
	 * 
	 * Core Functions
	 * 
	 ************************************************************************************/
	private boolean failoverVMsOnThisHostIfHad(List<VMRef> vmRefs) {
		boolean result = false;
		int vmNum = 0;
		for (VMRef vm : vmRefs) {
			startFailoverVMIfNeed(vm);
			vmNum++;
		}
		if (vmNum == vmRefs.size()) {
			result = true;
		}
		return result;
	}

	private void startFailoverVMIfNeed(VMRef vm) {
		if (vm.isStarted()) {
			boolean sucessful = VMUtils.startVM(m_pool.getXenConn(), vm.getHostUUID(),
					vm.getVmUUID());
			if (!sucessful) {
				m_logger.info("Start VM " + vm.getVmUUID() + " failed.");
				vm.setVMPowerStatus(false);
				dbUtils.updateVMStatus(vm.getVmUUID(), vm.isStarted());
			} else {
				m_logger.info("Start VM " + vm.getVmUUID() + " sucessful.");
				updateNoVNCConsoleForVM(vm);
			}
		}
	}

	/**
	 * NoVNC无法启动，会被忽略，因为虚拟机已经正常运行，还可以通过远程桌面进行访问
	 * 
	 * @param vm
	 */
	private void updateNoVNCConsoleForVM(VMRef vm) {
		try {
			String hostIP = Types.toHost(vm.getHostUUID()).getAddress(
					m_pool.getXenConn());
			int port = getVNCPort(vm.getVmUUID(), m_pool.getXenConn());
			NoVNC.deleteToken(vm.getVmUUID().substring(0, 8));
			NoVNC.createToken(vm.getVmUUID().substring(0, 8), hostIP, port);
		} catch (Exception e) {
			m_logger.error(e.getMessage());
		}
	}

	/********************************************************************************
	 * 
	 * Others
	 * 
	 *********************************************************************************/
	private void markServerAvaiable(HostRef nServer) {
		nServer.resetVmUnavaiableTimes();
	}

	private void markServerUnavaiable(HostRef nServer) {
		nServer.incrementVmUnavaiableTimes();
	}

	// 连续三次不可达，即视为Crash
	private boolean checkHostCrash(HostRef vmm) {
		try {
			return (vmm.getVmUnavaiableTimes() >= 3) ? true : false;
		} catch (Exception e) {
			// 传入参数可能为null
			return false;
		}
	}

	// 如果normalHosts的大小为0，说明数据库不可达
	private boolean checkHostValid(List<HostRef> normalHosts) {
		if (normalHosts == null || normalHosts.size() == 0) {
			m_logger.error("Pool size cannot be 0， please check whether database is reachable. Stop HA for Pool "
					+ m_pool.getPoolUUID());
			m_started = false;
			return false;
		}

		for (HostRef hostRef : normalHosts) {
			if (!IPUtils.invalidIP(hostRef.getVMHostIP())) {
				return false;
			}
		}

		return true;
	}

	public void stop() {
		this.m_started = false;
	}

	public static int getVNCPort(String uuid, Connection conn) {
		int port = 0;
		try {
			VM vm = Types.toVM(uuid);
			String location = vm.getVNCLocation(conn);
			port = 5900;
			int len = location.length();
			if (len > 5 && location.charAt(len - 5) == ':') {
				port = Integer.parseInt(location.substring(len - 4));
			}
		} catch (Exception e) {
		}
		return port;
	}

	/**
	 * 判断对象是否有效
	 * 
	 * @param obj
	 * @return
	 */
	private static boolean invalid(Object obj) {
		return (obj == null) ? true : false;
	}

	private static String getDate() {
		SimpleDateFormat formatter;
	    formatter  =   new  SimpleDateFormat ( " yyyy-MM-dd HH:mm:ss " );
	    String ctime  =  formatter.format(new Date());
		return ctime;
	}

	private static URL createXendURL(String IP, int port) {
		URL url = null;
		try {
			url = new URL("HTTP", IP, SystemConstant.DEFAULT_PORT,"/");
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return url;
	}

	public boolean isStarted() {
		return m_started;
	}

	/**
	 * @author lining
	 * @param poolUuid
	 *            获取高可用的控制接入方案
	 */
	private OCHa getHaServiceHa(String poolUuid) {
		return dbUtils.getHAService(poolUuid);
	}
}
