package com.bahus.ConwayLife.GUI;

import com.bahus.ConwayLife.Core.BitLife;
import com.bahus.ConwayLife.Core.Storage.Bounds;
import com.bahus.ConwayLife.Core.HashLife;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by denislavrov on 8/14/14.
 */
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
    private boolean playPressed = false;
    private boolean slow = false;
    private int GRIDSIZE = 10;
    private BitLife nLife = new BitLife();
    private MouseTracer mt = new MouseTracer();
    //private HashLife nLife = new HashLife();

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
                //System.out.println(mt.getTotalDelta());
            }

        });



        playButton.addActionListener(e -> {
            playPressed = !playPressed;
            int looptill = (Integer) playNumber.getValue();
            Timer timer = new Timer(slow ? 100 : 0, new ActionListener() {
                private int position;

                @Override
                public void actionPerformed(ActionEvent e) {
                    position++;
                    nLife.nextGen();
                    if ((position < looptill) && (playPressed)) {
                        progressBar1.setValue(1+(int)((double)position*100.0/(double)looptill));
                        canvasPanel.repaint();
                    } else {
                        ((Timer) e.getSource()).stop();
                        System.gc();
                        progressBar1.setValue(progressBar1.getMinimum());
                        playPressed = false;
                    }
                }
            });
            timer.start();

    });
    nextGenerationButton.addActionListener(e -> {
        long stime = System.nanoTime();
        nLife.nextGen();
        System.out.printf("Generation took: %d micro seconds.\n", (System.nanoTime() - stime)/1000);
        //System.out.println(nLife.getBounds());
        canvasPanel.repaint();

    });
    resetButton.addActionListener(e -> {
        nLife.reset();
        mt.resetTotalDelta();
        GRIDSIZE = 10;
        canvasPanel.repaint();
        });

        slowCheckBox.addActionListener(e -> {
            slow = !slow;
        });
        zoomIn.addActionListener(e -> {
            GRIDSIZE++;
            canvasPanel.repaint();
        });
        zoomOut.addActionListener(e -> {
            GRIDSIZE--;
            canvasPanel.repaint();
        });
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
                boolean[][] board = nLife.getBoard();
                drawGrid(gg, new Bounds(0l,0l,
                        ((canvasPanel.getWidth()/GRIDSIZE) < board.length) ? board.length : (canvasPanel.getWidth()/GRIDSIZE),
                        ((canvasPanel.getHeight()/GRIDSIZE) < board[0].length) ? board[0].length : (canvasPanel.getHeight()/GRIDSIZE) ));
                drawCells(gg, board);
            }
        };
    }

    private void drawGrid(Graphics2D g, Bounds bounds){
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
    private void drawCells(Graphics2D g, boolean[][] cells){
        Rectangle cRect = new Rectangle();
        for (int x = 0; x < cells.length; x++){
            for (int y = 0; y < cells[0].length; y++) {
                if(cells[x][y]){
                    cRect.setBounds(x*GRIDSIZE - mt.tdx(),y*GRIDSIZE - mt.tdy(),GRIDSIZE,GRIDSIZE);
                    g.fill(cRect);
                }
            }
        }

    }

}
