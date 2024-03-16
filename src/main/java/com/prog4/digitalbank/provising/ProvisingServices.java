package com.prog4.digitalbank.provising;

import com.prog4.digitalbank.CrudOperations.Save;
import com.prog4.digitalbank.balance.Balance;
import com.prog4.digitalbank.balance.BalanceServices;
import com.prog4.digitalbank.idGenretor.IdGenerator;
import com.prog4.digitalbank.insertGeneralisation.InsertServices;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.SQLException;

import java.sql.Timestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.List;

import static java.sql.Date.valueOf;

@AllArgsConstructor
@Service
public class ProvisingServices {
    private Save<Provisioning> provisingSave ;
    private InsertServices insertServices;

    public Provisioning saveProvising (Provisioning provisioning) throws SQLException {
        String id = IdGenerator.generateId(12);
        Double amount = provisioning.getAmount();
        String reason = provisioning.getReason();
        Date effective = provisioning.getEffectiveDate();
        Date record = Date.valueOf(LocalDate.now());
        String accountId = provisioning.getAccountId();

        Provisioning provisioningToInsert = new Provisioning(
                id,
                amount,
                reason,
                effective,
                record,
                accountId
        );

        Provisioning insert = provisingSave.insert(provisioningToInsert);
        insertServices.insertBalance(accountId , amount , effective);
        insertServices.insertTransaction(accountId , amount , effective , "credit");
        return insert;
    }
}
