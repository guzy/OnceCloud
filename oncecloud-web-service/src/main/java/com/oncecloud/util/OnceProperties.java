/**
 * @author hty
 * @time 上午9:39:37
 * @date 2014年12月5日
 */
package com.oncecloud.util;

import java.io.IOException;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

public class OnceProperties {

	public static Properties load(String fielPath) throws IOException {
		Properties prop = new Properties();
		prop.load(OnceProperties.class.getClassLoader()
				.getResourceAsStream(fielPath));
		return prop;
	}
	
	public static Properties loadXML(String filePath) throws InvalidPropertiesFormatException, IOException {
		Properties prop = new Properties();
		prop.loadFromXML(OnceProperties.class.getClassLoader()
				.getResourceAsStream(filePath));
		return prop;
	}
	
}
