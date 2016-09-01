package net.gy.SwiftFrameWork.Service.cache.control;

import net.gy.SwiftFrameWork.Service.cache.IRamCache;
import net.gy.SwiftFrameWork.config.configs;

/**
 * Created by gy on 2015/11/5.
 */
public class RamCacheControl<K, V> implements IRamCache<K, V> {

    private IRamCache cacheStrategy;

    @Override
    public boolean put(K key, V value) {
        if (configs.isrelease)
            ;//
        cacheStrategy.put(key, value);
        return false;
    }

    @Override
    public V get(K key) {
        V v;
        v = (V) cacheStrategy.get(key);
        return v;
    }

    @Override
    public void remove(K k) {

    }

    @Override
    public void clear() {
        cacheStrategy.clear();
    }

    @Override
    public void setlimit(long size) {
        cacheStrategy.setlimit(size);
    }
}
