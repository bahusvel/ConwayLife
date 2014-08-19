package com.bahus.ConwayLife.Core;


import com.bahus.ConwayLife.Core.Storage.*;

/**
 * Created by denislavrov on 8/12/14.
 */
public class HashLife implements GenericLife{
    private Bounds bounds = new Bounds();
    private BitArray2D cells = new PointSet();
    private WeightHashMap gen = new WeightHashMap(cells.getGrownBounds());


    public Bounds getBounds(){
        bounds.updateBounds(cells.getBounds());
        //System.out.println(bounds);
        return bounds;
    }

    public void reset(){
        cells.clear();
    }

    public void toggleCell(int x, int y){
        if (cells.get(x,y)) cells.set(x,y, false);
        else cells.set(x, y, true);
    }


    public void nextGen(){

        // before creating new gen container check if the existing one is capable to hold data
        if (gen.getBounds().smaller(cells.getBounds())){
            gen = new WeightHashMap(cells.getGrownBounds());
        }

        for(int y : cells.yValues()){
            for (int x : cells.xValues(y)){
                gen.inc(x, y);
                gen.inc(x-1,y-1);
                gen.inc(x-1,y);
                gen.inc(x-1,y+1);
                gen.inc(x  ,y-1);
                gen.inc(x  ,y+1);
                gen.inc(x+1,y-1);
                gen.inc(x+1, y);
                gen.inc(x+1,y+1);
            }
        }

        for (long point : gen.keys()){
            int x = gen.getX(point);
            int y = gen.getY(point);

            switch (gen.get(point)){
                case 3:
                    cells.set(x,y, true);
                    break;
                case 4:
                    break;
                default:
                    cells.set(x,y, false);
                    break;

            }

        }
        // clear the generated data
        gen.clear();
    }

    @Override
    public BitArray2D getCells() {
        return cells;
    }

}
