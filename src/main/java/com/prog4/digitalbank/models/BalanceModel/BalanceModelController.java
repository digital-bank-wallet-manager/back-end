package com.prog4.digitalbank.models.BalanceModel;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.sql.SQLException;

@RestController
@AllArgsConstructor
@RequestMapping("/account/balance")
public class BalanceModelController {
    private BalanceModelServices balanceModelServices;
    @GetMapping("/{accountId}")
    public BalanceModel balanceModel(@PathVariable String accountId){
        return balanceModelServices.actualBalance(accountId);
    }

   @GetMapping("/{accountId}/{date}")
    public BalanceModel balanceModelAtTime(@PathVariable String accountId , @PathVariable Date date) throws SQLException {
        return balanceModelServices.balanceAtDate(accountId , date);
    }

}
