package com.bahus.ConwayLife.Core.CellHashLife;

import com.bahus.ConwayLife.Core.GenericLife;
import com.bahus.ConwayLife.Core.Storage.Bounds;
import com.bahus.ConwayLife.Core.Storage.NoHashBitMap.BitArray2D;
import com.bahus.ConwayLife.Core.Storage.NoHashBitMap.BitSetMap;

/**
 * Created by denislavrov on 8/25/14.
 */
public class CellHashLife implements GenericLife {
    private BitSetMap cells1 = new BitSetMap();
    private BitSetMap cells2 = new BitSetMap();
    private BitSetMap ccells = cells1;
    private BitSetMap ncells = cells2;
    private CellHashGenerator generator = new CellHashGenerator();
    private BitArray2D cells = ccells;

    @Override
    public Bounds getBounds() {
        return cells.getBounds();
    }

    @Override
    public void reset() {
        cells1.clear();
        cells2.clear();
        ccells = cells1;
        ncells = cells2;
        cells = ccells;
    }

    @Override
    public void toggleCell(int x, int y) {
        if(cells.get(x,y)) cells.set(x,y,false);
        else cells.set(x,y,true);
        //System.out.println(x+" "+y);
    }

    private void computeXY(int x, int y){
        // this method does add some overhead,
        // and probably messes up the JIT a little
        // it is only for my convenience
        // in production, shove the method back into nextGen();

        for (int x1 = x-1; x1 <= x+1; x1++) {
            for (int y1 = y-1; y1 <= y+1; y1++) {
                switch ((int)generator.cellHashes.get(ccells.cell2Hash(x1,y1))){
                    case 3:
                        ncells.set(x1,y1,false);
                        break;
                    case 1:
                        ncells.set(x1,y1,true);
                        break;
                    case 0:
                                /* Not an optimization
                                    I guess JIT does it's job here, and fixes this problem in a better way.
                                if(cells.get(x1,y1))
                                ncells.set(x1,y1,true);
                                */
                        ncells.set(x1,y1,ccells.get(x1,y1));
                        break;
                }
            }
        }
    }

    @Override
    public void nextGen() {
        for(int y : ccells.yValues()){
            for (int x : ccells.xValues(y)) {
                computeXY(x,y);
            }
        }
        ccells.clear();

        if (ccells == cells1){
            ccells = cells2;
            ncells = cells1;
        }
        else{
            ccells = cells1;
            ncells = cells2;
        }
        cells = ccells;

    }

    @Override
    public BitArray2D getCells() {
        return cells;
    }

    @Override
    public void cleanup() {
        cells.cleanup();
    }
}
