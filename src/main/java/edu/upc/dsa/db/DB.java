package edu.upc.dsa.db;

import java.sql.*;

public class DB {

    public static void insert() throws SQLException{

        Connection connection = DBUtils.getConnection();
        Statement statement1  = connection.createStatement();
        statement1.execute("INSERT INTO user (id, nom, cognom, nomusuari, password) VALUES (0,'nom5', 'cognom1', 'nomusuari1', 'password1')");
        // i = x / 0
        connection.close();

/*
        Connection connection=null;
        try {
            connection = DBUtils.getConnection();


            Statement statement1  = connection.createStatement();
            statement1.execute("INSERT INTO User (ID, lastName, firstName, address, city) VALUES (0,'88888', 'thMceF\'irstName', 'theAddress', 'theCity')");
            //connection.close();

        }
        catch (Exception e) {
           // ...
        }
        finally {
            connection.close();
        }
*/
    }


    private static String getType (int type) {
        String ret = null;
        switch (type) {
            case Types.VARCHAR:
                ret ="VARCHAR";
                break;
            case Types.INTEGER:
                ret = "INTEGER";
                break;
            case Types.DATE:
                ret = "DATE";
                break;

        }

        return ret;
    }


    public static void findAll()  throws Exception {
        Connection connection = null;
        try {
            connection = DBUtils.getConnection();
            Statement statement2 = connection.createStatement();
            ResultSet rs = statement2.executeQuery("SELECT *  FROM user WHERE 1=1");
            // INTROSPECCIÓ BBDD
            ResultSetMetaData rsmd = rs.getMetaData();
            System.out.println("cols "+rsmd.getColumnCount());
            int i = 1;
            while (i<=rsmd.getColumnCount()) {
                System.out.println("col  "+i+" "+rsmd.getColumnName(i)+" "+
                           rsmd.getColumnType(i)+ " "+DB.getType(rsmd.getColumnType(i)));
                i++;
            }

            int id;
            String nom, cognom, nomusuari, password;

            while (rs.next()) {
                id = (Integer) rs.getObject(1); // 0
                nom = (String) rs.getObject(2);
                cognom = (String) rs.getObject(3);
                nomusuari = (String) rs.getObject(4);
                password = (String) rs.getObject(5);
                System.out.println(id+" "+nom+" "+nomusuari+" "+cognom+" "+password);

                // Per cada propietat dins de la fila:
                // while (j<=rsmd.getNumColumn()) {
                     // theValue = rs.getObject(j);
                     // theProperty = rsmd.getColumnName(j);
                     // ObjectHelper.setter(theObject, theProperty, theValue);
                // }
            }
        } catch (Exception e) {
            //log.d ("", e)
            e.printStackTrace();
        } finally {
            connection.close();
        }
    }

    public static void main(String[] args) throws Exception {
        insert();
        findAll();
    }

}
