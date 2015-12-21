package com.oncecloud.vsphere;

public class ConstantUtil {

	public static String  getPara(String para) {
		String path = ConstantUtil.class.getResource("/").getPath(); 
		path = path.substring(1, path.indexOf("classes"));
		path = path + "config.properties";

//		Properties property = PropertyUtil.getProperties(path);
		
//		return property.getProperty(para);
		return "";
	}
}
