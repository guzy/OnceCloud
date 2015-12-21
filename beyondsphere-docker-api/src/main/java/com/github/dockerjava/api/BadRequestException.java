package com.github.dockerjava.api;

/**
 * @author langzi
 * 请求信息的异常处理
 */
public class BadRequestException extends DockerException {

	private static final long serialVersionUID = -2450396075981100160L;

	/**
	 * @param message
	 * @param cause
	 * Contructor 调用super方法
	 */
	public BadRequestException(String message, Throwable cause) {
        super(message, 400, cause);
    }
	
	/**
	 * @param message
	 * Contructor 调用本地构造方法，返回一个BadRequestException的对象实例
	 */
	public BadRequestException(String message) {
        this(message, null);
    }
	
	/**
	 * @param cause
	 * Contructor
	 */
	public BadRequestException(Throwable cause) {
        this(cause.getMessage(), cause);
    }

}
