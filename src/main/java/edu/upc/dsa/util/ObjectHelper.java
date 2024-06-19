package edu.upc.dsa.util;


import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ObjectHelper {
    public static String[] getFields(Object entity) {

        Class theClass = entity.getClass();

        Field[] fields = theClass.getDeclaredFields();

        String[] sFields = new String[fields.length];
        int i=0;

        for (Field f: fields) sFields[i++]=f.getName();

        return sFields;

    }


    public static void setter(Object object, String property, Object value) throws NoSuchFieldException, IllegalAccessException {
        /*try {
            Field field = object.getClass().getDeclaredField(property);
            field.setAccessible(true);
            field.set(object, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        List<Method> methods = new ArrayList<>(Arrays.asList(object.getClass().getDeclaredMethods()));
        try {
            Method m = methods.stream().filter((Method method) -> method.getName().contains("set" + getMethodName(property))).findFirst().get();
            m.invoke(object,  value);
        }catch ( IllegalAccessException e) {
            //logger.warn("No setter found for: " + property + " in " + object.getClass().getName());
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }*/
        List<Method> methods = Arrays.asList(object.getClass().getDeclaredMethods());

        // Buscar el método que contiene "set" seguido del nombre de la propiedad en formato CamelCase
        Method method = methods.stream()
                .filter(m -> m.getName().equalsIgnoreCase("set" + property))
                .findFirst()
                .orElseThrow(() -> new NoSuchFieldException("Setter not found for property: " + property));

        // Convertir el valor al tipo adecuado si es necesario
        Object convertedValue = convertValue(method.getParameterTypes()[0], value);

        // Invocar el método setter con el valor convertido
        try {
            method.invoke(object, convertedValue);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private static Object convertValue(Class<?> targetType, Object value) {
        if (value == null) {
            return null;
        }

        if (targetType.isAssignableFrom(value.getClass())) {
            return value;
        }

        if (targetType == int.class || targetType == Integer.class) {
            return Integer.parseInt(value.toString());
        } else if (targetType == long.class || targetType == Long.class) {
            return Long.parseLong(value.toString());
        } else if (targetType == double.class || targetType == Double.class) {
            return Double.parseDouble(value.toString());
        } else if (targetType == boolean.class || targetType == Boolean.class) {
            return Boolean.parseBoolean(value.toString());
        } else if (targetType == String.class) {
            return value.toString();
        }

        throw new IllegalArgumentException("Cannot convert value to target type: " + targetType.getName());
    }

    public static String getMethodName(String property) {
        return property.substring(0,1).toUpperCase()+property.substring(1);
    }

    public static Object getter(Object object, String property) {
        /*try {
            Field field = object.getClass().getDeclaredField(property);
            field.setAccessible(true);
            return field.get(object);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;*/
        String propToUppercase = property.substring(0, 1).toUpperCase() + property.substring(1);
        String getterName = "get" + propToUppercase;
        try {
            Method m = object.getClass().getDeclaredMethod(getterName);
            Object o = m.invoke(object);
            return o;

        }catch (NoSuchMethodException e){
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
