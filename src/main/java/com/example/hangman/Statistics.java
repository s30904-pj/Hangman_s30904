package com.example.hangman;

import lombok.Data;

@Data
public class Statistics {
    private int totalGames;
    private int wins;
    private int losses;

    public void addWin() {
        totalGames++;
        wins++;
    }

    public void addLoss() {
        totalGames++;
        losses++;
    }

    @Override
    public String toString() {
        return "Statistics: " +
               "Total games: " + totalGames +
               ", Wins: " + wins +
               ", Losses: " + losses;
    }
}