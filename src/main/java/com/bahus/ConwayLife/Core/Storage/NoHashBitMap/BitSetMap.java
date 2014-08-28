package com.bahus.ConwayLife.Core.Storage.NoHashBitMap;

import com.bahus.ConwayLife.Core.CellHashLife.CellHashGenerator;
import com.bahus.ConwayLife.Core.Storage.Bounds;
import gnu.trove.map.hash.TIntObjectHashMap;

/**
 * Created by denislavrov on 8/24/14.
 */

public class BitSetMap implements BitArray2D {

    private TIntObjectHashMap<LongMappedSet> container = new TIntObjectHashMap<>();

    public BitSetMap(){

    }

    public BitSetMap(BitSetMap other){
        this.container = other.getContainer();
    }

    public boolean get(int x, int y){
        LongMappedSet check = container.get(y);
        return check != null && check.get(x);
    }

    public void set(int x, int y, boolean val){
        if (!container.containsKey(y)) container.put(y, new LongMappedSet());
        container.get(y).set(x,val);
    }

    public int[] yValues(){
        return container.keys();
    }

    public int[] xValues(int y){
        return container.get(y).returnValues();
    }

    public TIntObjectHashMap<LongMappedSet> getContainer(){
        return this.container;
    }

    public Bounds getBounds(){
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

    public short cell2Hash(int x,int y){
        short ret = 0;
        LongMappedSet checker;
        ret <<= 3;
        checker = container.get(y-1);
        ret |= (checker != null) ? checker.ret3bits(x) : 0;
        ret <<= 3;
        checker = container.get(y);
        ret |= (checker != null) ? checker.ret3bits(x) : 0;
        ret <<= 3;
        checker = container.get(y+1);
        ret |= (checker != null) ? checker.ret3bits(x) : 0;
        return ret;
    }

    public byte row5bitHash(int x, int y){
        LongMappedSet checker = container.get(y);
        return (checker != null) ? checker.ret5bits(x) : 0;
    }

    public Bounds getGrownBounds(){
        //adjust this variable to reduce memory leakage
        final int GROWSIZE = 300;
        Bounds bounds = getBounds();
        bounds.hx += GROWSIZE;
        bounds.hy += GROWSIZE;
        bounds.lx -= GROWSIZE;
        bounds.ly -= GROWSIZE;
        return bounds;
    }

    public void clear(){
        container = new TIntObjectHashMap<>();
    }

    public void cleanup(){
        for (int key : container.keys()){
            if (xValues(key).length == 0)
                container.remove(key);
        }
        System.gc();
    }

    public String toString(){
        return container.toString();
    }

    public static void main(String[] args) {
        CellHashGenerator gen = new CellHashGenerator();
        BitSetMap bsm = new BitSetMap();
        bsm.set(0,0,true);
        bsm.set(1,0,true);
        bsm.set(1,1,true);
        bsm.set(2,0,true);
        bsm.set(2,1,true);
        System.out.println(bsm.cell2Hash(1,1));
        System.out.println(gen.cellHashes.get(bsm.cell2Hash(1,1)));
    }
}
