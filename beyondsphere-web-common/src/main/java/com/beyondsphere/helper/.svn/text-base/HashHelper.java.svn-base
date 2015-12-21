package com.beyondsphere.helper;

import java.security.MessageDigest;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;


@Component
public class HashHelper {
	
	@Resource
	private StringHelper stringHelper;

	public String sha1Hash(String source) {
		try {
			String ret = new String(source);
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			ret = stringHelper.convertToHexString(
					md.digest(ret.getBytes()));
			return ret;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String md5Hash(String source) {
		try {
			String ret = new String(source);
			MessageDigest md = MessageDigest.getInstance("MD5");
			ret = stringHelper.convertToHexString(
					md.digest(ret.getBytes()));
			return ret.toUpperCase();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public String DiyHash(String source) {
		return stringHelper.converToSecret(source);
	}
	
	public String hashDIY(String source){
		return stringHelper.secretToNormal(source);
	}
}
