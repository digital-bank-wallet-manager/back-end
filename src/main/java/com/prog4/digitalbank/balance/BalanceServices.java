package com.prog4.digitalbank.balance;

import com.prog4.digitalbank.CrudOperations.Save;
import com.prog4.digitalbank.idGenretor.IdGenerator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class BalanceServices {
    private Save<Balance> save;

    public Balance save (Balance balance) throws SQLException {
        String id = IdGenerator.generateId(10);
        Double amount = 0.0;
        Timestamp dateTime = Timestamp.valueOf(LocalDateTime.now());
        String accountId = balance.getAccountId();

        Balance toSave = new Balance(id , amount , dateTime,accountId);
        return save.insert(toSave);
    }
}
