package com.prog4.digitalbank.models.transactionSum;

import com.prog4.digitalbank.transactions.TransactionServices;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/sum")
public class TransactionSumController {
    private TransactionSumServices transactionSumServices;
    @GetMapping("/byCategory")
      public List<TransactionSum> sumByCategory() throws SQLException {
          return transactionSumServices.sumByCategory();
      }
    @GetMapping(value = "/byCategory/{start}/{end}")
    public List<TransactionSum> sumByCategoryByDate(@PathVariable Date start , @PathVariable Date end) throws SQLException {
        return transactionSumServices.sumByCategoryAndDate(start, end);
    }
}
