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
    private Double sum;
    private String categoryName;


    public TransactionSum(Double sum) {
        this.sum = sum;
    }
}
