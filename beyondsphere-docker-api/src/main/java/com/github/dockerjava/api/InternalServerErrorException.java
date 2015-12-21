package com.github.dockerjava.api;


/**
 * @author langzi
 * 内部服务器异常处理
 */
public class InternalServerErrorException extends DockerException {

	private static final long serialVersionUID = -2450396075981100160L;

	/**
	 * @param message
	 * @param cause
	 */
	public InternalServerErrorException(String message, Throwable cause) {
        super(message, 500, cause);
    }
	
	/**
	 * @param message
	 */
	public InternalServerErrorException(String message) {
        this(message, null);
    }
	
	/**
	 * @param cause
	 */
	public InternalServerErrorException(Throwable cause) {
        this(cause.getMessage(), cause);
    }

}
