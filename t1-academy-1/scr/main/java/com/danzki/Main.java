package com.danzki;

import static com.danzki.TestRunner.runTests;

public class Main {

    public static void main(String[] args) {
        try {
            runTests(MyClass.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
