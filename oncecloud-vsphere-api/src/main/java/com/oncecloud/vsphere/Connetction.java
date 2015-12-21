package com.oncecloud.vsphere;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

//import com.oncecloud.helper.HashHelper;
import com.vmware.vim25.ManagedObjectReference;
import com.vmware.vim25.VirtualCdrom;
import com.vmware.vim25.VirtualDevice;
import com.vmware.vim25.VirtualDeviceBackingInfo;
import com.vmware.vim25.VirtualDeviceConfigSpec;
import com.vmware.vim25.VirtualDisk;
import com.vmware.vim25.VirtualE1000;
import com.vmware.vim25.VirtualEthernetCard;
import com.vmware.vim25.VirtualFloppy;
import com.vmware.vim25.VirtualIDEController;
import com.vmware.vim25.VirtualKeyboard;
import com.vmware.vim25.VirtualLsiLogicSASController;
import com.vmware.vim25.VirtualMachineConfigInfo;
import com.vmware.vim25.VirtualMachineVMCIDevice;
import com.vmware.vim25.VirtualMachineVideoCard;
import com.vmware.vim25.VirtualPCIController;
import com.vmware.vim25.VirtualPS2Controller;
import com.vmware.vim25.VirtualPointingDevice;
import com.vmware.vim25.VirtualSIOController;

public class Connetction {
	// 可逆的加密算法   
		 public static String converToSecret(String inStr) {   
			 StringBuffer sbu = new StringBuffer();  
			    char[] chars = inStr.toCharArray();   
			    for (int i = 0; i < chars.length; i++) {  
			        if(i != chars.length - 1)  
			        {  
			            sbu.append((int)chars[i]).append(",");  
			        }  
			        else {  
			            sbu.append((int)chars[i]);  
			        }  
			    }  
			    return sbu.toString();  
		 }   
		  
		 // 加密后解密   
		 public static String secretToNormal(String inStr) {   
			StringBuffer sbu = new StringBuffer();  
		    String[] chars = inStr.split(",");  
		    for (int i = 0; i < chars.length; i++) {  
		        sbu.append((char) Integer.parseInt(chars[i]));  
		    }  
		    return sbu.toString();  
		 }
	public static void main(String args[]) throws Exception{
		
//		HashHelper helper = new HashHelper();
//		String str = converToSecret("12345678");
//		String str12 = secretToNormal("49,50,51,52,53,54,55,56");
//		System.out.println("str---"+str+"----str12---"+str12);
//		System.out.println(System.currentTimeMillis());
//		VMWareUtil.connect("https://192.168.1.154/sdk","root","12345678");
//		//主机属性
//		List<String> list = new ArrayList<String>();
//		String str = VMWareUtil.printHostProductDetails();
//		if(str.length()>0)
//		{
//			String[] hostKey2Value = str.split(":");
//			
//			for(int i = 0;i < hostKey2Value.length;i ++) {
//				list.add(hostKey2Value[i]);
//			}
//		}
//		ManagedObjectReference mor = VMWareUtil.getHostByHostName("promote");
//		VMWareUtil.getDatastoreNameWithFreeSpace(null,"VM02");
		//虚拟机属性
//		String propertyStr = VMWareUtil.callWaitForUpdatesEx("60", "1","VM01")  ;
//		System.out.println(propertyStr);
//		VMWareUtil.getHosts("192.168.1.154");
		
		//所有虚拟机
//				try {
//					Map<String, ManagedObjectReference>  vmlist = VMWareUtil.getAllVms();
//					for (Entry<String, ManagedObjectReference> entry : vmlist.entrySet()) {
//						ManagedObjectReference vm = entry.getValue();
//					
//						Map<String,Object> vmi = new HashMap<String, Object>();
//						VirtualMachineConfigInfo vminfo = new VirtualMachineConfigInfo();
//						try
//						{
//							vminfo = (VirtualMachineConfigInfo) VMWareUtil.getDynamicProperty(vm, "config");
//						}
//						catch(Exception e)
//						{
//							continue;
//						}
//						if(vminfo.isTemplate())
//							continue;
//						else
//						{
//							vminfo.getHardware().getDevice();//硬盘
//							int mem =  vminfo.getHardware().getMemoryMB();//
//							int cpu = vminfo.getHardware().getNumCPU();//cpu
//							int cores = vminfo.getHardware().getNumCoresPerSocket();//每个插槽的CPU核数
//							String vmname = vminfo.getName();//VM名称
//							
//							vminfo.getDatastoreUrl().get(0).getName();//存储的名称
//							vminfo.getDatastoreUrl().get(0).getUrl();//存储的地址
//							List<VirtualDevice> devices= vminfo.getHardware().getDevice();
//							List<Map<String,Object>> map = new ArrayList<Map<String,Object>>();
//							VirtualEthernetCard e1000;
//							//虚拟机所在vlan的uuid
//							int i=0;
//							for(VirtualDevice device:devices){
//								if(device instanceof VirtualIDEController){//IDE0
//								}
//								if(device instanceof VirtualIDEController){//IDE1
//								}
//								if(device instanceof VirtualPS2Controller){//PS2控制器
//								}
//								if(device instanceof VirtualPCIController){//PCI控制器
//								}
//								if(device instanceof VirtualSIOController){//SIO控制器
//								}
//								if(device instanceof VirtualKeyboard){//键盘
//								}
//								if(device instanceof VirtualPointingDevice){//定点设备
//								}
//								if(device instanceof VirtualMachineVideoCard){//显卡
//								}
//								if(device instanceof VirtualMachineVMCIDevice){//VMCI设备
//								}
//								if(device instanceof VirtualLsiLogicSASController){//SCSI控制器
//								}
//								if(device instanceof VirtualCdrom){//CD/DVD驱动器
//								}
//								if(device instanceof VirtualDisk){//硬盘
//									VirtualDisk disk = (VirtualDisk) device;
//								}
//								if(device instanceof VirtualFloppy){//软驱
//								}
//								if(device instanceof VirtualEthernetCard){//适配器类型
//									e1000= (VirtualEthernetCard) device;
//								}
//								System.out.println(++i);
//							}
//						
//						}
//					}
//					
//				} catch (IllegalArgumentException e) {
//					e.printStackTrace();
//				} catch (Exception e) {
//					e.printStackTrace();
//				}finally {
//				}
		
	}
}
