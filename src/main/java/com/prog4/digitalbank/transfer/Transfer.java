package com.prog4.digitalbank.transfer;

import lombok.*;

import java.sql.Timestamp;
import java.util.Date;

@AllArgsConstructor
@Getter@Setter@ToString@EqualsAndHashCode
@NoArgsConstructor
public class Transfer {
    private String id;
    private Double amount;
    private String reason;
    private Timestamp dateTime;
    private Date effectiveDate;
    private String transferRef;
    private String receiverAccountId;
    private String senderAccountId;
    private String idForeignTransfer;

}
