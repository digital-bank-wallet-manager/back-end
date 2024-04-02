package com.prog4.digitalbank.models.expensePovisioningSum;

import com.prog4.digitalbank.category.Category;
import com.prog4.digitalbank.category.CategoryServices;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@AllArgsConstructor
@Service
public class ExpenseProvisioningSumServices {
    private ExpenseProvisioningSumRepository expenseSumRepository;
    private CategoryServices categoryServices;

    public List<ExpenseSum> expenseSum() throws SQLException {
        List<Category> categories = categoryServices.findCategoryByType("debit");
        List<ExpenseSum> expenseSums = new ArrayList<>();
        for (Category category : categories){
            ExpenseSum expenseSum = expenseSumRepository.expenseSum(category.getName());
            expenseSums.add(expenseSum);
        }
        expenseSums.removeAll(Collections.singleton(null));
        return expenseSums;
    }

    public List<ExpenseSum> expenseSumByDate(Date start , Date end) throws SQLException {
        List<ExpenseSum> expenseSums = new ArrayList<>();
        if (start.toLocalDate().isBefore(end.toLocalDate())){
            List<Category> categories = categoryServices.findCategoryByType("debit");
        for (Category category : categories){
            ExpenseSum expenseSum = expenseSumRepository.expenseSumByDate(category.getName(),start,end);
            expenseSums.add(expenseSum);
        }
        }
        expenseSums.removeAll(Collections.singleton(null));
        return expenseSums;
    }

    public List<ProvisioningSum> provisioningSums() throws SQLException {
        List<Category> categories = categoryServices.findCategoryByType("credit");
        List<ProvisioningSum> provisioningSums = new ArrayList<>();
        for (Category category : categories){
            ProvisioningSum provisioningSum = expenseSumRepository.provisioningSum(category.getName());
            provisioningSums.add(provisioningSum);
        }
        provisioningSums.removeAll(Collections.singleton(null));
        return provisioningSums;
    }

    public List<ProvisioningSum> provisioningSumByDate(Date start, Date end) throws SQLException {
        List<Category> categories = categoryServices.findCategoryByType("credit");
        List<ProvisioningSum> provisioningSums = new ArrayList<>();
        for (Category category : categories){
            ProvisioningSum provisioningSum = expenseSumRepository.provisioningSumByDate(category.getName(),start,end);
            provisioningSums.add(provisioningSum);
        }
        provisioningSums.removeAll(Collections.singleton(null));
        return provisioningSums;
    }

}
