package com.bahus.ConwayLife.Core;

import com.bahus.ConwayLife.Core.Storage.*;

/**
 * Created by denislavrov on 8/15/14.
 */

public class BitLife implements GenericLife {
    private Bounds bounds = new Bounds();
    private BitArray2D cells = new BitArrayMap();
    private WeightHashMap gen = new WeightHashMap(cells.getGrownBounds());



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
        if (gen.getBounds().smaller(cells.getBounds())){
            gen = new WeightHashMap(cells.getGrownBounds());
        }

        for(int y : cells.yValues()){
            for (int x : cells.xValues(y)){
                gen.inc(x, y);
                gen.inc(x-1,y-1);
                gen.inc(x-1,y);
                gen.inc(x-1,y+1);
                gen.inc(x  ,y-1);
                gen.inc(x  ,y+1);
                gen.inc(x+1,y-1);
                gen.inc(x+1, y);
                gen.inc(x+1,y+1);
            }
        }

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

    gen.clear();
    }

}
