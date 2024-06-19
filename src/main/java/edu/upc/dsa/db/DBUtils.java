package edu.upc.dsa.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtils {

    public static final String DB_NAME = "scaperoom";
    public static final String DB_HOST = "127.0.0.1";
    public static final String DB_USER = "scapeUser";
    public static final String DB_PASS = "sc4p3r00m";
    public static final String DB_PORT = "3306";

    public static String getDb() {
        return DB_NAME;
    }

    public static String getDbHost(){
        return DB_HOST;
    }

    public static String getDbUser() {
        return DB_USER;
    }

    public static String getDbPasswd() {
        return DB_PASS;
    }

    public static  String getDbPort() {
        return DB_PORT;
    }

    public static Connection getConnection() throws SQLException {
        String db = DBUtils.getDb();
        String host = DBUtils.getDbHost();
        String port = DBUtils.getDbPort();
        String user = DBUtils.getDbUser();
        String pass = DBUtils.getDbPasswd();

        Connection connection = DriverManager.getConnection("jdbc:mariadb://" + host + ":" + port + "/" +
                db + "?user=" + user + "&password=" + pass);

        // Verificar si la conexión se ha establecido correctamente
        if (connection != null) {
            System.out.println("Conexión establecida correctamente.");
        } else {
            System.out.println("Error al establecer la conexión.");
        }

        return connection;
    }

}
