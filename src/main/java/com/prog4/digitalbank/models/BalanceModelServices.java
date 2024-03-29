package com.prog4.digitalbank.models;

import com.prog4.digitalbank.account.Account;
import com.prog4.digitalbank.account.AccountServices;
import com.prog4.digitalbank.balance.Balance;
import com.prog4.digitalbank.balance.BalanceServices;
import com.prog4.digitalbank.loan.LoanEvolution;
import com.prog4.digitalbank.loan.LoanServices;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

@Service
@AllArgsConstructor
public class BalanceModelServices {
    private LoanServices loanServices;
    private BalanceServices balanceServices;


    public BalanceModel actualBalance (String accountId){
        Balance balance = balanceServices.actualBalance(accountId);
        LoanEvolution loanEvolution = loanServices.actualLoan(accountId);
        return new BalanceModel(balance.getId(),
                Date.valueOf(LocalDate.now()),
                balance.getAmount(),
                loanEvolution.getTotalInterest(),
                loanEvolution.getRest());
    }

    public BalanceModel balanceAtDate(String accountId , Date date) throws SQLException {
        Balance balance = balanceServices.balanceForSpecificTime(accountId,date);
        LoanEvolution loanEvolution = loanServices.loanEvolutionByDate(accountId,date);
        return new BalanceModel(balance.getId(),
                date,
                balance.getAmount(),
                loanEvolution.getTotalInterest(),
                loanEvolution.getRest());
    }
}
