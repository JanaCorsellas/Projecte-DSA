package edu.upc.dsa.util;

import edu.upc.dsa.util.ObjectHelper;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class QueryHelper {

    public static String createQueryINSERT(Object entity) {
        StringBuilder query = new StringBuilder("INSERT INTO ");
        query.append(entity.getClass().getSimpleName().toLowerCase());
        query.append(" (");

        String[] fields = ObjectHelper.getFields(entity);

        /*for (String field : fields) {
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

        return query.toString();*/
        for (int i = 0; i < fields.length; i++) {
            query.append(fields[i]);

            // Agrega una coma si no es el último campo
            if (i < fields.length - 1) {
                query.append(", ");
            }
        }

        query.append(") VALUES (");

        /*for (String field: fields) {
            sb.append(", ?");
        }*/
        for (int i = 0; i < fields.length; i++) {
            if (i > 0) {
                query.append(", ");
            }
            query.append("?");
        }

        query.append(")");

        return query.toString();
    }


    public static String createQuerySELECT(Object entity) {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT * FROM ").append(entity.getClass().getSimpleName());
        sb.append(" WHERE ID = ?");

        return sb.toString();
    }

    public static String createQuerySELECTall(Class theClass) {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT * FROM ").append(theClass.getSimpleName());

        return sb.toString();
    }

    public static String createQueryDELETE(Class theClass, String columna, String username) {
        StringBuffer sb = new StringBuffer();
        sb.append("DELETE FROM ").append(theClass.getSimpleName());
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
}
