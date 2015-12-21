package com.oncecloud.service;

import java.io.IOException;

import org.springframework.http.HttpHeaders;

import com.oncecloud.model.CaptchaNumber;


/**
 * @author hty
 * 生成验证码
 */
public interface CaptchaService {
	
	public abstract CaptchaNumber getCaptchaNumber();
	
	public abstract HttpHeaders getCaptchaHeaders();
	
	public abstract byte[] getImage(Integer num1, Integer num2)throws IOException ;
}
