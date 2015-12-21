package com.github.dockerjava.api;


/**
 * @author langzi
 * 请求无法接受的异常处理
 */
public class NotAcceptableException extends DockerException {

	private static final long serialVersionUID = -1771212181727204375L;

	/**
	 * @param message
	 * @param cause
	 */
	public NotAcceptableException(String message, Throwable cause) {
        super(message, 406, cause);
    }
	
	/**
	 * @param message
	 */
	public NotAcceptableException(String message) {
        this(message, null);
    }
	
	/**
	 * @param cause
	 */
	public NotAcceptableException(Throwable cause) {
        this(cause.getMessage(), cause);
    }

}
