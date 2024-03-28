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
    private FindAll<Balance> balanceFindAll;
    public Balance getLastBalanceById(String accountId , Timestamp referenceDate){
        Balance balances = new Balance();
        String sql = "select * from balance where account_id = ? and date_time <= ? order by date_time desc limit 1";
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1 , accountId);
            statement.setTimestamp(2,referenceDate);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()){
                String id = resultSet.getString("id");
                Double amount = resultSet.getDouble("amount");
                Timestamp dateTime = resultSet.getTimestamp("date_time");
                String accountId1 = resultSet.getString("account_id");
                balances = new Balance(id , amount , dateTime , accountId1);
            }

            return balances;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List<Balance> findBalanceByDate(String accountId, Date date){
        List<Balance> balances = new ArrayList<>();
        String Sql = "SELECT * FROM balance WHERE DATE_TRUNC('day',date_time) <= ? and account_id = ? order by date_time desc limit 1";
        try(PreparedStatement statement = connection.prepareStatement(Sql)) {
            statement.setDate(1,date);
            statement.setString(2,accountId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                balances.add(balanceFindAll.convertToList(resultSet , Balance.class));
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
        return balances;
    }
}
