package com.prog4.digitalbank.transactions;

import com.prog4.digitalbank.CrudOperations.Save;
import com.prog4.digitalbank.idGenretor.IdGenerator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;

@Service
@AllArgsConstructor
public class TransactionServices {
    private Save<Transaction> transactionSave;

    public Transaction insertTransaction (Transaction transaction  ,  String action) throws SQLException {
        String id = IdGenerator.generateTransactionRef();
        Double amount = transaction.getAmount();
        String type = transaction.getType();
        Timestamp dateTime = transaction.getDateTime();
        String accountId = transaction.getAccountId();
        Transaction toInsert = null;

        if (Objects.equals(action, "provisioning")){
            String provisioningId = transaction.getProvisioningId();
             toInsert = new Transaction(
                     id,
                     amount,
                    type,
                     dateTime,
                     accountId,
                     provisioningId,
                     null,
                     null);
            transactionSave.insert(toInsert);
        }
        if (Objects.equals(action, "loan")) {
            String bankLoanId = transaction.getBankLoanId();
            toInsert = new Transaction(
                     id,
                     amount,
                     type,
                     dateTime,
                     accountId,
                    null,
                     bankLoanId,
                    null
            );
            transactionSave.insert(toInsert);
        }
        if (Objects.equals(action, "transfert")) {
            String transferId = transaction.getTransferId();
            toInsert = new Transaction(
                    id,
                    amount,
                    type,
                    dateTime,
                    accountId,
                    null,
                     null,
                     transferId);
            transactionSave.insert(toInsert);
        }

        return toInsert;
    }

}
