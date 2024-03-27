package com.prog4.digitalbank.transfer;


import com.prog4.digitalbank.CrudOperations.Save;
import com.prog4.digitalbank.account.AccountServices;
import com.prog4.digitalbank.balance.BalanceServices;
import com.prog4.digitalbank.insertGeneralisation.InsertServices;
import com.prog4.digitalbank.loan.BankLoan;
import com.prog4.digitalbank.loan.LoanRepository;
import com.prog4.digitalbank.methods.CheckDateValidy;
import com.prog4.digitalbank.methods.Conversion;
import com.prog4.digitalbank.methods.IdGenerators;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Service
@AllArgsConstructor
public class TransferServices {
        private BalanceServices balanceServices;
        private AccountServices accountServices;
        private InsertServices insertServices;
        private Save<ForeignTransfer> foreignTransferSave;
        private Save<Transfer> transferSave;
        private LoanRepository loanRepository;



        public boolean checkConditionsForeign( Transfer transfer , List<ForeignReceiver>foreignReceivers){
            String accountSenderId = transfer.getSenderAccountId();
            List<BankLoan> unpaidLoan = loanRepository.findByAccountId(accountSenderId);
            if (unpaidLoan.isEmpty()){
            for (ForeignReceiver foreignReceiver : foreignReceivers){
                Date date = foreignReceiver.getEffectiveDate();
                if (date != null){
                     if (!CheckDateValidy.checkDateValidity(date , 2)){
                       return false;
                };

                }
            }
            }

            return true;
        }

        public String foreignTransferOperation(Transfer transfer , List<ForeignReceiver>foreignReceivers) throws SQLException {
            if (checkConditionsForeign(transfer , foreignReceivers)){
                String transferRef = IdGenerators.generateTransferRef();
                for (ForeignReceiver foreignReceiver : foreignReceivers){
                    String foreignTransferId = IdGenerators.generateId(6);
                    ForeignTransfer foreignTransfer = new ForeignTransfer(foreignTransferId , foreignReceiver.getReceiverAccount());
                    foreignTransferSave.insert(foreignTransfer);
                    String transferId = IdGenerators.generateId(12);
                    Date duration = null;
                    if (foreignReceiver.getEffectiveDate() == null){
                         duration = CheckDateValidy.addDayToDate(foreignReceiver.getEffectiveDate(),2);
                    }
                    duration = foreignReceiver.getEffectiveDate();
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
                return "all transfer intiated";
            }
            return "transfer fail : unpaid loan / invalid Date effect";
        }







        public Boolean checkExistance(String accountRef , String firstName , String lastName){
            if (accountServices.findByAccountRef(accountRef,firstName,lastName) != null){
                return true;
            }else {
                return false;
            }
        }


}
