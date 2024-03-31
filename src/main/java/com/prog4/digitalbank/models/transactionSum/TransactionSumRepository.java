package com.prog4.digitalbank.models.transactionSum;

import com.prog4.digitalbank.CrudOperations.FindAll;
import com.prog4.digitalbank.CrudOperations.FindById;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.lang.reflect.InvocationTargetException;
import java.security.PrivilegedAction;
import java.sql.*;

@Repository
@AllArgsConstructor
public class TransactionSumRepository {
    private Connection connection;
    private FindAll<TransactionSum> useConvertToList;

    public TransactionSum findTransactionSum( String categoryName){
        TransactionSum transactionSum = null;
        String sql = "SELECT SUM(transaction_sum) AS sum, category_name FROM (SELECT SUM(t.amount) AS transaction_sum, c.name AS category_name\n" +
                "    FROM \"transaction\" AS t\n" +
                "    LEFT JOIN \"sub_category\" AS sc\n" +
                "    ON t.sub_category_id = sc.id\n" +
                "    INNER JOIN \"category\" AS c\n" +
                "    ON \"sc\".category_id = \"c\".id\n" +
                "    GROUP BY c.name, t.date_time\n" +
                "    HAVING c.name ilike ?) AS by_sub_category GROUP BY by_sub_category.category_name, category_name;";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1,categoryName);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                transactionSum = useConvertToList.convertToList(resultSet, TransactionSum.class);
            }
            return transactionSum;
        } catch (SQLException | InvocationTargetException | NoSuchMethodException | InstantiationException |
                 IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
