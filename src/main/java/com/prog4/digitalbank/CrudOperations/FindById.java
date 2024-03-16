package com.prog4.digitalbank.CrudOperations;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@AllArgsConstructor
@Repository
public class FindById<T>{
    private final Connection connection;
    private final FindAll<T> findAll;

    public T findById (Class<T> clazz , String id){
        T entity = null ;
        String tableName = CamelCaseToSnakeCase
                .convertToSnakeCase(clazz.getSimpleName().toLowerCase());
        String sql = "select * from "+tableName+" where id = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1 , id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                entity = findAll.convertToList(resultSet , clazz);
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
