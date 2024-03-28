package com.prog4.digitalbank.transactions;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;
@AllArgsConstructor
@RestController
public class TransactionController {
    private TransactionServices transactionServices;
    @GetMapping("/transactions")
    public List<Transaction> transactions(){
        return transactionServices.notAppliedTransaction();
    }

    @PutMapping("/transaction/cancel/{transactionId}")
    public String cancelTransaction(@PathVariable String transactionId) throws SQLException {
        return transactionServices.cancelTransaction(transactionId);
    }
}
