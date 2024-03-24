package com.prog4.digitalbank.transactions;

import com.prog4.digitalbank.transfer.TransferServices;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@AllArgsConstructor
@RestController
public class TrasactionController {
    private TransactionServices transactionServices;
    @GetMapping("/transactions")
    public List<Transaction> transactions(){
        return transactionServices.notAppliedTransaction();
    }
}
