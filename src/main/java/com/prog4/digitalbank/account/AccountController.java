package com.prog4.digitalbank.account;

import com.prog4.digitalbank.Messages;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
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
    @PostMapping("/accounts/save")
    public Messages save (@RequestBody Account account) throws SQLException {
        return accountServices.insert(account);

    }

    @GetMapping ("/account/{id}")
    public Account findById (@PathVariable String id ){
        return accountServices.findById(Account.class , id);
    }

    @PutMapping("/account/loan/{id}")
    public Messages giveLoanAuthorization(@PathVariable String id){
        return accountServices.giveAuthorization(id);
    }

    @PutMapping("/account/salary/{id}/{salary}")
    public Messages updateSalary(@PathVariable String id , @PathVariable Double salary){
        return accountServices.updateSalary(id , salary);
    }

}
