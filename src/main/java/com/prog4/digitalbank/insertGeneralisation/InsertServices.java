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
    private BalanceServices balanceServices;
    private TransactionServices transactionServices;

    public void insertBalance (String accountId , Double amount , Date effective) throws SQLException {
        Timestamp timestamp = DateToTimestamp(effective);
        List<Balance> lastBalance = balanceServices.getLastBalanceById(accountId ,timestamp);
        Double amountOfBalance = lastBalance.get(0).getAmount();
        Double newBalanceAmount = amount + amountOfBalance;
        Balance balanceToInsert = new Balance(newBalanceAmount ,timestamp , accountId);
        balanceServices.saveBalanceForSpecificTime(balanceToInsert);
    }

    public void upDateAndInsertBalances (String accountId , Double amount , Date effective) throws SQLException {
        Timestamp timestamp = DateToTimestamp(effective);
        List<Balance> balances = balanceServices.getNotEffectiveBalance(accountId , timestamp);

        if (!balances.isEmpty()){
            insertBalance(accountId , amount , effective);
            balanceServices.upDatebalances(accountId , timestamp , amount);
        }else{
            insertBalance(accountId , amount , effective);
        }

    }
    public void insertTransaction (String accountId ,
                                   Double amount ,
                                   Date date ,
                                   String type ,
                                   String actionId,
                                   String action) throws SQLException
    {

        Timestamp timestamp = DateToTimestamp(date);
        Transaction transaction = new Transaction(amount ,type , timestamp , accountId , actionId ,actionId, actionId);
            transactionServices.insertTransaction(transaction , action);
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
