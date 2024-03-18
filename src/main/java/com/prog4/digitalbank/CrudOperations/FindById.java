package com.prog4.digitalbank.CrudOperations;

import com.prog4.digitalbank.balance.Balance;
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
public class FindById<T> {
    private final Connection connection;
    private final FindAll<T> findAll;

    public T findByIdOrderd(Class<T> clazz, String id , String order) {
        T entity = null;
        String tableName = CamelCaseToSnakeCase
                .convertToSnakeCase(clazz.getSimpleName().toLowerCase());
        String sql = "select * from " + tableName + " where id = ? "+order;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                entity = findAll.convertToList(resultSet, clazz);
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

        return entity;
    }

    public List<T> findByAccountId(Class<T> clazz, String id , String order) {
        List<T> entity = new ArrayList<>();
        String tableName = CamelCaseToSnakeCase
                .convertToSnakeCase(clazz.getSimpleName().toLowerCase());
        String sql = "select * from " + tableName + " where account_id = ? and date_time <= current_timestamp "+order;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                entity.add(findAll.convertToList(resultSet , clazz));
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

        return entity;
    }

}
