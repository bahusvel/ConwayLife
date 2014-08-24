package com.bahus.ConwayLife.Core.ConcurrentLife;

import com.bahus.ConwayLife.Core.Storage.NoHashBitMap.BitArrayMap;
import com.bahus.ConwayLife.Core.Storage.WeightHashMap;

import java.util.ArrayList;
import java.util.concurrent.RecursiveAction;

/**
 * Created by denislavrov on 8/24/14.
 */
public class RecursiveGeneration extends RecursiveAction {
    private BitArrayMap cells;
    private int sx, sy, xl, yl;
    //private ConcurrentWeightMap gen;
    private WeightHashMap gen;

    public RecursiveGeneration(BitArrayMap cells, int sx, int sy, int xl, int yl, /*ConcurrentWeightMap gen*/ WeightHashMap gen) {
        this.cells = cells;
        this.sx = sx;
        this.sy = sy;
        this.xl = xl;
        this.yl = yl;
        this.gen = gen;
    }

    protected void generateDirectly(){
        for(int y : cells.yValues()){
            if ((y >= sy) && (y < (sy+yl))) {
                for (int x : cells.xValues(y)) {
                    if ((x >= sx) && (x < (sx+xl))) {
                        gen.inc(x, y);
                        gen.inc(x - 1, y - 1);
                        gen.inc(x - 1, y);
                        gen.inc(x - 1, y + 1);
                        gen.inc(x, y - 1);
                        gen.inc(x, y + 1);
                        gen.inc(x + 1, y - 1);
                        gen.inc(x + 1, y);
                        gen.inc(x + 1, y + 1);
                    }
                }
            }
        }
    }

    protected static int xThresh = 500;
    protected static int yThresh = 500;

    @Override
    protected void compute() {
        if ((xl <= xThresh) && (yl <= yThresh)){
            //System.out.printf("Generating directly: xl = %d, yl = %d\n",xl,yl);
            generateDirectly();
            return;
        }

        ArrayList<RecursiveGeneration> generators = new ArrayList<>();

        for (int x = 0; x < xl / xThresh+1; x++) {
            for (int y = 0; y < yl/yThresh+1; y++) {
                generators.add(new RecursiveGeneration(cells,
                        sx + x*xThresh,
                        sy+y*yThresh,
                        (xl/xThresh+1 == x) ? xl%xThresh : xThresh,
                        (yl/yThresh+1 == x) ? yl%yThresh : yThresh,
                        gen));
            }
        }

        invokeAll(generators);

    }
}
