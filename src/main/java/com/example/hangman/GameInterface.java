package com.example.hangman;

public interface GameInterface {
    void startGame();
    void makeGuess(char letter);
    void showStatistics();
}