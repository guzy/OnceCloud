/**
 * @author hty
 * @time 上午11:05:48
 * @date 2014年12月9日
 */
package com.beyondsphere.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.beyondsphere.entity.OCVM;
import com.beyondsphere.model.VMPlatform;
import com.beyondsphere.model.VMPower;
import com.beyondsphere.model.VMStatus;

public interface VMDAO extends QueryListDAO<OCVM>{
	/**
	 * 获取主机
	 * 
	 * @param vmUuid
	 * @return
	 */
	public abstract OCVM getVM(String vmUuid);

	/**
	 * 获取使用中的主机
	 * 
	 * @param vmUuid
	 * @return
	 */
	public abstract OCVM getAliveVM(String vmUuid);

	/**
	 * 获取一页用户主机列表
	 * 
	 * @param userId
	 * @param page
	 * @param limit
	 * @param search
	 * @return
	 */
	public abstract List<OCVM> getOnePageVMs(int userId, int page, int limit,
			String search);
	
	/**
	 * 获取用户主机列表
	 * 
	 * @param userId
	 * @param page
	 * @param limit
	 * @param search
	 * @return
	 */
	public abstract List<OCVM> getVMsByUser(int userId);

	/**
	 * 获取一页管理员主机列表
	 * 
	 * @param page
	 * @param limit
	 * @param host
	 * @param importance
	 * @return
	 */
	public abstract List<OCVM> getOnePageVMsOfAdmin(int page, int limit,
			String host, int importance, int userid);

	/**
	 * 获取管理员主机总数
	 * 
	 * @param host
	 * @param importance
	 * @return
	 */
	public abstract int countVMsOfAdmin(String host, int importance, int userid);
	
	/**
	 * 获取虚拟主机总数
	 * 
	 * @param hostUuid
	 * @return
	 */
	public abstract int countAllVms();

	/**
	 * 获取指定服务器的主机总数
	 * 
	 * @param hostUuid
	 * @return
	 */
	public abstract int countVMsOfHost(String hostUuid);

	/**
	 * 预创建虚拟机
	 * 
	 * @param vmUuid
	 * @param vmPWD
	 * @param vmUID
	 * @param vmName
	 * @param vmPlatform
	 * @param vmMac
	 * @param vmMem
	 * @param vmCpu
	 * @param vmPower
	 * @param vmStatus
	 * @param createDate
	 * @return
	 */
	public abstract boolean preCreateVM(String vmUuid, String vmPWD,
			Integer vmUID, String vmName, Integer vmPlatform, String vmMac,
			Integer vmMem, Integer vmCpu, Integer vmPower, Integer vmStatus,
			Date createDate);

	/**
	 * 删除主机
	 * 
	 * @param userId
	 * @param vmUuid
	 */
	public abstract void removeVM(int userId, String vmUuid);

	/**
	 * 更新主机
	 * 
	 * @param vm
	 */
	public abstract boolean updateVM(OCVM vm);

	public abstract void saveVM(OCVM vm);

	/**
	 * 实际删除数据库信息
	 * @param vmUuid
	 * @return
	 */
	public abstract void deleteVM(OCVM vm, boolean flag);
	
	/**
	 * 更新主机
	 * 
	 * @param vmUuid
	 * @param vmPWD
	 * @param vmPower
	 * @param hostUuid
	 * @param ip
	 * @return
	 */
	public abstract boolean updateVM(String vmUuid, String vmPWD,
			int vmPower, String hostUuid);

	/**
	 * 更新主机电源状态和所在服务器
	 * 
	 * @param session
	 * @param uuid
	 * @param power
	 * @param hostUuid
	 */
	public abstract void updatePowerAndHost(String uuid,
			int power, String hostUuid);

	/**
	 * 更新主机电源状态
	 * 
	 * @param uuid
	 * @param powerStatus
	 * @return
	 */
	public abstract boolean updatePowerStatus(String uuid, int powerStatus);

	/**
	 * 更新主机所在服务器
	 * 
	 * @param uuid
	 * @param hostUuid
	 * @return
	 */
	public abstract boolean updateHostUuid(String uuid, String hostUuid);

