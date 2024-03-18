package com.prog4.digitalbank.balance;

import com.prog4.digitalbank.CrudOperations.FindById;
import com.prog4.digitalbank.CrudOperations.Save;
import com.prog4.digitalbank.methods.Conversion;
import com.prog4.digitalbank.methods.IdGenerators;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class BalanceServices {
    private Save<Balance> save;
    private BalanceRepository balanceRepository;
    private FindById<Balance> findById;

    public Balance saveBalance (Balance balance) throws SQLException {
        String id = IdGenerators.generateId(10);
        Double amount = balance.getAmount();
        Timestamp dateTime = Timestamp.valueOf(LocalDateTime.now());
        String accountId = balance.getAccountId();

        Balance toSave = new Balance(id ,amount ,dateTime,accountId);
        return save.insert(toSave);
    }

    public Balance saveBalanceForSpecificTime (Balance balance) throws SQLException {
        String id = IdGenerators.generateId(10);
        Double amount = balance.getAmount();
        Timestamp dateTime = balance.getDateTime();
        String accountId = balance.getAccountId();
        String transactionId = balance.getTransactionId();

        Balance toSave = new Balance(id ,amount ,dateTime,accountId , transactionId);
        return save.insert(toSave);
    }



    public List<Balance> getLastBalanceById (String accountId , Timestamp referenceDate){
        return balanceRepository.getLastBalanceById(accountId , referenceDate);
    }

    public List<Balance> getNotEffectiveBalance (String accountId , Timestamp referenceDate){
        return balanceRepository.getNotEffectiveBalance(accountId , referenceDate);
    }

    public String upDatebalances (String accountId , Timestamp referenceDate , Double amount , String transactionId){
        return balanceRepository.upDateBalances(accountId, referenceDate,amount , transactionId);
    }

    public List<Balance> findByAccountIdAndPeriod (String accountId , Date dateStart , Date dateEnd){
        Timestamp date1 = Conversion.DateToTimestamp(dateStart);
        Timestamp date2 =Conversion.DateToTimestamp(dateEnd);
        return balanceRepository.findBalancesByAccountIdAndPeriod(accountId , date1 , date2);
    }

    public List<Balance> findByAccountIdOrdered (Class<Balance> balanceClass ,String id ){
        String order = "order by date_time asc";
        return findById.findByAccountId(balanceClass ,id , order);
    }


}
