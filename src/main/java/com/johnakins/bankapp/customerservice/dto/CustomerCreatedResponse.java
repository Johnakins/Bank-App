// com.digitalbank.customerservice.dto.CustomerCreatedResponse
package com.johnakins.bankapp.customerservice.dto;

import java.time.LocalDateTime;

import com.johnakins.bankapp.customerservice.model.KycStatus;
import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class CustomerCreatedResponse {
    private String externalId;
    private KycStatus kycStatus;
    private Integer version;
    private LocalDateTime createdAt;
}
