package com.johnakins.bankapp.commonsdto.commons.exception;
public class UnauthorizedAccessException extends RuntimeException {
    public UnauthorizedAccessException(String message) {
        super(message);
    }
}
