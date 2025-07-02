package com.danzki;

import com.danzki.resolvers.WordTaskResolver;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WordTaskResolverTest {

    @Test
    @DisplayName("Тест - поиск слова с наибольшей длиной")
    public void getLongestWordSuccess() {
        List<String> words = List.of(
                "зима", "весна", "осень", "лето", "коллега"
        );

        String expected = "коллега";
        String actual = WordTaskResolver.getLongestWord(words);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Тест - метод строящий мапу по строке")
    public void getMapWordAppearanceSuccess() {
        String init = "слово животное лето огонь слово лето зима стакан суббота суббота суббота стакан";
        Map<String, Integer> wordMap = WordTaskResolver.getMapWordAppearance(init);

        Integer excepted = 3;
        Integer actual = wordMap.get("суббота");
        assertEquals(excepted, actual);

        excepted = 2;
        actual = wordMap.get("слово");
        assertEquals(excepted, actual);
    }

    @Test
    @DisplayName("Тест - найти любое самое длинное слово среди наборов строк из пяти слов")
    public void getLongestWordAmongSetSuccess() {
        List<String> strings = List.of(
                "коллега слово корабль луна вагон",
                "пушка бомба ансамбль ведро пузо",
                "куркума ноль один два раз",
                "перц лошадь пух ватрушка гость",
                "горшок пляж песок солнце вождь"
        );

        String expected = "ансамбль";
        String actual = WordTaskResolver.getLongestWordAmongSet(strings);

        assertEquals(expected, actual);

    }
}
