package com.github.dockerjava.api;


/**
 * @author langzi
 * 客户端存在缓存异常类处理
 */
public class NotModifiedException extends DockerException {

	private static final long serialVersionUID = -290093024775500239L;

	/**
	 * @param message
	 * @param cause
	 */
	public NotModifiedException(String message, Throwable cause) {
        super(message, 304, cause);
    }
	
	/**
	 * @param message
	 */
	public NotModifiedException(String message) {
        this(message, null);
    }
	
	/**
	 * @param cause
	 */
	public NotModifiedException(Throwable cause) {
        this(cause.getMessage(), cause);
    }

}
