package com.prog4.digitalbank.balance;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.PrivateKey;
import java.sql.SQLException;

@RestController
@AllArgsConstructor
public class BalanceController {
    private BalanceServices balanceServices;
    @PostMapping("/balance/save")
    public Balance save (@RequestBody Balance balance) throws SQLException {
        return balanceServices.save(balance);
    }
}
