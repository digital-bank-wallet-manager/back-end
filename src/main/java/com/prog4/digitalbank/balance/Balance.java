package com.prog4.digitalbank.balance;

import lombok.*;

import java.sql.Timestamp;
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

    public Balance(Double amount , String accountId) {
        this.accountId = accountId;
        this.amount = amount;
    }
}
