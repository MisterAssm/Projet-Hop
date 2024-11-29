package fr.hop.ui;

import fr.hop.entities.Axel;
import fr.hop.game.Field;

import javax.swing.*;
import java.awt.*;
import java.util.AbstractMap;

public class GamePanel extends JPanel {

    public static final int BLOCK_HEIGHT = 10;
    public static final int AXEL_WIDTH = 10;
    public static final int AXEL_HEIGHT = 10;

    private final Axel axel;
    private final Field field;

    public GamePanel(Field field, Axel axel) {
        this.field = field;
        this.axel = axel;

        setPreferredSize(new Dimension(field.width, field.height));
        setLayout(new FlowLayout(FlowLayout.CENTER)); // https://stackoverflow.com/questions/75519236/how-to-use-flow-layout-inside-a-panel-in-java
        setBackground(new Color(247, 239, 233));
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Dessiner le fond quadrillé
        drawGrid(g);

        g.setColor(Color.black);

        // Dessine les blocs
        field.getBlocks()
                .forEach(block -> g.fillRect(
                        block.getLeftPosition(),
                        field.getTop() - block.getAltitude(),
                        block.getWidth(),
                        BLOCK_HEIGHT
                ));

        AbstractMap.SimpleEntry<Integer, Integer> position = axel.nextDrawPosition();

        // Dessine le personnage
        g.fillOval(
                position.getKey() - AXEL_WIDTH / 2,
                field.getTop() - position.getValue() - AXEL_HEIGHT,
                AXEL_WIDTH,
                AXEL_HEIGHT
        );
    }

    private void drawGrid(Graphics g) {
        g.setColor(new Color(245, 231, 219));

        int gridSpacing = 20;

        for (int x = 0; x < field.width; x += gridSpacing) {
            g.drawLine(x, 0, x, field.height);
        }

        for (int y = 0; y < field.height; y += gridSpacing) {
            g.drawLine(0, y, field.width, y);
        }
    }

}
