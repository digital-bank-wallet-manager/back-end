package com.prog4.digitalbank.transfer;


import com.prog4.digitalbank.CrudOperations.Save;
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
import java.util.PrimitiveIterator;

import static java.sql.Date.valueOf;

@Service
@AllArgsConstructor
public class TransferServices {
        private BalanceServices balanceServices;
        private InsertServices insertServices;
        private Save<ForeignTransfer> foreignTransferSave;
        private Save<Transfer> transferSave;

        public Double getBalance ( String id ){
            List<Balance> balances = balanceServices.findByAccountIdOrdered(Balance.class , id);
            Balance lastBalance = balances.get(balances.size()-1);
            return lastBalance.getAmount();
        }

        public boolean checkConditions(String id , Double amount , Date effectiveDate ){
            boolean check = true;
            double lastBalance =getBalance(  id );
            if (lastBalance >= amount){
                if (effectiveDate != null){
                    check = CheckDateValidy.checkDateValidity(effectiveDate , 2);
                }
            }else{
                check = false;
            }
            return check;
        }

        public Transfer transferOperationForeign(String senderId,
                                                 Double amount ,
                                                 Date effectiveDate ,
                                                 String reason,
                                                 String accountRef) throws SQLException {
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
                        "transfert");
                insertServices.upDateAndInsertBalances(senderId , -amountTransfer , effective , idTransaction);
                return transfer;
            }else {
                Transfer error = new Transfer("amount or invalid date " +
                        "(please check your balance , the effective date must be at list 2 days after today)");
                return error;
            }

        }
}
