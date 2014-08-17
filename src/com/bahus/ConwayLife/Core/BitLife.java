package com.bahus.ConwayLife.Core;

import com.bahus.ConwayLife.Core.Storage.*;

/**
 * Created by denislavrov on 8/15/14.
 */

public class BitLife implements GenericLife {
    private Bounds bounds = new Bounds();
    private BitArray2D cells = new BitArrayMap();


    /* Unused function
    private static BitArrayMap getPerimeter(int x, int y){
        BitArrayMap retPer = new BitArrayMap();
        // add point itself
        retPer.set(x, y, true);
        // set the perimeter
        retPer.set(x-1,y-1, true);
        retPer.set(x-1,y, true);
        retPer.set(x-1,y+1, true);
        retPer.set(x  ,y-1, true);
        retPer.set(x  ,y+1, true);
        retPer.set(x+1,y-1, true);
        retPer.set(x+1, y, true);
        retPer.set(x+1,y+1, true);
        return retPer;
    }
    */


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

    @Override
    public void nextGen() {
        BitArray2D gen = new BitArrayMap();
        for(int y : cells.yValues()){
            for (int x : cells.xValues(y)){
                gen.set(x, y, true);
                // set the perimeter
                gen.set(x-1,y-1, true);
                gen.set(x-1,y, true);
                gen.set(x-1,y+1, true);
                gen.set(x  ,y-1, true);
                gen.set(x  ,y+1, true);
                gen.set(x+1,y-1, true);
                gen.set(x+1, y, true);
                gen.set(x+1,y+1, true);
            }
        }

        BitArray2D add = new BitArrayMap();
        BitArray2D sub = new BitArrayMap();
        for(int y : gen.yValues()) {
            for (int x : gen.xValues(y)) {
                byte isize = 0;

                if (cells.get(x, y)) isize++;
                if (cells.get(x-1,y-1)) isize++;
                if (cells.get(x-1,y)) isize++;
                if (cells.get(x-1,y+1)) isize++;
                if (cells.get(x  ,y-1)) isize++;
                if (cells.get(x  ,y+1)) isize++;
                if (cells.get(x+1,y-1)) isize++;
                if (cells.get(x+1, y)) isize++;
                if (cells.get(x+1,y+1)) isize++;

                switch (isize) {
                    case 3:
                        add.set(x, y, true);
                        break;
                    case 4:
                        break;
                    default:
                        sub.set(x, y, true);
                        break;
                }

            }

        }

        cells.addAll(add);
        cells.removeAll(sub);

    }

}
