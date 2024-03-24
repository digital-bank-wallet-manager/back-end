package com.prog4.digitalbank.expense;

import com.prog4.digitalbank.CrudOperations.Save;
import com.prog4.digitalbank.balance.Balance;
import com.prog4.digitalbank.balance.BalanceServices;
import com.prog4.digitalbank.insertGeneralisation.InsertServices;
import com.prog4.digitalbank.methods.IdGenerators;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
@AllArgsConstructor
@Service
public class ExpenseServices {
    private Save<Expense> expenseSave;
    private BalanceServices balanceServices;
    private InsertServices insertServices;

    public Expense saveExpense (Expense expense) throws SQLException {
        return expenseSave.insert(expense);
    }

    public boolean checkAmountCondition(Expense expense){
       Balance balance = balanceServices
               .getLastBalanceById(expense.getAccountId(),
                       Timestamp.valueOf(LocalDateTime.now()));
       Double actualBalance = balance.getAmount();
       if (actualBalance < expense.getAmount()){
           return false;
       }else {
           return true;
       }
    }

    public Expense expenseOperation (Expense expense , int subCategoryId) throws SQLException {
        if (checkAmountCondition(expense)){
            String id = IdGenerators.generateId(12);
            Double amount = expense.getAmount();
            Timestamp dateTime = Timestamp.valueOf(LocalDateTime.now());
            String accountId = expense.getAccountId();
            Expense expense1 = new Expense(id , amount , dateTime , accountId);
            saveExpense(expense1);
            insertServices.insertTransaction(accountId,
                    amount,
                    Date.valueOf(LocalDate.now()),
                    "debit",
                    id,
                    "expense",
                    subCategoryId);
            return expense1;
        }else {
            Expense error = new Expense("you actual balance is not enough for this operation");
            return error;
        }
    }
}
