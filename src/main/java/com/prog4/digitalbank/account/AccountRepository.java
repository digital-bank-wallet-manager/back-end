package com.prog4.digitalbank.account;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.*;

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

    public void updateMonthlyPay(String tableName , String id , String columnName , Double newValue){
        String sql = "update "+tableName+" set "+columnName+"=? where id=?";

        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDouble(1,newValue);
            statement.setString(2, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Account findByAccountRef(String accountRef , String firstName , String lastName){
        String sql = "select * from account where account_ref = ? and (first_name ilike ? and last_name ilike ?)";
        Account account = null;
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, accountRef);
            statement.setString(2,firstName);
            statement.setString(3 , lastName);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                String id = resultSet.getString("id");
                String firstNameRes = resultSet.getString("first_name");
                String lastNameRes = resultSet.getString("last_name");
                Date birthdate = resultSet.getDate("birthdate");
                Boolean loanAuthorization = resultSet.getBoolean("loan_authorization");
                Double monthlyPay = resultSet.getDouble("monthly_pay");
                String accountRefRes = resultSet.getString("account_ref");

                account = new Account(id,
                        firstNameRes ,
                        lastNameRes ,
                        birthdate ,
                        loanAuthorization ,
                        monthlyPay,
                        accountRefRes);
            }

            return account;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
