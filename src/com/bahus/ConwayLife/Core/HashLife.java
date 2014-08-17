package com.bahus.ConwayLife.Core;


import com.bahus.ConwayLife.Core.Storage.Bounds;
import com.bahus.ConwayLife.Core.Storage.Point;
import com.bahus.ConwayLife.Core.Storage.PointSet;

/**
 * Created by denislavrov on 8/12/14.
 */
public class HashLife implements GenericLife{
    private Bounds bounds = new Bounds();
    private PointSet cells = new PointSet();

    public boolean[][] getBoard(){
        bounds.updateBounds(cells);
        boolean[][] ret = new boolean[bounds.hx - bounds.lx +1][bounds.hy - bounds.ly +1];
        for (int y = bounds.ly; y <= bounds.hy; y++){
            for (int x = bounds.lx; x <= bounds.hx; x++) {
                ret[(x - bounds.lx)][(y-bounds.ly)] = cells.contains(x, y);
            }
        }
        return ret;
    }

    public Bounds getBounds(){
        bounds.updateBounds(cells);
        System.out.println(bounds);
        return bounds;
    }

    public void reset(){
        cells.clear();
    }

    public String display(){
        bounds.updateBounds(cells);
        String ret = "";
        ret += (StringMultiply("+-", bounds.hx - bounds.lx) + "+\n");
        for (int y = bounds.ly; y <= bounds.hy; y++){
            for (int x = bounds.lx; x <= bounds.hx; x++){
                ret += ("+" + (cells.contains(x, y) ? "X" : "0") + ((x == bounds.hx) ? "+" : ""));
            }
            ret += "\n";
            ret += (StringMultiply("+-", bounds.hx - bounds.lx) + "+\n");
        }
    return ret;
    }

    public void toggleCell(int x, int y){
        if (cells.contains(x,y)) cells.remove(x,y);
        else cells.add(new Point(x,y));
    }

    public String StringMultiply(String str, int times){
        String ret = str;
        for (int i = 0; i < times; i++){
            ret += str;
        }
    return ret;
    }

    public void nextGen(){
        PointSet gen = new PointSet();
        for (Point p : cells){
            // compute cell surroundings
            // add them to common set
            gen.addAll(getPerimeter(p));
        }
        // do the game of life computation
        PointSet bufferadd = new PointSet();
        PointSet buffersub = new PointSet();
        for (Point p : gen){
            PointSet intersect = getPerimeter(p);
            intersect.retainAll(cells);

            switch (intersect.size()){
                case 3:
                    bufferadd.add(p);
                    break;
                case 4:
                    break;
                default:
                    buffersub.add(p);
            }

        }
        cells.addAll(bufferadd);
        cells.removeAll(buffersub);
    }

    private PointSet getPerimeter(Point p){
        PointSet retPer = new PointSet();
        // add point itself
        retPer.add(p);
        // add the perimeter
        retPer.add(new Point(p,-1,-1));
        retPer.add(new Point(p,-1, 0));
        retPer.add(new Point(p,-1, 1));
        retPer.add(new Point(p, 0,-1));
        retPer.add(new Point(p, 0, 1));
        retPer.add(new Point(p, 1,-1));
        retPer.add(new Point(p, 1, 0));
        retPer.add(new Point(p, 1, 1));
        return retPer;
    }



}
