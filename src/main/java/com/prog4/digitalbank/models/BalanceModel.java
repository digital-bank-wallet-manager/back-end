package com.prog4.digitalbank.models;


import com.prog4.digitalbank.balance.Balance;
import com.prog4.digitalbank.loan.LoanEvolution;
import lombok.*;

import java.sql.Date;

@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class BalanceModel {
    private String id;
    private Date date;
    private Double balance;
    private Double totalInterest;
    private Double rest;

}
