package com.github.dockerjava.api;


/**
 * @author langzi
 * 冲突异常处理
 */
public class ConflictException extends DockerException {

	private static final long serialVersionUID = -290093024775500239L;

	/**
	 * @param message
	 * @param cause
	 * 调用超类的super方法，返回一个Docker Exception对象
	 */
	public ConflictException(String message, Throwable cause) {
        super(message, 409, cause);
    }
	
	/**
	 * @param message
	 */
	public ConflictException(String message) {
        this(message, null);
    }
	
	/**
	 * @param cause
	 */
	public ConflictException(Throwable cause) {
        this(cause.getMessage(), cause);
    }

}
