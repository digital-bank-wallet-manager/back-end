package com.prog4.digitalbank.balance;

import com.prog4.digitalbank.CrudOperations.FindAll;
import com.prog4.digitalbank.CrudOperations.Save;
import com.prog4.digitalbank.account.AccountRepository;
import com.prog4.digitalbank.idGenretor.IdGenerator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class BalanceServices {
    private Save<Balance> save;
    private BalanceRepository balanceRepository;


    public Balance saveBalance (Balance balance) throws SQLException {
        String id = IdGenerator.generateId(10);
        Double amount = balance.getAmount();
        Timestamp dateTime = Timestamp.valueOf(LocalDateTime.now());
        String accountId = balance.getAccountId();

        Balance toSave = new Balance(id ,amount ,dateTime,accountId);
        return save.insert(toSave);
    }



    public List<Balance> getLastBalanceById (String accountId){
        return balanceRepository.getLastBalanceById(accountId);
    }

}
