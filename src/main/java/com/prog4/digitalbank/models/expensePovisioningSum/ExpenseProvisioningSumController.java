package com.prog4.digitalbank.models.expensePovisioningSum;

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

public class ExpenseProvisioningSumController {
    private ExpenseProvisioningSumServices expenseSumServices;
    @GetMapping("/expense")
    public List<ExpenseSum> expenseSum() throws SQLException{
        return expenseSumServices.expenseSum();
    }
    @GetMapping("/expense/{start}/{end}")
    public List<ExpenseSum> expenseSumByDate(@PathVariable Date start , @PathVariable Date end) throws SQLException {
        return expenseSumServices.expenseSumByDate(start ,end);
    }

    @GetMapping("/provisioning")
    public List<ProvisioningSum> provisioningSums() throws SQLException{
        return  expenseSumServices.provisioningSums();
    }
}