	/**
	 * 获取所有OCVM对象
	 * @return
	 */
	public abstract List<String> getAllList();
	
	/**
	 * 获取同一个资源池内VM的列表
	 * @author lining
	 * @param poolUuid
	 * @return
	 */
	public abstract List<OCVM> getAllVmList();
	
	/**
	 * 获取同一个资源池下最大的cpu数和内存数
	 * @return
	 */
	public abstract Map<String, Object> getMaxCpuAndMemByPoolUuid(String poolUuid);
	
	/**
	 * 获取同一个资源池内VM的列表
	 * @author lining
	 * @param poolUuid
	 * @return
	 */
	public abstract List<OCVM> getVmListByPoolUuid(String poolUuid);

	/**
	 * vlan号统计同一个vlan中虚拟机数
	 * @author lining
	 * @param poolUuid
	 * @return
	 */
	public abstract List<OCVM> getVmListByVlan(String netId);

	/**
	 * 根据vlan号统计同一个vlan中虚拟机个数
	 * @param netId
	 * @return
	 */
	public abstract int countNumberOfVMByVlan(String netId);
	
	/**
	 * 根据hostUuid获取vm列表
	 * @author lining
	 * @param hostUuid
	 * @return
	 * 
	 */
	public abstract List<OCVM> getVmListByhostUuid(String hostUuid);
	
	/**
	 * 获取钱n名虚拟机使用最多的用户
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public abstract List<Map> getVMCountTop(int num);
	
	/**
	 * 获取endTime之前虚拟机使用总数
	 * @param endTime
	 * @return
	 */
	public abstract Integer getSumVMCount(Date endTime);
	
	/**
	 * 获取vlan虚拟机使用总数
	 * @return
	 */
	public abstract Integer getVlanVMCount();
	/**
	 * 通过vsphere保存虚拟机
	 * @return
	 */
	public abstract boolean saveVmFromVSphere(OCVM vm);
	
	/**
	 * 预创建虚拟机
	 * 
	 * @param vmUuid
	 * @param vmPWD
	 * @param vmUID
	 * @param vmName
	 * @param vmPlatform
	 * @param vmMac
	 * @param vmMem
	 * @param vmCpu
	 * @param vmPower
	 * @param vmStatus
	 * @param createDate
	 * @param tplUuid
	 * @return
	 */
	public abstract boolean preCreateVM(String vmUuid, String vmPwd,
			Integer vmUid, String vmName, VMPlatform vmPlatform, String vmMac,
			Integer vmMem, Integer vmCpu, VMPower vmPower, VMStatus vmStatus,
			Date vmCreateDate, String tplUuid);

	/**
	 * 通过虚拟机uuid获取一台虚拟机
	 * 
	 * @param vmUuid
	 * @return
	 */
	public abstract OCVM getVMByUuid(String vmUuid);

	/**
	 * 通过uuid删除虚拟机，vm_power = 0
	 * 
	 * @param vmUuid
	 * @return
	 */
	public abstract boolean deleteVMByUuid(int userId, String vmUuid);

	/**
	 * 创建虚拟机成功时，更新虚拟机信息
	 * 
	 * @param userId
	 * @param vmUuid
	 * @param pwd
	 * @param vmPower
	 * @param hostUuid
	 * @return
	 */
	public abstract boolean updateVMPowerWhenCreateVM(int userId,
			String vmUuid, String pwd, VMPower vmPower, String hostUuid);

	/**
	 * 启动/销毁虚拟机时，更改虚拟机数据信息
	 * 
	 * @param vmUuid
	 * @param vmPower
	 * @return
	 */
	public abstract boolean updateVMPower(String vmUuid, VMPower vmPower);

	/**
	 * 更改虚拟机所在物理服务器
	 * 
	 * @param vmUuid
	 * @param hostUuid
	 * @return
	 */
	public abstract boolean updateVMHost(String vmUuid, String vmHostUuid);

	/**
	 * 更改虚拟机配置
	 * 
	 * @param vmUuid
	 * @param vmMem
	 * @param vmCpu
	 * @return
	 */
	public abstract boolean updateVMConfigure(String vmUuid, int vmMem,
			int vmCpu);

