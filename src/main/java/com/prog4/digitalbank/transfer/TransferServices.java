package com.prog4.digitalbank.transfer;



import com.prog4.digitalbank.CrudOperations.FindById;
import com.prog4.digitalbank.CrudOperations.Save;
import com.prog4.digitalbank.Messages;
import com.prog4.digitalbank.account.AccountServices;
import com.prog4.digitalbank.balance.BalanceServices;
import com.prog4.digitalbank.insertGeneralisation.InsertServices;
import com.prog4.digitalbank.loan.BankLoan;

import com.prog4.digitalbank.loan.LoanServices;
import com.prog4.digitalbank.methods.CheckDateValidy;

import com.prog4.digitalbank.methods.Conversion;
import com.prog4.digitalbank.methods.IdGenerators;
import com.prog4.digitalbank.transactions.Transaction;
import com.prog4.digitalbank.transactions.TransactionServices;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Service
@AllArgsConstructor
public class TransferServices {
        private BalanceServices balanceServices;
        private AccountServices accountServices;
        private InsertServices insertServices;
        private Save<ForeignTransfer> foreignTransferSave;
        private Save<Transfer> transferSave;
        private LoanServices loanServices;
        private TransferRepository transferRepository;
        private TransactionServices transactionServices;
        private FindById<Transfer> transferFindById;

        private boolean checkUnpaidLoan( Transfer transfer){

            String accountSenderId = transfer.getSenderAccountId();
            List<BankLoan> unpaidLoan = loanServices.findByAccountId(accountSenderId);
            return unpaidLoan.isEmpty();
        }

        private Boolean checkDateValidity(List<ForeignReceiver> foreignReceivers){
            for (ForeignReceiver foreignReceiver : foreignReceivers){
                Date date = foreignReceiver.getEffectiveDate();
                if (date != null){
                    if (!CheckDateValidy.checkDateValidity(date , 2)){
                        return false;
                    }
                }
            }
            return true;
        }


        public String foreignTransferOperation(Transfer transfer , List<ForeignReceiver>foreignReceivers) throws SQLException {
           if (checkUnpaidLoan(transfer)){
               if (checkDateValidity(foreignReceivers)){
                String transferRef = IdGenerators.generateTransferRef();
                for (ForeignReceiver foreignReceiver : foreignReceivers){
                    String foreignTransferId = IdGenerators.generateId(6);
                    ForeignTransfer foreignTransfer = new ForeignTransfer(foreignTransferId , foreignReceiver.getReceiverAccount());
                    foreignTransferSave.insert(foreignTransfer);
                    String transferId = IdGenerators.generateId(12);
                    Date duration = null;
                    if (foreignReceiver.getEffectiveDate() == null){

                         duration = CheckDateValidy.addDayToDate(Date.valueOf(LocalDate.now()),2);
                    }else {
                    duration = foreignReceiver.getEffectiveDate();
                    }


                    Transfer transfer1 = new Transfer(
                            transferId,
                            foreignReceiver.getAmount(),
                            foreignReceiver.getReason(),
                            Timestamp.valueOf(LocalDateTime.now()),
                            duration,
                            transferRef,
                            null,
                            transfer.getSenderAccountId(),
                            foreignTransferId
                            );
                    transferSave.insert(transfer1);
                    insertServices.insertTransaction(transfer.getSenderAccountId(),
                            foreignReceiver.getAmount(),
                            duration,
                            "debit",
                            transferId,
                            "transfert",
                            foreignReceiver.getSubCategory());
                }
                return "all transfer initiated";

               }else {

                   return "an outside transfer required at least 48h to validate";
               }
           }else {
               return "you have an unpaid loan";

           }

        }

        private Boolean checkAvailableBalance(Transfer transfer ,  List<LocalReceiver> localReceivers){
        List<Double> instantTransfer= new ArrayList<>();
        for(LocalReceiver localReceiver:localReceivers){
        Date effectiveDate=localReceiver.getEffectiveDate();
        if(effectiveDate.equals(Date.valueOf(LocalDate.now()))||
        effectiveDate==null){
        instantTransfer.add(localReceiver.getAmount());
        }
        }
        Double neededBalance=instantTransfer.stream().reduce(0.0,Double::sum);
        double availableBalance=balanceServices
        .actualBalance(transfer
        .getSenderAccountId()).getAmount();

        if(neededBalance>availableBalance){
        return false;
        }
        return true;
        }


