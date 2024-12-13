
package fr.hop.ui;

import fr.hop.entities.Axel;
import fr.hop.entities.BonusBlock;
import fr.hop.game.Field;
import fr.hop.utilities.ImageUtilities;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.List;

public class GamePanel extends JPanel {

    public static final int BLOCK_HEIGHT = 10;
    public static final int AXEL_WIDTH = 48;
    public static final int AXEL_HEIGHT = 48;

    private final List<Axel> axelList;
    private final Field field;

    private BufferedImage[] axelImages;
    private BufferedImage[] axelSymmetryImages;
    private BufferedImage blockImage; // Image pour les blocs
    private BufferedImage bonusBlockImage; // Image pour les blocs bonus

    public GamePanel(Field field, List<Axel> axelList) {
        this.field = field;
        this.axelList = axelList;

        try {
            axelImages = new BufferedImage[axelList.size()];
            axelSymmetryImages = new BufferedImage[axelList.size()];

            for (int i = 0; i < axelList.size(); i++) {
                final BufferedImage image = getImageAxelByNumber(i);

                axelImages[i] = image;
                axelSymmetryImages[i] = ImageUtilities.miroirImage(image);
            }

            blockImage = ImageIO.read(this.getClass().getResource("/images/Block.png")); // Charger l'image du bloc
            bonusBlockImage = ImageIO.read(this.getClass().getResource("/images/blueblock.png")); // Charger l'image du bloc bonus
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("ERREUR: L'image n'a pas pu charger...");
        }

        setPreferredSize(new Dimension(field.width, field.height));
        setLayout(new FlowLayout(FlowLayout.CENTER));
        setBackground(new Color(247, 239, 233));
    }

    private BufferedImage getImageAxelByNumber(int n) throws IOException {
        return ImageUtilities.resizeImage(ImageIO.read(this.getClass().getResource("/images/axel_" + n + ".png")), AXEL_WIDTH, AXEL_HEIGHT);
    }



    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Dessiner le fond quadrillé
        drawGrid(g);

        g.setColor(Color.black);

        // Dessiner les blocs (normaux et bonus)
        field.getBlocks().forEach(block -> {
            BufferedImage blockImageToDraw = (block instanceof BonusBlock) ? bonusBlockImage : blockImage;

            // Dessiner le bloc
            g.drawImage(
                    blockImageToDraw,
                    block.getLeftPosition(),
                    field.getTop() - block.getAltitude() - BLOCK_HEIGHT, // Position ajustée pour la hauteur
                    block.getWidth(),
                    BLOCK_HEIGHT, // Hauteur du bloc
                    null
            );
        });

        // Dessiner Axel
        int n = 0;
        for (Axel axel : axelList) {
            final AbstractMap.SimpleEntry<Integer, Integer> position = axel.nextDrawPosition();
            final BufferedImage image = getCurrentImage(axel, n++);

            g.drawImage(
                    image,
                    (position.getKey() - image.getWidth() / 2 - Math.floorDiv(AXEL_WIDTH, 10)), // Ajuster la position pour Axel
                    field.getTop() - position.getValue() - image.getHeight(),
                    null
            );
        }
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

    private BufferedImage getCurrentImage(Axel axel, int n) {
        return axel.isLastFacingLeft() ? axelSymmetryImages[n] : axelImages[n];
    }
}