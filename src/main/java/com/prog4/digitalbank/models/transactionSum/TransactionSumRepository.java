package com.prog4.digitalbank.models.transactionSum;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
@AllArgsConstructor
public class TransactionSumRepository {
    private Connection connection;

    public Double findTransactionSum(Date start , Date end , String category){
        Double sum = 0.0;
        String sql = "SELECT SUM(t.amount) AS transaction_sum\n" +
                "FROM \"transaction\" AS t\n" +
                "         INNER JOIN \"sub_category\" AS sc\n" +
                "                    ON t.sub_category_id = sc.id\n" +
                "         INNER JOIN \"category\" AS c\n" +
                "                    ON \"sc\".category_id = \"c\".id\n" +
                "GROUP BY c.name, t.date_time\n" +
                "HAVING c.name ilike ?\n" +
                "AND t.date_time BETWEEN ? AND ?;";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1,category);
            preparedStatement.setDate(2,start);
            preparedStatement.setDate(3,end);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                 sum = resultSet.getDouble("transaction_sum");
            }
            return sum;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
