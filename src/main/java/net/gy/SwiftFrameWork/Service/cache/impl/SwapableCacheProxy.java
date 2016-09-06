package net.gy.SwiftFrameWork.Service.cache.impl;

import net.gy.SwiftFrameWork.Service.cache.entity.ICacheEntry;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by gy on 2016/9/6.
 */
public class SwapableCacheProxy<K,V> implements ICacheEntry<K,V>{

    private Map<K,String> SerailMap = new ConcurrentHashMap<>();
    private ICacheEntry<K,V> origin;
    private String path;

    @Override
    public boolean put(K key, V value) {
        return origin.put(key, value);
    }

    @Override
    public V get(K key) {
        V v = origin.get(key);
        if (v == null){
            if (SerailMap.get(key) != null){
                v = (V) Swapper.get(SerailMap.get(key));
                origin.put(key,v);
                SerailMap.remove(key);
            }
        }
        return v;
    }

    @Override
    public Set<K> getKeys() {
        return origin.getKeys();
    }

    @Override
    public V remove(K k) {
        return origin.remove(k);
    }

    @Override
    public void clear() {
        origin.clear();
    }

    @Override
    public void setlimit(long size) {
        origin.setlimit(size);
    }

    @Override
    public long compress() {
        return origin.compress();
    }

    @Override
    public void swapout() {
        for (K k:getKeys()){
            V value = origin.get(k);
            if (value == null)
                continue;
            String serialNo = Swapper.save((Serializable) value,path);
            if (serialNo == null)
                continue;
            SerailMap.put(k, serialNo);
            origin.remove(k);
        }
    }

    @Override
    public void swapin() {

    }

    @Override
    public ICacheEntry<K, V> getEntry() {
        return origin.getEntry();
    }
}
