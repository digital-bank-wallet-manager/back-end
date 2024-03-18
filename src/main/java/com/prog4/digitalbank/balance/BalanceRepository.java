package com.prog4.digitalbank.balance;

import com.prog4.digitalbank.CrudOperations.FindAll;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@AllArgsConstructor
public class BalanceRepository {
    private Connection connection;

    public List<Balance> getLastBalanceById(String accountId , Timestamp referenceDate){
        List<Balance> balances = new ArrayList<>();
        String sql = "select * from balance where account_id = ? and date_time < ? order by date_time desc limit 1";
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1 , accountId);
            statement.setTimestamp(2,referenceDate);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()){
                String id = resultSet.getString("id");
                Double amount = resultSet.getDouble("amount");
                Timestamp dateTime = resultSet.getTimestamp("date_time");
                String accountId1 = resultSet.getString("account_id");
                balances.add(new Balance(id , amount , dateTime , accountId1));
            }

            return balances;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Balance> getNotEffectiveBalance(String accountId , Timestamp referenceDate){
        List<Balance> balances = new ArrayList<>();
        String sql = "select * from balance where account_id = ? and date_time > ? ";
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1 , accountId);
            statement.setTimestamp(2,referenceDate);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()){
                String id = resultSet.getString("id");
                Double amount = resultSet.getDouble("amount");
                Timestamp dateTime = resultSet.getTimestamp("date_time");
                String accountId1 = resultSet.getString("account_id");
                balances.add(new Balance(id , amount , dateTime , accountId1));
            }

            return balances;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String upDateBalances (String accountId , Timestamp referenceDate , Double amount , String transactionId){
        String sql = "update balance set amount = amount + ? where account_id = ? and transaction_id != ? and date_time >= ? ";
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDouble(1, amount);
            statement.setString(2, accountId);
            statement.setString(3,transactionId);
            statement.setTimestamp(4,referenceDate);
            statement.executeUpdate();
            return "updated successfully";
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Balance> findBalancesByAccountIdAndPeriod(String accountId , Timestamp timeStart , Timestamp timeEnd){
        String sql = "select * FROM balance where account_id = ? AND (date_time >= ? and date_time <= ?) order by date_time asc";
        List<Balance> balances = new ArrayList<>();
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, accountId);
            statement.setTimestamp(2, timeStart);
            statement.setTimestamp(3,timeEnd);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                String id = resultSet.getString("id");
                Double amount = resultSet.getDouble("amount");
                Timestamp timestamp = resultSet.getTimestamp("date_time");
                String accountId1 = resultSet.getString("account_id");
                String transactionId = resultSet.getString("transaction_id");
                Balance balance = new Balance(id , amount , timestamp , accountId1 , transactionId);
                balances.add(balance);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return balances;
    }
}
