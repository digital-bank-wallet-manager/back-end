package com.prog4.digitalbank.transfer;

import lombok.*;

import java.sql.Date;
import java.sql.Timestamp;
@AllArgsConstructor
@Getter@Setter@ToString@EqualsAndHashCode
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

    public Transfer(String id) {
        this.id = id;
    }
}
