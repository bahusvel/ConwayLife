package com.bahus.ConwayLife.GUI;

import ch.randelshofer.quaqua.QuaquaManager;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by denislavrov on 8/14/14.
 */
public class Runner {
    public static void main(String[] args) {
        // Runnable() replaced with lambda
        EventQueue.invokeLater( () -> {
            Set<String> includes = new HashSet<String>();
            includes.add("ColorChooser");
            QuaquaManager.setIncludedUIs(includes);
            try {
                UIManager.setLookAndFeel(QuaquaManager.getLookAndFeel());
            } catch (UnsupportedLookAndFeelException e) {
                e.printStackTrace();
            }

            JFrame frame = new JFrame("Conway's Game of Life.");
            MainWindow mw = new MainWindow();
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.add(mw.getMainPanel());
            frame.setVisible(true);
            frame.pack();
            frame.setLocationRelativeTo(null);

        });
    }

}
