package com.johnakins.bankapp.commonsdto.commons.exception;
public class JwtAuthenticationException extends RuntimeException {
    public JwtAuthenticationException(String message) {
        super(message);
    }
}
