package com.prog4.digitalbank.models.BankstatementModel;

import lombok.*;

import java.sql.Date;
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class BankStatement {
    private Date date;
    private String transactionRef;
    private String operation;
    private Double amount;
    private Double balance;
    private String pattern;
}
