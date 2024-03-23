package com.prog4.digitalbank.transactions;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@AllArgsConstructor
@Repository
public class TransactionRepository {
    private Connection connection;

    public void updateStatus() throws SQLException {
        String sql = "update transaction set status = 'done' where (status = 'apending' or status is null) and date_time <= current_timestamp";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.executeUpdate();
        }
    }
}
