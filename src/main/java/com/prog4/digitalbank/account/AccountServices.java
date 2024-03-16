package com.prog4.digitalbank.account;

import com.prog4.digitalbank.CrudOperations.FindAll;
import com.prog4.digitalbank.CrudOperations.FindById;
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
    private FindById<Account> findById;
    public List<Account> findAll (Class<Account> accountModelClass) throws SQLException {
        return findAll.findAll(accountModelClass);
    }
    public Account insert (Account account) throws SQLException {
        String id = IdGenerator.generateId(10);
        String firstName = account.getFirstName();
        String lastName = account.getLastName();
        Date birthdate = account.getBirthdate();
        Boolean authorization = false ;
        Double salary = account.getMonthlyPay();
        String accountRef = IdGenerator.generateAccountNumber();
        if (CheckAge.calculateAge(birthdate) < 21){
            Account error = new Account("error: you must be up than 21 years old ");
            return error;
        }else {
            Account insert = new Account(
                    id,
                    firstName,
                    lastName,
                    birthdate,
                    authorization,
                    salary,
                    accountRef);
            return save.insert(insert);
        }

    }

    public Account findById (Class<Account> accountClass , String id){
        return findById.findById(accountClass , id);
    }

}
