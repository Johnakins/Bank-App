package com.johnakins.bankapp.commonsdto.commons.exception;

import java.util.UUID;

public class OwnerAccessDeniedException extends RuntimeException {
    public OwnerAccessDeniedException() {
        super("Invalid Owner " );
    }
}
