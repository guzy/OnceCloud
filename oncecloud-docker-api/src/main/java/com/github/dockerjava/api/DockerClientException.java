package com.github.dockerjava.api;


/**
 * @author langzi
 * Docker Client的异常信息处理
 */
public class DockerClientException extends RuntimeException {

private static final long serialVersionUID = 7667768099261650608L;
	
    /**
     * @param message
     * Contructor 返回特定的DockerException对象
     */
    public DockerClientException(String message) {
        super(message);
    }

    /**
     * @param message
     * @param cause
     */
    public DockerClientException(String message, Throwable cause) {
        super(message, cause);
    }
}
