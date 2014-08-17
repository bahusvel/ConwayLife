package Test;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class PaintRectangle extends JPanel {

    private Point mouseLocation;

    public PaintRectangle() {
        setPreferredSize(new Dimension(500, 500));

        MouseAdapter listener = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                updateMouseRectangle(e);
            }

            private void updateMouseRectangle(MouseEvent e) {
                mouseLocation = e.getPoint();
                repaint();
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                updateMouseRectangle(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                mouseLocation = null;
                repaint();
            }
        };
        addMouseListener(listener);
        addMouseMotionListener(listener);
    }

    private Rectangle getRectangle() {
        if(mouseLocation != null) {
            return new Rectangle(mouseLocation.x - 5, mouseLocation.y - 5, 10, 10);
        }
        else {
            return null;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Rectangle rectangle = getRectangle();
        if(rectangle != null) {
            Graphics2D gg = (Graphics2D) g;
            gg.setColor(Color.BLUE);
            gg.fill(rectangle);
            gg.setColor(Color.BLACK);
            gg.draw(rectangle);
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("Test");
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.getContentPane().add(new PaintRectangle());
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }
}