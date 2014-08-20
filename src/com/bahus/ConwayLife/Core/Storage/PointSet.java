package com.bahus.ConwayLife.Core.Storage;

import gnu.trove.list.array.TIntArrayList;
import gnu.trove.set.hash.THashSet;
import gnu.trove.set.hash.TIntHashSet;


/**
 * Created by denislavrov on 8/13/14.
 */
public class PointSet extends THashSet<Point> implements BitArray2D{
    /*
    public boolean removeAll(PointSet ps){
        THashSet<Point> subbuffer = new THashSet<>();
        for (Point p : this){
            for (Point pt : ps){
                if ((p.x == pt.x) && (p.y == pt.y)){
                    subbuffer.add(p);
                    break;
                }
            }
        }
        super.removeAll(subbuffer);
        return true;
    }


    public boolean addAll(PointSet ps) {
        THashSet<Point> addbuffer = new THashSet<>();
        for (Point pt : ps){
            boolean add = true;
            for (Point p : this){
                if ((p.x == pt.x) && (p.y == pt.y)){
                    add = false;
                    break;
                }
            }
            if (add) addbuffer.add(pt);
        }
        super.addAll(addbuffer);
        return true;
    }

    public boolean retainAll(PointSet ps) {
        THashSet<Point> subbuffer = new THashSet<>();
        for (Point p : this){
            boolean retain = false;
            for (Point pt : ps){
                if ((p.x == pt.x) && (p.y == pt.y)){
                    retain = true;
                    break;
                }
            }
            if (!retain) subbuffer.add(p);
        }
        super.removeAll(subbuffer);
        return true;
    }
    */

    private boolean contains(long x, long y){
        for (Point p : this){
            if ((p.x == x) && (p.y == y)) return true;
        }
        return false;
    }



    private boolean remove(long x, long y){
        for (Point p : this){
            if ((p.x == x) && (p.y == y)){
                this.remove(p);
                return true;
            }
        }
        return false;
    }


    @Override
    public boolean get(int x, int y) {
        return this.contains(x,y);
    }

    @Override
    public void set(int x, int y, boolean val) {
        if (val) this.add(new Point(x, y));
        else this.remove(x, y);
    }

    @Override
    public int[] yValues() {
        TIntHashSet tmp = new TIntHashSet();
        for (Point p : this){
            tmp.add(p.y);
        }
        return tmp.toArray();
    }

    @Override
    public int[] xValues(int y) {
        TIntHashSet tmp = new TIntHashSet();
        for (Point p : this){
            if (p.y == y) tmp.add(p.x);
        }
        return tmp.toArray();
    }

    @Override
    public Bounds getBounds() {
        int xmin = 0, xmax = 0, ymin = 0, ymax = 0;
        boolean firstRun = true;

        for (Point p : this){
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
        return new Bounds(xmin,ymin,xmax,ymax);
    }

    @Override
    public Bounds getGrownBounds() {
        final int GROWSIZE = 200;
        Bounds bounds = getBounds();
        bounds.hx += GROWSIZE;
        bounds.hy += GROWSIZE;
        bounds.lx -= GROWSIZE;
        bounds.ly -= GROWSIZE;
        return bounds;
    }
}
