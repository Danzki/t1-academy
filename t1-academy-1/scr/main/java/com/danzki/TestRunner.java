package com.danzki;

import com.danzki.annotation.*;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class TestRunner {

    private static List<Method> methodsToInvoke = new ArrayList<>();
    private static List<Method> methodsBeforeEvery  = new ArrayList<>();
    private static List<Method> methodsAfterEvery  = new ArrayList<>();

    private static void runMethods(List<Method> methods, Object obj) throws Exception {
        for(Method method : methods) {
            Object instance = Modifier.isStatic(method.getModifiers()) ? null : obj;
            Object[] args = new Object[0];
            if (method.isAnnotationPresent(CsvSource.class)) {
                args = AnnotationProcessor.getParsedArgs(method, method.getAnnotation(CsvSource.class).value());
            }                
            
            if (method.canAccess(instance)) {
                method.invoke(instance, args);
            } else {
                method.setAccessible(true);
                method.invoke(instance, args);
                method.setAccessible(false);
            }
        }
    }

    public static void runTests(Class clz) throws Exception {
        Object obj = clz.getDeclaredConstructor().newInstance();
        Method[] methods = obj.getClass().getDeclaredMethods();

        AnnotationProcessor.checkStaticMethod(methods, BeforeSuite.class);
        AnnotationProcessor.checkStaticMethod(methods, AfterSuite.class);

        methodsBeforeEvery = AnnotationProcessor.getMethodsForEvery(methods, BeforeTest.class);
        methodsAfterEvery = AnnotationProcessor.getMethodsForEvery(methods, AfterTest.class);

        TestRunner.methodsToInvoke = AnnotationProcessor.getSortedMethodsToRun(methods, methodsBeforeEvery, methodsAfterEvery);

        try {
            runMethods(TestRunner.methodsToInvoke, obj);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
