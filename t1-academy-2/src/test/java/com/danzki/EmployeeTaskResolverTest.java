package com.danzki;

import com.danzki.resolvers.EmployeeTaskResolver;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EmployeeTaskResolverTest {

    @Test
    @DisplayName("Топ 3 самых возрастных инженеров")
    public void printTopThreeOldestWithTitleSuccess() {
        List<Employee> employees = List.of(
                new Employee("Алексей", 45, "Инженер"),
                new Employee("Мария", 30, "Менеджер"),
                new Employee("Иван", 50, "Инженер"),
                new Employee("Ольга", 35, "Инженер"),
                new Employee("Сергей", 55, "Инженер"),
                new Employee("Дмитрий", 40, "Разработчик")
        );

        List<Employee> expected = List.of(
                new Employee("Сергей", 55, "Инженер"),
                new Employee("Иван", 50, "Инженер"),
                new Employee("Алексей", 45, "Инженер")
        );

        List<Employee> actual = EmployeeTaskResolver.printTopThreeOldestWithTitle(employees, "Инженер");

        assertAll(
                () -> assertEquals(expected.size(), actual.size()),
                () -> {
                    for (int i = 0; i < expected.size(); i++) {
                        assertEquals(expected.get(i).getName(), actual.get(i).getName());
                        assertEquals(expected.get(i).getAge(), actual.get(i).getAge());
                        assertEquals(expected.get(i).getPosition(), actual.get(i).getPosition());
                    }
                }
        );

    }

    @Test
    @DisplayName("Найти средний возраст всех сотрудников с должностью инженер")
    public void averageAgeByPosition() {
        List<Employee> employees = List.of(
                new Employee("Алексей", 45, "Инженер"),
                new Employee("Мария", 30, "Менеджер"),
                new Employee("Иван", 50, "Инженер"),
                new Employee("Ольга", 35, "Инженер"),
                new Employee("Сергей", 55, "Инженер"),
                new Employee("Дмитрий", 40, "Разработчик")
        );

        assertEquals(46.25, EmployeeTaskResolver.averageAgeByPosition(employees, "Инженер"));
    }
}
