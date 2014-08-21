package com.bahus.ConwayLife.Core.Storage;


import gnu.trove.map.hash.TIntObjectHashMap;
import org.roaringbitmap.RoaringBitmap;


/**
 * Created by denislavrov on 8/15/14.
 */
public class BitArrayMap implements BitArray2D{
    private TIntObjectHashMap<RoaringBitmap> container = new TIntObjectHashMap<>();


    public boolean get(int x, int y){
        if (!container.containsKey(y)) container.put(y, new RoaringBitmap());
        return container.get(y).contains(x);
    }

    public void set(int x, int y, boolean val){
        if (!container.containsKey(y)) container.put(y, new RoaringBitmap());
        if (val) container.get(y).add(x);
        else container.get(y).remove(x);
    }

    private RoaringBitmap getY(int y){
        if (!container.containsKey(y)) container.put(y, new RoaringBitmap());
        return container.get(y);
    }

    public int[] yValues(){
        return container.keys();
    }

    public int[] xValues(int y){
        return getY(y).toArray();
    }

    public Bounds getBounds(){
        int ymin = 0, ymax = 0, xmin = 0, xmax = 0;
        boolean firstRun = true;
        for (int y : yValues()){
            if (firstRun) ymin = y;
            if (y < ymin) ymin = y;
            if (y > ymax) ymax = y;
            //System.out.println(getY(y));
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

    public Bounds getGrownBounds(){
        //adjust this variable to reduce memory leakage
        final int GROWSIZE = 200;
        Bounds bounds = getBounds();
        bounds.hx += GROWSIZE;
        bounds.hy += GROWSIZE;
        bounds.lx -= GROWSIZE;
        bounds.ly -= GROWSIZE;
        return bounds;
    }

    public int size(){
        int size = 0;
        for (int i : yValues()) size += getY(i).getCardinality();
        return size;
    }

    public void clear(){
        container.clear();
    }

    public String toString(){
        return container.toString();
    }


}
