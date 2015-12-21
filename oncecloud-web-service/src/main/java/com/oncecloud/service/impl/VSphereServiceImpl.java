package com.oncecloud.service.impl;

import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.oncecloud.vsphere.VMWareUtil;
import com.oncecloud.dao.VSphereDAO;
import com.oncecloud.model.VMPower;
import com.oncecloud.service.VSphereService;
import com.vmware.vim25.ManagedObjectReference;
import com.vmware.vim25.VirtualMachineConfigInfo;
@Service("VSphereService")
public class VSphereServiceImpl implements VSphereService {
	@Resource
	private VSphereDAO vSphereDAO;
	//虚拟机操作
	public void runOperation(String operation,String vmuuid) {
		boolean b = false;
		try {
			Map<String, ManagedObjectReference>  vmlist = VMWareUtil.getAllVms();
			for (Entry<String, ManagedObjectReference> entry : vmlist.entrySet()) {
				ManagedObjectReference vm = entry.getValue();
				VirtualMachineConfigInfo vminfo = new VirtualMachineConfigInfo();
				vminfo = (VirtualMachineConfigInfo) VMWareUtil.getDynamicProperty(vm, "config");
				if(vminfo.getUuid().equals(vmuuid))	{
					b = VMWareUtil.runOperation(operation, vminfo.getName());
					
					if(operation.contains("poweron")&&b){
						vSphereDAO.updateStatusOfPower(vminfo.getUuid(),VMPower.POWER_RUNNING);
					}
					if(operation.contains("poweroff")&&b){
						vSphereDAO.updateStatusOfPower(vminfo.getUuid(),VMPower.POWER_HALTED);
					}
				}
				
			}
		}catch(Exception e){
			e.printStackTrace();
		}		
		
	}	
	//虚拟机销毁
	public void vSphereDestroy(int userId,String vmuuid) {
		boolean b = false;
		try {
			Map<String, ManagedObjectReference>  vmlist = VMWareUtil.getAllVms();
			for (Entry<String, ManagedObjectReference> entry : vmlist.entrySet()) {
				ManagedObjectReference vm = entry.getValue();
				VirtualMachineConfigInfo vminfo = new VirtualMachineConfigInfo();
				vminfo = (VirtualMachineConfigInfo) VMWareUtil.getDynamicProperty(vm, "config");
				if(vminfo.getUuid().equals(vmuuid))	{
					
					String operation = VMWareUtil.callWaitForUpdatesEx("60", "1",vminfo.getName())  ;
					//1、关机
					if(operation.contains("POWERED_ON")){
						b = VMWareUtil.runOperation("poweroff", vminfo.getName());
					}
					//2、销毁
					if(b){
						b = VMWareUtil.deleteManagedEntity(vminfo.getName());
					}
					//3、删除数据库
					if(b){
						b = vSphereDAO.deleteVMByUuid(userId,vminfo.getUuid());
					}
				}
				
			}
		}catch(Exception e){
			e.printStackTrace();
		}		
		
	}
	//关闭电源
	@Override
	public void shutdownVM(String uuid, Integer userId) {
		this.runOperation("poweroff",uuid);
	}
	@Override
	public void startVM(String uuid, Integer userId) {
		this.runOperation("poweron",uuid);
		
	}
	@Override
	public void vmDestory(int userId, String uuid) {
		this.vSphereDestroy(userId, uuid);
		
	}		
}
