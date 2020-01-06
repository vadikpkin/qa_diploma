package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    private static final String URL_MYSQL = "jdbc:mysql://localhost:3306/app";
    private static final String URL_POSTGRESQL = "jdbc:postgresql://localhost:5432/app";
    private static final String USER = "admin";
    private static final String PASS = "password";

    public static Connection getConnection(DataBase dataBase) {
        switch (dataBase) {
            case MYSQL:
                try {
                    return DriverManager.getConnection(URL_MYSQL, USER, PASS);
                } catch (SQLException e) {
                    throw new RuntimeException("failed to connect to db");
                }
            case POSTGRESQL:
                try {
                    return DriverManager.getConnection(URL_POSTGRESQL, USER, PASS);
                } catch (SQLException e) {
                    throw new RuntimeException("failed to connect to db");
                }
            default:
                throw new RuntimeException("failed to connect to db");
        }
    }
}
