package com.bahus.ConwayLife.Core;

import com.bahus.ConwayLife.Core.Storage.Bounds;

/**
 * Created by denislavrov on 8/15/14.
 */
public interface GenericLife {
    public boolean[][] getBoard();
    public Bounds getBounds();
    public void reset();
    public void toggleCell(long x, long y);
    public void nextGen();
}
