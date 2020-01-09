package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    private static final String USER = "admin";
    private static final String PASS = "password";

    public static Connection getConnection() {

        try {
            return DriverManager.getConnection(System.getProperty("db.url"), USER, PASS);
        } catch (SQLException e) {
            throw new RuntimeException("failed to connect to db");
        }

    }

}
