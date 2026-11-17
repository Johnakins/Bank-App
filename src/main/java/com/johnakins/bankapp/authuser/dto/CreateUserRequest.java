package com.johnakins.bankapp.authuser.dto;
import lombok.Data;

@Data
public  class CreateUserRequest {
        private String email;
        private String password;
        private String customerId;
        
    }