package com.oncecloud.constants;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.stereotype.Component;

@Component
public class LoggerConfig {
	
	static {
		PropertyConfigurator.configure(LoggerConfig.class
				.getResourceAsStream("/com/oncecloud/config/log4j.properties"));
	}
	
	private static Logger logger = Logger.getLogger(LoggerConfig.class);
	
	public static Logger getInstance(Class<?> clazz){
		return Logger.getLogger(clazz);
	}
	
	public void logDebug(Object message){
		logger.debug(message);
	}
	
	public void logError(Object message){
		logger.error(message);
	}
	
	public void logInfo(Object message){
		logger.info(message);
	}
}
