package com.bahus.ConwayLife.GUI;

import com.bahus.ConwayLife.Core.BitLife;
import com.bahus.ConwayLife.Core.GenericLife;
import com.bahus.ConwayLife.Core.ImageController.ImageController;
import com.bahus.ConwayLife.Core.Storage.BitArray2D;
import com.bahus.ConwayLife.Core.Storage.Bounds;
import org.apache.commons.io.FilenameUtils;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

/**
 * Created by denislavrov on 8/14/14.
 */

@SuppressWarnings("ALL")
public class MainWindow {
    // UI COMPONENTS //
    private JPanel mainPanel;
    private JButton nextGenerationButton;
    private JButton resetButton;
    private JButton playButton;
    private JPanel buttonPanel;
    private JPanel canvasPanel;
    private JButton zoomIn;
    private JButton zoomOut;
    private JSpinner playNumber;
    private JProgressBar progressBar1;
    private JCheckBox animateCheckBox;
    private JTabbedPane tabbedPane1;
    private JTextField fPath;
    private JButton browseButton;
    private JButton saveButton;
    private JButton loadButton;
    private JPanel zoom;
    private JPanel Settings;
    private JSlider Speed;
    private JComboBox formatChooser;
    private JPanel playPanel;
    // VARIABLES //
    private boolean playPressed = false;
    private boolean animate = true;
    private int GRIDSIZE = 10;
    private GenericLife nLife = new BitLife();
    private MouseTracer mt = new MouseTracer();
    private File ioFile;
    private Bounds cellBounds;

