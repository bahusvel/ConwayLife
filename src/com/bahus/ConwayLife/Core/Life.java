package com.bahus.ConwayLife.Core;

import java.util.ArrayList;

/**
 * Created by denislavrov on 8/12/14.
 */
class Life {
    private ArrayList<ArrayList<Boolean>> board1 = new ArrayList<>();
    private ArrayList<ArrayList<Boolean>> board2 = new ArrayList<>();
    private long cGen = 0;

    public void nextGen(){

    }

    public ArrayList<ArrayList<Boolean>> getCurrentBoard(){
        if (cGen % 2 == 0) return board1;
        else return board2;
    }

    public void setCurrentBoard(ArrayList<ArrayList<Boolean>> board){
        if (cGen % 2 == 0) board1 = board;
        else board2 = board;
    }

    private Boolean nextCellState(int y, int x){
        ArrayList<ArrayList<Boolean>> cboard;
        byte sum = 0;
        if (cGen % 2 == 0)cboard = board1;
        else cboard = board2;

        try{

        }
        catch(IndexOutOfBoundsException e){

        }

        return true;
    }
 }
