package com.bahus.ConwayLife.Core.Storage.SmallEntityHash;

import gnu.trove.map.hash.TLongLongHashMap;

/**
 * Created by denislavrov on 8/22/14.
 */
public class SmallHashManager {
    private TLongLongHashMap container = new TLongLongHashMap();

    public boolean check(long key){
        return container.containsKey(key);
    }

    public long get(long key){
        return container.get(key);
    }

    public void set(long key, long value){
        container.put(key, value);
    }

}
