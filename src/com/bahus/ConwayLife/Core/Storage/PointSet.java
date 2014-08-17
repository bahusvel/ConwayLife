package com.bahus.ConwayLife.Core.Storage;

import gnu.trove.set.hash.THashSet;

/**
 * Created by denislavrov on 8/13/14.
 */
public class PointSet extends THashSet<Point> /*implements BitArray2D*/{

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

    public boolean contains(long x, long y){
        for (Point p : this){
            if ((p.x == x) && (p.y == y)) return true;
        }
        return false;
    }



    public boolean remove(long x, long y){
        for (Point p : this){
            if ((p.x == x) && (p.y == y)){
                this.remove(p);
                return true;
            }
        }
        return false;
    }


    //@Override
    public boolean get(int x, int y) {
        return this.contains(x,y);
    }

    //@Override
    public void set(int x, int y, boolean val) {
        if (val) this.add(new Point(x, y));
        else this.remove(x, y);
    }
}
