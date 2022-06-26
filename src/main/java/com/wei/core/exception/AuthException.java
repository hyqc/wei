package com.wei.core.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author Administrator
 */
public class AuthException extends AuthenticationException {
    public AuthException(String msg) {
        super(msg);
    }
}

