package com.prog4.digitalbank.transactions;

import com.prog4.digitalbank.CrudOperations.FindAll;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Repository
public class TransactionRepository {
    private Connection connection;
    private FindAll<Transaction> findAll;

    public void updateStatus() throws SQLException {
        String sql = "update transaction set status = 'done' where (status = 'apending' or status is null) and date_time <= current_timestamp";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.executeUpdate();
        }
    }

    public void cancel(String transactionId) throws SQLException {
        String sql = "update transaction set status = 'canceled' where (status = 'apending' or status is null) and transaction_id = ?";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1,transactionId);
            statement.executeUpdate();
        }
    }

    public List<Transaction> getNotAppliedTransaction(){
        String sql = "SELECT transaction.*FROM transaction \n" +
                "LEFT JOIN balance ON transaction.id = balance.transaction_id \n" +
                "WHERE balance.transaction_id IS NULL and status = 'done' order by transaction.date_time asc;";
        List<Transaction> transactions = new ArrayList<>();
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                transactions.add(findAll.convertToList(resultSet , Transaction.class));
            }
        } catch (SQLException | InvocationTargetException | NoSuchMethodException | InstantiationException |
                 IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return transactions;
    }

}
