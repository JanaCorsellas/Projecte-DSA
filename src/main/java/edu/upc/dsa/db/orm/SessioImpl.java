package edu.upc.dsa.db.orm;

import edu.upc.dsa.util.ObjectHelper;
import edu.upc.dsa.util.QueryHelper;

import java.sql.*;
import java.util.HashMap;
import java.util.List;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class SessioImpl implements Sessio {
    private final Connection conn;

    public SessioImpl(Connection conn) {
        this.conn = conn;
    }


    @Override
    public void save(Object entity) {
        if (conn == null) {
            throw new IllegalStateException("Connection is not initialized.");
        }

        String insertQuery = QueryHelper.createQueryINSERT(entity);

        try (PreparedStatement pstm = conn.prepareStatement(insertQuery)) {
            int i = 1;
            for (String field : ObjectHelper.getFields(entity)) {
                if (!field.equalsIgnoreCase("id")) {
                    pstm.setObject(i++, ObjectHelper.getter(entity, field));
                }
            }

            pstm.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void close() {

    }

    @Override
    public Object get(Class theClass, Object ID) {
        return null;
    }

    public Object get(Class theClass, int ID) {

//        String sql = QueryHelper.createQuerySELECT(theClass);
//
//        Object o = theClass.newInstance();
//
//
//        ResultSet res = null;
//
//        ResultSetMetaData rsmd = res.getMetaData();
//
//        int numColumns = rsmd.getColumnCount();
//        int i=0;
//
//        while (i<numColumns) {
//            String key = rsmd.getColumnName(i);
//            String value = res.getObject(i);
//
//            ObjectHelper.setter(o, key, value);
//
//        }

        return null;
    }

    public void update(Object object) {

    }

    public void delete(Object object) {

    }

    public List<Object> findAll(Class theClass) {
        return null;
    }

    public List<Object> findAll(Class theClass, HashMap params) {
     /*   String theQuery = QueryHelper.createSelectFindAll(theClass, params);
        PreparedStatement pstm = null;
        pstm = conn.prepareStatement(theQuery);

        int i=1;
        for (Object value : params.values()) {
            pstm.setObject(i++, value );
        }
        //ResultSet rs = pstm.executeQuery();




        return result;
*/
     return null;
    }

    public List<Object> query(String query, Class theClass, HashMap params) {
        return null;
    }
}
