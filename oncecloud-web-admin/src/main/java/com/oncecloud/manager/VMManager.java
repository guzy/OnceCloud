/**
 * @author hty
 * @time 上午11:05:48
 * @date 2014年12月9日
 */
package com.oncecloud.manager;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.Xen.API.Connection;
import org.Xen.API.VM.Record;
import org.json.JSONArray;
import org.json.JSONObject;

import com.oncecloud.entity.OCVM;


public interface VMManager {

	public abstract void adminDestoryVM(int userId, String uuid);

	public abstract boolean doAdminStartVm(int userId, String uuid);
	//暂停虚拟主机
	public abstract void doAdminStopHost(int userId, String uuid, String force);
	//恢复虚拟主机
	public abstract void doRestartHostVm(int userId, String uuid, String force);
	
	public abstract void doAdminShutDown(int userId, String uuid, String force);

	public abstract JSONArray getAdminVMList(int page, int limit, String host,
			int importance, int userid, String type);

	public abstract JSONArray getISOList(String poolUuid);

	public abstract void createVMByISO(String vmUuid, String isoUuid, int isotype,
			String srUuid, String name, int cpu, int memory, int volumeSize,
			String poolUuid, int userId);

	public abstract boolean addMac(String uuid, String type, String physical,
			String vnetid);

	/*public abstract boolean modifyVnet(String uuid, String type, String vnetid,
			String vifUuid);*/

	public abstract boolean modifyPhysical(String uuid, String type,
			String physical, String vifUuid);

	public abstract boolean deleteMac(String uuid, String type, String vifUuid);

	public abstract JSONArray getMacs(String uuid, String type);

	public abstract JSONArray getNets(String uuid, String type);

	public abstract void saveToDataBase(String vmUuid, String vmPWD, int vmUID,
			int vmPlatform, String vmName, String vmIP);

	/*public abstract boolean modifyVnet(String uuid, String type, String vnetid,
			String vifUuid);*/
	
	public abstract boolean templateToVM(String uuid, int userId);

	public abstract boolean vmToTemplate(String uuid, int userId);

	public abstract boolean migrate(int userId, String uuid, String tarHost,
			String content, String conid);

	public abstract int getDiskLimit(String vmUuid, String type, String ioType);

	public abstract boolean clearLimit(String vmUuid, int userID);
	
	public abstract boolean limitDisk(String vmUuid, int rate, String type, String ioType, int userId);

	public abstract boolean limitNetwork(String vmUuid, String vifUuid, int rate, String type, int userId);
	
	/**
	 * @author lining
	 * @param poolUuid
	 * @return map
	 * 获取同一个资源池内创建的虚拟机中最大cpu数和最大内存容量
	 * 
	 */
	public abstract Map<String, Object> getMaxCpuAndMemByPoolUuid(String poolUuid);
	
	/**
	 * @author lining
	 * @param poolUuid
	 * @return map
	 * 获取同一个资源池内创建的虚拟机中最大cpu数和最大内存容量
	 * 
	 */
	public abstract int countVMsOfHost(String hostUuid);
	
	/**
	 * @author lining
	 * @param poolUuid
	 * @return map
	 * 获取同一个vlan内创建的虚拟机的个数
	 * 
	 */
	public abstract int countVMsOfVlan(String netId);
	
	/**
	 * @author lining
	 * @param uuid type
	 * @return
	 * 根据虚拟机所在的资源池获取switch列表
	 * 
	 */
	public abstract JSONArray getSwitchListByvm(String vmid, int type);
	
	/**
	 * @author lining
	 * @param uuid switchid vlanid
	 * @return
	 * 根据虚拟机所在的资源池获取switch列表
	 * 
	 */
	public abstract boolean joinVlan(int userId, String vmid, String vifid, int switchid, int vlanid, int type);
	
	/**
	 * 校验虚拟机状态
	 * @author lining
	 * @return
	 * 
	 */
	public abstract boolean checkVMStatus(int userId, String vmUuid);
	
	/**
	 * 查询vm排名钱n的企业
	 * @param topNum
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public abstract List<Map> getAdminVMTop(int num);
	
	/**
	 * 查询endTime之前的虚拟机总数
	 * @param topNum
	 * @return
	 */
	public abstract Integer getSumVMCount(Date endTime);
	
	/**
	 * 查询vlan的虚拟机总数
	 * @param topNum
	 * @return
	 */
	public abstract Integer getVlanVMCount();
	
	/**
	 * 导出虚拟机列表
	 * @author lining
	 * @return
	 */
	public abstract JSONObject exportVM();
	
	/**
	 * 获取虚拟机通过uuid
	 * @param topNum
	 * @return
	 */
	public abstract OCVM getVMbyId(String uuid);
	/**
	 * 将虚机分配到用户
	 * @param userid 
	 * @param vmuuid 
	 * @return
	 */
	public abstract JSONObject distributeVM2User(String vmuuid, int userid);
	
	public abstract Record createVMOnHost(Connection c, String vmUuid, String tplUuid,
			String loginName, String loginPwd, long cpuCore,
			long memoryCapacity, String mac, String ip, String OS,
			String hostUuid, String imagePwd, String vmName, boolean ping);
	
	public abstract String getAllocateHost(String poolUuid, int memory);
	
	public abstract void assginIpAddress(Connection c, String url, String subnet,
			String vnUuid);
	/**
	 * 虚拟主机添加到路由器
	 * @param vmuuid
	 * @param netid
	 * @param routerid
	 * @param ip
	 * @param netmask
	 * @param gateway
	 * @return
	 */
	public abstract boolean add2Router(String vmuuid,int netid,String routerid,String ip,String netmask,String gateway);
	/**
	 * 虚拟机离线迁移
	 * @param userId 用户id
	 * @param uuid	 虚拟机id
	 * @param tarHost	迁移主机id
	 * @param content	
	 * @param conid
	 * @return
	 */
	public abstract boolean offmigrate(int userId, String uuid, String tarHost,String content, String conid);
	
	/**
	 * 获取usb列表
	 * @param uuid
	 * @param type
	 * @return
	 */
	public abstract JSONArray getUsbList(String uuid, String type);
	
	/**
	 * 挂载usb
	 * @param uuid
	 * @param type
	 * @param address 
	 * @return
	 */
	public abstract boolean createUSB(String uuid, String type, String address);
	
	/**
	 * 列出主机的所有可用usb
	 * @param uuid
	 * @return
	 */
	public abstract JSONArray hostUSBlist(String uuid, String type);
	
	/**
	 * 移除usb设备
	 * @param uuid
	 * @param type
	 * @param usbid
	 * @return
	 */
	public abstract boolean deleteUSB(String uuid, String type,String usbid);
	
}
