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
        String id = IdGenerator.generateId(10);
        String firstName = account.getFirstName();
        String lastName = account.getLastName();
        Date birthdate = account.getBirthdate();
        Boolean autho = false ;
        Double salary = account.getMonthlyPay();
        String accountRef = IdGenerator.generateAccountNumber();
        if (CheckAge.calculateAge(birthdate) < 21){
            Account account1 = new Account("you must be up than 21 years old ");
            return account1;
        }else {
            Account insert = new Account(id , firstName ,lastName ,birthdate , autho , salary , accountRef);
            return accountServices.insert(insert);
        }



    }
}
