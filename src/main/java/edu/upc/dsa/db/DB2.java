package edu.upc.dsa.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DB2 extends DB{
    /*public static void insert() throws SQLException{
        Connection connection = DBUtils.getConnection();

        // SQL INJECTION
        String theQuery = "INSERT INTO user (id, nom, cognom, nomusuari, password) VALUES (1, ?, ?, ?,?)";
        // log.debug

        PreparedStatement statement1  =  connection.prepareStatement(theQuery);
        statement1.setString(1, "nom6");
        statement1.setString(2, "cognom2");
        statement1.setString(3, "nomusuari2");
        statement1.setString(4, "password2");

        // a = b / 0  - null.method();

        /// NULLPOINTER ??
        statement1.execute();
        /// NULLPOINTER ??


        connection.close();

    }*/

    public static void insert() throws SQLException {
        Connection connection = null;
        try {
            connection = DBUtils.getConnection();
            String query = "INSERT INTO Usuari (id, nom, cognom, nomusuari, password) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement statement1 = connection.prepareStatement(query)) {
                statement1.setInt(1, 1);
                statement1.setString(2, "nom6");
                statement1.setString(3, "cognom2");
                statement1.setString(4, "nomusuari2");
                statement1.setString(5, "password2");
                statement1.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    public static void main(String[] args) throws Exception {
        insert();
        findAll();
    }
}
