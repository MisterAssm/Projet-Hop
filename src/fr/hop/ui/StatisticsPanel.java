package fr.hop.ui;

import fr.hop.entities.Axel;
import fr.hop.game.Field;

import javax.swing.*;
import java.awt.*;

public class StatisticsPanel extends JPanel {

    private final Field field;
    private final Axel axel;
    private final JLabel levelLabel;
    private final JLabel scoreLabel;

    public StatisticsPanel(Field field, Axel axel) {
        this.field = field;
        this.axel = axel;
        this.levelLabel = new JLabel();
        this.scoreLabel = new JLabel();

        update();

        JPanel levelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel scorePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        levelPanel.add(levelLabel);
        scorePanel.add(scoreLabel);

        setLayout(new BorderLayout());
        add(levelPanel, BorderLayout.WEST);
        add(scorePanel, BorderLayout.CENTER);
    }

    public void update() {
        levelLabel.setText(STR."Difficulté : \{field.getCurrentLevel()}");
        scoreLabel.setText(STR."Score : \{axel.getScore()}");
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        update();
        repaint();
    }
}
