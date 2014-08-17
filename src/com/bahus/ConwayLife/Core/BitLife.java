package com.bahus.ConwayLife.Core;

import com.bahus.ConwayLife.Core.Storage.*;

/**
 * Created by denislavrov on 8/15/14.
 */

public class BitLife implements GenericLife {
    private Bounds bounds = new Bounds();
    private BitArrayMap cells = new BitArrayMap();



    private BitArrayMap getPerimeter(int x, int y){
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

    @Override
    public boolean[][] getBoard() {
        bounds.updateBounds(cells.getBounds());
        //System.out.println(cells.getBounds());
        return cells.getBox(bounds);
    }

    @Override
    public Bounds getBounds() {
        bounds.updateBounds(cells.getBounds());
        //System.out.println(cells.getBounds());
        return bounds;
    }

    @Override
    public void reset() {
        cells.clear();
    }

    @Override
    public void toggleCell(long x, long y) {
        if (cells.get((int)x, (int)y)) cells.set( (int)x, (int)y, false);
        else cells.set((int)x, (int)y, true);

    }

    @Override
    public void nextGen() {
        BitArrayMap gen = new BitArrayMap();
        for(int y : cells.getContainer().keys()){
            for (int x : cells.getY(y).toArray()){
                gen.addAll(getPerimeter(x,y));
            }
        }
        BitArrayMap add = new BitArrayMap();
        BitArrayMap sub = new BitArrayMap();
        for(int y : gen.getContainer().keys()) {
            for (int x : gen.getY(y).toArray()) {
                BitArrayMap intersect = getPerimeter(x, y);
                intersect.retainAll(cells);
                switch (intersect.size()) {
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
