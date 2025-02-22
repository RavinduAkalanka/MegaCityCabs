package com.bms.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {
    private static DatabaseConfig instance;
    private Connection connection;

    private static final String URL = "jdbc:mysql://localhost:3306/megacitycabs?characterEncoding=utf8&serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Ravi@2001#";

    private DatabaseConfig() {
        try {
            // Load the MySQL driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Establish connection
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Database connection established successfully.");

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Database connection failed! Check your credentials and database configuration.", e);
        }
    }

    public static DatabaseConfig getInstance() {
        if (instance == null) {
            synchronized (DatabaseConfig.class) {
                if (instance == null) {
                    instance = new DatabaseConfig();
                }
            }
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

}
