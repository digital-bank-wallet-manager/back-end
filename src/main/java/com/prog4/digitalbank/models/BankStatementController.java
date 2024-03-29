package com.prog4.digitalbank.models;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class BankStatementController {
    private BankStatementServices bankStatementServices;
    @GetMapping("/bankStatement/{accountId}/{month}")
    public List<BankStatement> bankStatements (@PathVariable String accountId ,@PathVariable int month){
        return bankStatementServices.bankStatements(accountId , month);
    }
}
