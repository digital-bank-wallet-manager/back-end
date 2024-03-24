package com.prog4.digitalbank.balance;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.lang.module.ModuleDescriptor;
import java.security.PrivateKey;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

@RestController
@AllArgsConstructor
public class BalanceController {
    private BalanceServices balanceServices;
}
