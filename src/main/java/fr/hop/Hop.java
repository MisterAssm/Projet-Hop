package fr.hop;

import fr.hop.entities.Axel;
import fr.hop.inputs.GameHandler;
import fr.hop.ui.StatisticsPanel;
import fr.hop.game.Field;
import fr.hop.ui.GamePanel;
import fr.hop.ui.WelcomePanel;
import fr.hop.utilities.ScoreUtilities;

import javax.swing.*;
import java.awt.*;

public class Hop {
    public static final int WIDTH = 400; // largeur terrain
    public static final int HEIGHT = 600; // hauteur terrain
    public static final int DELAY = 40; // durée d'un tour de jeu

    private final JFrame frame;
    private final Field field;
    private Axel axel;
    private Timer gameTimer;
    private Timer drawTimer;

    private GamePanel gamePanel;
    private StatisticsPanel statisticsPanel;
    private WelcomePanel welcomePanel;

    private GameHandler gameHandler;

    public Hop() {
        this.field = new Field(WIDTH, HEIGHT);
        this.axel = new Axel(field, WIDTH / 2, Field.START_ALTITUDE); // ALTITUDE_GAP ?
        this.gamePanel = new GamePanel(field, axel);
        this.statisticsPanel = new StatisticsPanel(field, axel);
        this.gameHandler = new GameHandler(axel);
        this.welcomePanel = new WelcomePanel(this);

        this.frame = new JFrame("Hop!");
        frame.add(welcomePanel);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void round(boolean update) {
        if (update) {
            axel.update();
            field.update();
        }

        frame.repaint();
    }

    public void startGame() {
        frame.remove(welcomePanel);

        frame.add(gamePanel);
        frame.add(statisticsPanel, BorderLayout.NORTH);
        frame.addKeyListener(gameHandler);
        frame.pack();
        frame.setFocusable(true);
        frame.requestFocus();

        drawTimer = new Timer(1, ignored -> round(false));
        gameTimer = new Timer(DELAY, ignored -> {
            round(true);

            if (over()) {
                drawTimer.stop();
                gameTimer.stop();
                gameOver(axel.getScore());
            }
        });

        drawTimer.start();
        gameTimer.start();
    }

    public void gameOver(int score) {
        ScoreUtilities.saveBestScore(score);
        this.frame.getContentPane().remove(this.gamePanel);
        this.frame.getContentPane().add(this.statisticsPanel);

        JLabel label = new JLabel("Score: " + score);
        label.setFont(new Font("Arial", 1, 20));

        this.frame.add(label, "Center");
        this.frame.repaint();
    }

    public boolean over() {
        return axel.hasFallen();
    }
}
