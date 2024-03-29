package com.prog4.digitalbank.models;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class TransferHistoryController {
    private TransferHistoryServices transferHistoryServices;
    @GetMapping("transferHistory/{accountSender}")
    public List<TransferHistory> transferHistories(@PathVariable String accountSender){
        return transferHistoryServices.transferHistories(accountSender);
    }
}
