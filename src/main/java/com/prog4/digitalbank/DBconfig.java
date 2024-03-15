package com.prog4.digitalbank;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Configuration
public class DBconfig {
    private String username;

    private String password;
    @Bean
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/bank_wallet",
                username = "postgres",
                password = "Sqlohyvqdiko"
        );
    }
}
