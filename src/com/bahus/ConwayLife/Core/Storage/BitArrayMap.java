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

    public TIntObjectHashMap<RoaringBitmap> getContainer(){
        return container;
    }

    public RoaringBitmap getY(int y){
        if (!container.containsKey(y)) container.put(y, new RoaringBitmap());
        return container.get(y);
    }

    public boolean[][] getBox(int xmin, int ymin, int xmax, int ymax){
        boolean[][] ret = new boolean[xmax - xmin + 1][ymax - ymin + 1];
        for (int y = 0; y < (ymax - ymin); y++){
            for (int x : container.get(y).toArray()) ret[x][y] = true;
        }
        return ret;
    }

    public boolean[][] getBox(Bounds b){
        boolean[][] ret = new boolean[(int)(b.hx - b.lx + 2)][(int)(b.hy - b.ly + 2)];
        for (int y : container.keys()){
            for (int x : getY(y).toArray()){
                ret[(int)(x - b.lx)][(int)(y-b.ly)] = true;
            }
        }
        return ret;
    }

    public Bounds getBounds(){
        int ymin = 0, ymax = 0, xmin = 0, xmax = 0;
        boolean firstRun = true;
        for (int y : container.keys()){
            if (firstRun) ymin = y;
            if (y < ymin) ymin = y;
            if (y > ymax) ymax = y;
            //System.out.println(getY(y));
            for (int x : getY(y).toArray()){
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

    public int size(){
        int size = 0;
        for (int i : container.keys()) size += getY(i).getCardinality();
        //System.out.println(size);
        return size;
    }

    public void clear(){
        container = new TIntObjectHashMap<>();
    }

    public void addAll(BitArrayMap b2){
        for (int y : b2.getContainer().keys()){
            for (int x : b2.getY(y).toArray()){
                set(x, y, true);
            }
        }
    }

    public void removeAll(BitArrayMap b2){
        for (int y : b2.getContainer().keys()){
            for (int x : b2.getY(y).toArray()){
                if (get(x, y)) set(x, y, false);
            }
        }
    }

    public void retainAll(BitArrayMap b2){
        for (int y : container.keys()){
            for (int x : getY(y).toArray()){
                if (!b2.get(x,y)) set(x, y, false);
            }
        }
    }

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
}
