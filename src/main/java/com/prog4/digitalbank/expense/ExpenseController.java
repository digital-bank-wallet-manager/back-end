package com.prog4.digitalbank.expense;

import com.prog4.digitalbank.Messages;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
@AllArgsConstructor
public class ExpenseController {
    private ExpenseServices expenseServices;
    @PostMapping("/expense/{subCategoryId}")
    public Messages getMoney (@RequestBody Expense expense , @PathVariable int subCategoryId) throws SQLException {
        return expenseServices.expenseOperation(expense , subCategoryId);
    }
}
