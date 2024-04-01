package com.prog4.digitalbank.models.transactionSum;

import com.prog4.digitalbank.CrudOperations.FindAll;
import com.prog4.digitalbank.category.Category;
import com.prog4.digitalbank.category.CategoryServices;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class TransactionSumServices {
    private TransactionSumRepository transactionSumRepository;
    private CategoryServices categoryServices;

    public List<TransactionSum> sumByCategory() throws SQLException {
        List<Category> categories = categoryServices.findAllCategory();
        List<TransactionSum> transactionSums = new ArrayList<>();
        for (Category category : categories){
            String categoryName = category.getName();
            transactionSums.add(transactionSumRepository.findTransactionSum(categoryName));
        }
        transactionSums.removeAll(Collections.singleton(null));
        return transactionSums;
    }

    public List<TransactionSum> sumByCategoryAndDate(Date start , Date end) throws SQLException {
        List<TransactionSum> transactionSums = new ArrayList<>();
        if (start.toLocalDate().isBefore(end.toLocalDate())){
        List<Category> categories = categoryServices.findAllCategory();
        for (Category category : categories){
            String categoryName = category.getName();
            transactionSums.add(transactionSumRepository.findTransactionSumByDate(categoryName,start,end));
        }
        }
        transactionSums.removeAll(Collections.singleton(null));
        return transactionSums;
    }

}
