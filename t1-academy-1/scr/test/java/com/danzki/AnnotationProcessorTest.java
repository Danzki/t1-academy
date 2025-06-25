package com.danzki;

import com.danzki.annotation.AfterSuite;
import com.danzki.annotation.AfterTest;
import com.danzki.annotation.BeforeSuite;
import com.danzki.annotation.BeforeTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.List;

import static com.danzki.AnnotationProcessor.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AnnotationProcessorTest {
    private static Method[] methods;

    @BeforeAll
    public static void fillMethods() throws Exception {
        Class clz = MyClass.class;
        Object obj = clz.getDeclaredConstructor().newInstance();
        methods = obj.getClass().getDeclaredMethods();
    }

    @Test
    @DisplayName("Успешный тест метода getStaticMethodCount")
    public void getStaticMethodCountSuccess() {
        assertEquals(getStaticMethodCount(methods, BeforeSuite.class), 1);
    }

    @Test
    @DisplayName("Успешный тест метода getFirstAnnotatedMethod")
    public void getFirstAnnotatedMethodSuccess() {
        assertEquals(getFirstAnnotatedMethod(methods, AfterSuite.class).getName(), "methodAfter");
    }

    @Test
    @DisplayName("Успешный тест метода getMethodsForEvery")
    public void getMethodsForEverySuccess() {
        assertEquals(getMethodsForEvery(methods, BeforeTest.class).size(), 2);
    }

    @Test
    @DisplayName("Успешный тест метода getSortedMethodsToRun")
    public void getSortedMethodsToRunSuccess() {
        List<Method> methodsBeforeEvery = getMethodsForEvery(methods, BeforeTest.class);
        List<Method> methodsAfterEvery = getMethodsForEvery(methods, AfterTest.class);

        assertEquals(getSortedMethodsToRun(methods, methodsBeforeEvery, methodsAfterEvery).size(), 17);
    }
}
