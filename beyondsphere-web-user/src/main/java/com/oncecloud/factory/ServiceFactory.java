/**
 * @author hty
 * @time 上午9:39:37
 * @date 2014年12月5日
 */
package com.oncecloud.factory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.oncecloud.util.OnceProperties;
import com.oncecloud.wrapper.MACServiceWrapper;
import com.oncecloud.wrapper.VNCServiceWrapper;

@Configuration
@ComponentScan
public class ServiceFactory {

	@SuppressWarnings("unchecked")
	@Bean
	public VNCServiceWrapper getVNCFactory() throws Exception {
		Class<VNCServiceWrapper> vncClass = null;
		String className = OnceProperties.load(
				"./com/beyondsphere/config/LoadClass.properties").getProperty(
				"VNCService");
		try {
			vncClass = (Class<VNCServiceWrapper>) Class.forName(className);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vncClass.newInstance();
	}
	
	@SuppressWarnings("unchecked")
	@Bean
	public MACServiceWrapper getMACFactory() throws Exception {
		Class<MACServiceWrapper> macClass = null;
		String className = OnceProperties.load(
				"./com/beyondsphere/config/LoadClass.properties").getProperty(
				"MACService");
		try {
			macClass = (Class<MACServiceWrapper>) Class.forName(className);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return macClass.newInstance();
	}

}
