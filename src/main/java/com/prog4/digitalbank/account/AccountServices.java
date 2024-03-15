package com.prog4.digitalbank.account;

import com.prog4.digitalbank.CrudOperations.FindAll;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
@AllArgsConstructor
@Service
public class AccountServices {
    private FindAll findAll;
    public List<Account> findAll (Class<Account> accountModelClass) throws SQLException {
        return findAll.findAll(accountModelClass);
    }

}
