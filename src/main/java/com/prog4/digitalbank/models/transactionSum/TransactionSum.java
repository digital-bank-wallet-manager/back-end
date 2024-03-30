package com.prog4.digitalbank.models.transactionSum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TransactionSum {
    private Double transactionSum;
    private String category;
    private Date start;
    private Date end;

    public TransactionSum(Double transactionSum) {
        this.transactionSum = transactionSum;
    }
}
