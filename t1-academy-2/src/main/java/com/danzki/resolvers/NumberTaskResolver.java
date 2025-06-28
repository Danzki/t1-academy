package com.danzki.resolvers;

import java.util.List;

public class NumberTaskResolver {

    public static Integer findThirdMaxNumber(List<Integer> numbers) {
        return numbers.stream()
                .sorted((a, b) -> b - a)
                .skip(2)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "В списке меньше 3 уникальных чисел"
                ));
    }

    public static Integer findThirdMaxNumberAmongUnique(List<Integer> numbers) {
        return numbers.stream()
                .distinct()
                .sorted((a, b) -> b - a)
                .skip(2)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "В списке меньше 3 уникальных чисел"
                ));
    }
}
