package fr.hop.ui.buttons;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RectangleHoveredButton extends JButton {

    public RectangleHoveredButton(String text, Font font, Runnable onClick) {
        super(text);

        final Color fillColor = new Color(230, 203, 156, 255);
        final Color hoverColor = new Color(243, 227, 200, 255);

        setFont(font.deriveFont(20f));
        setFocusPainted(false);
        setBackground(fillColor);
        setForeground(Color.WHITE);
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        setOpaque(true);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                onClick.run();
            }

            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                setBackground(hoverColor);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                setBackground(fillColor);
            }
        });
    }
}
