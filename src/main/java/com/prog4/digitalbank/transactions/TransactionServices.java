package com.prog4.digitalbank.transactions;

import com.prog4.digitalbank.CrudOperations.Save;
import com.prog4.digitalbank.idGenretor.IdGenerator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class TransactionServices {
    private Save<Transaction> transactionSave;

    public Transaction insertTransaction (Transaction transaction) throws SQLException {
        String id = IdGenerator.generateTransactionRef();
        Double amount = transaction.getAmount();
        String type = transaction.getType();
        Timestamp dateTime = Timestamp.valueOf(LocalDateTime.now());
        String accountId = transaction.getAccountId();

        Transaction toInsert = new Transaction(
                id,
                amount,
                type,
                dateTime,
                accountId
        );

        transactionSave.insert(toInsert);
        return toInsert;
    }

}
