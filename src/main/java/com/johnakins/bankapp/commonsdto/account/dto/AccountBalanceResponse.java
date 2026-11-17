package com.johnakins.bankapp.commonsdto.account.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import lombok.*;



public record AccountBalanceResponse(
        BigDecimal balance,
        BigDecimal totalHolds,
        BigDecimal available
) {}