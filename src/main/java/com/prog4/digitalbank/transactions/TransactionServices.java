package com.prog4.digitalbank.transactions;

import com.prog4.digitalbank.CrudOperations.Save;
import com.prog4.digitalbank.methods.IdGenerators;
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
    public String insertTransaction (Transaction transaction  , String action , int subCategoryId) throws SQLException {

        String id = IdGenerators.generateTransactionRef();
        Double amount = transaction.getAmount();
        String type = transaction.getType();
        Timestamp dateTime = transaction.getDateTime();
        String accountId = transaction.getAccountId();
        Transaction toInsert = null;

        if (Objects.equals(action, "provisioning")) {
            String provisioningId = transaction.getProvisioningId();
            toInsert = new Transaction(
                    id,
                    amount,
                    type,
                    dateTime,
                    accountId,
                    provisioningId,
                    null,
                    null,
                    null,
                    subCategoryId
                    );


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
                    null,
                    null,
                    subCategoryId
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
                    transferId,
                    null,
                    subCategoryId);
            transactionSave.insert(toInsert);
        }
        if (Objects.equals(action, "expense")) {
            String expenseId = transaction.getExpenseId();
            toInsert = new Transaction(
                    id,
                    amount,
                    type,
                    dateTime,
                    accountId,
                    null,
                    null,
                    null,
                    expenseId,
                    subCategoryId);
            transactionSave.insert(toInsert);
        }

        return id;
    }

}
