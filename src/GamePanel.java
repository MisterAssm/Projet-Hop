import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    private static final int BLOCK_HEIGHT = 10;
    private static final int AXEL_WIDTH = 10;
    private static final int AXEL_HEIGHT = 10;

    private final Axel axel;
    private final Field field;

    public GamePanel(Field field, Axel axel) {
        this.field = field;
        this.axel = axel;

        setPreferredSize(new Dimension(field.width, field.height));
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
