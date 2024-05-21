package edu.upc.dsa.db.orm;

import edu.upc.dsa.util.ObjectHelper;
import edu.upc.dsa.util.QueryHelper;

import java.sql.*;
import java.util.HashMap;
import java.util.List;


public class SessioImpl implements Sessio {
    private final Connection conn;

    public SessioImpl(Connection conn) {
        this.conn = conn;
    }

    public void save(Object entity) {

        // INSERT INTO Partida () ()
        String insertQuery = QueryHelper.createQueryINSERT(entity);
        // INSERT INTO User (ID, lastName, firstName, address, city) VALUES (0, ?, ?, ?,?)


        PreparedStatement pstm = null;

        try {
            pstm = conn.prepareStatement(insertQuery);
            pstm.setObject(1, 0);
            int i = 2;

            for (String field: ObjectHelper.getFields(entity)) {
                pstm.setObject(i++, ObjectHelper.getter(entity, field));
            }

            pstm.executeQuery();

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
