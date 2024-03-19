package com.prog4.digitalbank.CrudOperations;

import com.prog4.digitalbank.methods.Conversion;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Repository
@AllArgsConstructor
public class Save<T> {
    private Connection connection;

    public T insert (T entity)throws SQLException {

        String tableName = Conversion
                .convertToSnakeCase
                        (Conversion
                                .firstCharToLowercase(entity.getClass()
                                .getSimpleName()));

        StringBuilder columns = new StringBuilder();
        StringBuilder values = new StringBuilder();

        Field[] fields = entity.getClass().getDeclaredFields();
        for (Field field : fields){
            field.setAccessible(true);
            columns.append(Conversion
                    .convertToSnakeCase(field.getName()))
                    .append(", ");
            values.append("?, ");
        }

        columns.setLength(columns.length()-2);
        values.setLength(values.length()-2);
        String sql = "insert into "+tableName+" ("+columns+") values ("+values+")";

        try (PreparedStatement statement = connection.prepareStatement(sql)){
            int indexParameters = 1 ;
            for (Field field : fields){
                field.setAccessible(true);
                statement.setObject(indexParameters++ , field.get(entity));
            }
            statement.executeUpdate();
        }

        catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        return entity;
    }
}
