package com.prog4.digitalbank.provising;

import com.prog4.digitalbank.CrudOperations.Save;
import com.prog4.digitalbank.Messages;
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

    public Messages saveProvising (Provisioning provisioning , int subCategoryId) throws SQLException {
        String id = IdGenerators.generateId(12);
        Double amount = provisioning.getAmount();
        String reason = provisioning.getReason();
        Date effective = null;
        if (provisioning.getEffectiveDate()!= null){
         effective = provisioning.getEffectiveDate();
        }else {
            effective = Date.valueOf(LocalDate.now());
        }
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

        if(effective.toLocalDate().isBefore(LocalDate.now())){
              return new Messages(null,"the effective date must be after today");

        }else {
            Provisioning insert = provisingSave.insert(provisioningToInsert);
             insertServices.insertTransaction(accountId, amount, effective, "credit", id, "provisioning", subCategoryId);
            return new Messages("your account will be credited by the amount of "+insert.getAmount()+
                    " at "+insert.getEffectiveDate().toString(),null);
        }
    }
}
