package com.bahus.ConwayLife.Core.Storage;

import java.util.Random;
import java.util.Scanner;

/**
 * Created by denislavrov on 8/16/14.
 */
public class TestingStorage {
    final static int SIZE = 100000;

    public static int randInt(int min, int max) {

        // NOTE: Usually this should be a field rather than a method
        // variable so that it is not re-seeded every call.
        Random rand = new Random();

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }

    public static void main(String[] args){
        BitArrayMap bar = new BitArrayMap();
        Scanner s = new Scanner(System.in);
        s.nextLine();
        long stime = System.nanoTime();

        for (int y = 0; y < SIZE; y++){
            bar.set(randInt(0, SIZE), y, randInt(0, 1) == 1);
        }
        System.out.printf("Creating %s of size: %d took %d microseconds.\n", bar.getClass(), SIZE, (System.nanoTime() - stime) / 1000);
        s.nextLine();
        stime = System.nanoTime();
        for (int y = 0; y < SIZE; y++){
            bar.set(randInt(0, SIZE), y, randInt(0, 1) == 1);
        }
        System.out.printf("Setting %s of size: %d took %d microseconds.\n", bar.getClass(), SIZE, (System.nanoTime() - stime) / 1000);
        s.nextLine();
        stime = System.nanoTime();
        for (int y = 0; y < SIZE; y++){
            bar.get(randInt(0, SIZE), y);
        }
        System.out.printf("Reading %s of size: %d took %d microseconds.\n", bar.getClass(), SIZE, (System.nanoTime() - stime) / 1000);
        s.nextLine();



    }
}
