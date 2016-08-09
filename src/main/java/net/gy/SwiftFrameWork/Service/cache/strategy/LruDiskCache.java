package net.gy.SwiftFrameWork.Service.cache.strategy;

import net.gy.SwiftFrameWork.Service.cache.IDiskCache;

/**
 * Created by gy on 2015/11/5.
 */

public class LruDiskCache implements IDiskCache {
    @Override
    public boolean put(Object key, Object value) {
        return false;
    }

    @Override
    public void remove(Object o) {

    }

    @Override
    public Object get(Object key) {
        return null;
    }

    @Override
    public void clear() {

    }
}
