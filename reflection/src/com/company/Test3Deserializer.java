package com.company;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class Test3Deserializer {
    public static <T> T deserializer(Class<T> cls, String str) throws IllegalAccessException, InstantiationException {
        T object = cls.newInstance();
        String[] strArray = str.split(", ");
        String[] temp;

        try {
            for (String s : strArray) {
                temp = s.split(": ");
                Field field = cls.getDeclaredField(temp[0]); // temp[0] - parameter name

                if (Modifier.isPrivate(field.getModifiers()) || Modifier.isProtected(field.getModifiers())) {
                    field.setAccessible(true);
                }

                if (field.isAnnotationPresent(Test2Saver.class)) {
                    if (field.getType() == String.class) {
                        field.set(object, temp[1]); // temp[1] - parameter value
                    } else if (field.getType() == int.class) {
                        field.setInt(object, Integer.parseInt(temp[1]));
                    } else if (field.getType() == float.class) {
                        field.setFloat(object, Float.parseFloat(temp[1]));
                    } else if (field.getType() == double.class) {
                        field.setDouble(object, Double.parseDouble(temp[1]));
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return object;
    }
}