	/**
	 * 根据用户id，获取该用户所有虚拟机数量
	 * 
	 * @param userId
	 * @param search
	 * @return
	 */
	public abstract int countByUserId(int userId, String search);

	/**
	 * 根据用户id，获取展示给该用户一定数量的虚拟机列表
	 * 
	 * @param userId
	 * @param page
	 * @param limit
	 * @param search
	 * @return
	 */
	public abstract List<OCVM> getOnePageByUserId(int userId, int page,
			int limit, String groupUuid, String search);
	
	
	/**
	 * @author lining
	 * @param hostUuid
	 * 根据hostUuid获取所有的虚拟机
	 */
	public abstract int getUsedMemByHostUuid(String hostUuid);
	
	/**
	 * 更新vm的备份时间
	 * 
	 * @param vmUuid
	 * @param backdate
	 * @return
	 */
	public abstract boolean updateVMBackdate(String vmUuid, Date backdate);

	/**
	 * 判断虚拟机是否仍然存活
	 * 
	 * @param vmUuid
	 * @return
	 */
	public abstract boolean isActive(String vmUuid);
	
	/**
	 * 获取设置了监控警告的主机列表
	 * 
	 * @return
	 */
	public abstract List<OCVM> getVMsWithAlarm();
	
	/**
	 * 获取一页未设置监控警告的主机列表
	 * 
	 * @param page
	 * @param limit
	 * @param search
	 * @param userId
	 * @return
	 */
	public abstract List<OCVM> getOnePageVMsWithoutAlarm(int page, int limit,
			String search, int userId);
	
	/**
	 * 获取未设置监控警告的主机总数
	 * 
	 * @param search
	 * @param userId
	 * @return
	 */
	public abstract int countVMsWithoutAlarm(String search, int userId);
	
	/**
	 * 更新主机监控警告
	 * 
	 * @param vmUuid
	 * @param alarmUuid
	 */
	public abstract boolean updateAlarm(String vmUuid, String alarmUuid);
	
	/**
	 * 获取对应监控警告的主机列表
	 * 
	 * @param vmUID
	 * @param alarmUuid
	 * @return
	 */
	public abstract List<OCVM> getVMsOfAlarm(int vmUid, String alarmUuid);

	/**
	 * 是否有主机具有该监控警告
	 * 
	 * @param alarmUuid
	 * @return
	 */
	public abstract boolean isNotExistAlarm(String alarmUuid);
	
	/**
	 * 更新虚拟机的描述信息
	 * @author lining
	 * @return
	 * 
	 */
	public void updateName(String vmUuid, String vmName, String vmDesc);
	
	/**
	 * 获取未设置分组的主机总数
	 * 
	 * @param userId
	 * @return
	 */
	public abstract int countVMsWithoutGroup(int userId);
	
	/**
	 * 获取一页未设置分组的主机列表
	 * 
	 * @param page
	 * @param limit
	 * @param userId
	 * @return
	 */
	public abstract List<OCVM> getOnePageVMsWithoutGroup(int page, int limit, int userId);
	
	/**
	 * 更新主机分组信息
	 * @author lining
	 * @param userId, vmuuid, groupUuid
	 * @return
	 */
	public abstract boolean updateGroup(String vmUuid, String groupUuid);
	
	/**
	 * 清空虚拟机的分组
	 * @author lining
	 * @param groupUuid
	 * @return
	 */
	public abstract boolean cleanVMGroup(String groupUuid);
	
	/**
	 * 是否有主机属于该分组
	 * @author lining
	 * @param groupUuid
	 * @return
	 */
	public abstract boolean isNotExistGroup(String groupUuid);
	
	/**
	 * 获取对应分组的主机列表
	 * 
	 * @param vmUID
	 * @param groupUuid
	 * @return
	 */
	public abstract List<OCVM> getVMsOfGroup(int page, int limit, int vmUid, String groupUuid);
	
	/**
	 * 获取对应分组的主机列表
	 * 
	 * @param vmUID
	 * @param groupUuid
	 * @return
	 */
	public abstract List<OCVM> getVMsOfGroup(int vmUid, String groupUuid);
}
