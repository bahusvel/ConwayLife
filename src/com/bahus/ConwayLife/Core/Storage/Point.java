package com.bahus.ConwayLife.Core.Storage;

/**
 * Created by denislavrov on 8/12/14.
 */
public class Point {
    public long x;
    public long y;

    public Point(long x, long y){
        this.x = x;
        this.y = y;
    }

    public Point(Point p, long dx, long dy){
        this.x = p.x + dx;
        this.y = p.y + dy;
    }


}
