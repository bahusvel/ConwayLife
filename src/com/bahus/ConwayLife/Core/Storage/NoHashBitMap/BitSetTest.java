package com.bahus.ConwayLife.Core.Storage.NoHashBitMap;

import java.util.BitSet;

/**
 * Created by denislavrov on 8/24/14.
 */
public class BitSetTest {
    public BitSet javabitset = new BitSet();
    public com.carrotsearch.hppc.BitSet hppcbitset = new com.carrotsearch.hppc.BitSet();

    public static void main(String[] args) {
        BitSetTest bst = new BitSetTest();
        /*bst.hppcbitset.set(10);
        bst.hppcbitset.set(10000000);
        bst.hppcbitset.flip(20);
        for (long i : bst.hppcbitset.bits){
            System.out.println(i);
        }
        */
        bst.javabitset.set(-10);
        bst.javabitset.set(20);
        for (long i : bst.javabitset.toLongArray()){
            System.out.println(i);
        }

    }

}
