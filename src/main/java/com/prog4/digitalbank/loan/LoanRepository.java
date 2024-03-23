package com.prog4.digitalbank.loan;

import lombok.AllArgsConstructor;
import org.postgresql.shaded.com.ongres.scram.common.ScramStringFormatting;
import org.springframework.stereotype.Repository;

import java.sql.*;

@AllArgsConstructor
@Repository
public class LoanRepository {
    private Connection connection;

    public LoanEvolution findByAccountId (String accountId){
        String sql = "select loan_evolution.id , loan_evolution.date_time ,\n" +
                "loan_evolution.total_interest , loan_evolution.rest , loan_evolution.bank_loan_id \n" +
                " from loan_evolution inner join bank_loan \n"+
                "on loan_evolution.bank_loan_id = bank_loan.id \n" +
                "where bank_loan.account_id = ? order by loan_evolution.date_time desc limit 1";
        LoanEvolution loanEvolution = null;

        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1,accountId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                String id = resultSet.getString("id");
                Timestamp dateTime = resultSet.getTimestamp("date_time");
                Double totalInterest = resultSet.getDouble("total_interest");
                Double rest = resultSet.getDouble("rest");
                String bankLoanId = resultSet.getString("bank_loan_id");
                 loanEvolution = new LoanEvolution(id,dateTime,totalInterest,rest,bankLoanId);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return loanEvolution;
    }


}
