package com.bahus.ConwayLife.Core.CellHashLife;

import gnu.trove.map.hash.TShortByteHashMap;

/**
 * Created by denislavrov on 8/25/14.
 */
public class CellHashGenerator {
    public TShortByteHashMap cellHashes = new TShortByteHashMap(210,0.75f,(short)3,(byte)3);

    public CellHashGenerator(){
        generateHashes();
    }


    public void generateHashes(){
        short key = 0;
        while (key < 511){
            key++;
            short v = key;
            byte c = (byte)0; // c accumulates the total bits set in v
            for (; v != 0; c++) v &= v - 1; // clear the least significant bit set
            if (c == 3) cellHashes.put(key,(byte)1);
            else if (c==4) cellHashes.put(key,(byte)0);
        }

    }

    public static void main(String[] args) {
        long stime = System.nanoTime();
        CellHashGenerator gen = new CellHashGenerator();
        System.out.printf("It took %d microseconds\n", (System.nanoTime()-stime)/1000);
        System.out.println(gen.cellHashes.size());
    }

}
