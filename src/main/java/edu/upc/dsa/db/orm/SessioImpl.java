package edu.upc.dsa.db.orm;

import edu.upc.dsa.models.Usuari;
import edu.upc.dsa.util.ObjectHelper;
import edu.upc.dsa.util.QueryHelper;

import java.sql.*;
import java.util.HashMap;
import java.util.LinkedList;
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
    public void save(Object entity){
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
        try {
            this.conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object get(Class theClass, String columna, String value) throws SQLException {
        String selectQuery  = QueryHelper.createQuerySELECT(theClass, columna, value);
        ResultSet rs;
        PreparedStatement pstm = null;

        try {
            pstm = conn.prepareStatement(selectQuery);
            pstm.setObject(1, value); //son los ?
            rs = pstm.executeQuery();
            Object o = theClass.newInstance();

            if (!rs.next()) {
                // No records found
                o = null;
            } else{
                ResultSetMetaData rsmd = rs.getMetaData();
                int numberOfColumns = rsmd.getColumnCount();

                do {
                    for (int i = 1; i <= numberOfColumns; i++) {
                        String columnName = rsmd.getColumnName(i);
                        ObjectHelper.setter(o, columnName, rs.getObject(i));
                    }
                } while (rs.next());
            }

            return o;

        } catch (SQLException e) {
            throw new SQLException();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(int saldo, String novaSkin, String nomUsuari) throws SQLException {
        String query = QueryHelper.createQueryUPDATE();
        PreparedStatement pstm = null;
        try {
            pstm = conn.prepareStatement(query);
            pstm.setInt(1, saldo);
            pstm.setString(2, novaSkin);
            pstm.setString(3, nomUsuari);
            pstm.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new SQLIntegrityConstraintViolationException();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (pstm != null) {
                pstm.close();
            }
        }
    }

    public void delete(Object object) {

    }

    public <T> List<T> findAll(Class theClass) {
        String query = QueryHelper.createQuerySELECTall(theClass);
        PreparedStatement pstm =null;
        ResultSet rs;
        List<T> list = new LinkedList<>();
        try {
            pstm = conn.prepareStatement(query);
            pstm.executeQuery();
            rs = pstm.getResultSet();

            ResultSetMetaData metadata = rs.getMetaData();
            int numberOfColumns = metadata.getColumnCount();

            while (rs.next()){
                T o = (T) theClass.newInstance();
                for (int j=1; j<=numberOfColumns; j++){
                    String columnName = metadata.getColumnName(j);
                    ObjectHelper.setter(o, columnName, rs.getObject(j));
                }
                list.add(o);
            }
        } catch (SQLException | InstantiationException | IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Object> query(String query, Class theClass, HashMap params) {
        return null;
    }
}
