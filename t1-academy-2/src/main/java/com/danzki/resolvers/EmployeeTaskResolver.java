package com.danzki.resolvers;

import com.danzki.Employee;

import java.util.List;
import java.util.stream.Collectors;

public class EmployeeTaskResolver {
    public static List<Employee> printTopThreeOldestWithTitle(List<Employee> employees, String position) {
        return employees.stream()
                .filter(e -> e.getPosition().equals(position))
                .sorted((e1, e2) -> e2.getAge() - e1.getAge())
                .limit(3)
                .collect(Collectors.toList())
                ;
    }

    public static Double averageAgeByPosition(List<Employee> employees, String position) {
        return employees.stream()
                .filter(e -> e.getPosition().equals(position))
                .mapToInt(Employee::getAge)
                .average()
                .orElse(0.0);
    }
}
