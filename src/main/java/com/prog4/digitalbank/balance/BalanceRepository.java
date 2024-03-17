package com.prog4.digitalbank.balance;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

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

    public String upDateBalances (String accountId , Timestamp referenceDate , Double amount){
        String sql = "update balance set amount = amount + ? where account_id = ? and date_time > ? ";
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDouble(1, amount);
            statement.setString(2, accountId);
            statement.setTimestamp(3,referenceDate);
            statement.executeUpdate();
            return "updated successfully";
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}