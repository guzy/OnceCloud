/**
 * Copyright (2015, ) Institute of Software, Chinese Academy of Sciences
 */
package com.oncedocker.core;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;


/**
 * @author henry(wuheng09!otcaix.iscas.ac.cn)
 * @date   2015年5月12日
 *
 */
public class DockerConfig {

	private final static Logger m_logger = Logger.getLogger(DockerConfig.class);
	
	public final static Properties props = new Properties();
	
	static {
		try {
			props.load(DockerConfig.class.getResourceAsStream(DockerConstants.CONFIG_DOCKER));
		} catch (IOException e) {
			m_logger.error(e);
		}
	}
	
	/**
	 * result is null for the below conditions:
	 *  (1) the key is null
	 *  (2) the key is not founded
	 * 
	 * @param key
	 * @return 
	 */
	public static String getValue(String key) {
		return (key == null) ? null : (String) props.get(key);
	}
	
	public static void main(String[] args) {
		System.out.println(DockerConfig.getValue(DockerConstants.ELEM_REPOURL));
	}
}
