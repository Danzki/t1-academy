package com.danzki;

import com.danzki.annotation.AfterSuite;
import com.danzki.annotation.BeforeSuite;
import com.danzki.annotation.Test;
import com.danzki.exception.AnnotationIsOnlyForStaticMethod;
import com.danzki.exception.TooManyStaticMethods;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

public class AnnotationProcessor {
    public static int getStaticMethodCount(Method[] methods, Class<? extends Annotation> annotation) {
        int count = 0;
        for(Method method : methods) {
            if (method.isAnnotationPresent(annotation)) {
                if (Modifier.isStatic(method.getModifiers())) {
                    count++;
                } else {
                    throw new AnnotationIsOnlyForStaticMethod("Annotation " + annotation.getSimpleName() + " can be used only with static methods");
                }
            }
        }
        return count;
    }

    public static Method getFirstAnnotatedMethod(Method[] methods, Class<? extends Annotation> annotation) {
        for(Method method : methods) {
            if (method.isAnnotationPresent(annotation)) {
                return method;
            }
        }
        return null;
    }

    public static void checkStaticMethod(Method[] methods, Class<? extends Annotation> annotation) {
        int staticMethodCount = AnnotationProcessor.getStaticMethodCount(methods, annotation);
        if (staticMethodCount > 1) {
            throw new TooManyStaticMethods("Too many static methods for @" + annotation.getSimpleName() + " annotation");
        }
    }

    public static List<Method> getMethodsForEvery(Method[] methods, Class<? extends Annotation> annotation) {
        List<Method> methods1 = new ArrayList<>();
        for(Method method : methods) {
            if (method.isAnnotationPresent(annotation)) {
                methods1.add(method);
            }
        }
        return methods1;
    }

    public static List<Method> getSortedMethodsToRun(Method[] methods, List<Method> beforeMethods, List<Method> afterMethods) {
        List<Method> runMethods = new ArrayList<>();

        Method method = AnnotationProcessor.getFirstAnnotatedMethod(methods, BeforeSuite.class);
        if (method != null) {
            runMethods.add(method);
        }

        TreeMap<Integer, Method> treeMap = new TreeMap<>(Comparator.reverseOrder());
        for(Method method1 : methods) {
            if (method1.isAnnotationPresent(Test.class)) {
                treeMap.put(method1.getAnnotation(Test.class).priority(), method1);
            }
        }

        for (Map.Entry<Integer, Method> entry : treeMap.entrySet()) {
            if (!beforeMethods.isEmpty()) {
                runMethods.addAll(beforeMethods);
            }
            runMethods.add(entry.getValue());
            if (!afterMethods.isEmpty()) {
                runMethods.addAll(afterMethods);
            }
        }

        method = AnnotationProcessor.getFirstAnnotatedMethod(methods, AfterSuite.class);
        if (method != null) {
            runMethods.add(method);
        }

        return runMethods;
    }

    private static Object convert(String value, Class<?> targetType) throws Exception {
        if (value == null) {
            return null;
        }

        // Обработка примитивов и их обёрток
        if (targetType == String.class) return value;
        if (targetType == int.class || targetType == Integer.class) return Integer.valueOf(value);
        if (targetType == long.class || targetType == Long.class) return Long.valueOf(value);
        if (targetType == double.class || targetType == Double.class) return Double.valueOf(value);
        if (targetType == boolean.class || targetType == Boolean.class) return Boolean.valueOf(value);


        try {
            Constructor<?> constructor = targetType.getConstructor(String.class);
            return constructor.newInstance(value);
        } catch (NoSuchMethodException ignored) {}

        throw new IllegalArgumentException("Не поддерживается преобразование в " + targetType.getName());
    }

    public static Object[] getParsedArgs(Method method, String agrString) throws Exception {
        Class[] paramTypes = method.getParameterTypes();
        String[] agrs = agrString.split("\\s*,\\s*");

        // Проверяем соответствие количества аргументов
        if (agrs.length != paramTypes.length) {
            throw new IllegalArgumentException(
                    "Неверное количество аргументов. Ожидается " +
                            paramTypes.length + ", получено " + agrs.length
            );
        }

        // Преобразуем каждый аргумент к нужному типу
        Object[] convertedArgs = new Object[paramTypes.length];
        for (int i = 0; i < paramTypes.length; i++) {
            convertedArgs[i] = convert(agrs[i], paramTypes[i]);
        }

        return convertedArgs;
    }
}
