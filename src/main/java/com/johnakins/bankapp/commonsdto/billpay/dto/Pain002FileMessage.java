package com.johnakins.bankapp.commonsdto.billpay.dto;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import com.johnakins.bankapp.commonsdto.events.billpay.Pain002Message;

public record Pain002FileMessage(
        UUID batchId,
        List<Pain002Message> items,
        OffsetDateTime generatedAt
) {}