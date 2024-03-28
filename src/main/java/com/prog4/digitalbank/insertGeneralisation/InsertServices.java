package com.prog4.digitalbank.insertGeneralisation;

import com.prog4.digitalbank.methods.Conversion;
import com.prog4.digitalbank.balance.Balance;
import com.prog4.digitalbank.balance.BalanceServices;
import com.prog4.digitalbank.transactions.Transaction;
import com.prog4.digitalbank.transactions.TransactionServices;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

@Service
@AllArgsConstructor
public class InsertServices {
    private BalanceServices balanceServices;
    private TransactionServices transactionServices;
    public void insertTransaction (String accountId ,
                                   Double amount ,
                                   Date date ,
                                   String type ,
                                   String actionId,
                                   String action,
                                     int subCategoryId) throws SQLException {

        Timestamp timestamp = Conversion.DateToTimestamp(date);
        Transaction transaction = new Transaction(amount ,type , timestamp , accountId , actionId ,actionId, actionId ,actionId);
        transactionServices.insertTransaction(transaction , action , subCategoryId);

    }
    @Scheduled(fixedRate = 6000)
    public void applyTransactionOnBalance() throws SQLException {
        List<Transaction> transactions = transactionServices.notAppliedTransaction();
        balanceServices.applyBalance(transactions);
    }

}
