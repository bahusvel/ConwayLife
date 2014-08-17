package com.bahus.ConwayLife.Core.Storage;

import com.bahus.ConwayLife.Core.Storage.Point;
import com.bahus.ConwayLife.Core.Storage.PointSet;

/**
 * Created by denislavrov on 8/12/14.
 */
public class Bounds {
    public long lx;
    public long ly;
    public long hx;
    public long hy;

    public Bounds() {

    }

    public Bounds(long xmin, long ymin, long xmax, long ymax) {
        lx = xmin;
        ly = ymin;
        hx = xmax;
        hy = ymax;
    }

    public void updateBounds(PointSet cells){
        long xmin = 0, xmax = 0, ymin = 0, ymax = 0;
        boolean firstRun = true;

        for (Point p : cells){
            if (firstRun) {
                xmin = p.x;
                ymin = p.y;
                firstRun = false;
            }
            if (p.x < xmin) xmin = p.x;
            if (p.y < ymin) ymin = p.y;
            if (p.x > xmax) xmax = p.x;
            if (p.y > ymax) ymax = p.y;
        }
        lx = xmin;
        hx = xmax;
        ly = ymin;
        hy = ymax;
    }

    public void updateBounds(Bounds b){
        lx = b.lx;
        hx = b.hx;
        ly = b.ly;
        hy = b.hy;
    }

    public String toString(){
        return "Xmin: " + lx + " Xmax: " + hx + " Ymin: " + ly + " Ymax: " + hy;
    }

}
