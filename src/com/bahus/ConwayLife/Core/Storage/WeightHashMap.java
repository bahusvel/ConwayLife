package com.bahus.ConwayLife.Core.Storage;

import gnu.trove.map.hash.TLongByteHashMap;

/**
 * Created by denislavrov on 8/18/14.
 */
public class WeightHashMap {
    private TLongByteHashMap container;
    public int width, height;
    private Bounds bounds;

    public WeightHashMap(Bounds bounds){
        this.bounds = bounds;
        //System.out.println(bounds);
        this.height = (bounds.hy-bounds.ly+1);
        this.width = (bounds.hx-bounds.lx+1);
        container = new TLongByteHashMap((width)*(height));
    }

    public void inc(int x, int y){
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

    public String toString(){
        return container.toString();
    }

    public static void main(String[] args){
        WeightHashMap gen = new WeightHashMap(new Bounds(-20, -20, 10, 10));
        gen.inc(-5, 5);
        gen.inc(-6,-5);
        gen.inc(10,-20);
        gen.inc(-5,-5);
        System.out.println(gen.width);
        for (long i : gen.keys()){
            System.out.println(i + " " + gen.getX(i) + " " + gen.getY(i));
        }
        System.out.println();
    }


}
