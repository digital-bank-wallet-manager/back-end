package com.prog4.digitalbank.models;

import com.prog4.digitalbank.balance.BalanceServices;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class BalanceModelController {
    private BalanceModelServices balanceModelServices;
    @GetMapping("/account/balance/{accountId}")
    public BalanceModel balanceModel(@PathVariable String accountId){
        return balanceModelServices.actualBalance(accountId);
    }

}