    public MainWindow() {
        setupMouse();
        setupButtons();
        initCells();
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    private void getFile() throws NullPointerException {
        while (!FilenameUtils.getExtension(fPath.getText()).equals((String) formatChooser.getSelectedItem())) {
            getFileSimple();
            if (!FilenameUtils.getExtension(fPath.getText()).equals((String) formatChooser.getSelectedItem())) {
                JOptionPane.showMessageDialog(mainPanel,
                        "You have selected file: " + ioFile.getName() + " of wrong type.\n" +
                                "Correct extension is: " + (String) formatChooser.getSelectedItem() + "\n" +
                                "You can change extensions in the Settings Tab.",
                        "WRONG FILE TYPE SELECTED!", JOptionPane.ERROR_MESSAGE);
            }
        }
        if (!ioFile.getAbsolutePath().equals(fPath.getText())){
            ioFile = new File(fPath.getText());
        }
    }

    private void getFileSimple() throws NullPointerException {
        FileFilter filter = new FileNameExtensionFilter("Image file", (String) formatChooser.getSelectedItem());
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(filter);
        fileChooser.showDialog(mainPanel, "OK");
        ioFile = fileChooser.getSelectedFile();
        fPath.setText(ioFile.getAbsolutePath());
    }

    private void createUIComponents() {
        formatChooser = new JComboBox<String>(ImageController.getFormats());
        formatChooser.setSelectedItem("png");
        canvasPanel = new JPanel() {

            public Dimension getPreferredSize() {
                return new Dimension(500, 500);
            }

            public void paintComponent(Graphics g) {
                Graphics2D gg = (Graphics2D) g;
                super.paintComponent(g);
                // going to draw grid here.
                drawGrid(gg);
                drawCells(gg, nLife.getCells(), nLife.getBounds());
            }
        };
    }

    private void drawGrid(Graphics2D g) {
        //initializing grid size and position
        int width = canvasPanel.getWidth() / GRIDSIZE;
        int height = canvasPanel.getHeight() / GRIDSIZE;

        for (int x = 0; x < width + 2; x++)
            g.drawLine(
                    x * GRIDSIZE - mt.tdx()%GRIDSIZE,
                    0,
                    x * GRIDSIZE - mt.tdx()%GRIDSIZE,
                    (int) (height + 1) * GRIDSIZE - mt.tdy()%GRIDSIZE
            );
        for (int y = 0; y < height + 2; y++)
            g.drawLine(
                    0,
                    y * GRIDSIZE - mt.tdy()%GRIDSIZE,
                    (int) (width + 1) * GRIDSIZE - mt.tdx()%GRIDSIZE,
                    y * GRIDSIZE - mt.tdy()%GRIDSIZE
            );
    }

    private void drawCells(Graphics2D g, BitArray2D b, Bounds bounds) {
        for (int y : b.yValues()) {
            for (int x : b.xValues(y)) {
                int px = dx((x) * GRIDSIZE) + ((canvasPanel.getWidth()/2)/GRIDSIZE)*GRIDSIZE;
                int py = dy((y) * GRIDSIZE) + ((canvasPanel.getHeight()/2)/GRIDSIZE)*GRIDSIZE;
                if (canvasPanel.contains(px+GRIDSIZE/2,py+GRIDSIZE/2) && b.get(x, y)) {
                    g.fillRect(
                            px,
                            py,
                            GRIDSIZE,
                            GRIDSIZE
                    );
                }
            }
        }

    }

    private void initCells() {
        nLife.toggleCell(0, 0);
        nLife.toggleCell(1, 0);
        nLife.toggleCell(1, 2);
        nLife.toggleCell(3, 1);
        nLife.toggleCell(4, 0);
        nLife.toggleCell(5, 0);
        nLife.toggleCell(6, 0);
    }

    private void setupMouse() {
        canvasPanel.addMouseListener(new MouseAdapter() {
            // I can add localized mouse listeners here.

            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                Point mL = e.getPoint();
                int mlx = mL.x - ((canvasPanel.getWidth()/2)/GRIDSIZE)*GRIDSIZE;
                int mly = mL.y - ((canvasPanel.getHeight()/2)/GRIDSIZE)*GRIDSIZE;
                nLife.toggleCell(
                        idx(mlx) < 0 ? idx(mlx) / GRIDSIZE - 1 : idx(mlx) / GRIDSIZE,
                        idy(mly) < 0 ? idy(mly) / GRIDSIZE - 1 : idy(mly) / GRIDSIZE
                );
                canvasPanel.repaint();

            }


            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                mt.resetDelta();
                Cursor cursor = Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);
                canvasPanel.setCursor(cursor);
            }

        });

        canvasPanel.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                mt.updatePosition(e.getPoint());
                Cursor cursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
                canvasPanel.setCursor(cursor);
                canvasPanel.repaint();
            }

        });
    }

    private void setupButtons(){
        playButton.addActionListener(e -> {
            if (!animate) {
                for (int i = 0; i < (Integer) playNumber.getValue(); i++) nLife.nextGen();
                canvasPanel.repaint();
                System.gc();
            } else {
                playPressed = !playPressed;
                if (playPressed) playButton.setText("Stop");
                else playButton.setText("Play");

                int looptill = (Integer) playNumber.getValue();
                Timer timer = new Timer(Speed.getValue(), new ActionListener() {
                    private int position;

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        position++;
                        nLife.nextGen();
                        if ((playPressed) && (position < looptill)) {
                            progressBar1.setValue(1 + (int) ((double) position * 100.0 / (double) looptill));
                            canvasPanel.repaint();
                        } else {
                            ((Timer) e.getSource()).stop();
                            System.gc();
                            progressBar1.setValue(progressBar1.getMinimum());
                            playPressed = false;
                            canvasPanel.repaint();
                        }
                    }
                });
                timer.start();
            }
        });
        nextGenerationButton.addActionListener(e -> {
            long stime = System.nanoTime();
            nLife.nextGen();
            System.out.printf("Generation took %d micro seconds.\n", (System.nanoTime() - stime) / 1000);
            canvasPanel.repaint();

        });
        resetButton.addActionListener(e -> {
            nLife.reset();
            mt.resetTotalDelta();
            GRIDSIZE = 10;
            canvasPanel.repaint();
        });

        zoomIn.addActionListener(e -> {
            GRIDSIZE++;
            canvasPanel.repaint();
        });
        zoomOut.addActionListener(e -> {
            GRIDSIZE--;
            canvasPanel.repaint();
        });
        animateCheckBox.addActionListener(e -> animate = !animate);
        browseButton.addActionListener(e -> {
            try {
                getFileSimple();
            } catch (NullPointerException ignored) {

            }

        });
        saveButton.addActionListener(e -> {
            try {
                getFile();
                ImageController.save(nLife.getCells(), ioFile, (String) formatChooser.getSelectedItem());
            } catch (NullPointerException exc) {
                JOptionPane.showMessageDialog(mainPanel, "You haven't selected any file.");
            }

        });

        loadButton.addActionListener(e -> {
            try {
                getFile();
                if (ioFile.canRead()) ImageController.load(nLife.getCells(), ioFile);
            } catch (NullPointerException exc) {
                JOptionPane.showMessageDialog(mainPanel, "You haven't selected any file.");
            }
            finally {
                canvasPanel.repaint();
            }
        });
    }

    private int dx(int x){
        return x - mt.tdx();
    }

    private int dy(int y){
        return y - mt.tdy();
    }

    private int idx(int x){
        return x + mt.tdx();
    }

    private int idy(int y){
        return y + mt.tdy();
    }

}
