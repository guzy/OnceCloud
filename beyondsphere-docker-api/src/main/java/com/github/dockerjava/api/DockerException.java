package com.github.dockerjava.api;




/**
 *
 * @author Konstantin Pelykh (kpelykh@gmail.com)
 * DockerException是DockerAPI中所有异常类的超类，所有的异常类都集成于此。
 */

public class DockerException extends RuntimeException {

private static final long serialVersionUID = 7667768099261650608L;
	
	private int httpStatus = 0;

    /**
     * @param message
     * @param httpStatus
     * Constructor
     */
    public DockerException(String message, int httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    /**
     * @param message
     * @param httpStatus
     * @param cause
     */
    public DockerException(String message, int httpStatus, Throwable cause) {
        super(message, cause);
    }
    
    /**
     * @return
     * 获得http请求的状态
     */
    public int getHttpStatus() {
		return httpStatus;
	}
}
