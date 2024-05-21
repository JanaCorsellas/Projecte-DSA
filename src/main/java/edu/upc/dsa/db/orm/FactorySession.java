package edu.upc.dsa.db.orm;


import edu.upc.dsa.db.DBUtils;

import java.sql.Connection;
import java.sql.DriverManager;

public class FactorySession {

    public static Sessio openSession() {
        Connection conn = getConnection();
        Sessio session = new SessioImpl(conn);
        return session;
    }



    public static Connection getConnection()  {
        String db = DBUtils.getDb();
        String host = DBUtils.getDbHost();
        String port = DBUtils.getDbPort();
        String user = DBUtils.getDbUser();
        String pass = DBUtils.getDbPasswd();


        Connection connection = null;
        try {
            DriverManager.getConnection("jdbc:mariadb://"+host+":"+port+"/"+
                    db+"?user="+user+"&password="+pass);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return connection;
    }

    public static Sessio openSession(String url, String user, String password) {
        return null;
    }
}
