package com.example.hangman;

import java.util.List;
import java.util.Random;

public class WordProvider {
    private List<String> words;

    public WordProvider(List<String> words) {
        this.words = words;
    }

    public String getRandomWord() {
        Random random = new Random();
        return words.get(random.nextInt(words.size()));
    }
}