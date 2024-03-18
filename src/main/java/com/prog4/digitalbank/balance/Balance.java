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

    public Balance(Double amount , String accountId ) {
        this.accountId = accountId;
        this.amount = amount;

    }

    public Balance(double amount, Timestamp dateTime, String accountId) {
        this.amount = amount;
        this.dateTime = dateTime;
        this.accountId = accountId;
    }
}
