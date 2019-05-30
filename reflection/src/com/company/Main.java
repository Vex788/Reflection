package com.company;

import java.lang.reflect.*;

public class Main {
    // Test 1
    static class Summary {
        @Test1(a = 5, b = 5)
        public static int getSumm(int a, int b) { // task 1 summary
            return a + b;
        }
    }
    // end Test1
    private static String getParameterTypesString(Class<?>[] parameterTypes) {
        StringBuilder stringBuilder = new StringBuilder();

        for (Class<?> param : parameterTypes) {
            stringBuilder.append(param.toString() + " ");
        }

        return stringBuilder.toString();
    }

    public static void Task1Method() throws InvocationTargetException, IllegalAccessException { // Test 1 - annotation with parameters
        Class<?> cls = Summary.class;
        Method[] methodsArray = cls.getDeclaredMethods(); // get all methods

        System.out.println("--- Test 1 ---");

        for (Method method : methodsArray) {
            System.out.println(method.getDeclaringClass().getName() + " | " + method.getName() +
                    " | param types: " + getParameterTypesString(method.getParameterTypes()) +
                    " | return type: " + method.getAnnotatedReturnType());
            if (method.isAnnotationPresent(Test1.class)) {
                Test1 test1 = method.getAnnotation(Test1.class); // object Test1
                // call method 'getSumm' with default parameters
                int summaryTest1 = (Integer) method.invoke(null, test1.a(), test1.b());

                System.out.println(String.format("Test1: %1$s + %2$s = %3$s", test1.a(), test1.b(), summaryTest1));
            }
        }
        System.out.println("--- end Test 1 ---");
    }

    public static void Task2Method() throws InvocationTargetException, IllegalAccessException { // Test 2 - class SaveTo, Saver, TextContainer
        Task2TextContainer textContainer = new Task2TextContainer();
        Class<?> cls = textContainer.getClass();
        Method[] methodsArray = cls.getDeclaredMethods();

        System.out.println("--- Test 2 ---");

        if (!cls.isAnnotationPresent(Test2SaveTo.class)) { // check class on exists
            System.out.println("Annotation present = false");
            return;
        }

        for (Method method : methodsArray) {
            System.out.println(method.getDeclaringClass().getName() + " | " + method.getName() +
                    " | param types: " + getParameterTypesString(method.getParameterTypes()) +
                    " | return type: " + method.getAnnotatedReturnType());
            if (method.isAnnotationPresent(Test2Saver.class)) {
                // invoke method save(path) in class TextContainer
                boolean success = (boolean)method.invoke(textContainer, cls.getAnnotation(Test2SaveTo.class).path());

                if (success) { // if write success
                    System.out.println("Task 2: File write success");
                } else {
                    System.out.println("Task 2: File write failed");
                }
            }
        }
        System.out.println("--- end Test 2 ---");
    }

    public static void Task3Method() throws IllegalAccessException, InstantiationException { // Task 3 - serialize and deserialize data in/out file
        Test3DataStructure dataStructure = new Test3DataStructure();

        dataStructure.name = "Name1";
        dataStructure.a = 8;
        dataStructure.b = 3;
        // serialization
        String result = Test3Serializer.serializer(dataStructure);
        // deserialization
        dataStructure = Test3Deserializer.deserializer(Test3DataStructure.class, result);

        System.out.println("--- Test 3 ---");
        System.out.println(String.format("Task 3 Serialization: " + result));
        System.out.println(String.format("Task 3 Deserialization: name: %1$s, a: %2$s, b: %3$s",
                dataStructure.name, dataStructure.a, dataStructure.b));
        System.out.println("--- end Test 3 ---");
    }

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException, InstantiationException {
        Task1Method();
        Task2Method();
        Task3Method();
    }
}
