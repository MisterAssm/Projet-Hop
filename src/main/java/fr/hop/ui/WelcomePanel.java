package fr.hop.ui;

import fr.hop.Hop;
import fr.hop.ui.buttons.CircleHoveredButton;
import fr.hop.utilities.ScoreUtilities;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

public class WelcomePanel extends JPanel {

    private final Hop hop;
    private Font customFont;

    public WelcomePanel(Hop hop) {
        this.hop = hop;

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

        // Ajouter les composants
        addLogoText();
        addButtons();
        addBestScore();
    }

    private void addLogoText() {
        JPanel logoPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                g2d.setColor(Color.RED);
                g2d.setFont(customFont);
                String text = "DoodleJump";
                FontMetrics fm = g2d.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(text)) / 2;
                int y = (getHeight() / 2) + fm.getAscent() / 4;
                g2d.drawString(text, x, y);
            }
        };
        logoPanel.setPreferredSize(new Dimension(400, 200));
        logoPanel.setBackground(new Color(247, 239, 233));
        add(logoPanel, BorderLayout.NORTH);
    }

    private void addButtons() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 1, 10, 10));
        buttonPanel.setBackground(new Color(247, 239, 233));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50)); // Marges

        final Color fillColor = new Color(243, 227, 200, 255);
        final Color borderColor = new Color(232, 169, 64, 255);
        final Color hoverColor = new Color(216, 141, 123, 255);

        // Bouton pour jouer en solo
        JButton soloButton = new CircleHoveredButton("Jouer seul", fillColor, hoverColor, borderColor, customFont, hop::startGame);
        buttonPanel.add(soloButton);

        // Bouton pour jouer en multijoueur
        JButton multiplayerButton = new CircleHoveredButton("Jouer en multijoueur", fillColor, hoverColor, borderColor, customFont, () -> System.out.println("TODO 2"));
        buttonPanel.add(multiplayerButton);

        add(buttonPanel, BorderLayout.CENTER);
    }

    private void addBestScore() {
        JPanel scorePanel = new JPanel();
        scorePanel.setBackground(new Color(247, 239, 233));

        JLabel bestScoreLabel = new JLabel();
        bestScoreLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        bestScoreLabel.setForeground(new Color(90, 90, 90));
        bestScoreLabel.setHorizontalAlignment(SwingConstants.CENTER);

        bestScoreLabel.setText("Meilleur score : " + ScoreUtilities.getBestScore());

        scorePanel.add(bestScoreLabel);
        add(scorePanel, BorderLayout.SOUTH);
    }
}
