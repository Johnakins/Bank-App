package com.johnakins.bankapp.customerservice.dto;

import com.johnakins.bankapp.customerservice.model.KycStatus;
import lombok.*;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerResponse {
	 private Long id;
	    private String firstName;
	    private String lastName;
	    private String email;
	    private String phone;
	    private String address;
	    private String externalId;
	    private Boolean active;
	    private KycStatus kycStatus;
	    private LocalDateTime createdAt;
	    private LocalDateTime updatedAt;
	    private Integer version;
}