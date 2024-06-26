package com.prog4.digitalbank.balance;

import com.prog4.digitalbank.CrudOperations.FindById;
import com.prog4.digitalbank.CrudOperations.Save;
import com.prog4.digitalbank.methods.IdGenerators;
import com.prog4.digitalbank.transactions.Transaction;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;


@Service
@AllArgsConstructor
public class BalanceServices {
    private Save<Balance> save;
    private BalanceRepository balanceRepository;
    private FindById<Balance> findById;
    public void saveBalance (Balance balance) throws SQLException {
        String id = IdGenerators.generateId(10);
        Double amount = balance.getAmount();
        Timestamp dateTime = Timestamp.valueOf(LocalDateTime.now());
        String accountId = balance.getAccountId();

        Balance toSave = new Balance(id ,amount ,dateTime,accountId);
        save.insert(toSave);
    }

    public void saveBalanceForSpecificTime (Balance balance) throws SQLException {
        String id = IdGenerators.generateId(10);
        Double amount = balance.getAmount();
        Timestamp dateTime = balance.getDateTime();
        String accountId = balance.getAccountId();
        String transactionId = balance.getTransactionId();

        Balance toSave = new Balance(id ,amount ,dateTime,accountId , transactionId);
        save.insert(toSave);
    }



    public Balance getLastBalanceById (String accountId , Timestamp referenceDate){
        return balanceRepository.getLastBalanceById(accountId , referenceDate);
    }


    public void applyBalance(List<Transaction> transactions) throws SQLException {
        for (Transaction transaction : transactions){
            String transactionId = transaction.getId();
            String accountId = transaction.getAccountId();
            Timestamp timestamp = transaction.getDateTime();
            Balance lastBalance = getLastBalanceById(accountId , timestamp);
            Double lastAmount = lastBalance.getAmount();
            Double transactionAmount = transaction.getAmount();
            Double newAmount = 0.0;
            String transactionType = transaction.getType();
            if (Objects.equals(transactionType,"credit")){
                newAmount = lastAmount + transactionAmount;
            }else {
                newAmount = lastAmount - transactionAmount;
            }
            Balance toInsert = new Balance(newAmount,timestamp,accountId,transactionId);
            saveBalanceForSpecificTime(toInsert);
        }

    }

    public Balance actualBalance(String accountId){
        return balanceRepository.findBalanceByDate(accountId , Date.valueOf(LocalDate.now())).get(0);
    }

    public Balance balanceForSpecificTime (String accountId , Date date){

        Timestamp creationDate = findById.findByAccountId(Balance.class ,
                accountId ,
                "order by date_time asc limit 1",
                "").get(0).getDateTime();
        LocalDate creation = new Date(creationDate.getTime()).toLocalDate();
        LocalDate givenDate = date.toLocalDate();
         if (givenDate.isBefore(creation)){
            return new Balance("your account was not created yet ",0.0,creationDate,accountId);
        }
        if (givenDate.isAfter(LocalDate.now())){
            return new Balance("invalid date",0.0,creationDate,accountId);
        }

        return balanceRepository.findBalanceByDate(accountId , date).get(0);
    }
}
