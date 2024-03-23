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
    private String provisioningId;
    private String bankLoanId;
    private String transferId;
    private String expenseId;
    private int subCategoryId;

    public Transaction(double amount, String type, Timestamp dateTime, String accountId) {
        this.amount = amount;
        this.type = type;
        this.dateTime = dateTime;
        this.accountId = accountId;
    }

    public Transaction(double amount,
                       String type,
                       Timestamp dateTime,
                       String accountId ,
                       String provisioningId,
                       String bankLoanId,
                       String transferId,
                       String expenseId) {
        this(amount , type , dateTime , accountId);
        this.provisioningId = provisioningId;
        this.bankLoanId = bankLoanId;
        this.transferId = transferId;
        this.expenseId = expenseId;
    }



}
