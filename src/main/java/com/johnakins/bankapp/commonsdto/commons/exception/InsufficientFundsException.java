package com.johnakins.bankapp.commonsdto.commons.exception;

public class InsufficientFundsException extends RuntimeException {
    public InsufficientFundsException() {
        super("Insufficient Funds");
    }
}
