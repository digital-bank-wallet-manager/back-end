package com.prog4.digitalbank.transfer;


import com.prog4.digitalbank.CrudOperations.Save;
import com.prog4.digitalbank.account.Account;
import com.prog4.digitalbank.account.AccountServices;
import com.prog4.digitalbank.balance.Balance;
import com.prog4.digitalbank.balance.BalanceServices;
import com.prog4.digitalbank.insertGeneralisation.InsertServices;
import com.prog4.digitalbank.methods.CheckDateValidy;
import com.prog4.digitalbank.methods.IdGenerators;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import static java.sql.Date.valueOf;

@Service
@AllArgsConstructor
public class TransferServices {
        private BalanceServices balanceServices;
        private AccountServices accountServices;

        private InsertServices insertServices;
        private Save<ForeignTransfer> foreignTransferSave;
        private Save<Transfer> transferSave;

        public Double getBalance ( String id ){
            List<Balance> balances = balanceServices.findByAccountIdOrdered(Balance.class , id);
            Balance lastBalance = balances.get(0);
            return lastBalance.getAmount();
        }

        public boolean checkConditions(String id , Double amount , Date effectiveDate ){
            boolean check = true;
            double lastBalance = getBalance(id);
            if (lastBalance >= amount){
                if (effectiveDate != null){
                    check = CheckDateValidy.checkDateValidity(effectiveDate , 2);
                }
            }else{
                check = false;
            }
            return check;
        }


        public boolean conditionInside(String id , Double amount){

            boolean check = false;
            double lastBalance = getBalance(id);
            if (lastBalance >= amount){
                check = true;
                }else {
                check = false;
            }
            return check;
        }


        public Transfer transferOperationForeign(String senderId,
                                                 Double amount ,
                                                 Date effectiveDate ,
                                                 String reason,
                                                 String accountRef,
                                                 int subCategoryId) throws SQLException {
            if (checkConditions(senderId , amount , effectiveDate )){
                String idForeign = IdGenerators.generateId(6);
                String idTransfer = IdGenerators.generateId(10);
                Double amountTransfer = amount;
                Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
                Date effective = null;
                if (effectiveDate == null){
                    effective = CheckDateValidy.addDayToDate(valueOf(LocalDate.now()), 2);
                }else {
                    effective = effectiveDate;
                }

                String transferRef = IdGenerators.generateTransferRef();
                String receiver = null;
                String sender = senderId;
                ForeignTransfer foreignTransfer = new ForeignTransfer(idForeign , accountRef);
                foreignTransferSave.insert(foreignTransfer);
                Transfer transfer = new Transfer(idTransfer,
                        amountTransfer,
                        reason,
                        timestamp,
                        effective,
                        transferRef,
                        receiver,
                        sender,
                        idForeign);
                transferSave.insert(transfer);
                String idTransaction = insertServices.insertTransaction(sender,
                        amountTransfer,
                        effective ,
                        "debit",
                        idTransfer ,
                        "transfert",
                        subCategoryId
                        );
                insertServices.upDateAndInsertBalances(senderId , -amountTransfer , effective , idTransaction);
                return transfer;
            }else {
                Transfer error = new Transfer("amount or invalid date " +
                        "(please check your balance , the effective date must be at list 2 days after today)");
                return error;
            }

        }
        public String getReceiverId (String accountRef , String firstName , String lastName){
            Account receiver = accountServices.findByAccountRef(accountRef ,firstName , lastName);
            return receiver.getId();
        }

        public Boolean checkExistance(String accountRef , String firstName , String lastName){
            if (accountServices.findByAccountRef(accountRef,firstName,lastName) != null){
                return true;
            }else {
                return false;
            }
        }

        private Transfer composititon (Transfer transfer , String receiverId){
            String id = IdGenerators.generateId(10);
            Double amount = transfer.getAmount();
            String reason = transfer.getReason();
            Timestamp dateTime = Timestamp.valueOf(LocalDateTime.now());
            Date effectiveDate = null;
            if (transfer.getEffectiveDate() == null){
                effectiveDate = Date.valueOf(LocalDate.now());
            }else {
                effectiveDate = transfer.getEffectiveDate();
            }
            String transferRef = IdGenerators.generateTransferRef();
            String receiverAccount = receiverId;
            String senderAccount = transfer.getSenderAccountId();
            Transfer transfer1 = new Transfer(id,
                    amount ,
                    reason ,
                    dateTime ,
                    effectiveDate ,
                    transferRef ,
                    receiverAccount ,
                    senderAccount ,
                    null);
            return transfer1;
        }

        public Transfer transferInsideOperation (Transfer transfer,
                                                 String accountRef ,
                                                 String firstName ,
                                                 String lastName,
                                                 int subCategoryId) throws SQLException {

            if (checkExistance(accountRef , firstName , lastName)){
                String receiverId = getReceiverId(accountRef , firstName , lastName);
                if (conditionInside(transfer.getSenderAccountId() , transfer.getAmount())){
                   Transfer transferExecuted = composititon(transfer , receiverId );
                   transferSave.insert(transferExecuted);
                   String senderTransactionId = insertServices.insertTransaction(transferExecuted.getSenderAccountId(),
                           transferExecuted.getAmount(),
                           transferExecuted.getEffectiveDate(),
                           "debit",
                           transferExecuted.getId(),
                           "transfert",
                           subCategoryId);
                   insertServices.upDateAndInsertBalances(transferExecuted.getSenderAccountId(),
                           -transferExecuted.getAmount(),
                           transferExecuted.getEffectiveDate(),
                           senderTransactionId);
                   String receiverTransactionId = insertServices.insertTransaction(transferExecuted.getReceiverAccountId(),
                            transferExecuted.getAmount(),
                            transferExecuted.getEffectiveDate(),
                            "credit",
                            transferExecuted.getId(),
                            "transfert",
                           subCategoryId);
                   insertServices.upDateAndInsertBalances(transferExecuted.getReceiverAccountId(),
                            transferExecuted.getAmount(),
                            transferExecuted.getEffectiveDate(),
                            receiverTransactionId);
                   return transferExecuted;

                }else{
                    Transfer error = new Transfer("you do not have the amount of "+transfer.getAmount()+" in your account please check your balance");
                    return error;
                }
            }else {
                Transfer error = new Transfer(accountRef+" in the name of "+firstName+" "+lastName+" do not exist");
                return error;
            }
        }

}
