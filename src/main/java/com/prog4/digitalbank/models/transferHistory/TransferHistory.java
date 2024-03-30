package com.prog4.digitalbank.models.transferHistory;

import lombok.*;

import java.sql.Date;
import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class TransferHistory {
    private String transactionId;
    private String transferId;
    private Double amount;
    private String label;
    private Timestamp effectiveDate;
    private String transferRef;
    private String accountReceiverRef;
    private String receiverName;
    private String foreignAccount;
    private String Status;
}
