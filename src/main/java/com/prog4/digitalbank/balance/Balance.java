package com.prog4.digitalbank.balance;

import lombok.*;

import java.sql.Timestamp;
import java.util.Date;

@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class Balance {
    private String id;
    private double amount;
    private Timestamp dateTime;
    private String accountId;
    private String transactionId;

    public Balance(Double amount , String accountId ) {
        this.accountId = accountId;
        this.amount = amount;

    }

    public Balance(double amount, Timestamp dateTime, String accountId , String transactionId) {
        this.amount = amount;
        this.dateTime = dateTime;
        this.accountId = accountId;
        this.transactionId = transactionId;
    }

    public Balance(String id, Double amount, Timestamp dateTime, String accountId) {
        this.id = id;
        this.amount = amount;
        this.dateTime = dateTime;
        this.accountId = accountId;

    }
}
