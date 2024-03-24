package com.prog4.digitalbank.models;

import com.prog4.digitalbank.balance.BalanceServices;
import com.prog4.digitalbank.loan.LoanServices;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BalanceModelServices {
    private LoanServices loanServices;
    private BalanceServices balanceServices;

    public BalanceModel actualBalance (String accountId){
        BalanceModel balanceModel = new BalanceModel(balanceServices.actualBalance(accountId),loanServices.actualLoan(accountId));
        return balanceModel;
    }
}
