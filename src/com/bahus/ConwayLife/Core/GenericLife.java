package com.bahus.ConwayLife.Core;

import com.bahus.ConwayLife.Core.Storage.NoHashBitMap.BitArray2D;
import com.bahus.ConwayLife.Core.Storage.Bounds;

/**
 * Created by denislavrov on 8/15/14.
 */
public interface GenericLife {
    public Bounds getBounds();
    public void reset();
    public void toggleCell(int x, int y);
    public void nextGen();
    public BitArray2D getCells();
}