        private boolean checkAccount (List<LocalReceiver> localReceivers){
            for (LocalReceiver localReceiver : localReceivers){
                if (accountServices.findByAccountRef(localReceiver.getAccountRef(),localReceiver.getFirstName(),localReceiver.getLastName()) == null){
                    return false;
                }
            }
            return true;
        }

        private boolean checkAllDates(List<LocalReceiver> localReceivers){
            for (LocalReceiver localReceiver : localReceivers){
                if (localReceiver.getEffectiveDate().toLocalDate().isBefore(LocalDate.now())){
                    return false;
                }
            }
            return true;
        }

        public Messages localTransferOperation(Transfer transfer , List<LocalReceiver> localReceivers) throws SQLException {
            String transferRef = IdGenerators.generateTransferRef();
            if (checkUnpaidLoan(transfer)){
                if (checkAccount(localReceivers)){
                    if (checkAvailableBalance(transfer , localReceivers)){
                        if (checkAllDates(localReceivers)){
                        for (LocalReceiver localReceiver : localReceivers){
                            Date date = null;
                            if (localReceiver.getEffectiveDate() == null){
                                date = Date.valueOf(LocalDate.now());
                            }else {
                            date = localReceiver.getEffectiveDate();
                            }
                            String transferId = IdGenerators.generateId(12);
                            String receiverId = accountServices.findByAccountRef(
                                    localReceiver.getAccountRef(),
                                    localReceiver.getFirstName(),
                                    localReceiver.getLastName()).getId();
                            Conversion.DateToTimestamp(localReceiver.getEffectiveDate());
                            Transfer transfer1 = new Transfer(
                                    transferId,
                                    localReceiver.getAmount(),
                                    localReceiver.getReason(),
                                    Timestamp.valueOf(LocalDateTime.now()),
                                    date,
                                    transferRef,
                                    receiverId,
                                    transfer.getSenderAccountId(),
                                    null
                            );
                            transferSave.insert(transfer1);
                            insertServices.insertTransaction(
                                    transfer.getSenderAccountId(),
                                    localReceiver.getAmount(),
                                    date,
                                    "debit",
                                    transferId,
                                    "transfert",
                                    localReceiver.getSubCategoryId()
                            );
                            insertServices.insertTransaction(
                                    receiverId,
                                    localReceiver.getAmount(),
                                    date,
                                    "credit",
                                    transferId,
                                    "transfert",
                                    localReceiver.getSubCategoryId()
                            );
                        }
                        }else {
                            return new Messages(null,"there is an invalid effective date");
                        }
                    }else {
                        return new Messages (null,"operation failed : your balance is not enough");
                    }

                }else {
                    return new Messages(null,"operation failed : invalid account ref / invalid lastname or firstname ");
                }
            }else {
                return new Messages(null,"operation failed : you have an unpaid loan");
            }
            return new Messages("transfer initiated transfer ref: "+transferRef,null);
        }

        private List<Transfer> appendingTransfer(){
            return transferRepository.appendingTransfer();
        }

        private void updateTransferStatus(List<Transfer> transfers) throws SQLException {
            for (Transfer transfer : transfers){
                Double amount = balanceServices.actualBalance(transfer.getSenderAccountId()).getAmount();
                if (amount > transfer.getAmount()){
                    transferRepository.updateTransferStatus(transfer.getId(),"done");
                }else {
                    transferRepository.updateTransferStatus(transfer.getId(),"canceled");
                }
            }
        }

        @Scheduled(fixedRate = 2000)
        private void transferExecute() throws SQLException {
            List<Transfer> transfers = appendingTransfer();
            updateTransferStatus(transfers);
        }

    public List<Transfer> getTransferBySenderId(String senderId){
            return transferRepository.getTransferBySenderId(senderId);
    }

    public Messages cancelTransfer(String transferId){
            Transfer transfer = transferFindById.findByIdOrderd(Transfer.class,transferId,"");
            if (transfer != null){
            Transaction transaction = transactionServices.transactionByTransferId(transferId);
          String status = transaction.getStatus();
          if (Objects.equals(status,"apending")){
              transferRepository.cancelTransfer(transferId);
              return new Messages("transfer canceled successfully",null);
          }
          return new Messages(null,"cancel failed : this transfer is already done");
            }else {
                return new Messages(null,"transfer with id: "+transferId+" doesn't exist");
            }
    }
}
