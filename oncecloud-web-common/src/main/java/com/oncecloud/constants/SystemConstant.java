package com.oncecloud.constants;

import org.springframework.stereotype.Component;

import com.oncecloud.configs.SystemConfig;

@Component
public class SystemConstant {
	public static enum Status {
		不可用, 可用
	};

	public static enum Platform {
		Windows, Linux,ROUTER, LOADBALANCE
	};

	public static String noVNCServer = SystemConfig.getValue("no_vnc");
	public static String noVNCServerPublic = SystemConfig
			.getValue("no_vnc_public");
	public static Double CPU_PRICE = 0.04;
	public static Double MEMORY_PRICE = 0.06;
	public static Double VOLUME_PRICE = 0.02;
	public static Double SNAPSHOT_PRICE = 0.01;
	public static Double EIP_PRICE = 0.03;
	public static Double IMAGE_PRICE = 0.04;
	//主机服务器类型
	public static String BEUONDCLOUD = "beyondCloud";
	public static String VSPHERE = "vSphere";
	public static String DOCKER = "beyondDocker";
	public static String KVM = "KVM";
	//vsphere虚拟机操作
	public static String POWEROFF = "poweroff";
	public static String POWERON = "poweron";
	//xen连接用户名和端口号
	public final static String DEFAULT_USER = "root";
	public final static int DEFAULT_PORT = 9363;
	
	/*static {
		PropertyConfigurator.configure(SystemConstant.class
				.getResourceAsStream("/com/oncecloud/config/log4j.properties"));
	}
	
	public final static Logger getLogger (Class<?> clazz){
		return Logger.getLogger(clazz);
	}*/
}
