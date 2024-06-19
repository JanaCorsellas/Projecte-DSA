package edu.upc.dsa.db.orm;

import edu.upc.dsa.models.Usuari;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.List;

import java.sql.SQLException;

public interface Sessio {
    Object get(Class theClass, String columna, String value) throws SQLException;                                 // cRud
    void save(Object entity) throws SQLIntegrityConstraintViolationException;                                           // Crud
    void close();
    void update(int saldo, String novaSkin, String nomUsuari) throws SQLException;                                  // crUd
    void delete(Object object);                                         // cruD
    public <T> List<T> findAll(Class theClass);                               // cR
    //List<Object> findAll(Class theClass, HashMap params);
    List<Object> query(String query, Class theClass, HashMap params);
}
