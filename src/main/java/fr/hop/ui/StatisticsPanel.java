package fr.hop.ui;

import fr.hop.entities.Axel;
import fr.hop.game.Field;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class StatisticsPanel extends JPanel {

    private final Field field;
    private final List<Axel> axelList;
    private final JLabel levelLabel;
    private final JLabel scoreLabel;

    public StatisticsPanel(Field field, List<Axel> axelList) {
        this.field = field;
        this.axelList = axelList;
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
        levelLabel.setText(String.format("Difficulté : %s", field.getCurrentLevel()));
        scoreLabel.setText(String.format("Score : %s", axelList.stream().map(Axel::getScore).max(Integer::compare).orElse(0)));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        update();
        repaint();
    }
}
