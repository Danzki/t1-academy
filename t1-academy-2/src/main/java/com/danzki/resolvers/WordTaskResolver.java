package com.danzki.resolvers;

import java.util.*;

public class WordTaskResolver {

    public static String getLongestWord(List<String> words) {
        return words.stream()
                .max(Comparator.comparingInt(String::length))
                .orElseThrow(() -> new NoSuchElementException("Список слов пуст"));
    }

    public static HashMap<String, Integer> getMapWordAppearance(String init) {
        return Arrays.stream(init.split("\\s+"))
                .filter(word -> !word.isEmpty())
                .collect(
                        HashMap::new,
                        (map, word) -> map.merge(word, 1, Integer::sum),
                        HashMap::putAll
                );
    }

    public static void printWordsByLengthDesc(List<String> words) {
        words.stream()
                .sorted(Comparator.comparingInt(String::length)
                    .thenComparing(Comparator.naturalOrder()))
                .forEach(System.out::println);
    }

    public static String getLongestWordAmongSet(List<String> word) {
        return word.stream()
                .flatMap(s -> Arrays.stream(s.split("\\s+")))
                .max(Comparator.comparingInt(String::length))
                .orElseThrow(() -> new NoSuchElementException("Список слов пуст"))
                ;
    }
}
