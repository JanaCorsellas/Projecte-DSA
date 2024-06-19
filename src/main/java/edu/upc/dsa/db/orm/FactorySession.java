package edu.upc.dsa.db.orm;


import edu.upc.dsa.db.DBUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class FactorySession {

    public static Connection getConnection() {
        String db = DBUtils.getDb();
        String host = DBUtils.getDbHost();
        String port = DBUtils.getDbPort();
        String user = DBUtils.getDbUser();
        String pass = DBUtils.getDbPasswd();


        Connection connection = null;
        try {
            DriverManager.getConnection("jdbc:mariadb://" + host + ":" + port + "/" +
                    db + "?user=" + user + "&password=" + pass);
            System.out.println("jdbc:mariadb://" + host + ":" + port + "/" +
                    db + "?user=" + user + "&password=" + pass);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return connection;
    }

    public static Sessio openSession() {
        Connection conn = null;
        try {
            conn = DBUtils.getConnection(); // Asume que DBUtils tiene el método para obtener la conexión
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new SessioImpl(conn);
    }

}
