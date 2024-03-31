package com.prog4.digitalbank.models.BankstatementModel;

import com.prog4.digitalbank.CrudOperations.FindAll;
import com.prog4.digitalbank.models.BankstatementModel.BankStatement;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
@AllArgsConstructor
public class BankStatementRepository {
    private Connection connection;
    private FindAll<BankStatement> useConvertToList;
    public List<BankStatement> bankStatements(String accountId , int month){
        List<BankStatement> bankStatements =new ArrayList<>();
        String sql = "SELECT \n" +
                "    CAST(transaction.date_time AS DATE) AS date,\n" +
                "    transaction.id AS transaction_ref,\n" +
                "    transaction.type AS operation,\n" +
                "    transaction.amount AS amount,\n" +
                "    balance.amount AS balance,\n" +
                "    sub_category.name AS pattern\n" +
                "FROM \n" +
                "    transaction \n" +
                "INNER JOIN \n" +
                "    balance ON transaction.id = balance.transaction_id \n" +
                "INNER JOIN \n" +
                "    sub_category ON transaction.sub_category_id = sub_category.id \n" +
                "WHERE \n" +
                "    balance.account_id = ? \n" +
                "    and extract(month from transaction.date_time) = ?\n" +
                "ORDER BY \n" +
                "    transaction.date_time ASC;";

        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1,accountId);
            statement.setInt(2,month);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                bankStatements.add(useConvertToList.convertToList(resultSet,BankStatement.class));
            }
            return bankStatements;
        } catch (SQLException | InvocationTargetException | NoSuchMethodException | InstantiationException |
                 IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
