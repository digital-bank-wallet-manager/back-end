package com.prog4.digitalbank.insertGeneralisation;

import com.prog4.digitalbank.CrudOperations.Save;
import com.prog4.digitalbank.balance.Balance;
import com.prog4.digitalbank.balance.BalanceServices;
import com.prog4.digitalbank.transactions.Transaction;
import com.prog4.digitalbank.transactions.TransactionServices;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

@Service
@AllArgsConstructor

public class InsertServices {
    private Save<Balance> balanceSave;
    private BalanceServices balanceServices;
    private TransactionServices transactionServices;
    public void insertBalance (String accountId , Double amount , Date effective) throws SQLException {
        List<Balance> lastBalance = balanceServices.getLastBalanceById(accountId);
        Double amountOfBalance = lastBalance.get(0).getAmount();
        Double newBalanceAmount = amount + amountOfBalance;
        Timestamp timestamp = DateToTimestamp(effective);
        Balance balanceToInsert = new Balance(newBalanceAmount ,timestamp , accountId);
        balanceServices.saveBalanceForSpecificTime(balanceToInsert);
    }

    public void insertTransaction (String accountId , Double amount , Date date , String type) throws SQLException {
        Timestamp timestamp = DateToTimestamp(date);
        Transaction transaction = new Transaction(amount ,type , timestamp , accountId);
        transactionServices.insertTransaction(transaction);
    }

    private Timestamp DateToTimestamp (Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Timestamp timestamp = new Timestamp(calendar.getTimeInMillis());
        return timestamp;
    }
}
