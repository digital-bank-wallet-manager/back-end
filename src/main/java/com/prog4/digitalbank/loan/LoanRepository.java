package com.prog4.digitalbank.loan;

<<<<<<< HEAD
import com.prog4.digitalbank.CrudOperations.FindAll;
=======
>>>>>>> Prod
import lombok.AllArgsConstructor;
import org.postgresql.shaded.com.ongres.scram.common.ScramStringFormatting;
import org.springframework.stereotype.Repository;

<<<<<<< HEAD
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
=======
import java.sql.*;
>>>>>>> Prod

@AllArgsConstructor
@Repository
public class LoanRepository {
    private Connection connection;
<<<<<<< HEAD
    private FindAll<LoanEvolution> useConvertList;
=======
>>>>>>> Prod

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

<<<<<<< HEAD
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

   /* public LoanEvolution getActualLoan (String accountId){
        String sql = "SELECT COALESCE(CAST(rest AS DOUBLE PRECISION), 0) as tota ,\n " +
                "" +
                "FROM loan_evolution \n" +
                "INNER JOIN bank_loan ON loan_evolution.bank_loan_id = bank_loan.id \n" +
                "WHERE DATE_TRUNC('day', loan_evolution.date_time) <= CURRENT_DATE \n" +
                "AND account_id =?;\n order by ";
        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1,accountId);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }*/
=======
>>>>>>> Prod

}
