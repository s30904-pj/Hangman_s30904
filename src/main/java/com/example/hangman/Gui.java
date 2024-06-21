package com.example.hangman;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Gui extends JFrame implements GameInterface {
    private JPanel panel;
    private JLabel slowoLabel;
    private JTextField literaField;
    private JButton sprawdzButton;
    private JButton nowaGraButton;
    private JLabel wynikLabel;
    private JLabel pozostaleProbyLabel;
    private JLabel bledneLiteryLabel;

    private Game game;
    private Statistics statistics;
    private WordProvider wordProvider;
    private int maxAttempts;

    private String[] latwyZestaw = {"pjatk", "kuba", "java"};
    private String[] ciezkiZestaw = {"zukowo", "komputer", "cocacola"};

    public Gui() {
        this.statistics = new Statistics();
        przygotujGui();
        wyswietlPowitalnyKomunikat();
    }

    private void przygotujGui() {
        setTitle("Wisielec");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        panel = new JPanel();
        panel.setLayout(new GridLayout(7, 1));

        slowoLabel = new JLabel();
        panel.add(slowoLabel);

        literaField = new JTextField();
        panel.add(literaField);

        sprawdzButton = new JButton("Sprawdź");
        sprawdzButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sprawdzLitere();
            }
        });
        panel.add(sprawdzButton);

        nowaGraButton = new JButton("Nowa gra");
        nowaGraButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                wyswietlPowitalnyKomunikat();
            }
        });
        panel.add(nowaGraButton);

        wynikLabel = new JLabel();
        panel.add(wynikLabel);

        pozostaleProbyLabel = new JLabel();
        panel.add(pozostaleProbyLabel);

        bledneLiteryLabel = new JLabel();
        panel.add(bledneLiteryLabel);

        add(panel);
    }

    private void wyswietlPowitalnyKomunikat() {
        JPanel panel = new JPanel(new GridLayout(0, 1));
        JLabel instrukcjaLabel = new JLabel(
                "<html>Witaj w grze w Wisielca!<br><br>" +
                        "Twoim zadaniem jest odgadnięcie wylosowanego słowa.<br>" +
                        "Masz określoną liczbę prób na odgadnięcie całego słowa<br>" +
                        "poprzez wprowadzenie pojedynczych dużych liter.<br><br>" +
                        "Powodzenia!</html>", SwingConstants.CENTER);
        panel.add(instrukcjaLabel);

        String[] poziomy = {"Standard - 6 prób", "Ciężki - 3 próby"};
        JComboBox<String> poziomComboBox = new JComboBox<>(poziomy);
        panel.add(poziomComboBox);

        String[] zestawy = {"Łatwy zestaw", "Ciężki zestaw"};
        JComboBox<String> zestawComboBox = new JComboBox<>(zestawy);
        panel.add(zestawComboBox);

        int result = JOptionPane.showConfirmDialog(this, panel, "Wisielec", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            String poziom = (String) poziomComboBox.getSelectedItem();
            String zestaw = (String) zestawComboBox.getSelectedItem();

            if (zestaw != null) {
                switch (zestaw) {
                    case "Łatwy zestaw":
                        wordProvider = new WordProvider(java.util.Arrays.asList(latwyZestaw));
                        break;
                    case "Ciężki zestaw":
                        wordProvider = new WordProvider(java.util.Arrays.asList(ciezkiZestaw));
                        break;
                }
            }

            if (poziom != null) {
                switch (poziom) {
                    case "Standard - 6 prób":
                        maxAttempts = 6;
                        break;
                    case "Ciężki - 3 próby":
                        maxAttempts = 3;
                        break;
                }
            }
            nowaGra(maxAttempts);
        }
    }

    private void sprawdzLitere() {
        String litera = literaField.getText();
        if (litera.length() == 1) {
            game.makeGuess(litera.charAt(0));
            aktualizujGui();
        }
    }

    private void nowaGra(int maxAttempts) {
        game = new StandardGame(wordProvider.getRandomWord(), maxAttempts);
        game.startGame();
        aktualizujGui();
    }

    private void aktualizujGui() {
        slowoLabel.setText(game.currentGuess.toString());
        pozostaleProbyLabel.setText("Pozostałe próby: " + game.remainingAttempts);
        bledneLiteryLabel.setText("Błędne litery: " + game.incorrectGuesses.toString());
        literaField.setText(""); // Resetowanie pola tekstowego
        panel.revalidate(); // Odświeżenie panelu
        panel.repaint(); // Przemalowanie panelu

        if (game.currentGuess.toString().equals(game.wordToGuess)) {
            JOptionPane.showMessageDialog(this, "Gratulacje! Wygrałeś! Hasło to: " + game.wordToGuess, "Wygrana", JOptionPane.INFORMATION_MESSAGE);
            wyswietlPowitalnyKomunikat();
        } else if (game.remainingAttempts == 0) {
            JOptionPane.showMessageDialog(this, "Przegrałeś! Hasło to: " + game.wordToGuess, "Przegrana", JOptionPane.INFORMATION_MESSAGE);
            wyswietlPowitalnyKomunikat();
        }
    }

    @Override
    public void startGame() {
        setVisible(true);
    }

    @Override
    public void makeGuess(char letter) {
        game.makeGuess(letter);
        aktualizujGui();
    }

    @Override
    public void showStatistics() {
        game.showStatistics();
    }
}
