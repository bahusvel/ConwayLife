package com.bahus.ConwayLife.Core.TrueHashLife;

import com.bahus.ConwayLife.Core.GenericLife;
import com.bahus.ConwayLife.Core.Storage.Bounds;
import com.bahus.ConwayLife.Core.Storage.NoHashBitMap.BitArray2D;

/**
 * Created by denislavrov on 8/23/14.
 */
public class TrueHashLife implements GenericLife {
    private LifeAlgo algo;
    private HashlifeState lState;
    private IntBooleanArray tmp;

    public TrueHashLife(){
        algo = new HashlifeAlgo();
        lState = new HashlifeState(new int[10][10]);
        algo.setState(lState);
    }

    @Override
    public Bounds getBounds() {
        tmp = new IntBooleanArray((HashlifeAlgo) algo);
        return tmp.getBounds();
    }

    @Override
    public void reset() {
        algo = new HashlifeAlgo();
        lState = new HashlifeState(new int[10][10]);
        algo.setState(lState);
    }

    @Override
    public void toggleCell(int x, int y) {
        algo.toggleCellAt(x,y);
    }

    @Override
    public void nextGen() {
        algo.evolve(1);
    }

    @Override
    public BitArray2D getCells() {
        tmp = new IntBooleanArray((HashlifeAlgo)algo);
        return tmp;
    }

    @Override
    public void cleanup() {
        tmp.cleanup();
    }
}
