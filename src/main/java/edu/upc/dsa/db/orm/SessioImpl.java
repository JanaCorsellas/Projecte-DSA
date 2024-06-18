package edu.upc.dsa.db.orm;

import edu.upc.dsa.util.ObjectHelper;
import edu.upc.dsa.util.QueryHelper;

import java.sql.*;
import java.util.ArrayList;
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
    public void save(Object entity) throws SQLIntegrityConstraintViolationException {
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
        } catch (SQLIntegrityConstraintViolationException e) {
            throw e; // Rethrow to indicate constraint violation
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void close() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public Object get(Class theClass, Object ID) throws SQLException {
        if (conn == null) {
            throw new IllegalStateException("Connection is not initialized.");
        }

        String selectQuery = QueryHelper.createQuerySELECT(theClass);
        try (PreparedStatement pstm = conn.prepareStatement(selectQuery)) {
            pstm.setObject(1, ID);
            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    Object instance = theClass.getDeclaredConstructor().newInstance();
                    ResultSetMetaData metaData = rs.getMetaData();
                    int columnCount = metaData.getColumnCount();
                    for (int i = 1; i <= columnCount; i++) {
                        String columnName = metaData.getColumnName(i);
                        Object columnValue = rs.getObject(i);
                        ObjectHelper.setter(instance, columnName, columnValue);
                    }
                    return instance;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public void update(Object entity) {
        if (conn == null) {
            throw new IllegalStateException("Connection is not initialized.");
        }

        // Implementación de la consulta UPDATE
        StringBuilder query = new StringBuilder("UPDATE ");
        query.append(entity.getClass().getSimpleName().toLowerCase()).append(" SET ");

        String[] fields = ObjectHelper.getFields(entity);
        for (String field : fields) {
            if (!field.equalsIgnoreCase("id")) {
                query.append(field).append(" = ?, ");
            }
        }
        query.delete(query.length() - 2, query.length()); // Eliminar la última coma y espacio
        query.append(" WHERE id = ?");

        try (PreparedStatement pstm = conn.prepareStatement(query.toString())) {
            int i = 1;
            for (String field : fields) {
                if (!field.equalsIgnoreCase("id")) {
                    pstm.setObject(i++, ObjectHelper.getter(entity, field));
                }
            }
            pstm.setObject(i, ObjectHelper.getter(entity, "id"));
            pstm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Object entity) {
        if (conn == null) {
            throw new IllegalStateException("Connection is not initialized.");
        }

        String deleteQuery = QueryHelper.createQueryDELETE(entity.getClass(), "id", ObjectHelper.getter(entity, "id").toString());

        try (PreparedStatement pstm = conn.prepareStatement(deleteQuery)) {
            pstm.setObject(1, ObjectHelper.getter(entity, "id"));
            pstm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Object> findAll(Class theClass) {
        if (conn == null) {
            throw new IllegalStateException("Connection is not initialized.");
        }

        String query = QueryHelper.createQuerySELECTall(theClass);
        List<Object> results = new ArrayList<>();

        try (PreparedStatement pstm = conn.prepareStatement(query);
             ResultSet rs = pstm.executeQuery()) {
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (rs.next()) {
                Object instance = theClass.getDeclaredConstructor().newInstance();
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnName(i);
                    Object columnValue = rs.getObject(i);
                    ObjectHelper.setter(instance, columnName, columnValue);
                }
                results.add(instance);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }
    @Override
    public List<Object> findAll(Class theClass, HashMap params) {
        if (conn == null) {
            throw new IllegalStateException("Connection is not initialized.");
        }

        String query = QueryHelper.createSelectFindAll(theClass, params);
        List<Object> results = new ArrayList<>();

        try (PreparedStatement pstm = conn.prepareStatement(query)) {
            int i = 1;
            for (Object value : params.values()) {
                pstm.setObject(i++, value);
            }

            try (ResultSet rs = pstm.executeQuery()) {
                ResultSetMetaData metaData = rs.getMetaData();
                int columnCount = metaData.getColumnCount();

                while (rs.next()) {
                    Object instance = theClass.getDeclaredConstructor().newInstance();
                    for (int j = 1; j <= columnCount; j++) {
                        String columnName = metaData.getColumnName(j);
                        Object columnValue = rs.getObject(j);
                        ObjectHelper.setter(instance, columnName, columnValue);
                    }
                    results.add(instance);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }

    @Override
    public List<Object> query(String query, Class theClass, HashMap params) {
        if (conn == null) {
            throw new IllegalStateException("Connection is not initialized.");
        }

        List<Object> results = new ArrayList<>();

        try (PreparedStatement pstm = conn.prepareStatement(query)) {
            int i = 1;
            for (Object value : params.values()) {
                pstm.setObject(i++, value);
            }

            try (ResultSet rs = pstm.executeQuery()) {
                ResultSetMetaData metaData = rs.getMetaData();
                int columnCount = metaData.getColumnCount();

                while (rs.next()) {
                    Object instance = theClass.getDeclaredConstructor().newInstance();
                    for (int j = 1; j <= columnCount; j++) {
                        String columnName = metaData.getColumnName(j);
                        Object columnValue = rs.getObject(j);
                        ObjectHelper.setter(instance, columnName, columnValue);
                    }
                    results.add(instance);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }

    @Override
    public int size(Class theClass) {
        if (conn == null) {
            throw new IllegalStateException("Connection is not initialized.");
        }

        String query = QueryHelper.createQuerySELECTall(theClass);
        int numberOfRows = 0;

        try (PreparedStatement pstm = conn.prepareStatement(query);
             ResultSet rs = pstm.executeQuery()) {
            while (rs.next()) {
                numberOfRows++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return numberOfRows;
    }
}
