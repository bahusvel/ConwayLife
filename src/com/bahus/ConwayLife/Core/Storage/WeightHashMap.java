package com.bahus.ConwayLife.Core.Storage;

import gnu.trove.map.hash.TLongByteHashMap;

/**
 * Created by denislavrov on 8/18/14.
 */
public class WeightHashMap {
    private TLongByteHashMap container;
    public int width, height;

    public Bounds getBounds() {
        return bounds;
    }

    private Bounds bounds;

    public WeightHashMap(Bounds bounds){
        this.bounds = bounds;
        //System.out.println(bounds);
        this.height = (bounds.hy-bounds.ly+1);
        this.width = (bounds.hx-bounds.lx+1);
        container = new TLongByteHashMap((width)*(height));
    }

    public synchronized void inc(int x, int y){
        container.adjustOrPutValue((y-bounds.ly)*width + (x-bounds.lx),(byte)1,(byte)1);
    }

    public byte get(int x, int y){
        return container.get((y- bounds.ly)*width + (x-bounds.lx));
    }

    public byte get(long point){
        return container.get(point);
    }

    public long[] keys(){
        return container.keys();
    }

    public int getX(long point){
        return (int)(point%width + bounds.lx);
    }

    public int getY(long point){
        return (int)(point/width + bounds.ly);
    }

    public void clearPoint(long point){
        container.remove(point);
    }

    public String toString(){
        return container.toString();
    }

    public void clear(){
        container.clear();
    }

    public static void main(String[] args){
        WeightHashMap gen = new WeightHashMap(new Bounds(-20, -20, 10, 10));

    }


}
