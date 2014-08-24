package com.bahus.ConwayLife.Core;

import com.bahus.ConwayLife.Core.Storage.NoHashBitMap.BitArray2D;
import com.bahus.ConwayLife.Core.Storage.NoHashBitMap.BitArrayMap;
import com.bahus.ConwayLife.Core.Storage.Bounds;

/**
 * Created by denislavrov on 8/22/14.
 */
public class SmallHashLife implements GenericLife{
    private BitArray2D cells = new BitArrayMap();
    private boolean updated = false;

    @Override
    public Bounds getBounds() {
        return null;
    }

    @Override
    public void reset() {

    }

    @Override
    public void toggleCell(int x, int y) {
        updated = true;

    }

    @Override
    public void nextGen() {
        if (updated){
            // Split board into entities.
        }





        // put entities back onto the board.

    }

    @Override
    public BitArray2D getCells() {
        return null;
    }
}
