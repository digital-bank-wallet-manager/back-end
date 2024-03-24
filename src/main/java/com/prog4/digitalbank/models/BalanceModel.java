package com.prog4.digitalbank.models;


import com.prog4.digitalbank.balance.Balance;
import com.prog4.digitalbank.loan.LoanEvolution;
import lombok.*;

import java.util.Date;
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class BalanceModel {
    private Balance balance;
    private LoanEvolution loanEvolution;

}
