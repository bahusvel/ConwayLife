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
    }

    @Override
    public void nextGen() {
        for(int y : ccells.yValues()){
            for (int x : ccells.xValues(y)) {
                    byte[] hy = ccells.hashField(x, y);

                    for (int x1 = -1; x1 <= 1; x1++) {
                        for (int y1 = -1; y1 <= 1; y1++) {
                            short hash = 0;

                            hash |= ((hy[y1 + 1] >> (1 + x1)) & 7) << 6;
                            hash |= ((hy[y1 + 2] >> (1 + x1)) & 7) << 3;
                            hash |= ((hy[y1 + 3] >> (1 + x1)) & 7);

                            int res = generator.cellHashes.get(hash);

                            if (res == 3) ncells.set(x + x1, y + y1, false);
                            else if (res == 1) ncells.set(x + x1, y + y1, true);
                            else if (ccells.get(x + x1, y + y1)) ncells.set(x + x1, y + y1, true);

                        }
                    }


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
