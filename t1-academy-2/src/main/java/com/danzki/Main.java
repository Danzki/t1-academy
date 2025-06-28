package com.danzki;

import com.danzki.resolvers.EmployeeTaskResolver;
import com.danzki.resolvers.NumberTaskResolver;
import com.danzki.resolvers.WordTaskResolver;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        //  task1
        System.out.print("3-е наибольшее число: ");
        System.out.println(NumberTaskResolver.findThirdMaxNumber(List.of(5, 2, 10, 9, 4, 3, 10, 1, 13, 10)));

        //  task2
        System.out.print("3-е наибольшее число среди уникальных: ");
        System.out.println(NumberTaskResolver.findThirdMaxNumberAmongUnique(List.of(5, 2, 10, 9, 4, 3, 10, 1, 13, 10)));

//        task3
        System.out.println("Список имен 3 самых старших сотрудников с должностью «Инженер», в порядке убывания возраста");
        List<Employee> employees = List.of(
                new Employee("Алексей", 45, "Инженер"),
                new Employee("Мария", 30, "Менеджер"),
                new Employee("Иван", 50, "Инженер"),
                new Employee("Ольга", 35, "Инженер"),
                new Employee("Сергей", 55, "Инженер"),
                new Employee("Дмитрий", 40, "Разработчик")
        );
        List<Employee> top3OldestEngineers = EmployeeTaskResolver.printTopThreeOldestWithTitle(employees, "Инженер");
        System.out.println(top3OldestEngineers);

        //        task4
        System.out.print("Средний возраст сотрудников с должностью Инженер: ");
        System.out.println(EmployeeTaskResolver.averageAgeByPosition(employees, "Инженер"));

//        task5
        List<String> words = List.of(
            "зима", "весна", "осень", "лето", "коллега"
        );

        System.out.print("Самое длинное слово в списке: ");
        System.out.println(WordTaskResolver.getLongestWord(words));

//        task6
        String init = "слово животное лето огонь слово лето зима стакан суббота суббота суббота стакан";
        System.out.println(WordTaskResolver.getMapWordAppearance(init));

//        task7
        System.out.println("Печать в консоль слова в порядке возрастания длины, если длина одинаковая - в алфавитном порядке: ");
        WordTaskResolver.printWordsByLengthDesc(words);

//        task8
        List<String> strings = List.of(
                "коллега слово корабль луна вагон",
                "пушка бомба ансамбль ведро пузо",
                "куркума ноль один два раз",
                "перц лошадь пух ватрушка гость",
                "горшок пляж песок солнце вождь"
        );

        System.out.print("Любое длинное слово: ");
        System.out.println(WordTaskResolver.getLongestWordAmongSet(strings));
    }

}
