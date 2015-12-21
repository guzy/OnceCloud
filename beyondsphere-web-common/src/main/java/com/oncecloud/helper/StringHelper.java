package com.oncecloud.helper;

import java.net.URLEncoder;

import org.springframework.stereotype.Component;

@Component
public final class StringHelper {

	public String convertToHexString(byte[] b) {
		String ret = "";
		for (int i = 0; i < b.length; i++) {
			String hex = Integer.toHexString(b[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			ret += hex;
		}
		return ret;
	}
	
	
	// 可逆的加密算法   
	 public String converToSecret(String inStr) {   
		 
		StringBuffer sbu = new StringBuffer();  
	    char[] chars = inStr.toCharArray();   
	    for (int i = 0; i < chars.length; i++) {  
	        if(i != chars.length - 1)  
	        {  
	            sbu.append((int)chars[i]).append(",");  
	        }  
	        else {  
	            sbu.append((int)chars[i]);  
	        }  
	    }  
	    return sbu.toString();  
		 
	 /* char[] a = inStr.toCharArray();   
	  for (int i = 0; i < a.length; i++) {   
	   a[i] = (char) (a[i] ^ 't');   
	  }   
	  String s = new String(a);   
	  return s;   */
	 }   
	  
	 // 加密后解密   
	 public String secretToNormal(String inStr) {   
		StringBuffer sbu = new StringBuffer();  
	    String[] chars = inStr.split(",");  
	    for (int i = 0; i < chars.length; i++) {  
	        sbu.append((char) Integer.parseInt(chars[i]));  
	    }  
	    return sbu.toString();  
	  /*char[] a = inStr.toCharArray();   
	  for (int i = 0; i < a.length; i++) {   
	   a[i] = (char) (a[i] ^ 't');   
	  }   
	  String k = new String(a);   
	  return k;*/   
	 }

	private byte uniteBytes(byte src0, byte src1) {
		byte _b0 = Byte.decode("0x" + new String(new byte[] { src0 }))
				.byteValue();
		_b0 = (byte) (_b0 << 4);
		byte _b1 = Byte.decode("0x" + new String(new byte[] { src1 }))
				.byteValue();
		byte ret = (byte) (_b0 ^ _b1);
		return ret;
	}

	public byte[] convertToBytes(String src) {
		byte[] ret = new byte[src.getBytes().length / 2];
		byte[] tmp = src.getBytes();
		for (int i = 0; i < ret.length; i++) {
			ret[i] = this.uniteBytes(tmp[i * 2], tmp[i * 2 + 1]);
		}
		return ret;
	}

	public String encodeText(String text) {
		String result = "";
		try {
			result = URLEncoder.encode(text, "utf-8").replace("+", "%20");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}