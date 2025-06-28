package com.danzki;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@ToString
public class Employee {
    private final String name;
    private final Integer age;
    private final String position;
}
