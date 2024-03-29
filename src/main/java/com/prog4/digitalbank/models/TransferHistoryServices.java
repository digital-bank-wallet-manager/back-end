package com.prog4.digitalbank.models;

import com.prog4.digitalbank.CrudOperations.FindById;
import com.prog4.digitalbank.account.Account;
import com.prog4.digitalbank.account.AccountServices;
import com.prog4.digitalbank.transactions.Transaction;
import com.prog4.digitalbank.transactions.TransactionServices;
import com.prog4.digitalbank.transfer.ForeignTransfer;
import com.prog4.digitalbank.transfer.Transfer;
import com.prog4.digitalbank.transfer.TransferServices;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class TransferHistoryServices {
    private AccountServices accountServices;
    private TransferServices transferServices;
    private TransactionServices transactionServices;
    private FindById<ForeignTransfer> foreignTransferFindById;

    public List<TransferHistory> transferHistories(String accountSender){
        List<TransferHistory> transferHistories = new ArrayList<>();
        List<Transfer> transfers = transferServices.getTransferBySenderId(accountSender);
        if (!transfers.isEmpty()){
            for (Transfer transfer : transfers){
                Transaction transaction = transactionServices.transactionByTransferId(transfer.getId());
                String name = null;
                String accountRef = null;
                String foreignAccount = null;
                if (transfer.getReceiverAccountId() != null){
                    Account account = accountServices.findById(Account.class , transfer.getReceiverAccountId());
                    name = account.getLastName();
                    accountRef = account.getAccountRef();
                }else {
                    name = "";
                    accountRef = "";
                    foreignAccount = foreignTransferFindById
                            .findByIdOrderd(ForeignTransfer.class,transfer.getIdForeignTransfer(),"")
                            .getAccountRef();

                }

            TransferHistory transferHistory = new TransferHistory(
                    transaction.getId(),
                    transfer.getId(),
                    transfer.getAmount(),
                    transfer.getReason(),
                    transaction.getDateTime(),
                    transfer.getTransferRef(),
                    accountRef,
                    name,
                    foreignAccount,
                    transaction.getStatus()
            );
                transferHistories.add(transferHistory);
            }
        }
        return transferHistories;
    }
}
