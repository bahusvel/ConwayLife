package com.bahus.ConwayLife.Core.TrueHashLife;

/**
 * Created by denislavrov on 8/22/14.
 */
public class Test {
    public static void main(String[] args) {
        IntBooleanArray tmp;
        LifeAlgo algo = new HashlifeAlgo();
        algo.setState(new HashlifeState(new int[10][10]));
        algo.toggleCellAt(0, 0);
        algo.toggleCellAt(1, 0);
        algo.toggleCellAt(1, 2);
        algo.toggleCellAt(3, 1);
        algo.toggleCellAt(4, 0);
        algo.toggleCellAt(5, 0);
        algo.toggleCellAt(6, 0);
/*
        tmp = new IntBooleanArray(algo.saveToArray());

        for (boolean[] y : tmp.getContainer()){
            for (boolean x : y) System.out.print(x);
            System.out.println("");
        }
*/
        System.out.println("");
        algo.evolve(1);

        for (int[] y : algo.saveToArray()){
            for (int x : y) System.out.print(x);
            System.out.println("");
        }

    }
}
