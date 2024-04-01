package com.prog4.digitalbank.account;

import com.prog4.digitalbank.CrudOperations.FindAll;
import com.prog4.digitalbank.CrudOperations.FindById;
import com.prog4.digitalbank.CrudOperations.Save;
import com.prog4.digitalbank.Messages;
import com.prog4.digitalbank.balance.Balance;
import com.prog4.digitalbank.balance.BalanceServices;
import com.prog4.digitalbank.methods.CheckAge;
import com.prog4.digitalbank.methods.IdGenerators;
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
    private AccountRepository accountRepository;
    private BalanceServices balanceServices;
    public List<Account> findAll (Class<Account> accountModelClass) throws SQLException {
        return findAll.findAll(accountModelClass , "");
    }
    public Messages insert (Account account) throws SQLException {
        String id = IdGenerators.generateId(10);
        String firstName = account.getFirstName();
        String lastName = account.getLastName();
        Date birthdate = account.getBirthdate();
        Boolean authorization = false ;
        Double salary = account.getMonthlyPay();
        String accountRef = IdGenerators.generateAccountNumber();
        if (CheckAge.calculateAge(birthdate) < 21){
            return new Messages(null,"error: you must be up than 21 years old ");

        }else {
            Account insert = new Account(
                    id,
                    firstName,
                    lastName,
                    birthdate,
                    authorization,
                    salary,
                    accountRef);

            Balance firstBalance = new Balance(0.0 ,id);
            Account saved =  save.insert(insert);
            balanceServices.saveBalance(firstBalance);
            return new Messages(insert.getId(),null);
        }

    }

    public Account findById (Class<Account> accountClass , String id){
        return findById.findByIdOrderd(accountClass , id , "");
    }

    public Messages giveAuthorization (String id ){
        Account account = findById(Account.class , id);
        if (account != null){
         accountRepository.updateAuthorization(
                "account" ,
                id ,
                "loan_authorization",
                true);
            return new Messages("you are now allowed to loan from D-Bank",null);
        }else {
            return new Messages(null,"account with "+id+" doesn't exist");
        }
    }

    public Messages updateSalary (String id , Double salary){
        Account account = findById(Account.class , id);
        if (account != null){
         accountRepository.updateMonthlyPay(
                "account",
                id,
                "monthly_pay",
                salary);
         return new Messages(account.getAccountRef()+" new monthly pay :"+salary,null);
        }else {
            return new Messages(null,"account with "+id+" doesn't exist");
        }
    }

    public Account findByAccountRef (String accountRef , String firstName , String lastName){
        return accountRepository.findByAccountRef(accountRef , firstName , lastName);
    }

}
