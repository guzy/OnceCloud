/**
 * 
 */
package com.beyondsphere.core.util;

import java.util.HashMap;
import java.util.Map;

import com.beyondsphere.core.hostinfo.DockerHostInfo;
import com.beyondsphere.core.hostinfo.XenHostInfo;


/**
 * @author luogan 2015年5月19日 下午5:36:28
 */
public class TypeUtils {

	public static final Map<String, String> tMapping = new HashMap<String, String>();

	static {
		tMapping.put("beyondCloud", XenHostInfo.class.getName());
		tMapping.put("beyondDocker", DockerHostInfo.class.getName());
	}

	public static String getClassname(String key) {
		return (key == null) ? null : tMapping.get(key);
	}
	
}
