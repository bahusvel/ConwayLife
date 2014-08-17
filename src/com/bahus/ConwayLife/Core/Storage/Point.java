package com.bahus.ConwayLife.Core.Storage;

/**
 * Created by denislavrov on 8/12/14.
 */
public class Point {
    public int x;
    public int y;

    public Point(int x, int y){
        this.x = x;
        this.y = y;
    }

    public Point(Point p, int dx, int dy){
        this.x = p.x + dx;
        this.y = p.y + dy;
    }


}
