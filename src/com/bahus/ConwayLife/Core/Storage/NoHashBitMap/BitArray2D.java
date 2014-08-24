package com.bahus.ConwayLife.Core.Storage.NoHashBitMap;

import com.bahus.ConwayLife.Core.Storage.Bounds;

/**
 * Created by denislavrov on 8/16/14.
 */
public interface BitArray2D {
    public boolean get(int x, int y);
    public void set(int x, int y, boolean val);
    public int[] yValues();
    public int[] xValues(int y);
    public Bounds getBounds();
    public Bounds getGrownBounds();
    public void clear();



}
