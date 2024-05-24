package edu.upc.dsa.db.orm;

import java.util.HashMap;
import java.util.List;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface Sessio {
    Object get(Class theClass, Object ID);                                 // cRud
    void save(Object entity);                                           // Crud
    void close();
    void update(Object object);                                         // crUd
    void delete(Object object);                                         // cruD
    List<Object> findAll(Class theClass);                               // cR
    List<Object> findAll(Class theClass, HashMap params);
    List<Object> query(String query, Class theClass, HashMap params);
}
