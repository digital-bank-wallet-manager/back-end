package com.prog4.digitalbank.insertGeneralisation;

import com.prog4.digitalbank.methods.Conversion;
import com.prog4.digitalbank.balance.Balance;
import com.prog4.digitalbank.balance.BalanceServices;
import com.prog4.digitalbank.transactions.Transaction;
import com.prog4.digitalbank.transactions.TransactionServices;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

@Service
@AllArgsConstructor
public class InsertServices {
    private BalanceServices balanceServices;
    private TransactionServices transactionServices;


    public void insertBalance (String accountId , Double amount , Date effective, String transactionId) throws SQLException {

        Timestamp timestamp = Conversion.DateToTimestamp(effective);
        Balance balance = balanceServices.getLastBalanceById(accountId ,timestamp);
        Double amountOfBalance = balance.getAmount();
        Double newBalanceAmount = amount + amountOfBalance;
        Balance balanceToInsert = new Balance(newBalanceAmount ,timestamp , accountId , transactionId );
        balanceServices.saveBalanceForSpecificTime(balanceToInsert);
    }

    public void upDateAndInsertBalances (String accountId , Double amount , Date effective , String transactionId) throws SQLException {
        Timestamp timestamp = Conversion.DateToTimestamp(effective);
        List<Balance> balances = balanceServices.getNotEffectiveBalance(accountId , timestamp);

        if (!balances.isEmpty()){
            insertBalance(accountId , amount , effective , transactionId);
            balanceServices.upDatebalances(accountId , timestamp , amount , transactionId);
        }else{
            insertBalance(accountId , amount , effective , transactionId);
        }

    }

    public String insertTransaction (String accountId ,
                                   Double amount ,
                                   Date date ,
                                   String type ,
                                   String actionId,
                                   String action) throws SQLException {

        Timestamp timestamp = Conversion.DateToTimestamp(date);
        Transaction transaction = new Transaction(amount ,type , timestamp , accountId , actionId ,actionId, actionId);
        String id =  transactionServices.insertTransaction(transaction , action);
           return id;
    }

}
