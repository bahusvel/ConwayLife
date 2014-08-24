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
            try {
                Set<String> includes = new HashSet<>();
                includes.add("ColorChooser");
                QuaquaManager.setIncludedUIs(includes);
                UIManager.setLookAndFeel(QuaquaManager.getLookAndFeel());
            } catch (Exception e) {
                System.out.println("No pretty interface for you :(");
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
