package com.prog4.digitalbank.transfer;

import com.prog4.digitalbank.balance.Balance;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import java.sql.SQLException;
@AllArgsConstructor
@RestController
public class TransferController {
    private TransferServices transferServices;

    @PostMapping("/transfer/foreing/{accountRef}/{subCategoryId}")
    public Transfer foreignTransfer (@RequestBody Transfer transfer,
                                     @PathVariable String accountRef ,
                                     @PathVariable int subCategoryId) throws SQLException {


        return transferServices.transferOperationForeign(transfer.getSenderAccountId(),
                transfer.getAmount(),
                transfer.getEffectiveDate(),
                transfer.getReason(),
                accountRef,
                subCategoryId);
    }


    @PostMapping("/transfer/inside/{accountRef}/{firstName}/{lastName}/{subCategoryId}")
    public Transfer insideTransfer (@RequestBody Transfer transfer,
                                    @PathVariable String accountRef,
                                    @PathVariable String firstName,
                                    @PathVariable String lastName,
                                    @PathVariable int subCategoryId) throws SQLException {
        return transferServices.transferInsideOperation(transfer ,accountRef , firstName , lastName , subCategoryId);
    }
}
