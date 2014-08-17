package com.bahus.ConwayLife.Core.Storage;

/**
 * Created by denislavrov on 8/15/14.
 */

public class DynamicBitSet /*implements BitArray2D*/{
    public int getSizefactor() {
        return sizefactor;
    }

    public void setSizefactor(int sizefactor) {
        this.sizefactor = sizefactor;
    }

    private int sizefactor = 10;
    private boolean[][] matrix;

    public DynamicBitSet(){
        matrix = new boolean[5][5];
    }

    public DynamicBitSet(int xsize, int ysize){
        matrix = new boolean[xsize][ysize];
    }

    public void set(int x, int y, boolean value) {
        if (x >= matrix.length) {
            boolean[][] tmp = matrix;
            matrix = new boolean[x + sizefactor][];
            System.arraycopy(tmp, 0, matrix, 0, tmp.length);
            for (int i = x; i < x + sizefactor; i++) {
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

    public boolean get(int x, int y) {
        return !(x >= matrix.length || y >= matrix[x].length) && matrix[x][y];
    }

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
}
