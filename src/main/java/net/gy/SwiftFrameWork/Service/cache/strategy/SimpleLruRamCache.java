package net.gy.SwiftFrameWork.Service.cache.strategy;

import net.gy.SwiftFrameWork.Service.cache.IRamCache;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by gy on 2015/11/16.
 */
public class SimpleLruRamCache<K, V> implements IRamCache<K, V> {

    private Map<K, V> cache;
    private long size = 0;
    private long limit = 1000000;
    private K key;
    private V value;

    public SimpleLruRamCache() {
        cache = Collections.synchronizedMap(new LinkedHashMap<K, V>(10, 1.5f, true));
    }

    public SimpleLruRamCache(int size, float grow) {
        cache = Collections.synchronizedMap(new LinkedHashMap<K, V>(size, grow, true));
    }

    private void checkSize() {
        if (size > limit) {
            Iterator<Map.Entry<K, V>> iter = cache.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry<K, V> entry = iter.next();
                size -= getSizeInBytes(entry.getValue());
                iter.remove();
                if (size <= limit)
                    break;
            }

        }
    }

    private long getSizeInBytes(V value) {
        return 0;
    }


    @Override
    public boolean put(K key, V value) {
        try {
            if (cache.containsKey(key))
                size -= getSizeInBytes(cache.get(key));
            cache.put(key, value);
            size += getSizeInBytes(value);
            checkSize();
            return true;
        } catch (NullPointerException e) {
            return false;
            // TODO: handle exception
        }
    }

    @Override
    public V get(K key) {
        try {
            if (!cache.containsKey(key))
                return null;
            else
                return cache.get(key);
        } catch (NullPointerException e) {
            // TODO: handle exception
            return null;
        }
    }

    @Override
    public void clear() {
        cache.clear();
    }

    @Override
    public void setlimit(long size) {
        this.limit = limit;
    }
}
