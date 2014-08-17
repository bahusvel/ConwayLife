package com.bahus.ConwayLife.GUI;

import javax.swing.*;
import java.awt.*;

/**
 * Created by denislavrov on 8/14/14.
 */
class Board extends JPanel {

    public Board() {
        setBorder(BorderFactory.createLineBorder(Color.black));
    }

    public Dimension getPreferredSize() {
        return new Dimension(250,200);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Rectangle rectangle = new Rectangle(10,10,10,10);
        Graphics2D gg = (Graphics2D) g;
        gg.setColor(Color.BLUE);
        gg.fill(rectangle);
        gg.setColor(Color.BLACK);
        gg.draw(rectangle);
    }
}
