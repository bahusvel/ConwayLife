package com.bahus.ConwayLife.Core.Storage.SmallEntityHash;

import com.bahus.ConwayLife.Core.Storage.Bounds;

/**
 * Created by denislavrov on 8/22/14.
 */
public class Entity {
    int x, y;
    Bounds gBounds;
    boolean[][] cells; // allocated with regards to size of entity. smaller entities die faster


    public void nextGen(){
        // can COMPUTE (not find hash) the entity here

    }

    public boolean isHashEntity(){
        // put in all the rules to check if entity can be hashed with hash manager
        return false;
    }

    public long cells2Long(){
        return 0;
    }

}
