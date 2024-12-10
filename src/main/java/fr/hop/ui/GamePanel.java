
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
    private BufferedImage blockImage; // Image pour les blocs

    public GamePanel(Field field, Axel axel) {
        this.field = field;
        this.axel = axel;

        try {
            axelImage = ImageUtilities.resizeImage(ImageIO.read(this.getClass().getResource("/images/axel.png")), AXEL_WIDTH, AXEL_HEIGHT);
            axelSymmetryImage = ImageUtilities.miroirImage(axelImage);
            blockImage = ImageIO.read(this.getClass().getResource("/images/Block.png")); // Charger l'image du bloc
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("ERREUR: L'image n'a pas pu charger...");
        }

        setPreferredSize(new Dimension(field.width, field.height));
        setLayout(new FlowLayout(FlowLayout.CENTER));
        setBackground(new Color(247, 239, 233));
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Dessiner le fond quadrillé
        drawGrid(g);

        g.setColor(Color.black);

        // Dessiner les blocs avec l'image
        field.getBlocks().forEach(block -> {
            // Utiliser l'image du bloc pour dessiner
            g.drawImage(
                    blockImage, // Image du bloc
                    block.getLeftPosition(),
                    field.getTop() - block.getAltitude() - BLOCK_HEIGHT, // Position ajustée pour la hauteur
                    block.getWidth(),
                    BLOCK_HEIGHT, // Hauteur du bloc
                    null
            );
        });

        // Dessiner Axel
        AbstractMap.SimpleEntry<Integer, Integer> position = axel.nextDrawPosition();
        final BufferedImage image = getCurrentImage();

        g.drawImage(
                image,
                (position.getKey() - image.getWidth() / 2 - Math.ceilDiv(AXEL_WIDTH, 10)), // Ajuster la position pour Axel
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