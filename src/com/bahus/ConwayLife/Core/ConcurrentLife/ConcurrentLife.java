package com.bahus.ConwayLife.Core.ConcurrentLife;

import com.bahus.ConwayLife.Core.GenericLife;
import com.bahus.ConwayLife.Core.Storage.Bounds;
import com.bahus.ConwayLife.Core.Storage.NoHashBitMap.BitArray2D;
import com.bahus.ConwayLife.Core.Storage.NoHashBitMap.BitArrayMap;
import com.bahus.ConwayLife.Core.Storage.WeightHashMap;

import java.util.concurrent.ForkJoinPool;

/**
 * Created by denislavrov on 8/24/14.
 */
public class ConcurrentLife implements GenericLife {
    private Bounds bounds = new Bounds();
    private BitArrayMap cells = new BitArrayMap();
    private WeightHashMap gen = new WeightHashMap(cells.getGrownBounds());
    //private ConcurrentWeightMap gen = new ConcurrentWeightMap(cells.getGrownBounds());
    private ForkJoinPool pool = new ForkJoinPool(/* Number of cores to be used goes here */);



    public BitArray2D getCells(){
        return cells;
    }

    @Override
    public Bounds getBounds() {
        bounds.updateBounds(cells.getBounds());
        return bounds;
    }

    @Override
    public void reset() {
        cells.clear();
    }

    @Override
    public void toggleCell(int x, int y) {
        if (cells.get(x, y)) cells.set(x, y, false);
        else cells.set(x, y, true);

    }


    public void nextGen() {

        // before creating new gen container check if the existing one is capable to hold data
        if (gen.getBounds().smaller(cells.getBounds())){
           //gen = new ConcurrentWeightMap(cells.getGrownBounds());
            gen = new WeightHashMap(cells.getGrownBounds());
        }

        bounds.updateBounds(cells.getBounds());

        RecursiveGeneration rg = new RecursiveGeneration(cells,
                bounds.lx, bounds.ly,bounds.hx-bounds.lx+1, bounds.hy-bounds.ly+1,
                gen);

        pool.invoke(rg);


        for (long point : gen.keys()){
            int x = gen.getX(point);
            int y = gen.getY(point);

            switch (gen.get(point)){
                case 3:
                    cells.set(x,y, true);
                    break;
                case 4:
                    break;
                default:
                    cells.set(x,y, false);
                    break;

            }

        }

        // clear the generated data

        gen.clear();
    }
}
