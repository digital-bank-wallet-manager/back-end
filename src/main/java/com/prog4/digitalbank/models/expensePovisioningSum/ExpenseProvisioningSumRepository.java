package com.prog4.digitalbank.models.expensePovisioningSum;

import com.prog4.digitalbank.CrudOperations.FindAll;
import com.prog4.digitalbank.category.Category;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;

@Repository
@AllArgsConstructor
public class ExpenseProvisioningSumRepository {
    private Connection connection;
    private FindAll<ExpenseSum> convertToList;
    private FindAll<ProvisioningSum> toList;

    public ExpenseSum expenseSum(String category){
        ExpenseSum expenseSum = null;
        String sql = "SELECT SUM(expense_sum) AS expense_total_sum, category_name FROM (SELECT SUM(e.amount) AS expense_sum, c.name AS category_name\n" +
                "      FROM \"expense\" AS e\n" +
                "      INNER JOIN \"transaction\" AS t\n" +
                "      ON t.expense_id = e.id\n" +
                "      INNER JOIN \"sub_category\" AS sc\n" +
                "      ON t.sub_category_id = sc.id\n" +
                "      INNER JOIN \"category\" AS c\n" +
                "      ON sc.category_id = c.id\n" +
                "      GROUP BY c.name, e.date_time\n" +
                "      HAVING c.name ilike ?) AS by_sub_category GROUP BY by_sub_category.category_name, category_name;";

        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1,category);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
               expenseSum = convertToList.convertToList(resultSet , ExpenseSum.class);
            }
        } catch (SQLException | InvocationTargetException | NoSuchMethodException | InstantiationException |
                 IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        return expenseSum;
    }

    public ExpenseSum expenseSumByDate(String category , Date start , Date end){
        ExpenseSum expenseSum = null;
        String sql = "SELECT SUM(expense_sum) AS expense_total_sum, category_name FROM (SELECT SUM(e.amount) AS expense_sum, c.name AS category_name\n" +
                "    FROM \"expense\" AS e\n" +
                "             INNER JOIN \"transaction\" AS t\n" +
                "                        ON t.expense_id = e.id\n" +
                "             INNER JOIN \"sub_category\" AS sc\n" +
                "                        ON t.sub_category_id = sc.id\n" +
                "             INNER JOIN \"category\" AS c\n" +
                "                        ON sc.category_id = c.id\n" +
                "    GROUP BY c.name, e.date_time\n" +
                "    HAVING c.name ilike ?\n" +
                "    AND e.date_time BETWEEN ? AND ?) AS by_sub_category GROUP BY by_sub_category.category_name, category_name;";

        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1,category);
            statement.setDate(2,start);
            statement.setDate(3,end);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                expenseSum = convertToList.convertToList(resultSet , ExpenseSum.class);
            }
        } catch (SQLException | InvocationTargetException | NoSuchMethodException | InstantiationException |
                 IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        return expenseSum;
    }

    public ProvisioningSum provisioningSum(String category){
        ProvisioningSum provisioningSum = null;
        String sql = "SELECT SUM(provisioning_sum) AS total_sum, category_name FROM (SELECT SUM(p.amount) AS provisioning_sum, c.name as category_name\n" +
                "                                                               FROM \"provisioning\" AS p\n" +
                "                                                                        INNER JOIN \"transaction\" AS t\n" +
                "                                                                                   ON t.expense_id = p.id\n" +
                "                                                                        INNER JOIN \"sub_category\" AS sc\n" +
                "                                                                                   ON t.sub_category_id = sc.id\n" +
                "                                                                        INNER JOIN \"category\" AS c\n" +
                "                                                                                   ON sc.category_id = c.id\n" +
                "                                                               GROUP BY c.name, p.effective_date\n" +
                "                                                               HAVING c.name ilike ?\n" +
                "                                                              ) AS by_sub_category GROUP BY by_sub_category.category_name, category_name;";

        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1,category);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                provisioningSum = toList.convertToList(resultSet , ProvisioningSum.class);
            }
        } catch (SQLException | InvocationTargetException | NoSuchMethodException | InstantiationException |
                 IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        return provisioningSum;
    }

    public ProvisioningSum provisioningSumByDate(String category,Date start , Date end){
        ProvisioningSum provisioningSum = null;
        String sql = "SELECT SUM(provisioning_sum) AS total_sum, category_name FROM (SELECT SUM(p.amount) AS provisioning_sum, c.name as category_name\n" +
                "                                                               FROM \"provisioning\" AS p\n" +
                "                                                                        INNER JOIN \"transaction\" AS t\n" +
                "                                                                                   ON t.expense_id = p.id\n" +
                "                                                                        INNER JOIN \"sub_category\" AS sc\n" +
                "                                                                                   ON t.sub_category_id = sc.id\n" +
                "                                                                        INNER JOIN \"category\" AS c\n" +
                "                                                                                   ON sc.category_id = c.id\n" +
                "                                                               GROUP BY c.name, p.effective_date\n" +
                "                                                               HAVING c.name ilike ?\n" +
                "                                                               AND p.effective_date BETWEEN ? AND ?\n" +
                "                                                              ) AS by_sub_category GROUP BY by_sub_category.category_name, category_name;";

        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1,category);
            statement.setDate(2,start);
            statement.setDate(3,end);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                provisioningSum = toList.convertToList(resultSet , ProvisioningSum.class);
            }
        } catch (SQLException | InvocationTargetException | NoSuchMethodException | InstantiationException |
                 IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        return provisioningSum;
    }
}




