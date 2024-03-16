package com.prog4.digitalbank.account;

import com.prog4.digitalbank.idGenretor.IdGenerator;
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
    public Account save (@RequestBody Account account) throws SQLException {
        return accountServices.insert(account);

    }
}
