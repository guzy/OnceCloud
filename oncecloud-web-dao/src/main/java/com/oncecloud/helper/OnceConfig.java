package com.oncecloud.helper;
/*package com.oncecloud.helper;

import java.io.FileOutputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

*//**
 * @author hehai
 * @version 2013/03/28
 *//*
public class OnceConfig {
	private static Properties pro;
	private static String configFilePath = "/com/oncecloud/config/config.xml";
	private static Logger logger = Logger.getLogger(OnceConfig.class);

	static {
		pro = new Properties();
		try {
			pro.loadFromXML(OnceConfig.class
					.getResourceAsStream(configFilePath));
		} catch (Exception e) {
			logger.debug("Load Configuration Failed");
		}
	}

	public static String getValue(String key) {
		return pro.getProperty(key);
	}

	public static void setValue(String key, String value) {
		pro.setProperty(key, value);
	}

	public static void setValueFile(String key, String value) {
		try {
			pro.setProperty(key, value);
			pro.storeToXML(
					new FileOutputStream(OnceConfig.class
							.getResource(configFilePath).getPath()
							.replace("%20", " ")), "");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
*/