package com.bahus.ConwayLife.Core.Storage;


import gnu.trove.map.hash.TIntObjectHashMap;
import org.roaringbitmap.RoaringBitmap;


/**
 * Created by denislavrov on 8/15/14.
 */
public class BitArrayMap implements BitArray2D{
    private TIntObjectHashMap<RoaringBitmap> container = new TIntObjectHashMap<>();

    public BitArrayMap(){

    }

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
        container = new TIntObjectHashMap<>();
    }

    public void addAll(BitArray2D b2){
        for (int y : b2.yValues()){
            for (int x : b2.xValues(y)){
                set(x, y, true);
            }
        }
    }

    public void removeAll(BitArray2D b2){
        for (int y : b2.yValues()){
            for (int x : b2.xValues(y)){
                if (get(x, y)) set(x, y, false);
            }
        }
    }

    public String toString(){
        return container.toString();
    }


    /* Unused function
    public void retainAll(BitArrayMap b2){
        for (int y : yValues()){
            for (int x : xValues(y)){
                if (!b2.get(x,y)) set(x, y, false);
            }
        }
    }
    */
    /* Testing purposes only
    public static void main(String[] args){
        RoaringBitmap r1 = new RoaringBitmap();
        RoaringBitmap r2 = new RoaringBitmap();
        r1.add(-2);
        r1.add(-3);
        r1.add(-7);
        r2.add(-2);
        r2.add(-3);
        r2.add(7);
        System.out.println(r1);
        System.out.println(r2);
        r1.and(r2);
        System.out.println(r1);
        System.out.println(r2);


    }
    */
}
