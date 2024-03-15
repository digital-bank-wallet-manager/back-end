package com.prog4.digitalbank.CrudOperations;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
@AllArgsConstructor
@Repository
public class FindAll <T>{
    private Connection connection;

    public List<T> findAll(Class<T> clazz) throws SQLException {
        List<T> entities = new ArrayList<>();
        String sql = "select * from "+clazz.getSimpleName().toLowerCase();

        try(PreparedStatement statement = connection.prepareStatement(sql)){
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){
                entities.add(convertToList(resultSet, clazz ));
            }

        }catch (SQLException | NoSuchMethodException | InvocationTargetException | InstantiationException |
                IllegalAccessException e){
            throw new RuntimeException(e) ;
        }finally {
            connection.close();
        }

        return entities;
    }

    private T convertToList (ResultSet resultSet, Class<T> clazz ) throws NoSuchMethodException,
                                                                                    SQLException,
                                                                                    InvocationTargetException,
                                                                                    InstantiationException,
                                                                                    IllegalAccessException {
        T entity = clazz.getDeclaredConstructor().newInstance();
        Field[] fields = clazz.getDeclaredFields();

        try {
            for (Field field : fields) {
                field.setAccessible(true);

                Object value = resultSet.getObject(field.getName());
                field.set(entity, value);
            }

        }catch (SQLException | IllegalAccessException e){
            throw new SQLException("conversion failed");
        }
        return entity;
    }


}