package com.github.dockerjava.api;


/**
 * @author langzi
 * Indicates that the given entity does not exist.
 */
public class NotFoundException extends DockerException {

	private static final long serialVersionUID = -2450396075981100160L;

	/**
	 * @param message
	 * @param cause
	 */
	public NotFoundException(String message, Throwable cause) {
        super(message, 404, cause);
    }
	
	/**
	 * @param message
	 */
	public NotFoundException(String message) {
        this(message, null);
    }
	
	/**
	 * @param cause
	 */
	public NotFoundException(Throwable cause) {
        this(cause.getMessage(), cause);
    }
}
