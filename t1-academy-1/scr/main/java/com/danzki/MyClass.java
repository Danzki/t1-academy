package com.danzki;

import com.danzki.annotation.*;

public class MyClass {

    @BeforeSuite
    public static void methodBefore() {
        System.out.println("Running static before all");
    }

    @AfterSuite
    public static void methodAfter() {
        System.out.println("Running static method after all");
    }

    @BeforeTest
    private void methodBeforeEvery() {
        System.out.println("Running methodBeforeEvery");
    }

    @BeforeTest
    private void methodBeforeEvery2() {
        System.out.println("Running methodBeforeEvery2");
    }

    @AfterTest
    private void methodAfterEvery() {
        System.out.println("Running methodAfterEvery");
    }

    @AfterTest
    private void methodAfterEvery2() {
        System.out.println("Running methodAfterEvery2");
    }

    @Test
    private void methodTest1() {
        System.out.println("Running methodTest1");
    }

    @Test(priority = 7)
    public void methodTest2() {
        System.out.println("Running methodTest2");
    }

    @Test(priority = 2)
    @CsvSource("10, Java, 20, true")
    public void methodTest3(int a, String b, int c, boolean d) {
        System.out.println("Running methodTest3 with parameters: ");
        System.out.println("a = " + String.valueOf(a));
        System.out.println("b = " + b);
        System.out.println("c = " + String.valueOf(c));
        System.out.println("d = " + (d ? "true" : "false"));
    }

}
