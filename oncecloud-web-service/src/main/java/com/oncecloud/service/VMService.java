package com.oncecloud.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.Xen.API.Connection;
import org.json.JSONArray;
import org.json.JSONObject;

import com.oncecloud.entity.OCVM;
import com.oncecloud.model.VMResult;

public interface VMService {
	
	/**
	 * 获取vm
	 * @author lining
	 * @param vmUuid
	 * @return
	 */
	public abstract OCVM getVm(String vmUuid); 
	
	/**
	 * 创建vm
	 * @author lining
	 * @param 
	 * @return
	 */
	public boolean preCreateVM(String vmUuid, String vmPWD, Integer vmUID,
			String vmName, Integer vmPlatform, String vmMac, Integer vmMem,
			Integer vmCpu, Integer vmPower, Integer vmStatus, Date createDate);
	
	/**
	 * 保存虚拟机
	 * @author lining
	 * @param vm
	 * @return
	 */
	public abstract void saveVM(OCVM vm);
	
	
	/**
	 * 更新vm
	 * @author lining
	 * @param vm
	 * @return
	 */
	public boolean updateVm(OCVM vm);
			
	/**
	 * 删除vm
	 * @author lining
	 * @param userId, vmUuid
	 * @return
	 */
	public abstract boolean removeVm(Integer userId, String vmUuid);
	
	/**
	 * 删除vm
	 * @author lining
	 * @param vm
	 * @return
	 */
	public abstract void removeVm(OCVM vm);	
	
	/**
	 * 更新vm电源状态
	 * @author lining
	 * @param vmUuid
	 * @return
	 */
	public abstract boolean updateStatusOfPower(String vmUuid, int status);
	
	/**
	 * 更新vm电源状态
	 * @author lining
	 * @param vmUuid
	 * @return
	 */
	public abstract void updatePowerAndHost(String uuid, int power, String hostUuid);
	
	/**
	 * 更新vm所属于物理主机的uuid
	 * @author lining
	 * @param vmUuid
	 * @return
	 */
	public abstract boolean updateHostUuidOfVM(String vmUuid, String hostUuid);
	
	/**
	 * 获取vm的资源池id
	 * @author lining
	 * @param vmUuid
	 * @return
	 */
	public abstract String getPoolUuidOfVM(String vmUuid);
	
	/**
	 * 获取服务器上的虚拟机数
	 * @author lining
	 * @param hostUuid
	 * @return
	 */
	public abstract int countVMNumberOfHost(String hostUuid);
	
	/**
	 * 获取服务器上的Vlan数
	 * @author lining
	 * @param hostUuid
	 * @return
	 */
	public abstract int countVlanNumberOfVM(String netId);
	
	/**
	 * 获取对应服务器上的虚拟机列表
	 * @author lining
	 * @param page, limit, host, importance, type
	 * @return 
	 */
	public abstract JSONArray getAdminVMList(int page, int limit, String host,
			int importance, int userid, String type);
	
	/**
	 * 获取虚拟机数量前n名的用户
	 * @param page
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public abstract List<Map> getVMCountTop(int num);
	
	/**
	 * 获取虚拟机总数
	 * @return
	 */
	public abstract Integer getSumVMCount(Date endTime);
	
	/**
	 * 获取vlan虚拟机总数
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
	 * 将虚机分配给用户
	 * @param vmuuid
	 * @param userid
	 * @return
	 */
	public abstract JSONObject distributeVM2User(String vmuuid, int userid);
	/**
	 * 根据vmuuid查询主机类型
	 * @param uuid
	 * @return
	 */
	public abstract String getHostType(String uuid);
	
	/**
	 * 删除vm
	 * 
	 * @param userId
	 * @param uuid
	 * @param c
	 */
	public abstract boolean deleteVM(int userId, String uuid, Connection c);

	/**
	 * 重启vm
	 * 
	 * @param userId
	 * @param uuid
	 * @param c
	 * @param vmResult
	 */
	public abstract boolean restartVM(String uuid, Connection c);

	/**
	 * 启动vm
	 * 
	 * @param c
	 * @param uuid
	 * @param poolUuid
	 */
	public abstract boolean startVM(String uuid, Connection c, String hostUuid);

	/**
	 * 关闭vm
	 * 
	 * @param userId
	 * @param uuid
	 * @param force
	 * @param c
	 */
	public abstract boolean shutdownVM(String uuid, String force, Connection c);

	/**
	 * 获取用户主机列表
	 * 
	 * @param userId
	 * @param page
	 * @param limit
	 * @param search
	 * @return
	 */
	public abstract JSONArray getVMList(int userId, int page, int limit, String groupUuid,
			String search);

	/**
	 * 创建虚拟机
	 * 
	 * @param vmUuid
	 * @param tplUuid
	 * @param userId
	 * @param vmName
	 * @param cpuCore
	 * @param memorySize
	 * @param pwd
	 * @param hostUuid
	 * @param mac
	 * @param c
	 * @param result
	 */
	public abstract boolean createVM(String vmUuid, String tplUuid, int userId,
			String vmName, int cpu, int memory, String pwd, String hostUuid,
			String mac, VMResult result, Connection c);

	/**
	 * 调整虚拟机配置
	 * 
	 * @param uuid
	 * @param userId
	 * @param cpu
	 * @param mem
	 * @param c
	 * @return
	 */
	public abstract boolean adjustMemAndCPU(String uuid, int userId, int cpu,
			int mem, Connection c);

	/**
	 * 获取一个主机的数据信息
	 * 
	 * @param uuid
	 * @return
	 */
	public abstract OCVM getOCVM(String uuid);

	/**
	 * 删除一个主机的数据信息
	 * 
	 * @param uuid
	 * @return
	 * @throws Exception
	 */
	public abstract boolean deleteOCVM(int useriId, String vmUuid);
	
	public abstract JSONArray getVMListOfVolume(int userId, int page, int limit,
			String search);
	
	/**
	 * 校验虚拟机状态
	 * @author lining
	 * @return
	 * 
	 */
	public abstract boolean checkVMStatus(int userId, String vmUuid);
	
	/**
	 * 获取虚拟机详细信息
	 * @author lining
	 * @return
	 */
	public abstract JSONObject getVMDetail(String vmUuid);
	
}
