package com.sda;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Application {
    public static void main(String[] args) {
        List<String> texts = Arrays.asList("Ala", null, "ma", "kota", null, "abc");

        for (String text : texts) {
            Optional<String> optional = Optional.ofNullable(text);

            optional.filter(e -> e.length()>2)
                    .orElse("Stop");
        }
    }
}
