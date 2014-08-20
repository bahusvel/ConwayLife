package com.bahus.ConwayLife.Core.Storage;

/**
 * Created by denislavrov on 8/12/14.
 */
public class Bounds {
    public int lx;
    public int ly;
    public int hx;
    public int hy;

    public Bounds() {

    }

    public Bounds(int xmin, int ymin, int xmax, int ymax) {
        lx = xmin;
        ly = ymin;
        hx = xmax;
        hy = ymax;
    }

    public void updateBounds(Bounds b){
        lx = b.lx;
        hx = b.hx;
        ly = b.ly;
        hy = b.hy;
    }

    public boolean smaller(Bounds b){
        return (hx <= b.hx) || (hy <= b.hy) || (ly >= b.ly) || (lx >= b.lx);
    }

    public String toString(){
        return "Xmin: " + lx + " Xmax: " + hx + " Ymin: " + ly + " Ymax: " + hy;
    }

}
