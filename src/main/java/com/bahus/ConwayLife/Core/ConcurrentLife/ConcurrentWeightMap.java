package com.bahus.ConwayLife.Core.ConcurrentLife;

import com.bahus.ConwayLife.Core.Storage.Bounds;
import com.carrotsearch.hppc.LongByteOpenHashMap;

/**
 * Created by denislavrov on 8/24/14.
 */
public class ConcurrentWeightMap {
    //private ConcurrentHashMap<Long,Byte>  container;
    private LongByteOpenHashMap container;
    public int width, height;

    public Bounds getBounds() {
        return bounds;
    }

    private Bounds bounds;

    public ConcurrentWeightMap(Bounds bounds){
        this.bounds = bounds;
        //System.out.println(bounds);
        this.height = (bounds.hy-bounds.ly+1);
        this.width = (bounds.hx-bounds.lx+1);
        container = new LongByteOpenHashMap(width*height);
    }

    public synchronized void inc(int x, int y){
        container.putOrAdd(((y - bounds.ly) * width + (x - bounds.lx)),(byte)1,(byte)1);
    }

    public byte get(int x, int y){
        return container.get((y- bounds.ly)*width + (x-bounds.lx));
    }

    public byte get(long point){
        return container.get(point);
    }

    public long[] keys(){
        return container.keys;

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

    public void clear(){
        container.clear();
    }

}
