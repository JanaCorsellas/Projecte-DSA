//package edu.upc.dsa;
//
//import edu.upc.dsa.db.DBUtils;
//import edu.upc.dsa.db.orm.FactorySession;
//import edu.upc.dsa.db.orm.Sessio;
//import edu.upc.dsa.db.orm.SessioImpl;
//import edu.upc.dsa.models.*;
//
//import org.junit.Test;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
//
//public class ORMTest {
//    @Test
//    public void registerTest() {
//        try {
//            Connection conn = DBUtils.getConnection();
//            Sessio session = new SessioImpl(conn);
//            Usuari usuari = new Usuari("nomtest1", "cognomtest", "nomusuartest", "pwdtest", "pwd2test");
//            session.save(usuari); // INSERT INTO usuari (idXXX, pepito, ...)
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    public void loginTest() {
//        String url = "127.0.0.1";
//        String user = "root";
//        String password = "azapata7532";
//
//        Connection conn = null;
//        try {
//            conn = DBUtils.getConnection();
//            Sessio session = new SessioImpl(conn);
//            Usuari e = (Usuari)session.get(Usuari.class, 3);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (conn != null) {
//                    conn.close();
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//}
