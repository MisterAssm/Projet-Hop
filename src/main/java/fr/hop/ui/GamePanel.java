package fr.hop.ui;

import fr.hop.entities.Axel;
import fr.hop.game.Field;
import fr.hop.utilities.ImageUtilities;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.AbstractMap;

public class GamePanel extends JPanel {

    public static final int BLOCK_HEIGHT = 10;
    public static final int AXEL_WIDTH = 48;
    public static final int AXEL_HEIGHT = 48;

    private final Axel axel;
    private final Field field;

    private BufferedImage axelImage;
    private BufferedImage axelSymmetryImage;

    public GamePanel(Field field, Axel axel) {
        this.field = field;
        this.axel = axel;

        try {
            axelImage = ImageUtilities.resizeImage(ImageIO.read(this.getClass().getResource("/images/axel.png")), AXEL_WIDTH, AXEL_HEIGHT);
            axelSymmetryImage = ImageUtilities.miroirImage(axelImage);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("ERREUR: L'image n'a pas pu charger...");
        }

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
        final BufferedImage image = getCurrentImage();

        g.drawImage(
                image,
                (position.getKey() - image.getWidth() / 2 - Math.ceilDiv(AXEL_WIDTH, 10)), // car son nez dépasse
                field.getTop() - position.getValue() - image.getHeight(),
                null
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

    private BufferedImage getCurrentImage() {
        return axel.isLastFacingLeft() ? axelSymmetryImage : axelImage;
    }
}
