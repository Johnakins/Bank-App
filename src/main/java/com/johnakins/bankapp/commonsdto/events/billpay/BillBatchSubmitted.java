package com.johnakins.bankapp.commonsdto.events.billpay;

import lombok.*;

@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class BillBatchSubmitted {
  private String eventId;
  private String batchId;
  private String correlationId;
  private String occurredAt;
  private String schemaVersion;
}
