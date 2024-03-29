package com.prog4.digitalbank.models;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BankStatementServices {
    private BankStatementRepository bankStatementRepository;
    public List<BankStatement> bankStatements(String accountId , int month){
        return bankStatementRepository.bankStatements(accountId,month);
    }
}
