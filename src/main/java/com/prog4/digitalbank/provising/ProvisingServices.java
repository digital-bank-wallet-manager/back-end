package com.prog4.digitalbank.provising;

import com.prog4.digitalbank.CrudOperations.Save;
import com.prog4.digitalbank.balance.Balance;
import com.prog4.digitalbank.balance.BalanceServices;
import com.prog4.digitalbank.idGenretor.IdGenerator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import static java.sql.Date.valueOf;

@AllArgsConstructor
@Service
public class ProvisingServices {
    private Save<Provisioning> provisingSave ;
    private BalanceServices balanceServices;

    public Provisioning saveProvising (Provisioning provisioning) throws SQLException {
        String id = IdGenerator.generateId(12);
        Double amount = provisioning.getAmount();
        String reason = provisioning.getReason();
        Date effective = provisioning.getEffectiveDate();
        Date record = valueOf(LocalDate.now());
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

        List<Balance> lastBalance = balanceServices.getLastBalanceById(accountId);
        Double amountOfBalance = lastBalance.get(0).getAmount();
        Double newBalanceAmount = amount + amountOfBalance;
        Balance balanceToInsert = new Balance(newBalanceAmount , accountId);
        balanceServices.saveBalance(balanceToInsert);

        return insert;
    }
}
