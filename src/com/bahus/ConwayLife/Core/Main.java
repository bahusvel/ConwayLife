package com.bahus.ConwayLife.Core;

public class Main {

    public static void main(String[] args) {
	    HashLife nLife = new HashLife();
        nLife.toggleCell(1,1);
        nLife.toggleCell(2,1);
        nLife.toggleCell(2,3);
        nLife.toggleCell(4,2);
        nLife.toggleCell(5,1);
        nLife.toggleCell(6,1);
        nLife.toggleCell(7,1);
        nLife.display();
        for(int i = 0; i < 10; i++){
            nLife.nextGen();
        }
        nLife.display();

    }
}
