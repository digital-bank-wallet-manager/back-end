package com.prog4.digitalbank.balance;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.PrivateKey;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

@RestController
@AllArgsConstructor
public class BalanceController {
    private BalanceServices balanceServices;
    @GetMapping("/account/balance/{accountId}/{start}/{end}")
    public List<Balance> save (@PathVariable String accountId,
                         @PathVariable Date start,
                         @PathVariable Date end) throws SQLException {
        return balanceServices.findByAccountIdAndPeriod(accountId , start ,end);
    }

    @GetMapping("/account/balance/{accountId}")
    public List<Balance> findByAccount(@PathVariable String accountId ){
        return balanceServices.findByAccountIdOrdered(Balance.class , accountId);
    }

}
