package com.bahus.ConwayLife.Core.Storage;

/**
 * Created by denislavrov on 8/16/14.
 */
public interface BitArray2D {
    public boolean get(int x, int y);
    public void set(int x, int y, boolean val);
    public boolean[][] getBox(int xmin, int ymin, int xmax, int ymax);
}
