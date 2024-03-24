package com.prog4.digitalbank.loan;

import com.prog4.digitalbank.account.Account;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@AllArgsConstructor
public class LoanController {
    private LoanServices loanServices;
    @PostMapping("/loan")
    public BankLoan bankLoan(@RequestBody BankLoan bankLoan) throws SQLException, IllegalAccessException {
        return loanServices.loanOperation(bankLoan);
    }
    @GetMapping("/loan/{accountId}")
    public List<BankLoan> find (@PathVariable String accountId){
        return loanServices.findBankLoanByAccountId(accountId);
    }
<<<<<<< HEAD


=======
>>>>>>> Prod
}