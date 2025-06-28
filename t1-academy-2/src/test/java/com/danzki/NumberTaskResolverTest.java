package com.danzki;

import com.danzki.resolvers.NumberTaskResolver;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class NumberTaskResolverTest {

    @Test
    @DisplayName("Задача1. Тест найти 3е наибольшее число в списке целых чисел")
    public void findThirdMaxNumberSuccess() {
        assertEquals(10, NumberTaskResolver.findThirdMaxNumber(List.of(5, 2, 10, 9, 4, 3, 10, 1, 13, 10)));
    }

    @Test
    @DisplayName("Задача1. Тест найти 3е наибольшее число среди уникальных в списке целых чисел")
    public void findThirdMaxNumberAmountUniqueSuccess() {
        assertEquals(9, NumberTaskResolver.findThirdMaxNumberAmongUnique(List.of(5, 2, 10, 9, 4, 3, 10, 1, 13, 10)));
    }


}
