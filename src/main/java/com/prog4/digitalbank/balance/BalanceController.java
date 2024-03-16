package com.prog4.digitalbank.balance;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.PrivateKey;
import java.sql.SQLException;
import java.util.List;

@RestController
@AllArgsConstructor
public class BalanceController {
    private BalanceServices balanceServices;
    @PostMapping("/balance/save")
    public Balance save (@RequestBody Balance balance) throws SQLException {
        return balanceServices.saveBalance(balance);
    }

}
