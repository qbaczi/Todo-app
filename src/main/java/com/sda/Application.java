package com.sda;

import java.util.Optional;

public class Application {
    public static void main(String[] args) {
        String name = null;
        int age = 5;
        if (age > 10) {
            name = "bla bla";
        }
        System.out.println(countWords(name, " "));
    }

    private static int countWords(String text, String separator) {
        return Optional.ofNullable(text).orElseGet(() -> "")
                .split(separator).length;
    }
}
