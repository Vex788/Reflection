package com.company;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class Test3Serializer {
    public static String serializer(Object object) throws IllegalAccessException {
        Class<?> cls = object.getClass();
        Field[] fieldsArray = cls.getDeclaredFields();

        StringBuilder stringBuilder = new StringBuilder();
        int index = 0;

        for (Field field : fieldsArray) {
            if (field.isAnnotationPresent(Test2Saver.class)) { // using class Saver for Task 2
                if (Modifier.isPrivate(field.getModifiers()) || Modifier.isProtected(field.getModifiers())) {
                    field.setAccessible(true); // change close modificator
                }
                // write structure data
                if (field.getType() == String.class){
                    stringBuilder.append(field.getName() + ": " + field.get(object));
                } else if (field.getType() == int.class){
                    stringBuilder.append(field.getName() + ": " + field.getInt(object));
                } else if (field.getType() == float.class){
                    stringBuilder.append(field.getName() + ": " + field.getFloat(object));
                } else if (field.getType() == double.class){
                    stringBuilder.append(field.getName() + ": " + field.getDouble(object));
                }
                // write separator
                if (index < fieldsArray.length - 1) {
                    stringBuilder.append(", ");
                }
                index++;
            }
        }
        return stringBuilder.toString();
    }
}
