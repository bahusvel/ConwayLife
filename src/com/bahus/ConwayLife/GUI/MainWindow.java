package com.bahus.ConwayLife.GUI;

import com.bahus.ConwayLife.Core.BitLife;
import com.bahus.ConwayLife.Core.GenericLife;
import com.bahus.ConwayLife.Core.Storage.BitArray2D;
import com.bahus.ConwayLife.Core.Storage.Bounds;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by denislavrov on 8/14/14.
 */

@SuppressWarnings("ALL")
public class MainWindow {
    private JPanel mainPanel;
    private JButton nextGenerationButton;
    private JButton resetButton;
    private JButton playButton;
    private JPanel buttonPanel;
    private JPanel canvasPanel;
    private JButton zoomIn;
    private JButton zoomOut;
    private JCheckBox slowCheckBox;
    private JSpinner playNumber;
    private JProgressBar progressBar1;
    private JCheckBox animateCheckBox;
    private boolean playPressed = false;
    private boolean slow = false;
    private boolean animate = true;
    private int GRIDSIZE = 10;
    private GenericLife nLife = new BitLife();
    private MouseTracer mt = new MouseTracer();

    public MainWindow() {
        nLife.toggleCell(1,1);
        nLife.toggleCell(2,1);
        nLife.toggleCell(2,3);
        nLife.toggleCell(4,2);
        nLife.toggleCell(5,1);
        nLife.toggleCell(6,1);
        nLife.toggleCell(7,1);

        canvasPanel.addMouseListener(new MouseAdapter() {
            // I can add localized mouse listeners here.


            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                Point mouseLocation = e.getPoint();
                nLife.toggleCell(nLife.getBounds().lx+((mouseLocation.x+mt.tdx())/GRIDSIZE), nLife.getBounds().ly+((mouseLocation.y+mt.tdy())/GRIDSIZE));
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



        playButton.addActionListener(e -> {
            if (!animate){
                for (int i = 0; i < (Integer) playNumber.getValue(); i++) nLife.nextGen();
                canvasPanel.repaint();
                System.gc();
            }
            else {
                playPressed = !playPressed;
                int looptill = (Integer) playNumber.getValue();
                Timer timer = new Timer(slow ? 100 : 0, new ActionListener() {
                    private int position;

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        position++;
                        nLife.nextGen();
                        if ((position < looptill) && (playPressed)) {
                            progressBar1.setValue(1 + (int) ((double) position * 100.0 / (double) looptill));
                            canvasPanel.repaint();
                            }
                        else {
                            ((Timer) e.getSource()).stop();
                            System.gc();
                            progressBar1.setValue(progressBar1.getMinimum());
                            playPressed = false;
                        }
                        /* Employ if system is memory leaking
                        if(position%100 == 0){
                            System.gc();
                        }
                        */
                    }
                });
                timer.start();
            }
    });
    nextGenerationButton.addActionListener(e -> {
        long stime = System.nanoTime();
        nLife.nextGen();
        System.out.printf("Generation took %d micro seconds.\n", (System.nanoTime() - stime)/1000);
        canvasPanel.repaint();

    });
    resetButton.addActionListener(e -> {
        nLife.reset();
        mt.resetTotalDelta();
        GRIDSIZE = 10;
        canvasPanel.repaint();
        });

        slowCheckBox.addActionListener(e -> slow = !slow);
        zoomIn.addActionListener(e -> {
            GRIDSIZE++;
            canvasPanel.repaint();
        });
        zoomOut.addActionListener(e -> {
            GRIDSIZE--;
            canvasPanel.repaint();
        });
        animateCheckBox.addActionListener(e -> animate = !animate);
    }


    public JPanel getMainPanel(){
        return mainPanel;
    }


    private void createUIComponents() {
            canvasPanel = new JPanel(){

                public Dimension getPreferredSize() {
                return new Dimension(500,500);
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

    private void drawGrid(Graphics2D g){
        Bounds cellBounds = nLife.getBounds();
        //initializing grid size and position
        Bounds bounds = new Bounds(0,0,
                ((canvasPanel.getWidth()/GRIDSIZE) < (cellBounds.hx - cellBounds.lx)) ? (cellBounds.hx - cellBounds.lx) : (canvasPanel.getWidth()/GRIDSIZE),
                ((canvasPanel.getHeight()/GRIDSIZE) < (cellBounds.hy - cellBounds.ly)) ? (cellBounds.hy - cellBounds.ly) : (canvasPanel.getHeight()/GRIDSIZE)
        );

        for (int x = 0; x < (bounds.hx - bounds.lx) + 2 ; x++ ) g.drawLine(
                x*GRIDSIZE - mt.tdx(),
                -mt.tdy(),
                x*GRIDSIZE - mt.tdx(),
                (int) (bounds.hy-bounds.ly+1)*GRIDSIZE - mt.tdy()
        );
        for (int y = 0; y < (bounds.hy - bounds.ly) + 2; y++ ) g.drawLine(
                -mt.tdx(),
                y*GRIDSIZE - mt.tdy(),
                (int) (bounds.hx-bounds.lx+1)*GRIDSIZE - mt.tdx(),
                y*GRIDSIZE - mt.tdy()
        );
    }

    private void drawCells(Graphics2D g, BitArray2D b, Bounds bounds){
        Rectangle cRect = new Rectangle();
        for (int y : b.yValues()){
            for (int x : b.xValues(y)) {
                if(b.get(x,y)){
                    cRect.setBounds((x-bounds.lx)*GRIDSIZE - mt.tdx(),(y-bounds.ly)*GRIDSIZE - mt.tdy(),GRIDSIZE,GRIDSIZE);
                    g.fill(cRect);
                }
            }
        }

    }

}
