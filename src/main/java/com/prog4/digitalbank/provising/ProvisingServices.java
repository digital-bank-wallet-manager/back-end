package com.prog4.digitalbank.provising;

import com.prog4.digitalbank.CrudOperations.Save;
import com.prog4.digitalbank.idGenretor.IdGenerator;
import com.prog4.digitalbank.insertGeneralisation.InsertServices;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.quartz.QuartzTransactionManager;
import org.springframework.stereotype.Service;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;


@AllArgsConstructor
@Service
public class ProvisingServices {
    private Save<Provisioning> provisingSave ;
    private InsertServices insertServices;
    @QuartzTransactionManager
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
        insertServices.upDateAndInsertBalances(accountId , amount , effective);
        insertServices.insertTransaction(accountId , amount , effective , "credit" , id , "loan");
        return insert;
    }
}
