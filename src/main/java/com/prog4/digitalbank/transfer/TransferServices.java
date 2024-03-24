package com.prog4.digitalbank.transfer;


import com.prog4.digitalbank.CrudOperations.Save;
import com.prog4.digitalbank.account.Account;
import com.prog4.digitalbank.account.AccountServices;
import com.prog4.digitalbank.balance.Balance;
import com.prog4.digitalbank.balance.BalanceServices;
import com.prog4.digitalbank.insertGeneralisation.InsertServices;
import com.prog4.digitalbank.loan.LoanRepository;
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
        private LoanRepository loanRepository;

        public Double getBalance ( String id ){
            List<Balance> balances = balanceServices.findByAccountIdOrdered(Balance.class , id);
            Balance lastBalance = balances.get(0);
            return lastBalance.getAmount();
        }

        public boolean checkConditions(String id , Double amount , Date effectiveDate ){
            boolean check = true;
            if (loanRepository.findByAccountId(id).size()>0){
                check = false;
            }
            double lastBalance = getBalance(id);
            if (lastBalance >= amount) {
                if (effectiveDate != null) {
                    check = CheckDateValidy.checkDateValidity(effectiveDate, 2);
                }
            }
            return check;
        }


        public boolean conditionInside(String id , Double amount){

            double lastBalance = getBalance(id);
            if (loanRepository.findByAccountId(id).size()>0){
                return false;
            }
            if (lastBalance < amount) {
                return false;
            }
                return true;
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
                insertServices.insertTransaction(sender,
                        amountTransfer,
                        effective ,
                        "debit",
                        idTransfer ,
                        "transfert",
                        subCategoryId
                        );
                return transfer;
            }else {
                Transfer error = new Transfer("operation denied / reasons : lack of balance , unpaid loan , date unvalid[at least 48h after the sending date]");
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
                   insertServices.insertTransaction(transferExecuted.getSenderAccountId(),
                           transferExecuted.getAmount(),
                           transferExecuted.getEffectiveDate(),
                           "debit",
                           transferExecuted.getId(),
                           "transfert",
                           subCategoryId);

                   insertServices.insertTransaction(transferExecuted.getReceiverAccountId(),
                            transferExecuted.getAmount(),
                            transferExecuted.getEffectiveDate(),
                            "credit",
                            transferExecuted.getId(),
                            "transfert",
                           subCategoryId);
                   return transferExecuted;

                }else{
                    Transfer error = new Transfer("operation denied / reason : unpaid laon or lack of balance");
                    return error;
                }
            }else {
                Transfer error = new Transfer(accountRef+" in the name of "+firstName+" "+lastName+" do not exist");
                return error;
            }
        }

}
