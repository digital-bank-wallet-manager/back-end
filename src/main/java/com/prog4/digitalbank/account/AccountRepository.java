package com.prog4.digitalbank.account;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Repository
@AllArgsConstructor
public class AccountRepository {
    private Connection connection;

    public String updateAuthorization(String tableName , String id , String columnName , boolean newValue){
        String sql = "update "+tableName+" set "+columnName+"=? where id=?";

        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setBoolean(1,newValue);
            statement.setString(2, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return "you are now allowed to loan from D-Bank";

    }

    public String updateMonthlyPay(String tableName , String id , String columnName , Double newValue){
        String sql = "update "+tableName+" set "+columnName+"=? where id=?";

        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDouble(1,newValue);
            statement.setString(2, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return "your monthly payed has ben updated to "+newValue;

    }

}
