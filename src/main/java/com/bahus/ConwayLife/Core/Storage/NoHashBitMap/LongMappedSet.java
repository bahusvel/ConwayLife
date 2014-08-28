package com.bahus.ConwayLife.Core.Storage.NoHashBitMap;

import gnu.trove.map.hash.TIntLongHashMap;
import gnu.trove.set.hash.TIntHashSet;

/**
 * Created by denislavrov on 8/24/14.
 */
public class LongMappedSet {
    private TIntLongHashMap container = new TIntLongHashMap(20,0.75f,0,0);
    //private TIntLongHashMap container = new TIntLongHashMap();
    TIntHashSet xVals  = new TIntHashSet();

    public void set(int x, boolean val){
        // TO OPTIMIZE use this less often it is already as efficient as it is feasible to do
        // Optimize your main loop dummy!
        if (val) xVals.add(x);
        else xVals.remove(x);

        int index;
        if (x<0) {
            index = x/63 - 1;
            x *= -1;
            x = 63 - x%63;
        }
        else {
            index = x / 63;
            x %= 63;
        }
        container.put(index, (container.get(index) & ~(1l << x)) | (val ? 1l << x : 0l));
    }

    public boolean get(int x){
        int index;
        if (x<0) {
            index = x/63 - 1;
            x *= -1;
            x = 63 - x%63;
        }
        else{
            index = x / 63;
            x %= 63;
        }
        return container.containsKey(index) && ((container.get(index) >> x) & 1l) == 1;
    }

    public byte ret5bits(int mx){
        int index1, index2;
        int sx = mx - 2;
        int ex = mx + 2;
        if (sx<0) {
            index1 = sx/63 - 1;
            sx *= -1;
            sx = 63 - sx%63;
        }
        else{
            index1 = sx / 63;
            sx %= 63;
        }

        if (ex<0) {
            index2 = ex/63 - 1;
        }
        else{
            index2 = ex / 63;
        }

        byte dst = 0;

        if (index1 == index2){
            dst ^= (container.get(index1) >> sx) & 31; // mask for first 5 bits
            return dst;
        }

        dst ^= ((container.get(index2) << (63-sx)) | ((container.get(index1) >> sx) & ((1 << (64-sx))-1))) & 31;


        return dst;
    }

    public byte ret3bits(int mx){
        // TODO NEEDS OPTIMISATION
        int index1, index2;
        int sx = mx - 1;
        int ex = mx + 1;
        if (sx<0) {
            index1 = sx/63 - 1;
            sx *= -1;
            sx = 63 - sx%63;
        }
        else{
            index1 = sx / 63;
            sx %= 63;
        }

        if (ex<0) {
            index2 = ex/63 - 1;
            ex *= -1;
            ex = 63 - ex%63;
        }
        else{
            index2 = ex / 63;
            ex %= 63;
        }

        byte dst = 0;

        if (index1 == index2){
            dst ^= (container.get(index1) >> sx) & 7;
            return dst;
        }

        dst ^= ((container.get(index2) << (ex == 1 ? 1 : (ex == 2 ? 0 : 2))) | ((container.get(index1) >> sx) & (ex == 2 ? 1 : 3))) & 7;

        return dst;
    }

    public int[] returnValues(){
        return xVals.toArray();
    }

    @Override
    public String toString() {
        return container.toString();
    }

    public static void main(String[] args) {
        LongMappedSet lms = new LongMappedSet();
        /*
        lms.set(-60,true);
        lms.set(-61,true);
        lms.set(-62,true);
        lms.set(-63,true);
        lms.set(-64,true);
        lms.set(-65,true);
        */
        lms.set(3,true);
        lms.set(2,true);
        lms.set(1,true);
        lms.set(0,true);
        //lms.set(-1,true);
        lms.set(-2,true);

        /*
        lms.set(-30,true);
        lms.set(-31,true);
        lms.set(-32,true);

        lms.set(60,true);
        lms.set(61,true);
        lms.set(62,true);
        lms.set(63,true);
        lms.set(64,true);
        */

        System.out.println(lms.ret5bits(1));


        for (int i : lms.returnValues()){
            System.out.println(i);
        }


    }


}
