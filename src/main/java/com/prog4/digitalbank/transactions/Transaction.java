package com.prog4.digitalbank.transactions;

import lombok.*;

import java.sql.Timestamp;
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Transaction {
    private String id ;
    private double amount ;
    private String type;
    private Timestamp dateTime ;
    private String accountId;


}
