package com.github.dockerjava.api;

/**
 * @author langzi
 * 访问未经授权的页面是异常处理
 */
public class UnauthorizedException extends DockerException {

	private static final long serialVersionUID = 8257731964780578278L;

	/**
	 * @param message
	 * @param cause
	 */
	public UnauthorizedException(String message, Throwable cause) {
        super(message, 401, cause);
    }
	
	/**
	 * @param message
	 */
	public UnauthorizedException(String message) {
        this(message, null);
    }
	
	/**
	 * @param cause
	 */
	public UnauthorizedException(Throwable cause) {
        this(cause.getMessage(), cause);
    }

}
