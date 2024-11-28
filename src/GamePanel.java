import javax.swing.*;
import java.awt.*;

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
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Dessine les blocs
        field.getBlocks()
                .forEach(block -> g.fillRect(
                        block.getLeftPosition(),
                        field.getTop() - block.getAltitude(),
                        block.getWidth(),
                        10
                ));

        // Dessine le personnage
        g.fillOval(
                axel.getX() - 5,
                field.getTop() - axel.getY() - 10,
                10,
                10
        );
    }

}
