package com.prog4.digitalbank.provising;

import com.prog4.digitalbank.CrudOperations.Save;
import com.prog4.digitalbank.insertGeneralisation.InsertServices;
import com.prog4.digitalbank.methods.IdGenerators;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;


@AllArgsConstructor
@Service
public class ProvisingServices {
    private Save<Provisioning> provisingSave ;
    private InsertServices insertServices;

    public Provisioning saveProvising (Provisioning provisioning , int subCategoryId) throws SQLException {
        String id = IdGenerators.generateId(12);
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

        if(effective.before(Date.valueOf(LocalDate.now()))){
             Provisioning error = new Provisioning("the effective date must be after today");
             return error;
        }else {
            Provisioning insert = provisingSave.insert(provisioningToInsert);
             insertServices.insertTransaction(accountId, amount, effective, "credit", id, "provisioning", subCategoryId);
            //insertServices.upDateAndInsertBalances(accountId, amount, effective, getTransactionId);
            return insert;
        }
    }
}
