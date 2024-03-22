package com.prog4.digitalbank.loan;

import lombok.*;

import java.sql.Date;
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class BankLoan {
    private String id;
    private Double amount;
    private Date loanDate;
    private Double interestAboveSevenDay;
    private String accountId;
    private Double interestSevenDay;

    public BankLoan(String id) {
        this.id = id;
    }
}
