package com.wei.core.exception;

/**
 * 业务异常
 * @author innocence
 */
public class BusinessException extends RuntimeException {

    public BusinessException(String message){
        super(message);
    }

}