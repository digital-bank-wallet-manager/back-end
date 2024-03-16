package com.prog4.digitalbank.account;

import com.prog4.digitalbank.CrudOperations.FindAll;
import com.prog4.digitalbank.CrudOperations.Save;
import com.prog4.digitalbank.idGenretor.IdGenerator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
@AllArgsConstructor
@Service
public class AccountServices {
    private FindAll<Account> findAll;
    private Save<Account> save;
    public List<Account> findAll (Class<Account> accountModelClass) throws SQLException {
        return findAll.findAll(accountModelClass);
    }

    public Account insert (Account account) throws SQLException {
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
            return save.insert(insert);
        }

    }

}
