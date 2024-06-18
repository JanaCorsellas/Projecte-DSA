package edu.upc.dsa.db.orm;


import edu.upc.dsa.db.DBUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class FactorySession {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/dsa";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "janacorsellas";

    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return conn;
    }

    public static Sessio openSession() {
        Connection conn = getConnection();
        if (conn != null) {
            return new SessioImpl(conn);
        } else {
            throw new IllegalStateException("Unable to establish a database connection.");
        }
    }

}
