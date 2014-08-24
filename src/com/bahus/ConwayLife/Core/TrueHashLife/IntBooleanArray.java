package com.bahus.ConwayLife.Core.TrueHashLife;

import com.bahus.ConwayLife.Core.Storage.Bounds;
import com.bahus.ConwayLife.Core.Storage.NoHashBitMap.BitArray2D;
import gnu.trove.set.hash.TIntHashSet;

/**
 * Created by denislavrov on 8/23/14.
 */
public class IntBooleanArray implements BitArray2D{
    private HashlifeAlgo algo;

    public IntBooleanArray(HashlifeAlgo algo){
        this.algo = algo;
    }

    @Override
    public boolean get(int x, int y) {
        return algo.getCellAt(x,y) > 0;
    }

    @Override
    public void set(int x, int y, boolean val) {
        algo.setCellAt(x,y, val ? 1 : 0);
    }

    @Override
    public int[] yValues() {
        TIntHashSet ret = new TIntHashSet();
        int[][] container = algo.saveToArray();
        for (int x = 0; x < container.length - 1; x++) {
            for (int y = 0; y < container[0].length - 1; y++) if (get(x,y)) ret.add(y);
        }
        return ret.toArray();
    }

    @Override
    public int[] xValues(int y) {
        TIntHashSet ret = new TIntHashSet();
        int[][] container = algo.saveToArray();
        for (int x = 0; x < container.length-1; x++) if (get(x,y)) ret.add(x);
        return ret.toArray();
    }

    @Override
    public Bounds getBounds() {
        int ymin = 0, ymax = 0, xmin = 0, xmax = 0;
        boolean firstRun = true;
        for (int y : yValues()){
            if (firstRun) ymin = y;
            if (y < ymin) ymin = y;
            if (y > ymax) ymax = y;
            for (int x : xValues(y)){
                if (firstRun){
                    xmin = x;
                    firstRun = false;
                }
                if (x < xmin) xmin = x;
                if (x > xmax) xmax = x;
            }
        }
        return new Bounds(xmin, ymin, xmax, ymax);
    }

    @Override
    public Bounds getGrownBounds() {
        final int GROWSIZE = 200;
        Bounds bounds = getBounds();
        bounds.hx += GROWSIZE;
        bounds.hy += GROWSIZE;
        bounds.lx -= GROWSIZE;
        bounds.ly -= GROWSIZE;
        return bounds;
    }

    @Override
    public void clear() {
        algo.loadFromArray(new int[10][10]);
    }
}
