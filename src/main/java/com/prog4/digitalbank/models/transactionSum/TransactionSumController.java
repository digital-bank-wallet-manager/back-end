package com.prog4.digitalbank.models.transactionSum;

import com.prog4.digitalbank.transactions.TransactionServices;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;

@AllArgsConstructor
@RestController
public class TransactionSumController {
    private TransactionSumServices transactionSumServices;
    @GetMapping("/sum/byCategory")
      public List<TransactionSum> sumByCategory() throws SQLException {
          return transactionSumServices.sumByCategory();
      }
}
