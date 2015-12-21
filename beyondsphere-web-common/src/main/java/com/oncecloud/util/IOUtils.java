/**
 * Copyright (2015, ) Institute of Software, Chinese Academy of Sciences
 */
package com.oncecloud.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import org.apache.log4j.Logger;

/**
 * @author henry
 * @date 2015年5月22日
 * 
 *
 */
public class IOUtils {

	private final static Logger m_logger = Logger.getLogger(IOUtils.class);
	
	/**
	 * 将inputstream类型转换成String类型，如果is为null，则返回""
	 * 
	 * @param is
	 * @return
	 */
	public static String toString(InputStream is) {
		BufferedReader br = toBufferedReader(is);
		return toStringBuffer(br).toString();
	}
	
	/**
	 * 如果为null，则返回""
	 * 
	 * @param br
	 * @return
	 */
	public static StringBuffer toStringBuffer(BufferedReader br) {
		StringBuffer sb = new StringBuffer();
		
		try {
			String line = null;
			while ((line = br.readLine()) != null) {
				sb.append(line).append("\n");
			}
		} catch (Exception e) {
			m_logger.error(e);
		}
		
		return sb;
	}
	
	/**
	 * 
	 * 如果inputstram无法转换，则返回null
	 * @param is
	 * @return BufferedReader
	 */
	public static BufferedReader toBufferedReader(InputStream is) {
		try {
			return new BufferedReader(new InputStreamReader(is));
		} catch (Exception e) {
			m_logger.error(e);
			return null;
		}
	}
	
	
	/**
	 * @param reader
	 */
	public static void close(Reader reader) {
		if(!ObjectUtils.isNull(reader)) {
			try {
				reader.close();
			} catch (IOException e) {
				m_logger.warn(e);
			}
		}
	}
	
	public static void close(InputStream is) {
		if(!ObjectUtils.isNull(is)) {
			try {
				is.close();
			} catch (IOException e) {
				m_logger.warn(e);
			}
		}
	}
}
