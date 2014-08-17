package com.bahus.ConwayLife.Core.Storage;

/**
 * Created by denislavrov on 8/16/14.
 */
public interface BitArray2D {
    public boolean get(int x, int y);
    public void set(int x, int y, boolean val);
    public int[] yValues();
    public int[] xValues(int y);
    public Bounds getBounds();
    public int size();
    public void clear();
    public void addAll(BitArray2D b2);
    public void removeAll(BitArray2D b2);


}
