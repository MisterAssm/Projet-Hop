package fr.hop.ui;

import fr.hop.Hop;
import fr.hop.ui.buttons.RectangleHoveredButton;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

public class OverPanel extends JPanel {

    private final int currentScore;
    private final int bestScore;
    private JButton newGameButton;
    private JButton mainMenuButton;
    private Font customFont;

    public OverPanel(int currentScore, int bestScore, Hop hop) {
        this.currentScore = currentScore;
        this.bestScore = bestScore;

        try {
            File fontFile = new File(this.getClass().getResource("/fonts/DoodleFont.ttf").toURI());
            customFont = Font.createFont(Font.TRUETYPE_FONT, fontFile).deriveFont(48f);
        } catch (IOException | FontFormatException | URISyntaxException e) {
            e.printStackTrace();
            System.out.println("ERREUR: La police n'a pas pu être chargée. Utilisation de la police par défaut.");
            customFont = new Font("Arial", Font.BOLD, 48); // Police par défaut en cas d'échec
        }

        // Configurer le panneau
        setPreferredSize(new Dimension(400, 600));
        setLayout(new BorderLayout());
        setBackground(new Color(247, 239, 233));

        addGameOverText();
        addScores();
        addButtons(hop);
    }

    private void addGameOverText() {
        JPanel gameOverPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                final Graphics2D g2d = (Graphics2D) g;

                g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                g2d.setColor(Color.RED);
                g2d.setFont(customFont);

                final String text = "Game Over";
                final FontMetrics fm = g2d.getFontMetrics();

                final int x = (getWidth() - fm.stringWidth(text)) / 2;
                final int y = (getHeight() / 2) + fm.getAscent() / 4;

                g2d.drawString(text, x, y);
            }
        };

        gameOverPanel.setPreferredSize(new Dimension(400, 150));
        gameOverPanel.setBackground(new Color(247, 239, 233));

        add(gameOverPanel, BorderLayout.NORTH);
    }

    private void addScores() {
        JPanel scorePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                final Graphics2D g2d = (Graphics2D) g;

                g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                g2d.setColor(Color.BLACK);
                g2d.setFont(customFont.deriveFont(24f));

                final String currentScoreText = "Score : " + currentScore;
                final String bestScoreText = "Meilleur score : " + bestScore;

                final FontMetrics fm = g2d.getFontMetrics();
                final int currentScoreX = (getWidth() - fm.stringWidth(currentScoreText)) / 2;
                final int bestScoreX = (getWidth() - fm.stringWidth(bestScoreText)) / 2;

                g2d.drawString(currentScoreText, currentScoreX, getHeight() / 3);
                g2d.drawString(bestScoreText, bestScoreX, (2 * getHeight()) / 3);
            }
        };

        scorePanel.setPreferredSize(new Dimension(400, 200));
        scorePanel.setBackground(new Color(247, 239, 233));

        add(scorePanel, BorderLayout.CENTER);
    }

    private void addButtons(Hop hop) {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 1, 10, 10));
        buttonPanel.setBackground(new Color(247, 239, 233));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50)); // Marges

        newGameButton = new RectangleHoveredButton("Nouvelle partie", customFont, hop::startGame);
        buttonPanel.add(newGameButton);

        mainMenuButton = new RectangleHoveredButton("Menu principal", customFont, hop::initWelcomePanel);
        buttonPanel.add(mainMenuButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }
}
