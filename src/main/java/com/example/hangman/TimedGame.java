package com.example.hangman;

import java.time.LocalTime;

public class TimedGame extends Game {
    private LocalTime endTime;

    public TimedGame(String wordToGuess, int maxAttempts, int durationInSeconds) {
        super(wordToGuess, maxAttempts);
        this.endTime = LocalTime.now().plusSeconds(durationInSeconds);
    }

    @Override
    public void startGame() {
    }

    @Override
    public void makeGuess(char letter) {
        if (LocalTime.now().isAfter(endTime)) {
            statistics.addLoss();
            return;
        }

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