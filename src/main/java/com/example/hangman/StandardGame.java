package com.example.hangman;

public class StandardGame extends Game { // dziedziczenie do Game
    public StandardGame(String wordToGuess, int maxAttempts) {
        super(wordToGuess, maxAttempts);
    }

    @Override
    public void startGame() {
    }

    @Override
    public void makeGuess(char letter) {
        if (wordToGuess.indexOf(letter) >= 0) {
            updateCurrentGuess(letter);
        } else {
            makeIncorrectGuess(letter);
        }
        checkGameState();
    }

    private void checkGameState() {
        if (currentGuess.toString().equals(wordToGuess)) {
            statistics.addWin();
        } else if (remainingAttempts == 0) {
            statistics.addLoss();
        } else {
        }
    }
}