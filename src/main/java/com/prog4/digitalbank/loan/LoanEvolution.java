package com.prog4.digitalbank.loan;

import lombok.*;

import java.sql.Timestamp;
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class LoanEvolution {
    private String id ;
    private Timestamp dateTime;
    private Double totalInterest;
    private Double rest;
    private String bankLoanId;
}
