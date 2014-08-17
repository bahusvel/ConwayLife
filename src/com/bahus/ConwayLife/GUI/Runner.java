package com.bahus.ConwayLife.GUI;

import javax.swing.*;
import java.awt.*;

/**
 * Created by denislavrov on 8/14/14.
 */
public class Runner {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("Test");
                MainWindow mw = new MainWindow();
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.add(mw.getMainPanel());
                frame.setVisible(true);
                frame.pack();
                frame.setLocationRelativeTo(null);

            }
        });
    }

}
