package com.prog4.digitalbank.account;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;

@AllArgsConstructor
@RestController
public class AccountController {
    private AccountServices accountServices;
    @GetMapping("/accounts")
    public List<Account> findAll () throws SQLException{
        return accountServices.findAll(Account.class);
    }
}
