package com.example.hangman;

import com.example.hangman.GameInterface;

import java.util.ArrayList;
import java.util.List;

public abstract class Game implements GameInterface {
    protected String wordToGuess;
    protected StringBuilder currentGuess;
    protected List<Character> incorrectGuesses;
    protected int remainingAttempts;
    protected Statistics statistics;

    public Game(String wordToGuess, int maxAttempts) { // konstruktor
        this.wordToGuess = wordToGuess.toUpperCase();
        this.currentGuess = new StringBuilder("?".repeat(wordToGuess.length()));
        this.incorrectGuesses = new ArrayList<>();
        this.remainingAttempts = maxAttempts;
        this.statistics = new Statistics();
    }

    protected void updateCurrentGuess(char letter) {
        boolean correctGuess = false;
        for (int i = 0; i < wordToGuess.length(); i++) {
            if (Character.toUpperCase(wordToGuess.charAt(i)) == Character.toUpperCase(letter)) {
                currentGuess.setCharAt(i, letter);
                correctGuess = true;
            }
        }
        if (!correctGuess) {
            makeIncorrectGuess(letter);
        }
    }

    protected void makeIncorrectGuess(char letter) {
        incorrectGuesses.add(letter);
        remainingAttempts--;
    }

    @Override
    public void showStatistics() {
        System.out.println(statistics);
    }

    public abstract void startGame();

    public void makeGuess(String guess) {
        if (guess.length() == 1) {
            makeGuess(guess.charAt(0));
        } else if (guess.length() > 1) {
            if (guess.equalsIgnoreCase(wordToGuess)) {
                // Odgadnięto całe hasło
                currentGuess = new StringBuilder(wordToGuess);
            } else {
                makeIncorrectGuess(guess.charAt(0));
            }
        }
    }
}
