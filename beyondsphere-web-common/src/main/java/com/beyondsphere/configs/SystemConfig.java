package com.beyondsphere.configs;

import java.io.FileOutputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * @author hehai
 * @version 2013/03/28
 */
public class SystemConfig {
	private static Properties props;
	private static String configFilePath = "/com/beyondsphere/config/config.xml";
	private static Logger logger = Logger.getLogger(SystemConfig.class);

	static {
		props = new Properties();
		try {
			props.loadFromXML(SystemConfig.class
					.getResourceAsStream(configFilePath));
		} catch (Exception e) {
			logger.debug("Load Configuration Failed");
		}
	}

	public static String getValue(String key) {
		return props.getProperty(key);
	}

	public static void setValue(String key, String value) {
		props.setProperty(key, value);
	}

	public static void setValueFile(String key, String value) {
		try {
			props.setProperty(key, value);
			props.storeToXML(
					new FileOutputStream(SystemConfig.class
							.getResource(configFilePath).getPath()
							.replace("%20", " ")), "");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
