package com.prog4.digitalbank.loan;
import com.prog4.digitalbank.CrudOperations.FindAll;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@Repository
public class LoanRepository {
    private Connection connection;
    private FindAll<BankLoan> useConvertList;


    public List<BankLoan> findByAccountId (String accountId){
        String sql = "select * from bank_loan where account_id = ? and status = ?";
        List<BankLoan> bankLoans = new ArrayList<>();

        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1,accountId);
            statement.setString(2,"unpaid");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                bankLoans.add(useConvertList.convertToList(resultSet , BankLoan.class));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return bankLoans;
    }


    public LoanEvolution getLastState(String bankLoanId){
        LoanEvolution loanEvolutions = null;
        String sql = "select * from loan_evolution where bank_loan_id = ? and date_time <= current_timestamp order by date_time desc limit 1";
        try(PreparedStatement statement = connection.prepareStatement(sql) ) {
            statement.setString(1,bankLoanId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                String id = resultSet.getString("id");
                Timestamp timestamp = resultSet.getTimestamp("date_time");
                Double interest = resultSet.getDouble("total_interest");
                Double rest = resultSet.getDouble("rest");
                String bankLoanId1 = resultSet.getString("bank_loan_id");
                loanEvolutions = new LoanEvolution(id,timestamp,interest,rest,bankLoanId1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return loanEvolutions;
    }

    public void updateBankLoanStatus(){
        String sql = "update bank_loan set status = 'paid' from loan_evolution \n" +
                "where bank_loan.id = loan_evolution.bank_loan_id and loan_evolution.rest = 0";
        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



}
