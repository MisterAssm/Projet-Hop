package fr.hop.ui.buttons;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class HoveredButton extends JButton {

    private final Color fillColor;
    private final Color hoverColor;
    private final Color borderColor;
    private final Font font;

    private boolean hovered;

    public HoveredButton(String text, Color fillColor, Color hoverColor, Color borderColor, Font font, Runnable onClick) {
        super(text);
        this.fillColor = fillColor;
        this.hoverColor = hoverColor;
        this.borderColor = borderColor;
        this.font = font;
        this.hovered = false;

        setFont(font.deriveFont(24f));
        setBorderPainted(false);
        setFocusPainted(false);
        setContentAreaFilled(false);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                onClick.run();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                setHovered(true);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setHovered(false);
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        final Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Background color
        g2d.setColor(hovered ? hoverColor : fillColor);
        g2d.fillOval(0, 0, getWidth(), getHeight());

        // Border
        g2d.setColor(borderColor);
        g2d.setStroke(new BasicStroke(3));
        g2d.drawOval(0, 0, getWidth() - 1, getHeight() - 1);

        g2d.setFont(font.deriveFont(24f));
        g2d.setColor(Color.BLACK);

        final FontMetrics fm = g2d.getFontMetrics();

        final int x = (getWidth() - fm.stringWidth(getText())) / 2;
        final int y = (getHeight() + fm.getAscent()) / 2 - fm.getDescent();

        g2d.drawString(getText(), x, y);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(200, 60);
    }

    public void setHovered(boolean hovered) {
        this.hovered = hovered;
        repaint();
    }
}
