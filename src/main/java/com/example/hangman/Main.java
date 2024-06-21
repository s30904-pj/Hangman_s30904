package com.example.hangman;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameInterface game = new Gui();
            game.startGame();
        });
    }
}