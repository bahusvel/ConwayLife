package com.bahus.ConwayLife.Core.Storage.NoHashBitMap;

import com.bahus.ConwayLife.Core.Storage.Bounds;
import gnu.trove.set.hash.TIntHashSet;

/**
 * Created by denislavrov on 8/15/14.
 */

public class DynamicBitSet implements BitArray2D{
    private static final int SIZEFACTOR = 10;
    private boolean[][] matrix;
    private int lx = 0;
    private int ly = 0;

    public DynamicBitSet(){
        matrix = new boolean[5][5];
    }

    public DynamicBitSet(int xsize, int ysize){
        matrix = new boolean[xsize][ysize];
    }

    public void set(int x, int y, boolean value) {
        if (x >= matrix.length) {
            boolean[][] tmp = matrix;
            matrix = new boolean[x + SIZEFACTOR][];
            System.arraycopy(tmp, 0, matrix, 0, tmp.length);
            for (int i = x; i < x + SIZEFACTOR; i++) {
                matrix[i] = new boolean[y];
            }
        }

        if (y >= matrix[x].length) {
            boolean [] tmp = matrix[x];
            matrix[x] = new boolean[y + 1];
            System.arraycopy(tmp, 0, matrix[x], 0, tmp.length);
        }

        matrix[x][y] = value;
    }

    @Override
    public int[] yValues() {
        TIntHashSet ret = new TIntHashSet();
        for (int x = 0; x < matrix.length - 1; x++) {
            for (int y = 0; y < matrix[0].length - 1; y++) if (get(x,y)) ret.add(y);
        }
        return ret.toArray();
    }

    @Override
    public int[] xValues(int y) {
        TIntHashSet ret = new TIntHashSet();
        for (int x = 0; x < matrix.length-1; x++) if (get(x,y)) ret.add(x);
        return ret.toArray();
    }

    @Override
    public Bounds getBounds() {
        int ymin = 0, ymax = 0, xmin = 0, xmax = 0;
        boolean firstRun = true;
        for (int y : yValues()){
            if (firstRun) ymin = y;
            if (y < ymin) ymin = y;
            if (y > ymax) ymax = y;
            for (int x : xValues(y)){
                if (firstRun){
                    xmin = x;
                    firstRun = false;
                }
                if (x < xmin) xmin = x;
                if (x > xmax) xmax = x;
            }
        }
        return new Bounds(xmin, ymin, xmax, ymax);
    }

    @Override
    public Bounds getGrownBounds() {
        final int GROWSIZE = 200;
        Bounds bounds = getBounds();
        bounds.hx += GROWSIZE;
        bounds.hy += GROWSIZE;
        bounds.lx -= GROWSIZE;
        bounds.ly -= GROWSIZE;
        return bounds;
    }

    @Override
    public void clear() {
        int xsize = matrix.length;
        int ysize = matrix[0].length;
        matrix = new boolean[xsize][ysize];
    }

    public boolean get(int x, int y) {
        return !(x >= matrix.length || y >= matrix[x].length) && matrix[x][y];
    }

    /*
    public static void main(String[] args) {
        DynamicBitSet matrix2d = new DynamicBitSet();
        long stime = System.nanoTime();
        matrix2d.set(1, 1, true);     // set (1, 1) to 1
        matrix2d.set(10, 10, false);   // set (10, 10) to 2
        matrix2d.set(100, 100, true); // set (100, 100) to 3
        //long stime = System.nanoTime();
        System.out.println(matrix2d.get(1, 1));     // outputs 1
        System.out.println(matrix2d.get(10, 10));   // outputs 2
        System.out.println(matrix2d.get(100, 100)); // outputs 3
        System.out.println("The thing took " + (System.nanoTime() - stime)/1000);
    }
    */
}
