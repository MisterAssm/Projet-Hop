package fr.hop;

import fr.hop.entities.Axel;
import fr.hop.game.Field;
import fr.hop.inputs.GameHandler;
import fr.hop.ui.GamePanel;
import fr.hop.ui.StatisticsPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Hop {
    public static final int WIDTH = 400; // largeur terrain
    public static final int HEIGHT = 600; // hauteur terrain
    public static final int DELAY = 40; // durée d'un tour de jeu

    private final JFrame frame;
    private final Field field;
    private final Axel axel;
    private Timer timer;
    private GamePanel gamePanel;
    private StatisticsPanel statisticsPanel;
    private GameHandler gameHandler;

    public Hop() {
        this.field = new Field(WIDTH, HEIGHT);
        this.axel = new Axel(field, WIDTH / 2, Field.START_ALTITUDE); // ALTITUDE_GAP ?
        this.gamePanel = new GamePanel(field, axel);
        this.statisticsPanel = new StatisticsPanel(field, axel);
        this.gameHandler = new GameHandler(axel);

        this.frame = new JFrame("fr.Hop!");
        frame.add(gamePanel);
        frame.add(statisticsPanel, BorderLayout.NORTH);
        frame.addKeyListener(gameHandler);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void round() {
        axel.update();
        field.update();
        frame.repaint();
    }

    public boolean over() {
        return axel.hasFallen();
    }

    public static void main(String[] args) {
        Hop game = new Hop();

        game.timer = new Timer(DELAY, (ActionEvent e) -> {
            game.round();
            if (game.over()) {
                game.timer.stop();
                game.frame.remove(game.gamePanel);
                // TODO: STATS INTERFACE
            }
        });

        game.timer.start();
    }
}
