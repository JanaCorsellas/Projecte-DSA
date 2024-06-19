package edu.upc.dsa.util;

import edu.upc.dsa.util.ObjectHelper;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class QueryHelper {

    public static String createQueryINSERT(Object entity) {
        StringBuilder query = new StringBuilder("INSERT INTO ");
        query.append(entity.getClass().getSimpleName().toLowerCase());
        query.append(" (");

        String[] fields = ObjectHelper.getFields(entity);
        for (String field : fields) {
            if (!field.equalsIgnoreCase("id")) {
                query.append(field).append(",");
            }
        }

        query.deleteCharAt(query.length() - 1); // Elimina la última coma
        query.append(") VALUES (");

        for (String field : fields) {
            if (!field.equalsIgnoreCase("id")) {
                query.append("?,");
            }
        }

        query.deleteCharAt(query.length() - 1); // Elimina la última coma
        query.append(")");

        return query.toString();
    }


    public static String createQuerySELECT(Class theClass, String columna, String user) {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT * FROM ").append(theClass.getSimpleName());
        sb.append(" WHERE " + columna + " = ?");

        return sb.toString();
    }


    public static String createSelectFindAll(Class theClass, HashMap<String, String> params) {

        Set<Map.Entry<String, String>> set = params.entrySet();

        StringBuffer sb = new StringBuffer("SELECT * FROM "+theClass.getSimpleName()+" WHERE 1=1");
        for (String key: params.keySet()) {
            sb.append(" AND "+key+"=?");
        }


        return sb.toString();
    }

    public static String createQuerySELECTall(Class theClass) {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT * FROM ").append(theClass.getSimpleName());

        return sb.toString();
    }

    /*public static String createQueryUPDATE(Object object) {
        StringBuilder query = new StringBuilder("UPDATE ");
        query.append(object.getClass().getSimpleName()).append(" SET ");

        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (!field.getName().equals("id")) { // Assuming 'id' is the primary key and shouldn't be updated
                query.append(field.getName()).append(" = ?, ");
            }
        }

        query.setLength(query.length() - 2); // Remove last comma and space
        query.append(" WHERE id = ?"); // Assuming 'id' is the primary key

        return query.toString();
    }*/

    public static String createQueryUPDATE() {
        StringBuffer sb = new StringBuffer();
        sb.append("UPDATE Usuari ");
        sb.append("SET coins = ?, skin = ? ");
        sb.append("WHERE nomusuari = ?");
        return sb.toString();
    }
}
