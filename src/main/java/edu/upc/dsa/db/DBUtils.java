package edu.upc.dsa.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtils {
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/scaperoom";
    private static final String USER = "root";
    private static final String PASSWORD = "Mazinger72";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}