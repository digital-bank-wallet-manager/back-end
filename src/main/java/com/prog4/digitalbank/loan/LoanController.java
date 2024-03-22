package com.prog4.digitalbank.loan;

import com.prog4.digitalbank.account.Account;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
@AllArgsConstructor
public class LoanController {
    private LoanServices loanServices;
    @PostMapping("/loan")
    public BankLoan bankLoan(@RequestBody BankLoan bankLoan) throws SQLException, IllegalAccessException {
        return loanServices.loanOperation(bankLoan);
    }
}
