package com.prog4.digitalbank.models;

import com.prog4.digitalbank.account.Account;
import com.prog4.digitalbank.account.AccountServices;
import com.prog4.digitalbank.balance.BalanceServices;
import com.prog4.digitalbank.loan.LoanServices;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.SQLException;

@Service
@AllArgsConstructor
public class BalanceModelServices {
    private LoanServices loanServices;
    private BalanceServices balanceServices;


    public BalanceModel actualBalance (String accountId){
        return new BalanceModel(balanceServices.actualBalance(accountId),loanServices.actualLoan(accountId));
    }

    public BalanceModel balanceAtDate(String accountId , Date date) throws SQLException {
        return new BalanceModel(
                balanceServices.balanceForSpecificTime(accountId , date),
                loanServices.loanEvolutionByDate(accountId,date));

    }
}
